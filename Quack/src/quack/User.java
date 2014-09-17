package quack;

import java.util.List;

public interface User 
{
	/* private long id;
	private String login, password, profileMsg, avatar, 
					name, email, location, website;
	private Calendar createdOn;*/
	
	public boolean follow(User followed);
	public boolean unfollow(User followed);
	public boolean changeProfileMsg(String newMsg);
	public boolean changeProfilePic(String filename);
	public boolean changeLocation(String newLocation);
	public boolean changePassword(String newPassword);
	public boolean changeEmail(String newEmail);
	public boolean block(User user);
	public boolean unblock(User user);
	public boolean changePrivacy(UserPrivacy privacy);
	public boolean changeWebsite(String newWebsite);
	public List<User> following();
	public List<User> followers();
	public int tweetCount();
	public int mediaCount();
	public int followingCount();
	public int followerCount();
	public int favoriteCount();
	
}
