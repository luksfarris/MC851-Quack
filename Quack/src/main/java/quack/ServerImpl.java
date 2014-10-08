package quack;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public abstract class ServerImpl implements Server {

	SessionTable sessionTable = null; // Conjunto de sessões abertas.
	Database database = null; // Conexão com a base de dados persistente.
	// As tabelas abaixo são cópias na memória dos objetos na base de dados.
	UserTable userTable = null; // Conjuto de usuários cadastrados.
	
	long numUsers = 0;     // Número de usuários na rede {Quack}.
	long numContacts = 0;  // Número total de contatos criados (incluindo inativos).
	long numMessages = 0;  // Número total de mensagens postadas (incuindo re-postagens).
	long numSessions = 0;  // Número total de sessões abertas no momento. 

	HTML html = null; // Cria paginas html.

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
		html = html.initialize(this);
	}
	
	private void loadDatabase() 
	// Carrega a base de dados {this.database} na memória, criando os objetos 
	// {User,Message,Contact} e ligando-os entre si.  Supõe que a conexão com o
	// servidor da base de dados já foi estabelecida.
	{
		// ??{ Implementar }??
	}
	
	public String processHomePageReq() {
		return html.homePage();
	}

	public String processRegistrationReq(String loginName, String email,
			String fullName, String password) {
		// Verifica se já existe usuário com esse {loginName} ou {email}:
		User user = this.userTable.getUserByLoginName(loginName);
		if (user != null) {
			return html.errorPage("login name already taken");
		}
		user = this.userTable.getUserByEmail(email);
		if (user != null) {
			return html.errorPage("there is already a user account with that email");
		}

		// Cria o usuário e acrescenta à tabela:
		user = new UserImpl();
		if (!user.initialize(loginName, email, fullName, password)) {
			return html.errorPage("user creation failed for unknown reason");
		}
		this.userTable.add(user);
		// ??{ Aqui deve gravar o usuário na base de dados persistente? }??
		return html.loginPage();
	}

	public String processLoginReq(String loginName, String password) {
		// Obtém o objeto que representa o usuário:
		User user = this.userTable.getUserByLoginName(loginName);
		if (user == null) {
			return html.errorPage("no such user");
		}
		// Verifica se a senha bate:
		if (!user.checkPassword(password)) {
			return html.errorPage("wrong password");
		}
		// Verifica se já existe sessão aberta para este usuário:
		Session session = this.sessionTable.getSessionByUser(user);
		if (session != null) { // Fecha a sessão existente:
			this.sessionTable.delete(session);
			session.close();
		} else { // Cria instâcia de sessão:
			session = new SessionImpl();
		}

		// Cria um cookie para a sessão, e abre a mesma:
		session.open(user, null);
		this.sessionTable.add(session);
		return html.loginSuccessfulPage(session.getCookie(), user);
	}

	public String processLogoutReq(String cookie) {
		// Obtém a sessão:
		Session session = this.sessionTable.getSessionByCookie(cookie);
		if (session == null) {
			return html.errorPage("no session with this cookie.");
		}
		// Fecha a sessão existente:
		this.sessionTable.delete(session);
		session.close();
		return html.homePage();
	}
	
	public String processShowUserProfileReq(String cookie, String loginName) {
		// Obtém a sessão:
		Session session = null; // Current session or {null}.
		User source = null; // Session owner or {null}.
		if (! cookie.equals("")) { 
			session = this.sessionTable.getSessionByCookie(cookie); 
		 	if (session == null) {
				return html.errorPage("no session with this cookie.");
			}
			source = session.getUser();
		}
		User target = this.userTable.getUserByLoginName(loginName);
		if (target == null) {
			return html.errorPage("no such user.");
		}
		return html.userProfilePage(cookie, source, target);
	}

	public String processShowPostedMessagesReq(String cookie, String loginName,
			String startTime, String endTime, int maxN) {
		// Obtém a sessão:
		Session session = this.sessionTable.getSessionByCookie(cookie);
		if (session == null) {
			return html.errorPage("no session with this cookie.");
		}
		User target = this.userTable.getUserByLoginName(loginName);
		if (target == null) { 
			return html.errorPage("no such user.");
		}
		// ??{ ... get specified messages from {target} ... }??
		List<Message> messages = target.getPostedMessages(-1, -1, maxN);
		return html.messageListPage(cookie, "posted", target, messages, maxN);
	}
	
	public String processModifyContactReq(String cookie, String loginName, String newStatus) {
		// Obtém a sessão:
		Session session = this.sessionTable.getSessionByCookie(cookie);
		if (session == null) {
			return html.errorPage("no session with this cookie.");
		}
		// Obtém os usuários de origem e alvo:
		User source = session.getUser();
		User target = this.userTable.getUserByLoginName(loginName);
		if (target == null) { 
			return html.errorPage("no such user.");
		}
		if (source == target) {
			return html.errorPage("you cannot have contact with yourself");
		}
		// Obtém o contato entre eles, se já existir:
		Contact cdir = source.getDirectContact(target);
		Contact crev = target.getReverseContact(source);
		assert((cdir == null) == (crev == null));
		
		if (cdir != null) {
			// Já existe contato entre eles, apenas altera seu estado:
			cdir.setStatus(newStatus);
			// ??{ Deveria aqui atualizar o estado do contato na base persistente. }??
		} else {
			// Não há ainda contato entre eles, acrescenta:
			Contact c = new ContactImpl();
			c.initialize(source, target, Calendar.getInstance(), newStatus);
			// ??{ Deveria aqui acrescentar o contato na base persistente. }??
			source.addDirectContact(c);
			target.addReverseContact(c);
		}
		return html.userProfilePage(cookie, source, target);
	}
	
	@Override 
	public long getNumUsers() {
		return this.numUsers;
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
		return this.numSessions;
	}
}
