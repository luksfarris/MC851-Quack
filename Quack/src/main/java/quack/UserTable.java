package quack;

import java.util.List;

public interface UserTable {
	
	// Uma instância desta classe é uma tabela de {User}s que permite
	// atualizações e buscas diversas.
	//
	// Enquanto o processo {Server} estiver rodando, deve existir apenas 
	// uma instância desta classe, que é a lista de todos os usuários 
	// da rede {Quack}.  Quando o {Server} começa a rodar, ele cria 
	// uma {UserTable} carregando na memória todo o conteúdo
	// da base de dados persistente (veja {inicialize} abaixo).
	// Quando um novo usuário é criado, quem cria deve atualizar esta tabela e também 
	// gravar o usuário na base de dados persistente.
	
	// ---------------------------------------------------------------------
	// INICIALIZAÇÃO E ADIÇÃO DE USUÁRIOS

	public void initialize();
	// Inicia uma instância de {this}

	public void add(User user);
	// Adiciona {user} à tabela {this}.  Deve ser chamado sempre que um novo
	// usuário é criado.
	
	// ------------------------------------------------------------------------------
	// BUSCA DE USUÁRIO ESPECÍFICO

	public User getUserById(long id);
	// Retorna o {User} na tabela {this} que tem o identificador {id}, ou {null} se nao existir.
	
	public User getUserByLoginPassword(String loginName, String password);
	// Retorna o {User} na tabela {this} que tem o nome de login {loginName} e senha {password}, ou {null} se não existir.

	
	public User getUserByLogin(String loginName);
	// Retorna o {User} na tabela {this} que tem o nome de login {loginName}, ou {null} se não existir.

	public User getUserByEmail(String email);
	// Retorna o {User} na tabela {this} que tem o email {email}, ou {null} se não existir.
	
	// ------------------------------------------------------------------------------
	// BUSCA DE USUÁRIOS POR OUTROS CRITÉRIOS
	
	public List<User> listUsersByFullName(String name);
	// Retorna uma lista de {User} com todos os usuários cujo nome completo
	// contém {name}, ou uma lista vazia se não houver.

	public int getUserCount();
	// Retorna a quantidade de usuários no banco de dados.
	
	public List<User> getAllUsers();
	// Retorna uma lista de {User} contendo todos os usuarios do sistema.
	
}
