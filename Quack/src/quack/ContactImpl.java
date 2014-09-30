package quack;

import java.util.Calendar;
import java.util.Date;

public class ContactImpl implements Contact {

	private User sourceUser;
	private User targetUser;
	private Calendar lastModified;
	private boolean blocked;
	
	public ContactImpl(User source, User target, Calendar calendar, boolean blocked) {
		sourceUser = source;
		targetUser = target;
		this.lastModified = calendar;
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
	public Calendar lastModified() {
		return lastModified;
	}

	@Override
	public boolean blocked() {
		return blocked;
	}

}
