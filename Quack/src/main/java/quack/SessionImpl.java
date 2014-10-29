package quack;

import java.util.UUID;

public class SessionImpl implements Session {

	private User loggedUser;
	private String cookie;
	
	public void open(User user, String cookie) {
		loggedUser = user;
		if (cookie == null || cookie.length() == 0) {
			this.cookie =  UUID.randomUUID().toString();
		} else {
			this.cookie = cookie;
		}
	}

	public String getCookie() {
		return cookie;
	}

	public User getUser() {
		return loggedUser;
	}
}
