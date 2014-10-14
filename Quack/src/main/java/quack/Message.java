package quack;

public interface Message {

	public long getId();
	// Retorna o id no banco da mensagem de {this}

	public String getText();
	// Retorna o conteúdo do atributo de texto da mensagem de {this}

	public User getUser();
	// Retorna o autor da mensagem {this}

	public Message getParent();
	// Retorna a mensagem pai de {this} (quando repost) ou {null}

	public long getDate();
	// Retorna a data de publicação da mensagem {this}

	public boolean initialize(String body, User user);
	// inicializa a mensagem {this} com os atributos {body} do texto
	// da mensagem, e {user} como autor.

	public boolean initialize(User user, Message parent);
	// inicializa a mensagem {this} com autor {user}, e como um "repostar" da
	// mensagem {parent}

}
