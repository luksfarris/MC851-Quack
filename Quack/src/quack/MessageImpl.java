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
	public boolean postMessage(String userId, String message) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean postMessage(String userId, String message, String place) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean postMessage(String userId, String message, Long imageId) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean postMessage(String userId, String message, String place,
			Long imageId) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean postReMessage(String userId, Long messageId) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public List<Message> getUserMessages(String userId) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<Message> getFollowingMessages(String userId, Calendar time,
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
	public String getUser() {
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
	@Override
	public Long getImageId() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
