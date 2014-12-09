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
	public boolean closeConnection() {
		boolean success = false;
		try {
			if (con != null && !con.isClosed()) {
				con.close();
				success = true;
			}
		} catch (SQLException e) {
			System.out.println("Erro ao encerrar conexão no banco de dados.");
			e.printStackTrace();
		}
		return success;
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
						rs.getString("password"), rs.getLong("id"), rs.getString("created"))) {

					nextUserId = Math.max(nextUserId, u.getDbIndex());

					ResultSet rs2 = getStatement(
							"SELECT * FROM message WHERE user_id="
									+ String.valueOf(u.getDbIndex()))
							.executeQuery();
					while (rs2.next()) {
						Message m = new MessageImpl();
						Timestamp t = new TimestampImpl();

						if (m.initialize(rs2.getString("body"), u, rs2
								.getLong("id"), t.fromString(rs2
								.getString("created"), "yyyy-MM-dd HH:mm:ss.SS")) == false)
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

		} catch (SQLException e) {
			System.out.println("Ocorreu um erro ao executar uma query.");
			e.printStackTrace();
		}
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
		} catch (SQLException e) {
			System.out.println("Ocorreu um erro ao executar uma query.");
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
		} catch (SQLException e) {
			System.out.println("Ocorreu um erro ao executar uma query.");
			e.printStackTrace();
		}
	}

	@Override
	public void modifyUser(User user) {
		try {
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			getConnection();
			getStatement("UPDATE user SET full_name ="+user.getFullName()+", last_modified='"+
					dateFormat.format(new Date(Calendar.getInstance().getTimeInMillis()))+
					"', pasword = '"+user.getPassword()+"' where id='"+user.getDbIndex() +"';").execute();
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
		} catch (SQLException e) {
			System.out.println("Ocorreu um erro ao executar uma query.");
			e.printStackTrace();
		}
	}

	@Override
	public boolean addMessage(Message message) {

		boolean success = false;
		try {
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			getConnection();
			getStatement("INSERT INTO message (id, user_id, body, parent,created)"
				+ "VALUES ("+message.getDBIndex()+","+message.getUser().getDbIndex()+
				",'"+message.getText()+"',NULL,'"
				+ dateFormat.format(new Date(message.getDate()*1000))+
				"');").execute();
			commit();
			
			message.getUser().addMessage(message);
			
			System.out.println("Mensagem inserida na tabela");
			success = true;
		} catch (SQLException e) {
			System.out.println("Ocorreu um erro ao executar uma query.");
			e.printStackTrace();
		}
		return success;
	}
	
	// Abre conexao {this} com servidor do banco de dados persistente. Retorna {null} se
	// houve algum problema.
	private Connection getConnection() {
		try {
			if (con == null || con.isClosed()) {
				Class.forName("com.mysql.jdbc.Driver");
				con = DriverManager.getConnection(
					"jdbc:mysql://sql2.lab.ic.unicamp.br:3306/" + db_name,
					db_login_name, db_pass);
				con.setAutoCommit(false);
			}
		} catch (Exception e) {
			System.out.println("Erro ao criar conexão com o banco de dados.");
			e.printStackTrace();
		}
		return con;
	}

	// Faz todas as alterações desde o último commit/rollback permanente e libera qualquer lock
	// do bando de dados para a esta conexão. Retorna {true} se a operação teve sucesso,
	// ou {false} caso contrario.	
	private boolean commit() {
		boolean success = false;
		try {
			con.commit();
			success = true;
		} catch (SQLException e) {
			System.out.println("Erro ao commitar no banco de dados.");
			e.printStackTrace();
		}
		return success;
	}

	// Prepara uma query SQL pré compilada. Essa função não executa a query
	// para dar liberdade ao usuário de definir argumentos na string SQL
	// para, enfim, executá-la. Retorna {null} se houve algum problema.
	private PreparedStatement getStatement(String query){
		PreparedStatement statement = null;
		try {
			statement = con.prepareStatement(query);
		} catch (SQLException e) {
			System.out.println("Erro ao preparar query do banco de dados.");
			e.printStackTrace();
		}
		return statement;
	}

}


