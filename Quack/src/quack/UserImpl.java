package quack;

import java.util.*;

public class UserImpl implements User
{
	private long id;
	private String login, password, profileMsg, avatar, 
					name, email, location, website;
	private Calendar createdOn;
	private UserPrivacy privacy;

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
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean changeProfilePic(String filename) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean changeLocation(String newLocation) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean changePassword(String newPassword) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean changeEmail(String newEmail) {
		// TODO Auto-generated method stub
		return false;
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
	public boolean changePrivacy(UserPrivacy privacy) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean changeWebsite(String newWebsite) {
		// TODO Auto-generated method stub
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
