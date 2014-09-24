package quack;

import java.util.List;

public interface UserTable {

	public void initialize(Database db);
	// Inicia uma instância de {this} e carrega todos os usuários
	// do banco {db} para uma lista de elementos {User}.
	
	public User getUserByLogin(String login);
	// Retorna o {User} de {this} cujo nome de usuário é {username}
	// ou null se não existir.
	
	public List<User> getUsersByName(String name);
	// Retorna uma lista de {User} com todos os usuários cujo nome
	// contém {name}, ou uma lista vazia se não houver.
	
	public List<User> getUsersByLocation(String location);
	// Retorna uma lista de {User} com todos os usuários cujo local
	// contém {location}, ou uma lista vazia se não houver.
	
	public void add(User user);
	// Adiciona {user} à lista de {this}.
	
	public void remove(User user);
	// Remove {user} da lista de {this}.
	
}