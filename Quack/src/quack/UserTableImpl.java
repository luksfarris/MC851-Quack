package quack;

import java.util.List;

public class UserTableImpl implements UserTable {

	private List<User> table;
	
	@Override
	public void initialize(Database db) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public User getUserByLogin(String login) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> getUsersByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> getUsersByLocation(String location) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void add(User user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remove(User user) {
		// TODO Auto-generated method stub
		
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
