package quack;

import java.util.Date;

public class ContactImpl implements Contact {

	private User sourceUser;
	private User targetUser;
	private Date lastModified;
	private boolean blocked;
	
	public ContactImpl(User source, User target, Date lastModified, boolean blocked) {
		sourceUser = source;
		targetUser = target;
		this.lastModified = lastModified;
		this.blocked = blocked;
	}
	
	@Override
	public User source() {
		return sourceUser;
	}

	@Override
	public User target() {
		return targetUser;
	}

	@Override
	public Date lastModified() {
		return lastModified;
	}

	@Override
	public boolean blocked() {
		return blocked;
	}

}
