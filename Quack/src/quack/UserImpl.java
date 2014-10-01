package quack;

import java.util.*;

public class UserImpl implements User
{
	private long id;
	private String userName, password, profileMsg, avatar, 
					fullName, email, website;
	private Calendar createdOn;
	
	private List<Contact> directContacts; // Arestas de saida do vertice {this} no grafo de relacoes.
	private List<Contact> reverseContacts; // Arestas de entrada do vertice {this} no grafo de relacoes.
	private List<Message> messages; // Lista de mensagens de autoria {this}.
	
	@Override
	public long getId() {
		return this.id;
	}


	@Override
	public boolean follow(User followed) {
		for (Contact c : reverseContacts) {
			if (c.source().equals(followed) && c.blocked()) {
				return false;
			}
		}
		
		for (Contact c : directContacts) {
			if (c.target().equals(followed) && c.blocked()) {
				return false;
			}
		}
		
		Contact relation = new ContactImpl(followed, this, Calendar.getInstance(), false);
		return directContacts.add(relation) && followed.getReverseContacts().add(relation);
	}
	
	
	
	@Override
	public boolean unfollow(User followed) {
		for (Contact c : directContacts) {
			if (c.target().equals(followed) && !c.blocked()) {
				directContacts.remove(c);
				for (Contact flwContact : followed.getReverseContacts()) {
					if (flwContact.source().equals(this) && !flwContact.blocked())
						followed.getReverseContacts().remove(flwContact);
				}
				return true;
			}
		}
		
		return false;
	}
	

	@Override
	public boolean block(User user) {
		unfollow(user);
		user.unfollow(this);
		Contact relation = new ContactImpl(user, this, Calendar.getInstance(), true);
		return directContacts.add(relation) && user.getReverseContacts().add(relation);
	}
	
	@Override
	public boolean unblock(User user) {
		for (Contact c : directContacts) {
			if (c.target().equals(user) && c.blocked()) {
				directContacts.remove(c);
				for (Contact flwContact : user.getReverseContacts()) {
					if (flwContact.source().equals(this) && !flwContact.blocked())
						user.getReverseContacts().remove(flwContact);
				}
				return true;
			}
		}
		return false;
	}
	

	@Override
	public boolean changePassword(String newPassword) {
		this.password = newPassword;
		return true;
	}
	@Override
	public boolean changeEmail(String newEmail) {
		this.email = newEmail;
		return true;
	}
	

	@Override
	public List<User> following() {
		List<User> follow = new LinkedList<User>();
		
		for (Contact c : directContacts) {
			if(!c.blocked())
				follow.add(c.target());
		}
		
		return follow;
	}
	@Override
	public List<User> followers() {
		List<User> follower = new LinkedList<User>();
		
		for (Contact c : reverseContacts) {
			if(!c.blocked())
				follower.add(c.target());
		}
		
		return follower;
	}
	
	
	public int mediaCount() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public int followingCount() {
		return following().size();
	}
	
	public int followerCount() {
		return followers().size();
	}
	
	public int favoriteCount() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public List<Message> getMessages(int start, int end) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<Message> getFollowingMessages(int start, int end) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getUserName() {
		return this.userName;
	}

	
	public String getProfileMsg() {
		return this.profileMsg;
	}
	
	
	public String getFullName() {
		return this.fullName;
	}

	@Override
	public String getEmail() {
		return this.email;
	}

	

	@Override
	public Calendar getCreatedDate() {
		return this.createdOn;
	}


	@Override
	public List<Contact> getDirectContacts() {
		return directContacts;
	}


	@Override
	public List<Contact> getReverseContacts() {
		return reverseContacts;
	}

	@Override
	public boolean checkPassword(String password) {
		return this.password.equals(password);
	}
	
	final String getPassword() {
		return password;
	}

	@Override
	public boolean initialize(String userName, String email, String fullName,
			String password) {
		this.userName = userName;
		this.fullName = fullName;
		this.email = email;
		this.password = password;
		return true;
	}


	@Override
	public List<Message> getMessages() {
		return messages;
	}


	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

}
