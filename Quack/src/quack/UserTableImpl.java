package quack;

import java.util.*;

public class UserTableImpl implements UserTable {

	private List<User> table;
	
	@Override
	public void initialize(Database db) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public User getUserByLogin(String login) {
		
		for(User u: table){
			if(u.getLogin() == login)
				return u;
		}
		
		return null;
	}

	@Override
	public List<User> getUsersByName(String name) {
		List<User> l = new LinkedList<User>();
		
		for(User u: table){
			if(u.getName() == name)
				l.add(u);
		}
		return l;
	}

	@Override
	public void add(User user) {
		// TODO Auto-generated method stub
		table.add(user);
	}

	@Override
	public void remove(User user) {
		table.remove(user);
		
	}

	@Override
	public User fromUserName(String userName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User fromEmail(String email) {
		// TODO Auto-generated method stub
		return null;
	}

}
