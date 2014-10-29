package quack;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import service.QuackService;

public final class ServerImpl implements Server {

	SessionTable sessionTable = null; // Conjunto de sessões abertas.
	Database database = null; // Conexão com a base de dados persistente.
	// As tabelas abaixo são cópias na memória dos objetos na base de dados.
	UserTable userTable = null; // Conjuto de usuários cadastrados.

	long numUsers = 0; // Número de usuários na rede {Quack}.
	long numContacts = 0; // Número total de contatos criados (incluindo
							// inativos).
	long numMessages = 0; // Número total de mensagens postadas (incuindo
							// re-postagens).
	long numSessions = 0; // Número total de sessões abertas no momento.

	HTML html; // Cria paginas html.

	private static final String passwordKey = "QUACK_PASSWORD";
	private static final String usernameKey = "QUACK_USERNAME";
	
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
		this.userTable.initialize(database);

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
		// ??{ Implementar }??
	}

	public String processHomePageReq() {
		return html.homePage();
	}

	public String processRegistrationReq(HttpServletRequest request,
			HttpServletResponse response, ServletContext context) throws IOException {

		//Verifica se já existe usuário com esse {loginName} ou {email}:	
		User user = this.userTable.getUserByLogin(request.getParameter("username"), "");

		if (user != null) {
			PrintWriter out = response.getWriter();  
			response.setContentType("text/html");  
			out.println("<script type=\"text/javascript\">");  
			out.println("alert('Este nome de usuario ja existe');");  
			out.println("</script>");		
			}
		user = this.userTable.getUserByEmail(request.getParameter("email"));
		if (user != null) {
			PrintWriter out = response.getWriter();  
			response.setContentType("text/html");  
			out.println("<script type=\"text/javascript\">");  
			out.println("alert('Ja existe uma conta com este email');");  
			out.println("</script>");	
		}

		// Cria o usuário e acrescenta à tabela:
		user = new UserImpl();
		if (!user.initialize(request.getParameter("username"), request.getParameter("email"), 
				request.getParameter("fullname"), request.getParameter("password"))) {
			return html.errorPage("user creation failed for unknown reason");
		}
		this.userTable.add(user);
		// ??{ Aqui deve gravar o usuário na base de dados persistente? }??
		response.sendRedirect("/Quack/loginrequest.jsp");
		return html.loginPage();
	}

	public void processLoginReq(HttpServletRequest request,
			HttpServletResponse response, ServletContext context) throws IOException {

		// pega os dados da sessão
		HttpSession requestSession = request.getSession();

		String login = request.getParameter("login");
		String password = request.getParameter("password");
		boolean remember = "true".equals(request.getParameter("remember"));
		User user;
		if(login.contains("@")){
			user = this.userTable.getUserByEmail(login);
		}
		else {
			user = this.userTable.getUserByLogin(login, password);
		}
		
		if(user == null){
			// usuario invalido, mostra pagina de erro.
			//response.sendRedirect("/Quack/loginerror.jsp");
			response.sendRedirect("/Quack/loginrequest.jsp");
			return;
		}
	
		// se o usuario acabou de fazer login
		if(user.checkPassword(password)) {
//			// usuario valido, salva na sessão do browser e recarrega a pagina
			requestSession.setAttribute(usernameKey, user.getLoginName());
			requestSession.setAttribute(passwordKey, password);			
			
			response.sendRedirect("/Quack/UserPage.jsp");
			return;
		}

		response.sendRedirect("/Quack/loginrequest.jsp");
		return;
	}

	public void processLogoutReq(HttpServletRequest request,
			HttpServletResponse response, ServletContext context) throws IOException {
		
		String cookie = request.getSession().getId();
		
		// Obtém a sessão:
		Session session = this.sessionTable.getSessionByCookie(cookie);
		if (session == null) {
			response.getWriter().println(html.errorPage("no session with this cookie."));
			return;
		}
		// Fecha a sessão existente:
		this.sessionTable.delete(session);
		session.close();
		response.getWriter().println(html.homePage());
		return;
	}

	public String processShowUserProfileReq(String cookie, String loginName) {
		// Obtém a sessão:
		Session session = null; // Current session or {null}.
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
		return html.userProfilePage(cookie, source, target);
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
		HttpSession requestSession = request.getSession();
		String cookieId = requestSession.getId();
		Session session = this.sessionTable.getSessionByCookie(cookieId);
				
		if (session == null){
			response.getWriter().println(html.errorPage("no session with this cookie."));
			return;
		}
		
		User user = session.getUser();
		String messageBody;
		
		if (user == null){
			response.getWriter().println(html.errorPage("session without user."));
			return;
		}
		
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
			user.addMessage(message);
			this.numMessages++;
			response.getWriter().println(html.homePage());
			return;
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
}
