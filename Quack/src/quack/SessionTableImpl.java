package quack;

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

}
