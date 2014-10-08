package quack;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface Database {

	public Connection getConnection() throws ClassNotFoundException, SQLException;
	// Abre conexao {this} com banco de dados MySQL
	
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
}
