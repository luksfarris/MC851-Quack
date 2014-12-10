package quack;

import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletContext;

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
	
	public void loadDatabase(UserTable userTable, Long nextUserId, Long nextMessageId, ServletContext context);
	// Carrega a base de dados {this.database} na memória, criando os objetos
	// {User,Message,Contact} e ligando-os entre si. Supõe que a conexão com o
	// servidor da base de dados já foi estabelecida.
	
	public void insertUser(User user);
	// Insere um usuário {User} no banco de dados.
	
	public void modifyContact(Contact contact);
	// Altera o contato {contact} entre dois usuários para o status, que marca o tipo de relacao que os usuarios terao. Podera
	// ser follow para seguir, block para bloquear, ou inactive para nao ter relacao nenhuma.
	
	public void modifyUser(User user);
	// Altera o usuário com os novos dados.
	
	public void insertContact (Contact contact);
	// Cria um novo contato {contact} entre dois usuários.O status do contact marca o tipo de relacao que os usuarios terao. Podera
	// ser follow para seguir, block para bloquear, ou inactive para nao ter relacao nenhuma.
	
	public boolean addMessage(Message message);
	// Adiciona uma mensagem {Message} a um usuario no banco de dados. Retorna true se
	// a operacao teve sucesso, ou false caso contrario.
	
	public void insertImage(User sessionUser, InputStream fileStream);
	// Adiciona uma imagem de perfil codificada em {fileStream} de um usuario {sessionUser}
	// na base de dados do sistema.
	
	public void loadProfileImage(User user, ServletContext context);
	// Busca a imagem de perfil de um usuário {user} e cria o arquivo
	// correspondente no sistema de arquivos do servidor.

}