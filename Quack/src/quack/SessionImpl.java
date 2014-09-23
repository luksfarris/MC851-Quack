package quack;

public class SessionImpl implements Session {

	/*
	 * (non-Javadoc)
	 * 
	 * @see quack.Session#createNewSession(java.lang.String)
	 */
	@Override
	public Session createNewSession(String userId) {
		// TODO: gerenciar sessão.
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see quack.Session#isUserSessionActive(java.lang.String)
	 */
	@Override
	public boolean isUserSessionActive(String userId) {
		// TODO: checar se usuario esta ativo.
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see quack.Session#terminateUserSession(java.lang.String)
	 */
	@Override
	public void terminateUserSession(String userId) {
		// TODO: encerra a sessão do usuário.

	}
}
