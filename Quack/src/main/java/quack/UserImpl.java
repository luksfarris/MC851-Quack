package quack;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class UserImpl implements User {
	private long dbIndex;
	private String loginName;
	private String password;
	private String fullName;
	private String email;
	private long creationTime;

	private List<Contact> directContacts; // Arestas de saida do vertice {this}
											// no grafo de relacoes.
	private List<Contact> reverseContacts; // Arestas de entrada do vertice
											// {this} no grafo de relacoes.
	private List<Message> messages; // Lista de mensagens de autoria {this}.

	@Override

	public boolean initialize(String loginName, String email, String fullName, String password, long dbIndex) {
		Timestamp t = new TimestampImpl();
		this.loginName = loginName;
		this.fullName = fullName;
		this.email = email;
		this.password = password;
		this.dbIndex = dbIndex;
		this.creationTime = t.now();
		
		this.directContacts = new LinkedList<Contact>();
		this.reverseContacts = new LinkedList<Contact>();
		this.messages = new LinkedList<Message>();
		return true;
	}
	
	@Override
	public boolean initialize(String loginName, String email, String fullName,
			String password, long dbIndex, String created) {
		Timestamp t = new TimestampImpl();
		this.loginName = loginName;
		this.fullName = fullName;
		this.email = email;
		this.password = password;
		this.dbIndex = dbIndex;
		this.creationTime = t.fromString(created);
		
		this.directContacts = new LinkedList<Contact>();
		this.reverseContacts = new LinkedList<Contact>();
		this.messages = new LinkedList<Message>();
		return true;
	}

	@Override
	public String getLoginName() {
		return this.loginName;
	}

	public String getFullName() {
		return this.fullName;
	}

	@Override
	public String getEmail() {
		return this.email;
	}

	@Override
	public void setEmail(String newEmail) {
		this.email = newEmail;
	}

	@Override
	public boolean checkPassword(String password) {
		return this.password.equals(password);
	}

	@Override
	public void setPassword(String newPassword) {
		this.password = newPassword;
	}

	@Override
	public long getCreationTime() {
		return this.creationTime;
	}

	@Override
	public long getDbIndex() {
		return this.dbIndex;
	}

	@Override
	public List<Contact> getDirectContacts() {
		return this.directContacts;
	}

	@Override
	public List<Contact> getReverseContacts() {
		return this.reverseContacts;
	}

	@Override
	public Contact getDirectContact(User target) {
		for (Contact c : this.directContacts) {
			if (c.target().getLoginName().equals(target.getLoginName())) {
				return c;
			}
		}
		return null;
	}

	@Override
	public Contact getReverseContact(User source) {
		for (Contact c : this.reverseContacts) {
			if (c.source().equals(source)) {
				return c;
			}
		}
		return null;
	}

	@Override
	public void addDirectContact(Contact contact) {
		assert (this.getDirectContact(contact.target()) == null);
		this.directContacts.add(contact);
	}

	@Override
	public void addReverseContact(Contact contact) {
		assert (this.getReverseContact(contact.source()) == null);
		this.reverseContacts.add(contact);
	}

	@Override
	public List<Message> getPostedMessages() {
		return this.messages;
	}

	@Override
	public List<Message> getPostedMessages(long startTime, long endTime,
			long maxN) {
		List<Message> trechoMsgs = new LinkedList<Message>();
		long i = 0;
		
		if(maxN > 0) { // Pega as {maxN} primeiras mensagens
			ListIterator<Message> messages = this.messages.listIterator();
			while(messages.hasNext() && i < maxN){
				Message m = messages.next();
				if (m.getDate() < startTime)
					break;
				if ((m.getDate() >= startTime || startTime == -1) 
						&& (endTime == -1 || m.getDate() <= endTime)) {
					trechoMsgs.add(m);
					i++;
				}
			}
			
		} else {
			maxN = -maxN;
			ListIterator<Message> messages = this.messages.listIterator(this.messages.size());
			while(messages.hasPrevious() && i < maxN){
				Message m = messages.previous();
				if (m.getDate() < startTime)
					break;
				if ((m.getDate() >= startTime || startTime == -1) 
						&& (endTime == -1 || m.getDate() <= endTime)) {
					trechoMsgs.add(0, m);
					i++;
				}
			}
		}
		return trechoMsgs;
	}

	@Override
	public void addMessage(Message message) {
		assert(message.getUser().getLoginName().equals(loginName));
		this.messages.add(0, message);		
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public int followsCount() {
		int count = 0;
		for(Contact c: this.directContacts){
			if(c.status().equals("Follow"))
				count++;
		}
		
		return count;
	}

	@Override
	public int followersCount() {
		int count = 0;
		for(Contact c: this.reverseContacts){
			if(c.status().equals("Follow"))
				count++;
		}
		
		return count;
	}

	@Override
	public Message getMessageByDBIndex(long DBIndex) {
		for (Message m : this.messages)
			if (m.getDBIndex() == DBIndex)
				return m;
		return null;
	}

}
