package quack;

import java.util.*;

public interface User 
{
	public long getId();
	// Retorna o ID de {this}.
	
	public String getLogin();
	// Retorna o nome de usuário de {this}.

	public String getName();
	// Retorna o nome de {this}.
	
	public String getEmail();
	// Retorna o e-mail de {this}..
	
	public Calendar getCreatedDate();
	// Retorna o timestamp da data em que o perfil de {this} foi criado.

	public boolean follow(User followed);
	// Faz com que {followed} passe a ser seguido por {this}.
	// Retorna se a operação foi bem sucedida.
	
	public boolean unfollow(User followed);
	// Faz com que {followed} deixe de ser seguido por {this}.
	// Retorna se a operação foi bem sucedida.

	public boolean changePassword(String newPassword);
	// Altera a senha de {this} para {newPassword}.
	// Retorna se a operação foi bem sucedida.
	
	public boolean changeEmail(String newEmail);
	// Altera o e-mail de {this} para {newEmail}.
	// Retorna se a operação foi bem sucedida.
	
	public boolean block(User user);
	// Faz com que {user} seja bloqueado por {this}.
	// Retorna se a operação foi bem sucedida.
	
	public boolean unblock(User user);
	// Faz com que {user} deixe de ser bloqueado por {this}.
	// Retorna se a operação foi bem sucedida.

	public List<User> following();
	// Retorna uma lista de todos os usuários que {this} segue.
	
	public List<User> followers();
	// Retorna uma lista de todos os usuários que seguem {this}.

	public List<Message> getMessages(int start, int end);
	// Retorna uma lista de mensagens em ordem cronológica
	// decrescente postadas por {this} cujo índice
	// na consulta está no intervalo [{start}, {end}].
	
	public List<Message> getFollowingMessages(int start, int end);
	// Retorna uma lista de mensagens em ordem cronológica 
	// decrescente postadas ou repostadas por usuários seguidos por
	// {this} ou que mencionam {this} cujo índice na consulta
	// está no intervalo [{start}, {end}].
	
	public boolean checkPassword(String password);
	// Retorna se a senha de {this} é {password}.

	public List<Contact> getDirectContacts();
	// Retorna a lista de quem {this} segue/bloqueou.
	
	public List<Contact> getReverseContacts();
	// Retorna a lista de quem segue/bloqueou {this}.

	public boolean initialize(String userName, String email, String fullName,
			String password);
	// inicializa um objeto {User} com os parametros passados. Retorna <true> se
	// houve sucesso, ou <false> caso ocorra algum erro.
	
	
}
