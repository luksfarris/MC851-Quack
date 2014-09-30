package quack;

import java.util.*;

public class UserImpl implements User
{
	private long id;
	private String login, password, profileMsg, avatar, 
					name, email, website;
	private Calendar createdOn;
	
	private List<Contact> contatosDiretos; // Arestas de saida do vertice {this} no grafo de relacoes.
	private List<Contact> contatosReversos; // Arestas de entrada do vertice {this} no grafo de relacoes.
	private List<Message> mensagens; // Lista de mensagens de autoria {this}.
	

	@Override
	public long getId() {
		return this.id;
	}

	@Override
	public boolean follow(User followed) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean unfollow(User followed) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean changeProfileMsg(String newMsg) {
		this.profileMsg = newMsg;
		return true;
	}
	@Override
	public boolean changeProfilePic(String filename) {
		this.avatar = filename;
		return true;
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
	public boolean block(User user) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean unblock(User user) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean changeWebsite(String newWebsite) {
		this.website = newWebsite;
		return false;
	}
	@Override
	public List<User> following() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<User> followers() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public int tweetCount() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public int mediaCount() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public int followingCount() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public int followerCount() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
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
}
