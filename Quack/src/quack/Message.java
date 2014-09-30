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
	
	
	public String getPlace();
	//Retorna o local de publicação da mensagem {this}, ou null se essa informação
	//não existe
	
	
	public boolean delete();
	//Deleta a mensagem {this} da base de dados
	//Esse método é normalmente chamado quando o usuário deseja deletar
	//uma mensagem sua. Devemos pensar como garantir a autenticação para
	//evitar que um invasor delete as mensagens de outras pessoas.
	
}
