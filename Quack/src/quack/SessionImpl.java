package quack;

public class SessionImpl implements Session {

	@Override
	public double createNewSession(String userId) {
		// TODO: gerenciar sessão.
		return 0;
	}

	@Override
	public boolean isUserSessionActive(String userId) {
		// TODO: checar se usuario esta ativo.
		return false;
	}

	@Override
	public void terminateUserSession(String userId) {
		// TODO: encerra a sessão do usuário.

	}
}
