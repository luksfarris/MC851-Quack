package quack;

import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class MessageImpl implements Message {
	@Id
	@GeneratedValue
	private long id;

	@Temporal(TemporalType.DATE)
	private long timestamp;
	private String body;
	private String loginName;
	private Message parent;

	public MessageImpl() {
	}

	@Override
	public String getText() {
		if (parent == null)
			return body;

		// TODO- mudar o caso de ter parent
		else
			return null;
	}

	@Override
	public User getUser() {
		// TODO: get user from UserImpl controller.
		return new UserTableImpl().getUserByLoginName(loginName);
	}

	@Override
	public long getDate() {
		return timestamp;
	}

	@Override
	public long getId() {
		return id;
	}

	@Override
	public boolean initialize(String body, User user) {
		this.timestamp = Calendar.getInstance().getTimeInMillis() / 1000;
		this.body = body;
		this.loginName = user.getLoginName();
		this.parent = null;
		return true;
	}

	@Override
	public boolean initialize(User user, Message parent) {
		if (parent.getParent() == null) {
			this.timestamp = Calendar.getInstance().getTimeInMillis() / 1000;
			this.body = parent.getText();
			this.loginName = user.getLoginName();
			this.parent = parent;
			return true;
		} else {
			return this.initialize(user, parent.getParent());
		}
	}

	@Override
	public Message getParent() {
		return parent;
	}
}
