package quack;

import java.util.Calendar;

//@Entity
public class MessageImpl implements Message {
	//@Id
	//@GeneratedValue
	
	private Long id;
		
	private	Calendar timestamp;
	private String body;
	private User user;
	private String place;
	private Long parent;
	
	//Diferentes construtores para a classe, com diferentes parâmetros
	public MessageImpl(Calendar timestamp, String body, 
			User user){
		//TODO mudar o id
		this.id = new Long(1);
		this.timestamp = timestamp;
		this.body = body;
		this.user = user;
		this.place = null;
		this.parent = null;
	}
	
	public MessageImpl(Calendar timestamp, String body, 
			User user, String place){
		//TODO mudar o id
		this.id = new Long(1);
		this.timestamp = timestamp;
		this.body = body;
		this.user = user;
		this.place = place;
		this.parent = null;
	}
	
	public MessageImpl(Calendar timestamp, String body, 
			User user, Long parent){
		//TODO mudar o id
		this.id = new Long(1);
		this.timestamp = timestamp;
		//TODO mudar o corpo da mensagem pro corpo da mensagem pai
		this.body = null;
		this.user = user;
		this.place = null;
		this.parent = parent;
	}
	
	public MessageImpl(Calendar timestamp, String body, 
			User user, String place, Long parent){
		//TODO mudar o id
		this.id = new Long(1);
		this.timestamp = timestamp;
		//TODO mudar o corpo da mensagem pro corpo da mensagem pai
		this.body = null;
		this.user = user;
		this.place = place;
		this.parent = parent;
	}
	
	@Override
	public boolean save() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteMessage() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getText() {
		if(parent == null)
			return body;
		//TODO- mudar o caso de ter parent
		else return null;
	}

	@Override
	public User getUser() {
		return user;
	}

	@Override
	public Calendar getDate() {
		return timestamp;
	}

	@Override
	public String getPlace() {
		return place;
	}

	@Override
	public Long getId() {
		return id;
	}
	
	
	
}
