package quack;

import java.io.IOException;
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
	
	public void homePage(HttpServletResponse response);
	// Retorna a pagina principal da rede {Quack}, com botões e campos
	// para login, cadastramento de novos usuários, listagem de dados públicos e 
	// e mensagens públicas de usuários, busca por conteúdo em mensagens e/ou usuários, etc..

	public void errorPage(HttpServletResponse response, String msg);
	// Compõe uma página HTML com a mensagem de erro {msg}, e a envia como resposta para o usuario usando {response}.
	// Alem disso, cria um log na saida padrao sobre a pagina de erro.

	public void loginPage(HttpServletResponse response);
	// Compoe uma pagina HTML com campos {loginName} e {password},
	// para o usuário fazer login e coloca no redirect de response.

	public String loginSuccessfulPage(String cookie, User user);
	// Pagina de resposta a um login bem sucedido. 
	
	public void userProfilePage(HttpServletResponse response, String loginName);
	// Página que mostra o perfil do usuário cujo loginName é {loginName} 
	// (que não pode ser {null}).
	// A página deve olhar a sessão salva no cookie para
	// exibir as relações entre o usuário da sessão e o usuário da página
	
	public void userProfilePage(HttpServletResponse response);
	// Página que exibe o perfil do usuário logado na sessão

	public String messageListPage(String cookie, String title, User user, List<Message> messages, int maxN);
	// Pagina que exibe uma lista de mensagens de um usuario {user}, com  
	// botões de navegação, por exemplo "next {maxN}", "prev {maxN}".
	
	public void timelinePage(HttpServletResponse response);
	// Coloca em redirect a página que representa a timeline do usuário.

	public void followersPage(HttpServletResponse response, String username);
	// Redireciona para a página que lista os usuários que {username} segue.

	public void followsPage(HttpServletResponse response, String username);
	// Redireciona para a página que lista os usuarios seguidos pelo usuario
	// que contem o nome {username}
	
	public String formatMessage(Message message);
	// Retorna o trecho html devidamente formatado para a representação
	// de uma mensagem {message} nas paginas do sistema.
	
}
