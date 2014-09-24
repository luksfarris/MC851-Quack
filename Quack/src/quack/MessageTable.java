package quack;

import java.util.List;

public interface MessageTable {

	public void initialize(Database db);
	
    // Inicializa uma instância recém-criada {this} de {MessageTable},
    // populando {this} com todos os registros de mensagens do
	// banco de dados {db}.
    //
    // Este método deve ser chamado uma vez apenas, na partida do
    // servidor {Quack}. Os métodos a seguir só podem ser chamados
    // depois que a tabela tiver sido inicializada.
	
	public void add(Message message);
    // Adiciona uma mensagem {message} à tabela {this}
	// Aborta se ja existir uma mensagem com o mesmo identificador
	// de {message}
	
	public void delete(Message message);
	// Remove uma mensagem {message} da tabela {this}
	// Aborta se não existir uma mensagem com o mesmo identificador
	// de {message} inserida na tabela
	
	public Message getMessageByID(long id);
	// Procura na tabela {this} uma mensagem com identificador {id}
	// Retorna null caso não tenha encontrado nenhum
	
	public List<Message> getMessagesByUser(User user, String startTime, String endTime, int maxN);
	// Procura na tabela {this} mensagens com autor {user}
	// que estejam no intervalo especificado {startTime} {endTime}.
	// Retorna uma lista com no maximo {maxN} registros.
	// Retorna uma lista vazia caso não tenha encontrado nenhum
}
