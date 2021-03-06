package quack;

public class ContactImpl implements Contact {

	private User sourceUser;
	private User targetUser;
	private long lastModified;
	private String status;

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
	public String status() {
		return status;
	}

	@Override
	public void initialize(User source, User target, long instance,
			String newStatus) {
		assert(sourceUser != null && targetUser != null &&
				(newStatus.equals("Follow") || newStatus.equals("Block") || 
						newStatus.equals("Inactive")));
		sourceUser = source;
		targetUser = target;
		this.lastModified = instance;
		this.status = newStatus;
	}

	@Override
	public void setStatus(String newStatus) {
		assert(newStatus.equals("Inactive") || newStatus.equals("Follow") 
				|| newStatus.equals("Block"));
		this.status = newStatus;
		Timestamp t = new TimestampImpl();
		this.lastModified = t.now();
		
	}

}
