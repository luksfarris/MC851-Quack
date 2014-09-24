package quack;

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

}
