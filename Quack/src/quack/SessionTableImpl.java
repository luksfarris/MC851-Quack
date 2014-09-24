package quack;

import java.util.List;

public class SessionTableImpl implements SessionTable {

	List<Session> sessions;

	public void initialize() {
		this.sessions = null;
	}

	public Session fromUser(User user) {
		Session session = null;
		// ??{ ... procura na lista {this.sessions} .. }??
		return session;
	}

	@Override
	public void add(Session session) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Session session) {
		// TODO Auto-generated method stub

	}

	@Override
	public Session fromCookie(String cookie) {
		// TODO Auto-generated method stub
		return null;
	}

}
