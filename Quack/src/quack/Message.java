package quack;

import java.util.Calendar;

public interface Message {

	public Long getId();
	//Retorna o id no banco da mensagem de {this}
		
	
	public String getText();
	//Retorna o conteúdo do atributo de texto da mensagem de {this}
	
	
	public User getUser();
	//Retorna o autor da mensagem {this}
	
	
	public Calendar getDate();
	//Retorna a data de publicação da mensagem {this}
	
}
