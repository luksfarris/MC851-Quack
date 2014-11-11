package quack;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import service.CookieHelper;

public final class ServerImpl implements Server {

	SessionTable sessionTable = null; // Conjunto de sessões abertas.
	Database database = null; // Conexão com a base de dados persistente.
	// As tabelas abaixo são cópias na memória dos objetos na base de dados.
	UserTable userTable = null; // Conjuto de usuários cadastrados.

	private long nextUserId;
	long numUsers = 0; // Número de usuários na rede {Quack}.
	long numContacts = 0; // Número total de contatos criados (incluindo
							// inativos).
	long numMessages = 0; // Número total de mensagens postadas (incuindo
							// re-postagens).
	long numSessions = 0; // Número total de sessões abertas no momento.

	HTML html; // Cria paginas html.

	// ??{ O s procedimentos a seguir deveriam construir e devolver
	// a construir página HTML de resultado adequada. }??

	public void initialize(String dbLoginName, String dbName, String dbPassword) {
		// Conecta com a base de dados persitente e carrega na memória:
		this.database = new DatabaseImpl();
		this.database.initialize(dbLoginName, dbName, dbPassword);

		// Cria a tabela de sessões abertas, vazia:
		this.sessionTable = new SessionTableImpl();
		this.sessionTable.initialize();

		// Cria a tabela de usuários, vazia:
		this.userTable = new UserTableImpl();
		this.userTable.initialize();
		
		this.loadDatabase();

		// Inicializa o criador de paginas html:
		html = new HTMLImpl();
		html.initialize(this);
	}

