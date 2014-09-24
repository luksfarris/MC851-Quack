package quack;

public class ServerImpl implements Server {

	SessionTable sessionTable = null; // Conjunto de sessões abertas.
	Database database = null; // Conexão com a base de dados persistente.
	// As tabelas abaixo são cópias na memória dos objetos na base de dados.
	UserTable userTable = null; // Conjuto de usuários cadastrados.
	ContactTable contactTable = null; // Usuários seguidos, bloqueados, etc. de
										// cada usuário.
	MessageTable messageTable = null; // Conjunto de mensagens armazenadas.

	// ??{ O s procedimentos a seguir deveriam construir e devolver
	// a construir página HTML de resultado adequada. }??

	public void initialize(String dbName, String dbPassword) {
		// Cria a tabela de sessões abertas:
		this.sessionTable = new SessionTableImpl();
		this.sessionTable.initialize();

		// Conecta com a base de dados persitente:
		this.database = new DatabaseImpl();
		this.database.initialize(dbName, dbPassword);

		// Cria a tabela de usuários:
		this.userTable = new UserTableImpl();
		this.userTable.initialize(this.database);

		// Cria a tabela de contatos entre usuários:
		this.contactTable = new ContactTableImpl();
		this.contactTable.initialize(this.database);

		// Cria a tabela de mensagens:
		this.messageTable = new MessageTableImpl();
		this.messageTable.initialize(this.database);
	}

	public String processRegistrationReq(String userName, String email,
			String fullName, String password) {
		// Verifica se já existe usuário com esse {userName} ou {email}:
		User user = this.userTable.fromUserName(userName);
		if (user != null) {
			return HTML.errorPage("username already taken");
		}
		user = this.userTable.fromEmail(email);
		if (user != null) {
			return HTML
					.errorPage("there is already a user account with that email");
		}

		// Cria o usuário e acrescenta à tabela:
		user = new UserImpl();
		user.initialize(userName, email, fullName, password);
		if (user == null) {
			return HTML.errorPage("user creation failed for unknown reason");
		}
		this.userTable.add(user);
		return HTML.loginPage();
	}

	public String processLoginReq(String userName, String password) {
		// Obtém o objeto que representa o usuário:
		User user = this.userTable.fromUserName(userName);
		if (user == null) {
			return HTML.errorPage("no such user");
		}
		// Verifica se a senha bate:
		if (!user.checkPassword(password)) {
			return HTML.errorPage("wrong password");
		}
		// Verifica se já existe sessão aberta para este usuário:
		Session session = this.sessionTable.fromUser(user);
		if (session != null) { // Fecha a sessão existente:
			this.sessionTable.delete(session);
			session.close();
		} else { // Cria instâcia de sessão:
			session = new SessionImpl();
		}

		// Cria um cookie para a sessão, e abre a mesma:
		String cookie = userName + randomString;
		session.open(user, cookie);
		this.sessionTable.add(session);
		return HTML.loginSuccessfulPage(cookie);
	}

	public String processLogoutReq(String cookie) {
		// Obtém a sessão:
		Session session = this.sessionTable.fromCookie(cookie);
		if (session == null) {
			return HTML.errorPage("no session with this cookie.");
		}
		// Fecha a sessão existente:
		this.sessionTable.delete(session);
		session.close();
		return HTML.homePage();
	}

	public String processShowOutMessagesReq(String cookie, String userName,
			String startTime, String endTime, int maxN) {
		// Obtém a sessão:
		Session session = this.sessionTable.fromCookie(cookie);
		if (session == null) {
			return HTML.errorPage("no session with this cookie.");
		}
		user = this.userTable.fromUserName(userName);
		// ??{ ... get specified messages from {user} ... }??
		return HTML.messageListPage(cookie, user, messages, maxN);
	}
}
