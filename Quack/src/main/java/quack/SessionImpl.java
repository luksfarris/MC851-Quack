package quack;

public class SessionImpl implements Session {
	
	private User loggedUser;
	private String cookie;
	
	public void open(User user, String cookie) {
		loggedUser = user;
		this.cookie = cookie;
	}

	public String getCookie() {
		return cookie;
	}

	public User getUser() {
		return loggedUser;
	}
}
