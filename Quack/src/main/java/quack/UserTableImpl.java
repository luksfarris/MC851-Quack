package quack;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class UserTableImpl implements UserTable {

	private List<User> table = new ArrayList<User>();

	@Override
	public void initialize(Database db) {
		try {
			db.getConnection();
			ResultSet rs = db.getStatement("SELECT * FROM user;").executeQuery();
			while(rs.next()){
				User u = new UserImpl();
				if(u.initialize(rs.getString("login_name"), 
						rs.getString("email"),
						rs.getString("full_name"),
						rs.getString("password"))){
					table.add(u);
				} else {
					System.out.println("Problema no carregamento do usuário!");
				}
			}
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	@Override
	public void add(User user) {
		
		this.table.add(user);
	}

	@Override
	public User getUserByLogin(String loginName, String password) {
		for(User u : this.table){
			if(u.getLoginName().equals(loginName))
				return u;
		}
		return null;
	}
			
	public List<User> listUsersByFullName(String fullName) {
		List<User> l = new LinkedList<User>();
		
		for(User u: table){
			if(u.getFullName().equals(fullName))
				l.add(u);
		}
		return null;
	}

	@Override
	public User getUserByEmail(String email) {
		for(User u : this.table){
			if(u.getEmail().equals(email))
				return u;
		}		return null;
	}

	@Override
	public int getUserCount() {
		return table.size();
	}


}
