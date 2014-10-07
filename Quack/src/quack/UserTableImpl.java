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

	@Override
	public User getUserByEmail(String email) {
		for(User u : this.table){
			if(u.getEmail() == email)
				return u;
		}		return null;
	}

	@Override
	public List<User> listUsersByFullName(String name) {
		for(User u : this.table){
			if(u.getFullName() == name)
				return u;
		}		
		return null;
	}
}
