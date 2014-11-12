package quack;

public interface Message {
	
	// Uma instância de {Message} é uma mensagem enviada dentro do sistema {Quack}.
	// Pode ser uma /mensagem original/ ou uma /repostagem/ de outra mensagem.
	// Uma mensagem original possui um texto; uma repostagem, no lugar do texto,
	// possui um apontador para a mensagem repostada (que pode ser outra repostagem,
	// e assim repetidamente).

	public String getText();
	// Retorna o texto da mensagem original associada {this}. Se {this} for uma repostagem, 
	// este método é aplicado à mensagem repostada, recursivamente.

	public Message getParent();
	// Se {this} é uma repostagem, retorna a mensagem repostada (que pode ser outra repostagem),
	// senão retormna {null}.

	public User getUser();
	// Retorna o usuário responsável pela publicação da mensagem {this}
	// (o autor, se for original, ou o usuário que repostou,
	// se for repostagem).

	public long getDate();
	// Retorna a datahorade publicação da mensagem {this} (a datahora de repostagem,
	// se for o caso)

	public boolean initialize(String body, User user, long id);
	// Inicializa uma nova instância {this} de {Message} como uma 
	// mensagem original com os atributos {body} (texto da mensagem) 
	// e {user} (autor).  A datahora de postagem fica sendo a datahora atual.
	// o campo {id} é o id do banco de dados

	public boolean initialize(User user, Message parent, long id);
	// Inicializa uma nova instância {this} como sendo a
	// repostagem da mensagem {parent}, repostada por {user}. 
	// A datahora de repostagem fica sendo a datahora atual.
	// o campo {id} é o id do banco de dados
	
	public long getId();
	// Retorna o id no banco da mensagem de {this}

}
