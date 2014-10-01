package quack;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseImpl implements Database {
	private Connection con = null;

	private String db_name;
	private String bd_user_name;
	private String bd_pass;


	public DatabaseImpl(String userName, String dbName, String dbPassword) {
		bd_user_name = userName;
		db_name = dbName;
		bd_pass = dbPassword;
	}

	@Override
	public Connection getConnection() throws ClassNotFoundException, SQLException {
		if (con != null && !con.isClosed())
			return con;

		Class.forName("com.mysql.jdbc.Driver");
		// TODO: definir URL.
		con = DriverManager.getConnection("url" + db_name, bd_user_name, bd_pass);
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
}
