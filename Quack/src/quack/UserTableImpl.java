package quack;

import java.util.*;

public class UserTableImpl implements UserTable {

	private List<User> table;
	
	@Override
	public void initialize(Database db) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public User getUserByLoginName(String loginName) {
		
		for(User u: table){
			if(u.getLoginName() == loginName)
				return u;
		}
		
		return null;
	}

	@Override
	public List<User> listUsersByFullName(String fullName) {
		List<User> l = new LinkedList<User>();
		
		for(User u: table){
			if(u.getFullName() == fullName)
				l.add(u);
		}
		return l;
	}

	@Override
	public void add(User user) {
		// TODO Auto-generated method stub
		table.add(user);
	}

	/*
	@Override
	public void remove(User user) {
		table.remove(user);
		
	}
	 */

	@Override
	public User getUserByEmail(String email) {
		// TODO Auto-generated method stub
		return null;
	}

}
