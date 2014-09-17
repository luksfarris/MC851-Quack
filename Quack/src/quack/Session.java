package quack;

public interface Session {

	/**
	 * Cria uma nova sessão a partir de um usuário logado.
	 * 
	 * @param userId
	 *            identificador único do usuário.
	 * @return identificador da sessão.
	 */
	public double createNewSession(String userId);

	/**
	 * Diz se a sessão de um usuário está ativa.
	 * 
	 * @param userId
	 *            identificação única do usuário.
	 * @return <code>true</code> se o usuário está logado, <code>false</code>
	 *         caso contrário.
	 */
	public boolean isUserSessionActive(String userId);

	/**
	 * Encerra a sessão do usuário.
	 * 
	 * @param userIdidentificação
	 *            única do usuário.
	 */
	public void terminateUserSession(String userId);
}
