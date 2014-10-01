package quack;

public abstract class ServerImpl implements Server {

	SessionTable sessionTable = null; // Conjunto de sessões abertas.
	Database database = null; // Conexão com a base de dados persistente.
	// As tabelas abaixo são cópias na memória dos objetos na base de dados.
	UserTable userTable = null; // Conjuto de usuários cadastrados.

	HTML html = null; // Cria paginas html.

	// ??{ O s procedimentos a seguir deveriam construir e devolver
	// a construir página HTML de resultado adequada. }??

	public void initialize(String dbUserName, String dbName, String dbPassword) {
		// Cria a tabela de sessões abertas:
		this.sessionTable = new SessionTableImpl();
		this.sessionTable.initialize();

		// Conecta com a base de dados persitente:
		this.database = new DatabaseImpl(dbUserName, dbName, dbPassword);

		// Cria a tabela de usuários:
		this.userTable = new UserTableImpl();
		this.userTable.initialize(this.database);

		// inicializa o criador de paginas html:
		html = new HTMLImpl();
		html = html.initialize();
	}

	public String processRegistrationReq(String loginName, String email,
			String fullName, String password) {
		// Verifica se já existe usuário com esse {userName} ou {email}:
		User user = this.userTable.getUserByLoginName(loginName);
		if (user != null) {
			return html.errorPage("username already taken");
		}
		user = this.userTable.getUserByEmail(email);
		if (user != null) {
			return html
					.errorPage("there is already a user account with that email");
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
		Session session = this.sessionTable.getSessionFromUser(user);
		if (session != null) { // Fecha a sessão existente:
			this.sessionTable.delete(session);
			session.close();
		} else { // Cria instâcia de sessão:
			session = new SessionImpl();
		}

		// Cria um cookie para a sessão, e abre a mesma:
		session.open(user, null);
		this.sessionTable.add(session);
		return html.loginSuccessfulPage(session.getCookie());
	}

	public String processLogoutReq(String cookie) {
		// Obtém a sessão:
		Session session = this.sessionTable.fromCookie(cookie);
		if (session == null) {
			return html.errorPage("no session with this cookie.");
		}
		// Fecha a sessão existente:
		this.sessionTable.delete(session);
		session.close();
		return html.homePage();
	}

	public String processShowOutMessagesReq(String cookie, String loginName,
			String startTime, String endTime, int maxN) {
		// Obtém a sessão:
		Session session = this.sessionTable.fromCookie(cookie);
		if (session == null) {
			return html.errorPage("no session with this cookie.");
		}
		User user = this.userTable.getUserByLoginName(loginName);
		// ??{ ... get specified messages from {user} ... }??
		return html.messageListPage(cookie, loginName, user.getMessages(0, 10), maxN);
	}
}
