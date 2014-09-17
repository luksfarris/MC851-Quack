package quack;

import java.util.Calendar;
import java.util.List;

public interface Message {
	
	//Insere uma mensagem com conteudo {message} do usuário {user}
	//Retorna sucesso ou falha na inserção
	public boolean postMessage(User user, String message);
	
	//Insere uma mensagem com conteudo {message} do usuário {user} e local {place}
	//Retorna sucesso ou falha na inserção
	public boolean postMessage(User user, String message, String place);
	
	//Insere uma mensagem que é uma repostagem de outra mensagem identificada por {messageId} 
	//do usuário {user}
	//Retorna sucesso ou falha na inserção
	public boolean postReMessage(User user, Long messageId);
	
	//Retorna a lista de mensagens do usuario {user} com no máximo {maxMessages}
	public List<Message> getUserMessages(User user, int maxMessages);
	
	//Retorna a lista das ultimas mensagens ordenadas por data de postagem com no máximo {maxMessages},
	//postadas pelas pessoas que o usuário {user} segue.
	public List<Message> getFollowingMessages(String userId, 
				int maxMessages);
	
	//Retorna a lista de mensagens ordenadas por data de postagem com no máximo {maxMessages},
	//postadas pelas pessoas que o usuário {user} segue, até a data indicada{time} 
	public List<Message> getMoreFollowingMessages(String userId, 
			Calendar time, int maxMessages);
	
	
	//Deleta a mensagem identificada por {messageId} do banco
	public boolean deleteMessage(Long messageId);
	
	//Retorna o conteúdo do atributo de texto da mensagem
	public String getMessage();
	
	//Retorna o autor da mensagem
	public User getUser();
	
	//Retorna a data de publicação da mensagem
	public Calendar getDate();
	
	//Retorna o local de publicação da mensagem, ou null se essa informação
	//não existe
	public String getPlace();
}
