package quack;

import java.util.Calendar;
import java.util.List;



public interface Message {
	
	boolean postMessage(String userId, String message);
	
	//boolean postMessage(String userId, String message, String imageLink);
	
	boolean postReMessage(String userId, String messageId);
	
	List<MessageImpl> getUserMessages(String userId);
	
	List<MessageImpl> getFollowingMessages(String userId, 
			Calendar timestamp, int maxMessages);
	
	boolean deleteMessage(String userId, String messageId);
	
	
}
