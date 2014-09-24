package quack;

import java.util.List;

public interface Database {

	// Metodo para criar conexao com banco de dados
	public boolean connect();

	// Metodo para fechar a conexao com banco de dados
	public boolean closeConnection();

	// Metodo para executar uma query
	public List<Object> runQuery(String query);

	// Metodo para executar um comando no banco de dados
	public boolean runCommand(String command);

	public void initialize(String dbName, String dbPassword);
	// Metodo para inicializar o banco de dados utilizando o nome do bando
	// {dbName} e a senha {dbPassword}.
}