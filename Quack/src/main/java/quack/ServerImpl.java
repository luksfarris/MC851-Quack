package quack;

import java.io.IOException;
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

import service.CookieHelper;

public final class ServerImpl implements Server {

	SessionTable sessionTable = null; // Conjunto de sessões abertas.
	Database database = null; // Conexão com a base de dados persistente.
	// As tabelas abaixo são cópias na memória dos objetos na base de dados.
	UserTable userTable = null; // Conjuto de usuários cadastrados.

	private long nextUserId = 0;
	private long nextMessageId = 0;
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
					
					
					this.nextUserId = Math.max(this.nextUserId, u.getDbIndex());
					
					ResultSet rs2 = this.database.getStatement("SELECT * FROM message WHERE user_id=" 
					+ String.valueOf(u.getDbIndex())).executeQuery();
					while(rs2.next()){
						Message m = new MessageImpl();
						if(m.initialize(rs2.getString("body"), u, rs2.getLong("id")) == false)
							System.out.println("Erro ao carregar mensagens");
						else{
							u.addMessage(m);
							this.nextMessageId = Math.max(this.nextMessageId, m.getId());
						}
					}
					this.userTable.add(u);
					}
				
				else {
					System.out.println("Problema no carregamento do usuário!");
				}
				
			}
			this.nextUserId++;
			this.nextMessageId++;
			
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

		System.out.println(request.getParameter(("username")));
		//Verifica se já existe usuário com esse username:
		User user = this.userTable.getUserByLogin(request.getParameter("username"), "");
		if (user != null) {  
			
			html.errorPage(response, "Este nome de usuario ja existe");
		} else{
			//Verifica se já existe usuário com esse email:
			user = this.userTable.getUserByEmail(request.getParameter("email"));
			if (user != null) {
				html.errorPage(response, "Ja existe uma conta com este email");
			}
			else{
				// Cria o usuário e acrescenta à tabela:
				user = new UserImpl();
				if (!user.initialize(request.getParameter("username"), request.getParameter("email"), 
						request.getParameter("fullName"), request.getParameter("password"), 
						this.nextUserId)) {
					html.errorPage(response, "Falha ao criar user");  
				} else {
					this.nextUserId++;
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
					response.sendRedirect("/Quack/pub/loginpage.jsp");
				}
			}
		}
	}

	public void processLoginReq(HttpServletRequest request,
			HttpServletResponse response, ServletContext context) throws IOException, ServletException {

		String login = request.getParameter("login");
		String password = request.getParameter("password");
		boolean remember = "true".equals(request.getParameter("remember"));
		
		User user = this.userTable.getUserByLogin(login, password);
		// se o usuario acabou de fazer login
		if (user != null) {
			//request.login(login, password);
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
			request.getSession().setAttribute("userPage", user);
			response.sendRedirect("/Quack/UserPage.jsp");
		} else {
			// usuario invalido, mostra erro.
			html.errorPage(response, "Falha ao realizar login");
		}

		return;
	}

	public void processLogoutReq(HttpServletRequest request,
			HttpServletResponse response, ServletContext context) throws IOException {
		
		// TODO: implementar o logout
		
		return;
	}

	public void processShowUserProfileReq(HttpServletRequest request,
			HttpServletResponse response, ServletContext context) throws IOException, ServletException{
		
			String URL = request.getRequestURI();
			String parseURL[] = URL.split("/");
			String params = parseURL[parseURL.length -1];
			
			User u =  userTable.getUserByLogin(params,"");
			
			if(u == null){
				html.errorPage(response, "Usuario nao existe!");
			}else{
				request.getSession().setAttribute("userPage", u);
				response.sendRedirect("/Quack/UserPage.jsp");
			}	
	}

	public String processShowPostedMessagesReq(String cookie, String loginName,
			String startTime, String endTime, int maxN) {
		List<Message> messages;
		// Obtém a sessão:
		Session session = this.sessionTable.getSessionByCookie(cookie);
		if (session == null) {
			//html.errorPage(response, "no session with this cookie.");
		}

		// Obtem o autor das mensagens procuradas
		User target = this.userTable.getUserByLogin(loginName, "");
		if (target == null) {
			//html.errorPage(response, "no such user.");
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

	public void processModifyContactReq(HttpServletRequest request,
			HttpServletResponse response, ServletContext context) throws IOException{
		
		User sessionUser = (User)request.getSession().getAttribute("user"); //User da sessao
		User contactUser = userTable.getUserByLogin(request.getParameter("userName"), ""); //Usuario contato
		String relation = request.getParameter("follow");
		Contact c;
				
		if(contactUser == null){
			html.errorPage(response, "Usuario nao existe!");
		}
		else{
			if(sessionUser == contactUser){
				html.errorPage(response, "Nao pode haver contato com si mesmo");
			}
			else{
				
				if(sessionUser.getDirectContact(contactUser) != null){
					//Contato ja existe
					
					Contact c_sessionUser = sessionUser.getDirectContact(contactUser);
					Contact c_contactUser = contactUser.getReverseContact(sessionUser);
					if(relation.equals("true")){
						c_sessionUser.setStatus("Follow");
						c_contactUser.setStatus("Follow");
						
						try {
							DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							this.database.getConnection();
							this.database.getStatement("UPDATE contact SET status='Follow' where source_id='"+
							sessionUser.getDbIndex() +"' and target_id='"+ contactUser.getDbIndex() +"';").execute();
							this.database.commit();	
						} catch (ClassNotFoundException e) {
							e.printStackTrace();
						} catch (SQLException e) {
							e.printStackTrace();
						}
						
					}
					else if(relation.equals("false")){
						c_sessionUser.setStatus("Block");
						c_contactUser.setStatus("Block");
						
						try {
							DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							this.database.getConnection();
							this.database.getStatement("UPDATE contact SET status='Block' where source_id='"+
							sessionUser.getDbIndex() +"' and target_id='"+ contactUser.getDbIndex() +"';").execute();
							this.database.commit();	
						} catch (ClassNotFoundException e) {
							e.printStackTrace();
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}	
				} else{//Contato ainda nao existe
					if(relation.equals("true")){ // Relacao de follow
						c = new ContactImpl();
						c.initialize(sessionUser, contactUser, Calendar.getInstance()
								.getTimeInMillis() / 1000, "Follow");
						sessionUser.addDirectContact(c);
						contactUser.addReverseContact(c);
						this.numContacts += 1;
						
						//Insere no banco de dados
						try {
							DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							this.database.getConnection();
							this.database.getStatement("INSERT INTO contact VALUES("+ sessionUser.getDbIndex()+","+contactUser.getDbIndex()+",'"+
							dateFormat.format(new Date(Calendar.getInstance()
									.getTimeInMillis()))+"','Follow'"+");").execute();
							this.database.commit();	
						} catch (ClassNotFoundException e) {
							e.printStackTrace();
						} catch (SQLException e) {
							e.printStackTrace();
						}
						html.errorPage(response, "acao concluida!");
					} else if(relation.equals("false")){ //Relacao de block
					
						c = new ContactImpl();
						c.initialize(sessionUser, contactUser, Calendar.getInstance()
								.getTimeInMillis() / 1000, "Block");
						sessionUser.addDirectContact(c);
						contactUser.addReverseContact(c);
						this.numContacts += 1;
						//Insere no banco de dados
						try {
							DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							this.database.getConnection();
							this.database.getStatement("INSERT INTO contact VALUES("+ sessionUser.getDbIndex()+","+contactUser.getDbIndex()+",'"+
									dateFormat.format(new Date(Calendar.getInstance()
											.getTimeInMillis()))+"','Block'"+");").execute();
							this.database.commit();	
						} catch (ClassNotFoundException e) {
							e.printStackTrace();
						} catch (SQLException e) {
							e.printStackTrace();
						}
						html.errorPage(response, "acao concluida!");
					} else{
						html.errorPage(response, "Relacao invalida");
					}	
				}
			}
		}
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
			html.errorPage(response, "no valid user.");
			return;
		}
		
		String messageBody;
		
		if (request.getParameter("messageText") == null) {
			html.errorPage(response, "no message text;");
			return;
		}
			
		// nova mensagem
		messageBody = new String(request.getParameter("messageText").getBytes("iso-8859-1"), "UTF-8");
		String replyLoginName = request.getParameter("replyLoginName");
		Message message = new MessageImpl();
					
		if (replyLoginName == null || replyLoginName.equals("")) {
			if (!message.initialize(messageBody, user, this.nextMessageId)) {
				html.errorPage(response, "message creation failed.");
				return;
			}

			this.nextMessageId++;
			
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
