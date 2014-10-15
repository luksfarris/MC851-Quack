package quack;

public interface SessionTable {

	// Normalmente existe apenas uma instância desta classe,
	// que é o conjunto das sessões abertas no servidor {Quack}
	// em um dado momento.
	//
	// Em nenhum momento deve haver duas sessões na tabela com o mesmo
	// {user} ou mesmo {cookie}.

	public void initialize();
	// Inicializa uma instância recém-criada {this} de {SessionTable},
	// deixando-a com zero sessões abertas.
	//
	// Este método deve ser chamado uma vez apenas, na partida do
	// servidor {Quack}. Os métodos a seguir só podem ser chamados
	// depois que a tabela tiver sido inicializado.

	public void add(Session session);
	// Acrescenta a sessão aberta {session} à tabela {this}.
	// Aborta se a sessão estiver fechada, ou se já existir uma
	// sessão na tabela com mesmo usuário ou mesmo {cookie}.

	public void delete(Session session);
	// Retira a sessão {session} da tabela {this}.
	// Aborta se a sessão não estiver na tabela.

	public Session getSessionByUser(User user);
	// Procura na tabela {this} uma sessão do usuário especificado.
	// Retorna {null} se não existir.

	public Session getSessionByCookie(String cookie);
	// Procura na tabela {this} uma sessão com o {cookie} especificado.
	// Retorna {null} se não existir.

	public int getSessionCount();
	// Obtem a quantidade de sessões abertas.
}
