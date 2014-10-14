package quack;

import java.util.*;

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

	public boolean initialize(String loginName, String email, String fullName, String password) {
		this.loginName = loginName;
		this.fullName = fullName;
		this.email = email;
		this.password = password;
		
		// TODO - calcular o dbindex do banco e colocar no objeto
		this.dbIndex = -1;
		this.creationTime = Calendar.getInstance().getTimeInMillis() / 1000;
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
			if (c.target().equals(target)) {
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
		for (Message m : this.messages) {
			if (m.getDate() < startTime || i > maxN)
				break;
			if (m.getDate() > (startTime)
					&& (endTime != 0.0 && m.getDate() < (endTime))) {
				trechoMsgs.add(m);
				i++;
			}
		}
		return trechoMsgs;

		/*
		 * // TODO Auto-generated method stub return null;
		 * //Message.extractMessageListSegment(this.messages, startTime,
		 * endTime, maxN);;
		 */
	}

}
