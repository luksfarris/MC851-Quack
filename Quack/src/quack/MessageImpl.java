package quack;

import java.util.Calendar;
import java.util.List;

//@Entity
public class MessageImpl implements Message {
	//@Id
	//@GeneratedValue
	/*
	private Long id;
		
	private	Calendar timestamp;
	private String body;
	private Long user;
	private String image;
	private Long parent;
	*/
	
	@Override
	public boolean postMessage(User user, String message) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean postMessage(User user, String message, String place) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean postReMessage(User user, Long messageId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Message> getUserMessages(User user, int maxMessages) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Message> getFollowingMessages(String userId, int maxMessages) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Message> getMoreFollowingMessages(String userId, Calendar time,
			int maxMessages) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean deleteMessage(Long messageId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User getUser() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Calendar getDate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPlace() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
}
