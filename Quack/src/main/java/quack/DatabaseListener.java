package quack;

public interface DatabaseListener {
	// este listener serve para que o objeto do database possa avisar para o server
	// que foi carregado, e em enviar dados para o servidor.
	

	public void onDatabaseLoaded(long users, long messages);
	// Este metodo avisa que o servidor foi carregado, e envia o numero de usuarios
	// e o numero de mensagens que foram carregados.
}
