package quack;

public interface Session {
	// Cada instância desta classe representa uma sessão de algum
	// usuário logado no {Quack}. Uma instância é /aberta/ entre as
	// chamadas de {open} e {close} abaixo, que correspondem ao login e
	// logout do usuário no {Quack}.

	void open(User user, String cookie);
	// Ao receber do servidor HTTP um comando login de algum usuário, o
	// servidor {Quack} deve criar uma nova instância {this} de
	// {Session} e chamar este método para inicializá-la, tornando-a
	// aberta. Os parâmetros são o objeto {user} que representa o usuário
	// e uma cadeia {cookie} que vai identificar esta sessão em
	// comandos futuros enviados pelo usuário, até o logout.

	String getCookie();
	// Retorna o {cookie} da sessão {this}, que deve estar aberta. Retorna
	// {null} se a sessão não está aberta.

	User getUser();
	// Retorna o objeto que descreve o usuário da sessão {this}, que deve estar
	// aberta.
	// Retorna {null} se a sessão não está aberta.
}
