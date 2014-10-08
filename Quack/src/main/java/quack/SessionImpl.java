package quack;

import java.math.BigInteger;
import java.security.SecureRandom;

public class SessionImpl implements Session {

	private User loggedUser;
	private String cookie;
	private SecureRandom random = new SecureRandom();
	
	public void open(User user, String cookie) {
		loggedUser = user;
		if (cookie == null || cookie.length() == 0) {
			this.cookie =  new BigInteger(130, random).toString(32);
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

	public void close() {
		loggedUser = null;
		cookie = null;
	}
}
