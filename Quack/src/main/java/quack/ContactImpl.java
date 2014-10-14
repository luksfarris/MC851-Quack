package quack;

public class ContactImpl implements Contact {

	private User sourceUser;
	private User targetUser;
	private long lastModified;
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
	public long lastModified() {
		return lastModified;
	}

	@Override
	public boolean blocked() {
		return blocked;
	}

	@Override
	public void initialize(User source, User target, long instance,
			String newStatus) {
		sourceUser = source;
		targetUser = target;
		this.lastModified = instance;
		if (newStatus.equals("Follow"))
			this.blocked = false;
		else
			this.blocked = true;
	}

	@Override
	public void setStatus(String newStatus) {
		if (newStatus.equals("Follow"))
			this.blocked = false;
		else
			this.blocked = true;
	}

}
