package quack;

import java.util.*;

public class UserTableImpl implements UserTable {

	private List<User> table;

	@Override
	public void initialize(Database db) {
		
	}

	@Override
	public void add(User user) {
		
		this.table.add(user);
	}

	@Override
	public User getUserByLoginName(String loginName) {
		for(User u : this.table){
			if(u.getLoginName() == loginName)
				return u;
		}
		return null;
	}
			
	public List<User> listUsersByFullName(String fullName) {
		List<User> l = new LinkedList<User>();
		
		for(User u: table){
			if(u.getFullName() == fullName)
				l.add(u);
		}
		return null;
	}

	@Override
	public User getUserByEmail(String email) {
		for(User u : this.table){
			if(u.getEmail() == email)
				return u;
		}		return null;
	}


}
