package quack;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseImpl implements Database {
	private Connection con = null;

	private String db_name;
	private String db_login_name;
	private String db_pass;

	@Override
	public Connection getConnection() throws ClassNotFoundException, SQLException {
		if (con != null && !con.isClosed())
			return con;

		Class.forName("com.mysql.jdbc.Driver");
		con = DriverManager.getConnection("jdbc:mysql://sql2.lab.ic.unicamp.br:3306/" + db_name, db_login_name, db_pass);
		con.setAutoCommit(false);

		return con;
	}

	@Override
	public void commit() throws SQLException {
		con.commit();
	}

	@Override
	public void closeConnection() throws ClassNotFoundException, SQLException {
		if (con != null && !con.isClosed())
			con.close();
	}

	@Override
	public PreparedStatement getStatement(String query) throws ClassNotFoundException, SQLException {
		return con.prepareStatement(query);
	}

	@Override
	public void initialize(String dbLoginName, String dbName, String dbPassword) {
		db_login_name = dbLoginName;
		db_name = dbName;
		db_pass = dbPassword;
	}
}
