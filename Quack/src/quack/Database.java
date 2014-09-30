package quack;

import java.util.List;

public interface Database {

	public boolean connect();
	// Abre conexao {this} com banco de dados MySQL
	
	public boolean closeConnection();
	// Fecha a conexao {this} com banco de dados MySQL
	
	public List<Object> runQuery(String query);
	// Conexão {this} com banco de dados roda a query {query}
	// retornando uma lista de objetos correspondentes a
	// resposta obtida. Retorna uma lista vazia caso a query
	// não tenha retornado nada.
	
	public boolean runCommand(String command);
	// Conexão {this} com o banco de dados roda o comando {command}
	// na base de dados, retornando se houve sucesso na execução.
	
	public void initialize(String dbName, String dbPassword);
	// Metodo para inicializar o banco de dados utilizando o nome do banco
	// {dbName} e a senha {dbPassword}.
}
