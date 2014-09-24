package quack;

public interface Session {

	// cria uma nova sessão e retorna o cookie gerado pelo browser. Passa como
	// parametro o nome e a senha do usuario que fez login.
	public String createNewSession(String userName, String password);

	// checa se um usuario esta ativo passando o cookie.
	public boolean isUserSessionActive(String cookie);

	// encerra a sessão do usuário.
	public void terminateUserSession(String cookie);
}
