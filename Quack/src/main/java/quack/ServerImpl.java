package quack;

import java.io.IOException;
import java.util.Calendar;
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
		
		this.database.loadDatabase(userTable, nextUserId, nextMessageId);

		// Inicializa o criador de paginas html:
		html = new HTMLImpl();
		html.initialize(this);
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
		User user = this.userTable.getUserByLogin(request.getParameter("username"));
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
					database.insertUser(user);
					this.userTable.add(user);

					response.sendRedirect("/Quack/pub/LoginPage.jsp");
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
		    response.sendRedirect("/Quack/Timeline");
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
		response.sendRedirect("/Quack/pub/LoginPage.jsp");
	}

	public void processShowUserProfileReq(HttpServletRequest request,
			HttpServletResponse response, ServletContext context) throws IOException, ServletException{
		
			String URL = request.getRequestURI();
			String parseURL[] = URL.split("/");
			String params = parseURL[parseURL.length -1];
			
			User u =  userTable.getUserByLogin(params);
			
			if(u == null){
				html.errorPage(response, "Usuario nao existe!");
			}else{
				response.sendRedirect("/Quack/UserPage.jsp"+"?u="+u.getLoginName());
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
		User target = this.userTable.getUserByLogin(loginName);
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
						DatabaseImpl.timestampFromString(endTime), maxN);
			}
		} else {
			if (endTime == null || endTime.equals("")) {
				messages = target.getPostedMessages(
						DatabaseImpl.timestampFromString(startTime), Calendar.getInstance()
								.getTimeInMillis() / 1000, maxN);
			} else {
				messages = target.getPostedMessages(
						DatabaseImpl.timestampFromString(startTime),
						DatabaseImpl.timestampFromString(endTime), maxN);
			}
		}

		return html.messageListPage(cookie, "posted", target, messages, maxN);
	}

	public void processModifyContactReq(HttpServletRequest request,
			HttpServletResponse response, ServletContext context) throws IOException{
		
		String cookie = CookieHelper.getCookieValue(request, CookieHelper.COOKIE_NAME);
		User sessionUser = (User)getUserFromCookie(cookie);
		User contactUser = userTable.getUserByLogin(request.getParameter("userName")); //Usuario contato
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
						
						database.modifyContact(sessionUser, contactUser, true);
						html.errorPage(response, "acao concluida!");
					}
					else if(relation.equals("false")){
						c_sessionUser.setStatus("Block");
						c_contactUser.setStatus("Block");
						
						database.modifyContact(sessionUser, contactUser, false);
						
						html.errorPage(response, "acao concluida!");
					}	
				} else{//Contato ainda nao existe
					if(relation.equals("true")){ // Relacao de follow
						c = new ContactImpl();
						c.initialize(sessionUser, contactUser, Calendar.getInstance()
								.getTimeInMillis() / 1000, "Follow");
						sessionUser.addDirectContact(c);
						contactUser.addReverseContact(c);
						this.numContacts += 1;
						database.insertContact(sessionUser, contactUser, true);
						html.errorPage(response, "acao concluida!");
					} else if(relation.equals("false")){ //Relacao de block
					
						c = new ContactImpl();
						c.initialize(sessionUser, contactUser, Calendar.getInstance()
								.getTimeInMillis() / 1000, "Block");
						sessionUser.addDirectContact(c);
						contactUser.addReverseContact(c);
						this.numContacts += 1;
						//Insere no banco de dados
						database.insertContact(sessionUser, contactUser, false);
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
		
		if(request.getParameter("startTime") == null)
			startTime = -1;
		else
			startTime = DatabaseImpl.timestampFromString((String) request.getParameter("startTime"));
		
		if(request.getParameter("endTime") == null)
			endTime = -1;
		else
			endTime = DatabaseImpl.timestampFromString((String) request.getParameter("endTime"));
		
		if(request.getParameter("maxN") == null)
			maxN = 15;
		else
			maxN = Integer.parseInt(((String) request.getParameter("maxN")));
		
		List<Message> messages = new LinkedList<Message>(); 
		
		for(Contact c : user.getDirectContacts()){
			if(c.status().equals("Follow")){
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
					
		if (replyLoginName == null || replyLoginName.equals("")) {
			if (!message.initialize(messageBody, user, this.nextMessageId, Calendar.getInstance().getTimeInMillis()/1000)) {
				html.errorPage(response, "message creation failed.");
				return;
			}

			this.nextMessageId++;
			if (database.addMessage(message, user)){
				user.addMessage(message);
				this.numMessages++;
			}
			
			response.sendRedirect("/Quack/UserPage.jsp");
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
		return userTable.getUserByLogin(loginName);
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
				
				messageAuthor = this.userTable.getUserByLogin(request.getParameter("author"));
				
				if(messageAuthor == null){
					html.errorPage(response, "invalid author");
				}
				
				message = messageAuthor.getMessageByDBIndex(Long.valueOf(request.getParameter("id")));
				
				if(message == null){
					html.errorPage(response, "message not found");
				}
					
				// nova mensagem
				Message newMessage = new MessageImpl();
							
					if (!newMessage.initialize(user, message, this.nextMessageId, Calendar.getInstance().getTimeInMillis()/1000)) {
						html.errorPage(response, "message creation failed.");
						return;
					}

					this.nextMessageId++;
					
					if (database.addMessage(newMessage, user)) {
						user.addMessage(message);
						this.numMessages++;
					}
					
					response.sendRedirect("/Quack/UserPage.jsp");
				
				// TODO Tratar de mensagens de reply		
				return;
	}

	@Override
	public void processShowFollowersReq(HttpServletRequest request,
			HttpServletResponse response, ServletContext context)
			throws IOException {
		String username = this.userTable.getUserById(Long.valueOf(request.getParameter("id"))).getLoginName();
		
		response.sendRedirect("/Quack/Followers.jsp?user=" + username);
	}
}
