package quack;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface Database {
	
	// Interface com o banco de dados persistente.
	// Enquanto o servidor Quack estiver rodando, deve existir apenas uma
	// instância desta classe, que encapsula a implementação do banco de dados
	// persitente.  Os métodos abaixo permitem acessar o banco de maneira
	// independente da implementação. 
	// ??{ Ainda não está nada independente. Todos os detalhes em {ServerImpl} que 
	// dependem do banco ser implementado em MySQL devem ser movidos para {DatabaseImpl}
	// e escondidos. }??

	public Connection getConnection() throws ClassNotFoundException, SQLException;
	// Abre conexao {this} com servidor do banco de dados persistente.
	
	public void closeConnection() throws ClassNotFoundException, SQLException;
	// Fecha a conexao {this} com banco de dados MySQL
	
	public void commit() throws SQLException;
	// Faz todas as alterações desde o último commit/rollback permanente e libera qualquer lock
	// do bando de dados para a esta conexão.
	
	public PreparedStatement getStatement(String query) throws ClassNotFoundException, SQLException;
	// Prepara uma query SQL pré compilada. Essa função não executa a query
	// para dar liberdade ao usuário de definir argumentos na string SQL
	// para, enfim, executá-la.

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
	
	public void modifyUser(User user);
	// Altera o usuário com os novos dados.
	
	public void insertContact (User sessionUser, User contactUser, String status);
	// Cria um novo contato entre dois usuários, {sessionUser} e {contactUser} para. {Status} marca o tipo de relacao que os usuarios terao. Podera
	// ser follow para seguir, block para bloquear, ou inactive para nao ter relacao nenhuma.
	
	public boolean addMessage(Message message, User user);
	// Adiciona uma mensagem {Message} a um usuario {User} no banco de dados. Retorna true se
	// a operacao teve sucesso, ou false caso contrario.

	
}