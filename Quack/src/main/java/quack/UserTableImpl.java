package quack;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class UserTableImpl implements UserTable {

	private List<User> table = new ArrayList<User>();

	@Override
	public void initialize() {
	
	}

	@Override
	public void add(User user) {
		
		this.table.add(user);
	}

	@Override
	public User getUserByLogin(String loginName) {
		for(User u : this.table){
			if(u.getLoginName().equals(loginName))
				return u;
		}
		return null;
	}
			
	public List<User> listUsersByFullName(String name) {
		List<User> l = new LinkedList<User>();
		
		for(User u: table){
			if(u.getFullName().contains(name))
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

	@Override
	public User getUserById(long id) {
		for(User u : this.table){
			if(u.getDbIndex() == id)
				return u;
		}		return null;
	}

	@Override
	public User getUserByLoginPassword(String loginName, String password) {
		for(User u : this.table){
			if(u.getLoginName().equals(loginName) && u.getPassword().equals(password))
				return u;
		}
		return null;
	}

	@Override
	public List<User> getAllUsers() {
		// TODO Auto-generated method stub
		return this.table;
	}

}
