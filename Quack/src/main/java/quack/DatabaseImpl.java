package quack;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DatabaseImpl implements Database {
	private Connection con = null;

	private String db_name;
	private String db_login_name;
	private String db_pass;

	@Override
	public Connection getConnection() throws ClassNotFoundException,
			SQLException {
		if (con != null && !con.isClosed())
			return con;

		Class.forName("com.mysql.jdbc.Driver");
		con = DriverManager.getConnection(
				"jdbc:mysql://sql2.lab.ic.unicamp.br:3306/" + db_name,
				db_login_name, db_pass);
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
	public PreparedStatement getStatement(String query)
			throws ClassNotFoundException, SQLException {
		return con.prepareStatement(query);
	}

	@Override
	public void initialize(String dbLoginName, String dbName, String dbPassword) {
		db_login_name = dbLoginName;
		db_name = dbName;
		db_pass = dbPassword;
	}

	@Override
	public void loadDatabase(UserTable userTable, Long nextUserId, Long nextMessageId) {
		try {
			getConnection();
			ResultSet rs = getStatement("SELECT * FROM user;")
					.executeQuery();
			while (rs.next()) {
				User u = new UserImpl();
				if (u.initialize(rs.getString("login_name"),
						rs.getString("email"), rs.getString("full_name"),
						rs.getString("password"), rs.getLong("id"))) {

					nextUserId = Math.max(nextUserId, u.getDbIndex());

					ResultSet rs2 = getStatement(
							"SELECT * FROM message WHERE user_id="
									+ String.valueOf(u.getDbIndex()))
							.executeQuery();
					while (rs2.next()) {
						Message m = new MessageImpl();

						if (m.initialize(rs2.getString("body"), u, rs2
								.getLong("id"), timestampFromString(rs2
								.getString("created"))) == false)
							System.out.println("Erro ao carregar mensagens");
						else {
							u.addMessage(m);
							nextMessageId = Math.max(nextMessageId,
									m.getDBIndex());
						}
					}
					userTable.add(u);
				}

				else {
					System.out.println("Problema no carregamento do usuário!");
				}
			}

			ResultSet rs3 = getStatement("SELECT * FROM contact;").executeQuery();
			while (rs3.next()) {
				Contact c = new ContactImpl();
				User s = userTable.getUserById(rs3.getLong("source_id"));
				User t = userTable.getUserById(rs3.getLong("target_id"));
				String st = rs3.getString("status");
				Long lm = rs3.getLong("last_modified");
				c.initialize(s, t, lm, st);
				s.addDirectContact(c);
				t.addReverseContact(c);
			}

			nextUserId++;
			nextMessageId++;

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static long timestampFromString(String time) {
		time = time.replaceAll("[()]", "");
		String date[] = time.split("[- :\\.]");

		// TODO - consertar timezone (ID de TimeZone eh uma string
		// do tipo "Brazil/East", e nao (-0300))
		Calendar a = Calendar.getInstance();
		a.set(Integer.parseInt(date[0]), Integer.parseInt(date[1])-1,
				Integer.parseInt(date[2]), Integer.parseInt(date[3]),
				Integer.parseInt(date[4]), Integer.parseInt(date[5]));

		return a.getTimeInMillis() / 1000;
	}

	@Override
	public void insertUser(User user) {

		try {
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			getConnection();
			getStatement("INSERT INTO user (id, login_name, full_name, email, password, created)"
					+ "VALUES ("+user.getDbIndex()+",'"+user.getLoginName()+
					"','"+user.getFullName()+"','"+
					user.getEmail()+"','"+user.getPassword()+"','"
					+ dateFormat.format(new Date(user.getCreationTime()*1000))+
					"');").execute();
			commit();
		
			System.out.println("User inserido na tabela");
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void modifyContact(User sessionUser, User contactUser, String status) {
		try {
			
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			getConnection();
			getStatement("UPDATE contact SET status="+status+", last_modified='"+
					dateFormat.format(new Date(Calendar.getInstance()
							.getTimeInMillis()))+"' where source_id='"+
			sessionUser.getDbIndex() +"' and target_id='"+ contactUser.getDbIndex() +"';").execute();
			commit();	
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void insertContact (User sessionUser, User contactUser, String status) {

		try {
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			getConnection();
			getStatement("INSERT INTO contact VALUES("+ sessionUser.getDbIndex()+","+contactUser.getDbIndex()+",'"+
			dateFormat.format(new Date(Calendar.getInstance()
					.getTimeInMillis()))+"',"+status+");").execute();
			commit();	
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean addMessage(Message message, User user) {

		boolean success = false;
		try {
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			getConnection();
			getStatement("INSERT INTO message (id, user_id, body, parent,created)"
				+ "VALUES ("+message.getDBIndex()+","+user.getDbIndex()+
				",'"+message.getText()+"',NULL,'"
				+ dateFormat.format(new Date(message.getDate()*1000))+
				"');").execute();
			commit();
			
			user.addMessage(message);
			
			System.out.println("Mensagem inserida na tabela");
			success = true;
		} catch (ClassNotFoundException e) {	
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return success;
	}
}
