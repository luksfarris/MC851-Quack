package quack;

import java.util.*;

public interface UserTable {

	public void initialize(Database db);
	// Inicia uma instância de {this} e carrega todos os usuários
	// do banco {db} para uma lista de elementos {User}.
	
	public User getUserByLoginName(String loginName);
	// Retorna o {User} de {this} cujo nome de usuário é {username}
	// ou null se não existir.
	
	public List<User> getUsersByFullName(String fullName);
	// Retorna uma lista de {User} com todos os usuários cujo nome
	// contém {name}, ou uma lista vazia se não houver.
	
	
	public void add(User user);
	// Adiciona {user} à lista de {this}.
	
	public void remove(User user);
	// Remove {user} da lista de {this}.

	public User getUserByEmail(String email);
	// Pega do banco de dados um objeto {User} que tem o email {email}
	
}
