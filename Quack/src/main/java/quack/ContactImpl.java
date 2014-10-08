package quack;

import java.util.Calendar;

public class ContactImpl implements Contact {

	private User sourceUser;
	private User targetUser;
	private Calendar lastModified;
	private boolean blocked;
	
	public ContactImpl() {
		
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
	public Calendar lastModified() {
		return lastModified;
	}

	@Override
	public boolean blocked() {
		return blocked;
	}

	@Override
	public void initialize(User source, User target, Calendar instance,
			String newStatus) {
		sourceUser = source;
		targetUser = target;
		this.lastModified = instance;
		if(newStatus.equals("Follow"))
			this.blocked = false;
		else
			this.blocked = true;
	}

	@Override
	public void setStatus(String newStatus) {
		if(newStatus.equals("Follow"))
			this.blocked = false;
		else
			this.blocked = true;
	}

}
