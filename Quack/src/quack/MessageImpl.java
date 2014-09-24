package quack;

import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class MessageImpl implements Message {
	@Id
	@GeneratedValue
	private Long id;

	@Temporal(TemporalType.DATE)
	private	Calendar timestamp;
	private String body;
	private long user_id;
	private String place;
	private Long parent;
	
	//Diferentes construtores para a classe, com diferentes parâmetros
	public MessageImpl(Calendar timestamp, String body, 
			User user){
		//TODO mudar o id
		this.id = new Long(1);
		this.timestamp = timestamp;
		this.body = body;
		this.user_id = user.getId();
		this.place = null;
		this.parent = null;
		save();
	}
	
	public MessageImpl(Calendar timestamp, String body, 
			User user, String place){
		//TODO mudar o id
		this.id = new Long(1);
		this.timestamp = timestamp;
		this.body = body;
		this.user_id = user.getId();
		this.place = place;
		this.parent = null;
		save();
	}
	
	public MessageImpl(Calendar timestamp, String body, 
			User user, Long parent){
		//TODO mudar o id
		this.id = new Long(1);
		this.timestamp = timestamp;
		//TODO mudar o corpo da mensagem pro corpo da mensagem pai
		this.body = null;
		this.user_id = user.getId();
		this.place = null;
		this.parent = parent;
		save();
	}
	
	public MessageImpl(Calendar timestamp, String body, 
			User user, String place, Long parent){
		//TODO mudar o id
		this.id = new Long(1);
		this.timestamp = timestamp;
		//TODO mudar o corpo da mensagem pro corpo da mensagem pai
		this.body = null;
		this.user_id = user.getId();
		this.place = place;
		this.parent = parent;
		save();
	}
	
	private boolean save() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete() {
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
		// TODO: get user from UserImpl controller.
		return new UserImpl();
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
