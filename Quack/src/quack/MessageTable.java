package quack;

import java.util.List;

public interface MessageTable {

	public void initialize();
	
    // Inicializa uma instância recém-criada {this} de {MessageTable},
    // deixando-a com zero mensagens em memoria.
    //
    // Este método deve ser chamado uma vez apenas, na partida do
    // servidor {Quack}. Os métodos a seguir só podem ser chamados
    // depois que a tabela tiver sido inicializada.
	
	public void add(Message message);
    // Adiciona uma mensagem {message} à tabela {this}
	// Aborta se ja existir uma mensagem com o mesmo identificador
	// de {message}
	
	public void remove(Message message);
	// Remove uma mensagem {message} da tabela {this}
	// Aborta se não existir uma mensagem com o mesmo identificador
	// de {message} inserida na tabela
	
	public Message getMessageByID(long id);
	// Procura na tabela {this} uma mensagem com identificador {id}
	// Retorna null caso não tenha encontrado nenhum
	
	public List<Message> getMessagesByUser(User user);
	// Procura na tabela {this} mensagens com autor {user}
	// Retorna uma lista vazia caso não tenha encontrado nenhum
	
	public void cleanTable();
	// Limpa da tabela {this} registros que não estejam mais sendo
	// acessados.
}
