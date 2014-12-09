package quack;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
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

	private Long nextUserId = new Long(0);
	private Long nextMessageId = new Long(0);

	long numContacts = 0; // Número total de contatos criados (incluindo
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
		
		this.database.loadDatabase(userTable, nextUserId, nextMessageId);

		// Inicializa o criador de paginas html:
		html = new HTMLImpl();
		html.initialize(this);
	}

	public void processHomePageReq(HttpServletRequest request,
			HttpServletResponse response, ServletContext context) throws IOException {
		html.homePage(response);
	}

	public void processRegistrationReq(HttpServletRequest request,
			HttpServletResponse response, ServletContext context) throws IOException {

		// Restringe os caracteres válidos de {loginName} para 
		// dígitos de 0 a 9, letras de A a Z (maiúsculas ou
		// minúsculas) e o caractere '_'.
		String username = request.getParameter("username");
		for (int i = 0; i < username.length(); i++)	{
			char c = username.charAt(i);
			if ((c < '0' || c > '9') && (c < 'A' || c > 'Z') 
					&& (c < 'a' || c > 'z') && c != '_') {
				html.errorPage(response, "Caractere inválido no nome do usuário.");
				return;
			}
		}
		// Restringe os caracteres válidos de {password} para
		// qualquer caractere da tabela ASCII, exceto
		// caracteres de controle.
		String password = request.getParameter("password");
		for (int i = 0; i < password.length(); i++)	{
			char c = password.charAt(i);
			if (c < ' ' || c > '~')	{
				html.errorPage(response, "Caractere inválido na senha.");
				return;
			}
		}
		
		//Verifica se já existe usuário com esse username:
		User user = this.userTable.getUserByLoginName(username);
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
						new String(request.getParameter("fullName").getBytes("iso-8859-1"), "UTF-8"),
						request.getParameter("password"), this.nextUserId)) {
					html.errorPage(response, "Falha ao criar user");  
				} else {
					this.nextUserId++;
					database.insertUser(user);
					this.userTable.add(user);

					html.loginPage(response);
					
				}
			}
		}
	}

	public void processLoginReq(HttpServletRequest request,
			HttpServletResponse response, ServletContext context) throws IOException, ServletException {

		String login = request.getParameter("login");
		String password = request.getParameter("password");
		
		User user = this.userTable.getUserByLoginPassword(login, password);
		// se o usuario acabou de fazer login
		if (user != null) {
			// cria uma sessao para o usuario
			String cookie = UUID.randomUUID().toString();
		    Session session = new SessionImpl();
		    session.open(user, cookie);
		    sessionTable.add(session);
		    // adiciona o cookie no browser
		    CookieHelper.addCookie(response, CookieHelper.COOKIE_NAME, cookie, CookieHelper.COOKIE_AGE);
		    html.timelinePage(response);
		} else {
			// usuario invalido, mostra erro.
			html.errorPage(response, "Falha ao realizar login");
		}

		return;
	}

	public void processLogoutReq(HttpServletRequest request,
			HttpServletResponse response, ServletContext context) throws IOException {
		
		String cookie = CookieHelper.getCookieValue(request, CookieHelper.COOKIE_NAME);
		// remove a sessao do servidor.
		Session session = sessionTable.getSessionByCookie(cookie);
		sessionTable.delete(session);
		// remove o cookie do navegador
		CookieHelper.removeCookie(response, CookieHelper.COOKIE_NAME);
		// envia para o login
		html.loginPage(response);
	}

	public void processShowUserProfileReq(HttpServletRequest request,
			HttpServletResponse response, ServletContext context) throws IOException, ServletException{
		
			String URL = request.getRequestURI();
			String parseURL[] = URL.split("/");
			String params = parseURL[parseURL.length -1];
			
			User u =  userTable.getUserByLoginName(params);
			
			if(u == null){
				html.errorPage(response, "Usuario nao existe!");
			}else{
				html.userProfilePage(response, u.getLoginName());
				
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
		User target = this.userTable.getUserByLoginName(loginName);
		if (target == null) {
			//html.errorPage(response, "no such user.");
		}

		// Obtem as mensagens, de acordo com os intervalos estabelecidos
		// (se nao em {startTime}, vem desde o epoch e se nao tem
		// {endTime} vem até agora
		Timestamp t = new TimestampImpl();
		if (startTime == null || startTime.equals("")) {
			if (endTime == null || endTime.equals("")) {
				messages = target.getPostedMessages(0, t.now(), maxN);
			} else {
				messages = target.getPostedMessages(0,
						t.fromString(endTime), maxN);
			}
		} else {
			if (endTime == null || endTime.equals("")) {
				messages = target.getPostedMessages(
						t.fromString(startTime), t.now(), maxN);
			} else {
				messages = target.getPostedMessages(
						t.fromString(startTime),
						t.fromString(endTime), maxN);
			}
		}

		return html.messageListPage(cookie, "posted", target, messages, maxN);
	}

	public void processModifyContactReq(HttpServletRequest request,
			HttpServletResponse response, ServletContext context) throws IOException{
		
		String cookie = CookieHelper.getCookieValue(request, CookieHelper.COOKIE_NAME);
		User sessionUser = (User)getUserFromCookie(cookie);
		User contactUser = userTable.getUserByLoginName(request.getParameter("userName")); //Usuario contato
		String relation = request.getParameter("state");
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
					c_sessionUser.setStatus(relation);
					c_contactUser.setStatus(relation);						
					database.modifyContact(c_sessionUser);
					html.errorPage(response, "acao concluida!");

				} else{//Contato ainda nao existe

					c = new ContactImpl();
					Timestamp t = new TimestampImpl();
					c.initialize(sessionUser, contactUser, t.now(), relation);
					sessionUser.addDirectContact(c);
					contactUser.addReverseContact(c);
					this.numContacts += 1;
					database.insertContact(c);
					html.errorPage(response, "acao concluida!");
				}
			}
		}
	}
	
	@Override
	public void processModifyUserReq(HttpServletRequest request,
			HttpServletResponse response, ServletContext context)
			throws IOException {
		String cookie = CookieHelper.getCookieValue(request, CookieHelper.COOKIE_NAME);
		User sessionUser = (User)getUserFromCookie(cookie);
		String newFullName = new String(request.getParameter("fullName").getBytes("iso-8859-1"), "UTF-8");
		String newPassword = request.getParameter("newPassword");
		String oldPassword = request.getParameter("oldPassword");
		
		User user = this.userTable.getUserByLoginName(sessionUser.getLoginName());
		
		if(newPassword != null && !newPassword.equals("")){
			if(sessionUser.checkPassword(oldPassword) == false){
				html.errorPage(response, "Senha antiga inválida");
				return;
			}
			
			// Restringe os caracteres válidos de {password} para
			// qualquer caractere da tabela ASCII, exceto
			// caracteres de controle.
			for (int i = 0; i < newPassword.length(); i++)	{
				char c = newPassword.charAt(i);
				if (c < ' ' || c > '~')	{
					html.errorPage(response, "Caractere inválido na senha.");
					return;
				}
			}
			user.setPassword(newPassword);
		}
		
		user.setFullName(newFullName);
		database.modifyUser(user);
		html.errorPage(response, "Dados alterados com sucesso");
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
		return this.nextMessageId;
	}

	@Override
	public long getNumSessions() {
		return sessionTable.getSessionCount();
	}

	@Override
	public List<Message> processShowReceivedMessagesReq(HttpServletRequest request,
			HttpServletResponse response, ServletContext context) throws IOException{
		
		String cookie = CookieHelper.getCookieValue(request, CookieHelper.COOKIE_NAME);
		User user = (User)getUserFromCookie(cookie);
		if (user == null){
			html.errorPage(response, "no valid user.");
			return null;
		}
		long startTime;
		long endTime;
		long maxN;
		Timestamp t = new TimestampImpl();
		
		if(request.getParameter("startTime") == null)
			startTime = -1;
		else
			startTime = t.fromString((String) request.getParameter("startTime"));
		
		if(request.getParameter("endTime") == null)
			endTime = -1;
		else
			endTime = t.fromString((String) request.getParameter("endTime"));
		
		if(request.getParameter("maxN") == null)
			maxN = 15;
		else
			maxN = Integer.parseInt(((String) request.getParameter("maxN")));
		
		List<Message> messages = new LinkedList<Message>(); 
		
		for(Contact c : user.getDirectContacts()){
			if(c.status().equalsIgnoreCase("Follow")){
				for(Message m : c.target().getPostedMessages(startTime, endTime, maxN)){
					messages.add(m);
				}
			}
		}
		
		Collections.sort(messages, new Comparator<Message>() {
			@Override
	        public int compare(Message a, Message b)
	        {
				if(a.getDate() > b.getDate())
					return -1;
				else if(a.getDate() == b.getDate())
					return 0;
				else return 1;
	        }
		});
		 
		return messages.subList(0, Math.min((int) maxN, messages.size()));
	}

	@Override
	public void processSendMessageReq(HttpServletRequest request,
			HttpServletResponse response, ServletContext context) throws IOException{
			
		// pega os dados da sessão
		String cookie = CookieHelper.getCookieValue(request, CookieHelper.COOKIE_NAME);
		User user = (User)getUserFromCookie(cookie);
					
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
		Timestamp t = new TimestampImpl();			
		if (replyLoginName == null || replyLoginName.equals("")) {
			if (!message.initialize(messageBody, user, this.nextMessageId, t.now())) {
				html.errorPage(response, "message creation failed.");
				return;
			}

			this.nextMessageId++;
			if (database.addMessage(message)){
				user.addMessage(message);
			}
			
			html.userProfilePage(response);
		}
		// TODO Tratar de mensagens de reply		
		return;
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
	
	public User getUserFromLoginName(String loginName) {
		return userTable.getUserByLoginName(loginName);
	}

	@Override
	public List<User> getAllUsers(){
		
		return this.userTable.getAllUsers();
	}

	@Override
	public void processRepostMessageReq(HttpServletRequest request,
			HttpServletResponse response, ServletContext context)
			throws IOException {
		
		// pega os dados da sessão
				String cookie = CookieHelper.getCookieValue(request, CookieHelper.COOKIE_NAME);
				User user = (User)getUserFromCookie(cookie);
							
				if (user == null){
					html.errorPage(response, "no valid user.");
					return;
				}
				
				Message message;
				User messageAuthor;
				
				if (request.getParameter("id") == null) {
					html.errorPage(response, "no message ID especified;");
					return;
				}
				
				messageAuthor = this.userTable.getUserByLoginName(request.getParameter("author"));
				
				if(messageAuthor == null){
					html.errorPage(response, "invalid author");
				}
				
				message = messageAuthor.getMessageByDBIndex(Long.valueOf(request.getParameter("id")));
				
				if(message == null){
					html.errorPage(response, "message not found");
				}
					
				// nova mensagem
				Message newMessage = new MessageImpl();
				Timestamp t = new TimestampImpl();
							
					if (!newMessage.initialize(user, message, this.nextMessageId, t.now())) {
						html.errorPage(response, "message creation failed.");
						return;
					}

					this.nextMessageId++;
					
					if (database.addMessage(newMessage)) {
						user.addMessage(message);
					}
					
					html.userProfilePage(response);
				
				// TODO Tratar de mensagens de reply		
				return;
	}

	@Override
	public void processShowFollowersReq(HttpServletRequest request,
			HttpServletResponse response, ServletContext context)
			throws IOException {
		String username = this.userTable.getUserById(Long.valueOf(request.getParameter("id"))).getLoginName();
		
		html.followersPage(response, username);

	}

	@Override
	public void processShowFollowsReq(HttpServletRequest request,
			HttpServletResponse response, ServletContext context)
			throws IOException {
		String username = this.userTable.getUserById(Long.valueOf(request.getParameter("id"))).getLoginName();
		
		html.followsPage(response, username);
			
	}

	@Override
	public void processFileUploadRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}

}
