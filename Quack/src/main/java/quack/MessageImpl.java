package quack;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import java.util.Date;
import java.text.SimpleDateFormat;

@Entity
public class MessageImpl implements Message {
	@Id
	@GeneratedValue
	private long DBIndex;

	@Temporal(TemporalType.DATE)
	private long timestamp;
	private String body;
	private User user;
	private Message parent;

	public MessageImpl() {
	}

	@Override
	public String getText() {
		if (parent == null)
			return body;

		else
			return parent.getText();
	}

	@Override
	public User getUser() {
		return user;
	}

	@Override
	public long getDate() {
		return timestamp;
	}

	public String getFormattedDate(String format) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		Date date = new Date(this.getDate());
		return dateFormat.format(date);
	}

	@Override
	public long getDBIndex() {
		return DBIndex;
	}

	@Override
	public boolean initialize(String body, User user, long DBIndex, long timestamp) {
		this.timestamp = timestamp;
		this.body = body;
		this.user = user;
		this.parent = null;
		this.DBIndex = DBIndex;
		return true;
	}

	@Override
	public boolean initialize(User user, Message parent, long DBIndex, long timestamp) {
		
		if (parent.getParent() != null) {
			parent = parent.getParent();
		}
		this.timestamp = timestamp;
		this.body = null;
		this.user = user;
		this.parent = parent;
		this.DBIndex = DBIndex;
		return true;
	}

	@Override
	public Message getParent() {
		return parent;
	}
}
