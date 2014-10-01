package quack;

import java.util.List;

public interface HTML {

	// Esta classe especifica funções para construção de páginas HTML
	// a serem enviadas ao browser através do servidor HTTP.

	public String errorPage(String msg);

	// Compõe uma página HTML com a mensagem de erro {msg}.

	public String loginPage();

	// compoe uma pagina html com o login.

	public HTML initialize();

	// inicializa o objeto.

	public String homePage();

	// pagina principal.

	public String loginSuccessfulPage(String cookie);
	// pagina de sucesso de login.

	public String messageListPage(String cookie, String userName,
			List<Message> messages, int maxN);
	// pagina que exibe uma lista de mensagens de um usuario.
}
