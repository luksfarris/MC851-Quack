package quack;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

public interface HTML {

	// Esta classe especifica funções para construção de páginas HTML
	// a serem enviadas ao browser através do servidor HTTP.
	// Enquanto o servidor {Quack} estiver sendo executado, haverá 
	// apenas uma instância desta classe existente.

	public void initialize(Server server);
	// Inicializa o construtor de páginas HTML.
	// Guarda referência ao estado {server} do servidor {Quack},
	// para obter por exemplo o número de usuários, sessões, etc.
	
	// ------------------------------------------------------------------------
	// CONSTRUÇÃO DE PÁGINAS COMPLETAS
	
	// O parâmetro {cookie} nos métodos abaixo vai ser incluído na página
	// para identificar a sessão de login em futuros pedidos do mesmo usuário.
	// ??{ precisa incuir o parâmetro {cookie} em todos métodos abaixo. }?? 
	// Em alguns métodos, o {cookie} pode ser "" ou {null}.
	// Em outros ele deve ser não-nulo.
	
	public String homePage();
	// Retorna a pagina principal da rede {Quack}, com botões e campos
	// para login, cadastramento de novos usuários, listagem de dados públicos e 
	// e mensagens públicas de usuários, busca por conteúdo em mensagens e/ou usuários, etc..

	public void errorPage(HttpServletResponse response, String msg);
	// Compõe uma página HTML com a mensagem de erro {msg}, e a envia como resposta para o usuario usando {response}.
	// Alem disso, cria um log na saida padrao sobre a pagina de erro.

	public String loginPage();
	// Compoe uma pagina HTML com campos {loginName} e {password},
	// para o usuário fazer login.

	public String loginSuccessfulPage(String cookie, User user);
	// Pagina de resposta a um login bem sucedido. 
	
	public String userProfilePage(String cookie, User source, User target);
	// Página que mostra o perfil do usuário {target} (que não pode ser {null}).
	// Se {source} for um usuário não nulo diferente de {target}, supõe que {source}
	// está logado e é o dono da sessão identificada pelo {cookie}; nesse caso,
	// a página mostrará o estado do contato entre {source} e {target} 
	// ("following", "blocked", "inactive", etc.),
	// com botões para alterar o estado do contato. Se {source} for o mesmo que {target},
	// mostra botões para alterar dados do perfil de {source}.

	public String messageListPage(String cookie, String title, User user, List<Message> messages, int maxN);
	// Pagina que exibe uma lista de mensagens de um usuario {user}, com  
	// botões de navegação, por exemplo "next {maxN}", "prev {maxN}".
}
