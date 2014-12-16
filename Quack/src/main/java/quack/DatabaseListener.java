package servlets;

public interface DatabaseListener {

	public void onDatabaseLoaded(long users, long messages);
}
