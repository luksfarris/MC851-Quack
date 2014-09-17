package quack;

import java.util.Calendar;

//@Entity
public class MessageImpl {
	//@Id
	//@GeneratedValue
	private Long id;
	
    private	Calendar timestamp;
	private String body;
	private Long user;
	private String image;
	private Long parent;
	
}