	private void loadDatabase()
	// Carrega a base de dados {this.database} na memória, criando os objetos
	// {User,Message,Contact} e ligando-os entre si. Supõe que a conexão com o
	// servidor da base de dados já foi estabelecida.
	{
		try {
			this.database.getConnection();
			ResultSet rs = this.database.getStatement("SELECT * FROM user;").executeQuery();
			while(rs.next()){
				User u = new UserImpl();
				if(u.initialize(rs.getString("login_name"), 
						rs.getString("email"),
						rs.getString("full_name"),
						rs.getString("password"),
						rs.getLong("id"))){
					
					ResultSet rs2 = this.database.getStatement("SELECT * FROM message WHERE user_id=" 
					+ String.valueOf(u.getDbIndex())).executeQuery();
					while(rs2.next()){
						Message m = new MessageImpl();
						if(m.initialize(rs2.getString("body"), u) == false)
							System.out.println("Erro ao carregar mensagens");
						else
							u.addMessage(m);	
					}
					this.userTable.add(u);
					}
				
				else {
					System.out.println("Problema no carregamento do usuário!");
				}
				
				}	
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void processHomePageReq(HttpServletRequest request,
			HttpServletResponse response, ServletContext context) throws IOException {
		response.getWriter().println(html.homePage());
		return;
	}

	public void processRegistrationReq(HttpServletRequest request,
			HttpServletResponse response, ServletContext context) throws IOException {

		PrintWriter out = null;
		System.out.println(request.getParameter(("username")));
		//Verifica se já existe usuário com esse username:
		User user = this.userTable.getUserByLogin(request.getParameter("username"), "");
		if (user != null) {
			out = response.getWriter();  
			response.setContentType("text/html");  
			out.println("<script type=\"text/javascript\">");  
			out.println("history.back(alert('Este nome de usuario ja existe'));");  
			out.println("</script>");		
			System.out.println("Ja existe user com esse nome");
			}
		
		else{
			//Verifica se já existe usuário com esse email:
			user = this.userTable.getUserByEmail(request.getParameter("email"));
			if (user != null) {
				out = response.getWriter();  
				response.setContentType("text/html");  
				out.println("<script type=\"text/javascript\">");  
				out.println("history.back(alert('Ja existe uma conta com este email'));");  
				out.println("</script>");
				System.out.println("Ja existe user com esse email");
			}
			else{
				// Cria o usuário e acrescenta à tabela:
				user = new UserImpl();
				if (!user.initialize(request.getParameter("username"), request.getParameter("email"), 
						request.getParameter("fullName"), request.getParameter("password"), 1)) { //MUDAR dbIndex!
					response.setContentType("text/html");  
					out.println("<script type=\"text/javascript\">");  
					out.println("history.back(alert('Falha ao criar user'));");  
					out.println("</script>");				}
				else{
				
					try {
						DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						this.database.getConnection();
						this.database.getStatement("INSERT INTO user (id, login_name, full_name, email, password, created)"
								+ "VALUES ("+user.getDbIndex()+",'"+user.getLoginName()+
								"','"+user.getFullName()+"','"+
								user.getEmail()+"','"+request.getParameter("password")+"','"
								+ dateFormat.format(new Date(user.getCreationTime()*1000))+
								"');").execute();
						this.database.commit();
						
						this.userTable.add(user);
					
						System.out.println("User inserido na tabela");
					} catch (ClassNotFoundException e) {
						
						e.printStackTrace();
					} catch (SQLException e) {
						e.printStackTrace();
					}
					response.sendRedirect("/Quack/loginpage.jsp");
				}
			}
		}
	}

	public void processLoginReq(HttpServletRequest request,
			HttpServletResponse response, ServletContext context) throws IOException, ServletException {

		// pega os dados da sessão
		HttpSession requestSession = request.getSession();

		String login = request.getParameter("login");
		String password = request.getParameter("password");
		boolean remember = "true".equals(request.getParameter("remember"));
		
		User user = this.userTable.getUserByLogin(login, password);
		// se o usuario acabou de fazer login
		if (user != null) {
			//request.login(login, password);
			request.setAttribute(CookieHelper.LOGGED_USER, user);
			request.getSession().setAttribute("user", user);
			if (remember) {
				String cookie = UUID.randomUUID().toString();
		        Session session = new SessionImpl();
		        session.open(user, cookie);
		        sessionTable.add(session);
		        CookieHelper.addCookie(response, CookieHelper.COOKIE_NAME, cookie, CookieHelper.COOKIE_AGE);
			} else {
				CookieHelper.removeCookie(response, CookieHelper.COOKIE_NAME);
			}
			response.sendRedirect("/Quack/UserPage.jsp");
		} else {
			// usuario invalido, mostra erro.
			response.setContentType("text/html");
			response.getWriter().println("<script type=\"text/javascript\">");  
			response.getWriter().println("history.back(alert('Falha ao realizar login'));");  
			response.getWriter().println("</script>");
		}

		return;
	}

	public void processLogoutReq(HttpServletRequest request,
			HttpServletResponse response, ServletContext context) throws IOException {
		
		// TODO: implementar o logout
		
		return;
	}

	public String processShowUserProfileReq(HttpServletRequest request,
			HttpServletResponse response, ServletContext context) throws IOException, ServletException{
		// Obtém a sessão:
		
		/*Session session = null; // Current session or {null}.
		User source = null; // Session owner or {null}.
		if (!cookie.equals("")) {
			session = this.sessionTable.getSessionByCookie(cookie);
			if (session == null) {
				return html.errorPage("no session with this cookie.");
			}
			source = session.getUser();
		}
		User target = this.userTable.getUserByLogin(loginName, "SENHA_AQUI");
		if (target == null) {
			return html.errorPage("no such user.");
		}
		return html.userProfilePage(cookie, source, target);*/
		return "";
	}

	public String processShowPostedMessagesReq(String cookie, String loginName,
			String startTime, String endTime, int maxN) {
		List<Message> messages;
		// Obtém a sessão:
		Session session = this.sessionTable.getSessionByCookie(cookie);
		if (session == null) {
			return html.errorPage("no session with this cookie.");
		}

		// Obtem o autor das mensagens procuradas
		User target = this.userTable.getUserByLogin(loginName, "");
		if (target == null) {
			return html.errorPage("no such user.");
		}

		// Obtem as mensagens, de acordo com os intervalos estabelecidos
		// (se nao em {startTime}, vem desde o epoch e se nao tem
		// {endTime} vem até agora
		if (startTime == null || startTime.equals("")) {
			if (endTime == null || endTime.equals("")) {
				messages = target.getPostedMessages(0, Calendar.getInstance()
						.getTimeInMillis() / 1000, maxN);
			} else {
				messages = target.getPostedMessages(0,
						timestampFromString(endTime), maxN);
			}
		} else {
			if (endTime == null || endTime.equals("")) {
				messages = target.getPostedMessages(
						timestampFromString(startTime), Calendar.getInstance()
								.getTimeInMillis() / 1000, maxN);
			} else {
				messages = target.getPostedMessages(
						timestampFromString(startTime),
						timestampFromString(endTime), maxN);
			}
		}

		return html.messageListPage(cookie, "posted", target, messages, maxN);
	}

	public String processModifyContactReq(String cookie, String loginName,
			String newStatus) {
		// Obtém a sessão:
		Session session = this.sessionTable.getSessionByCookie(cookie);
		if (session == null) {
			return html.errorPage("no session with this cookie.");
		}
		// Obtém os usuários de origem e alvo:
		User source = session.getUser();
		User target = this.userTable.getUserByLogin(loginName, "SENHA_AQUI");
		if (target == null) {
			return html.errorPage("no such user.");
		}
		if (source == target) {
			return html.errorPage("you cannot have contact with yourself");
		}
		// Obtém o contato entre eles, se já existir:
		Contact cdir = source.getDirectContact(target);
		Contact crev = target.getReverseContact(source);
		assert ((cdir == null) == (crev == null));

		if (cdir != null) {
			// Já existe contato entre eles, apenas altera seu estado:
			cdir.setStatus(newStatus);
			// TODO ??{ Deveria aqui atualizar o estado do contato na base
			// persistente. }??

		} else {
			// Não há ainda contato entre eles, acrescenta:
			Contact c = new ContactImpl();
			c.initialize(source, target, Calendar.getInstance()
					.getTimeInMillis() / 1000, newStatus);

			// TODO ??{ Deveria aqui acrescentar o contato na base
			// persistente. }??
			source.addDirectContact(c);
			target.addReverseContact(c);
			this.numContacts += 1;
		}
		return html.userProfilePage(cookie, source, target);
	}

	@Override
	public long getNumUsers() {
		return userTable.getUserCount();
	}

	@Override
	public long getNumContacts() {
		return this.numContacts;
	}

	@Override
	public long getNumMessages() {
		return this.numMessages;
	}

	@Override
	public long getNumSessions() {
		return sessionTable.getSessionCount();
	}

	@Override
	public String processShowReceivedMessagesReq(String cookie,
			String startTime, String endTime, int maxN) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void processSendMessageReq(HttpServletRequest request,
			HttpServletResponse response, ServletContext context) throws IOException{
			
		// pega os dados da sessão
		User user = (User) request.getSession().getAttribute("user");
					
		if (user == null){
			response.getWriter().println(html.errorPage("no valid user."));
			return;
		}
		
		String messageBody;
		
		if (request.getParameter("messageText") == null) {
			response.getWriter().println(html.errorPage("no message text;"));
			return;
		}
			
		// nova mensagem
		messageBody = request.getParameter("messageText");
		String replyLoginName = request.getParameter("replyLoginName");
		Message message = new MessageImpl();
					
		if (replyLoginName == null || replyLoginName.equals("")) {
			if (!message.initialize(messageBody, user)) {
				response.getWriter().println(html.errorPage("message creation failed."));
				return;
			}
			
			try {
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				this.database.getConnection();
				this.database.getStatement("INSERT INTO message (id, user_id, body, parent,created)"
					+ "VALUES ("+message.getId()+","+user.getDbIndex()+
					",'"+message.getText()+"',NULL,'"
					+ dateFormat.format(new Date(message.getDate()*1000))+
					"');").execute();
				this.database.commit();
				
				user.addMessage(message);
				this.numMessages++;
			
				System.out.println("Mensagem inserida na tabela");
			} catch (ClassNotFoundException e) {
				
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			response.sendRedirect("/Quack/UserPage.jsp");
		}
		// TODO Tratar de mensagens de reply		
		return;
	}

	private long timestampFromString(String time) {
		time = time.replaceAll("[()]", "");
		String date[] = time.split("-: ");

		// TODO - consertar timezone (ID de TimeZone eh uma string
		// do tipo "Brazil/East", e nao (-0300))
		Calendar a = Calendar.getInstance();
		a.set(Integer.parseInt(date[0]), Integer.parseInt(date[1]),
				Integer.parseInt(date[2]), Integer.parseInt(date[3]),
				Integer.parseInt(date[4]), Integer.parseInt(date[5]));

		return a.getTimeInMillis() / 1000;

	}

	@Override
	public User getUserFromCookie(String cookie) {
		Session session = sessionTable.getSessionByCookie(cookie);
		User user = null;
		if (session != null) {
			user = session.getUser();
		}
		return user;
	}
}
