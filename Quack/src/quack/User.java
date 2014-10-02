package quack;

import java.util.*;

public interface User {
	
	// Cada instância desta classe representa um usuário da rede {Quack}.
	
	// -------------------------------------------------------------------------------
	// INICIALIZAÇÃO
	
	public boolean initialize(String loginName, String email, String fullName,
			String password, long dbIndex);
	// Inicializa um objeto {User} recém-criado com os parametros passados. Retorna <true> se
	// houve sucesso, ou <false> caso ocorra algum erro.  O parâmetro {dbIndex} 
	// vai identificar o usuário no banco de dados persistente.
	
	// -------------------------------------------------------------------------------
	// ATRIBUTOS BÁSICOS
	
	public String getLoginName();
	// Retorna o nome de usuário de {this}.

	public String getFullName();
	// Retorna o nome de {this}.
	
	public String getEmail();
	// Retorna o e-mail de {this}.
	
	public boolean changeEmail(String newEmail);
	// Altera o e-mail de {this} para {newEmail}.
	// Retorna {true} se a operação foi bem sucedida.

	public boolean changePassword(String newPassword);
	// Altera a senha de {this} para {newPassword}.
	// Retorna {true} se a operação foi bem sucedida.
	
	public boolean checkPassword(String password);
	// Retorna se a senha de {this} é {password}.
	
	public Calendar getCreateTime();
	// Retorna o timestamp da data em que o usuário {this} foi acrescentado à rede {Quack}.

	public long getDbIndex();
	// Retorna o índice do usuário {this} na base de dados persistente.
	
	// -------------------------------------------------------------------------------
	// CONTATOS
	//
	// Um contato é um par ordenado de usuários, com alguns atributos, {(A,B,...)} representado por uma
	// instância da classe {Contact}.  Pode indicar que {A} segue {B}, {A} bloqueia {B}, etc.

	public List<Contact> getDirectContacts();
	// Retorna a lista de todos os contatos que tem como origem o usuário {this};
	// isto é, quem ele segue/bloqueou.
	
	public List<Contact> getReverseContacts();
	// Retorna a lista de todos os contatos que tem como destino o usuário {this};
	// isto é, quem o segue/bloqueou {this}.

	public boolean follow(User target);
	// Faz com que o usuário {target} passe a ser seguido por {this}.
	// Retorna {true} se a operação foi bem sucedida.
	
	public boolean unfollow(User target);
	// Faz com que o usuário {target} deixe de ser seguido por {this}.
	// Retorna {true} se a operação foi bem sucedida.
	
	public boolean block(User target);
	// Faz com que o usuário {target} seja bloqueado por {this}.
	// Retorna {true} se a operação foi bem sucedida.
	
	public boolean unblock(User target);
	// Faz com que o usuário {target} deixe de ser bloqueado por {this}.
	// Retorna {true} se a operação foi bem sucedida.
	
	// -------------------------------------------------------------------------------
	// MENSAGENS
	
	// Cada usuário tem uma lista de mensagens que ele postou (incluindo re-postagens)
	
	public List<Message> getPostedMessages();
	// Retorna a lista de mensagens postadas pelo usuário {this}, em ordem cronológica
	// inversa (mais recente primeiro).
	
	public List<Message> getPostedMessages(Calendar startTime, Calendar endTime, long maxN);
	// Retorna o trecho da lista de mensagens {this.getMessages()},
	// limitadas ao intervalo definido por {startTime,endTime,maxN}. 
	// Veja a interpretação destes parâmetros na interface {Server}.
	
}
