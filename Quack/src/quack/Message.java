package quack;

import java.util.Calendar;

public interface Message {
	//Salva a mensagem no banco de dados
	//Retorna sucesso ou falha na inserção
	public boolean save();
	
	//Deleta a mensagem do banco
	public boolean deleteMessage();
	
	//Retorna o id no banco da mensagem
	public Long getId();
		
	//Retorna o conteúdo do atributo de texto da mensagem
	public String getText();
	
	//Retorna o autor da mensagem
	public User getUser();
	
	//Retorna a data de publicação da mensagem
	public Calendar getDate();
	
	//Retorna o local de publicação da mensagem, ou null se essa informação
	//não existe
	public String getPlace();
}
