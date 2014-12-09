package quack;

public interface Database {
	
	// Interface com o banco de dados persistente.
	// Enquanto o servidor Quack estiver rodando, deve existir apenas uma
	// instância desta classe, que encapsula a implementação do banco de dados
	// persitente.  Os métodos abaixo permitem acessar o banco de maneira
	// independente da implementação. 
	// ??{ Ainda não está nada independente. Todos os detalhes em {ServerImpl} que 
	// dependem do banco ser implementado em MySQL devem ser movidos para {DatabaseImpl}
	// e escondidos. }??

	public boolean closeConnection();
	// Fecha a conexao {this} com banco de dados MySQL. Retorna {true} se a conexao foi
	// encerrada, ou {false} se houve algum problema.
	
	public void initialize(String dbLoginName, String dbName, String dbPassword);
	// Inicializa um banco de dados com parametros de login {dbLoginName}, senha {dbPassword}
	// e banco de nome {dbName}
	
	public void loadDatabase(UserTable userTable, Long nextUserId, Long nextMessageId);
	// Carrega a base de dados {this.database} na memória, criando os objetos
	// {User,Message,Contact} e ligando-os entre si. Supõe que a conexão com o
	// servidor da base de dados já foi estabelecida.
	
	public void insertUser(User user);
	// Insere um usuário {User} no banco de dados.
	
	public void modifyContact(User sessionUser, User contactUser, String status);
	// Altera o contato entre dois usuários, {sessionUser} e {contactUser} para. {Status} marca o tipo de relacao que os usuarios terao. Podera
	// ser follow para seguir, block para bloquear, ou inactive para nao ter relacao nenhuma.
	
	public void insertContact (User sessionUser, User contactUser, String status);
	// Cria um novo contato entre dois usuários, {sessionUser} e {contactUser} para. {Status} marca o tipo de relacao que os usuarios terao. Podera
	// ser follow para seguir, block para bloquear, ou inactive para nao ter relacao nenhuma.
	
	public boolean addMessage(Message message, User user);
	// Adiciona uma mensagem {Message} a um usuario {User} no banco de dados. Retorna true se
	// a operacao teve sucesso, ou false caso contrario.
}