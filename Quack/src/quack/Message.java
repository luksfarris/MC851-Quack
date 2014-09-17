package quack;

import java.util.Calendar;
import java.util.List;

public interface Message {
	
	//Basic method to post a single message
	public boolean postMessage(String userId, String message);
	
	//Post a message with the place information
	public boolean postMessage(String userId, String message, String place);
	
	//Post a message with an image
	public boolean postMessage(String userId, String message, Long imageId);
	
	//Post a message with place information and an image
	public boolean postMessage(String userId, String message, String place, Long imageId);
	
	//Post a reMessage (RT)
	public boolean postReMessage(String userId, Long messageId);
	
	//Get all messages from a user
	public List<Message> getUserMessages(String userId);
	
	//Get maxMessages from the people followed by userId
	//posted before time
	public List<Message> getFollowingMessages(String userId, 
			Calendar time, int maxMessages);
	
	//Delete the message identified by messageId
	public boolean deleteMessage(Long messageId);
	
	//Get the content of a message
	public String getMessage();
	
	//Get the author of a message
	public String getUser();
	
	//Get the date of a message
	public Calendar getDate();
	
	//Get the place from where the message was posted
	//Returns null if there is no place information
	public String getPlace();
	
	//Get the imageId of a message
	//Returns null if there is no image associated
	public Long getImageId();
	
}
