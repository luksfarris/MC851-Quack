package quack;

import java.util.ArrayList;
import java.util.List;

public class SessionTableImpl implements SessionTable {

	List<Session> sessions;

	public void initialize() {
		this.sessions = new ArrayList<Session>();
	}

	@Override
	public void add(Session session) {
		sessions.add(session);
	}

	@Override
	public void delete(Session session) {
		for (int i=0 ; i<sessions.size() ; i++) {
			Session currentSession = sessions.get(i);
			if (currentSession.getCookie().equals(session.getCookie())){
				sessions.remove(i);
				break;
			}
		}
	}

	@Override
	public Session getSessionByUser(User user) {
		
		Session session = null;
		
		for (int i=0 ; i<sessions.size() ; i++) {
			Session currentSession = sessions.get(i);
			if (currentSession.getUser().getDbIndex() == user.getDbIndex()) {
				session = currentSession;
				break;
			}
		}
		return session;
	}

	@Override
	public Session getSessionByCookie(String cookie) {
		
		Session session = null;
		for (int i=0 ; i<sessions.size() ; i++) {
			Session currentSession = sessions.get(i);
			if (currentSession.getCookie() == cookie) {
				session = currentSession;
				break;
			}
		}
		return session;
	}

	@Override
	public int getSessionCount() {
		return sessions.size();
	}
}
