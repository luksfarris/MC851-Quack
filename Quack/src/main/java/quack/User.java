package quack;

import java.util.*;

public interface User {

	// Cada instância desta classe representa um usuário da rede {Quack}.

	// -------------------------------------------------------------------------------
	// INICIALIZAÇÃO

	public boolean initialize(String loginName, String email, String fullName,
			String password);
	// Inicializa um objeto {User} recém-criado com os parametros passados. 
	// Retorna <true> se
	// houve sucesso, ou <false> caso ocorra algum erro.

	// A cadeia {loginName} deve começar com letra, terminar letra ou dígito, e
	// conter apenas letras, dígitos, pontos "." e hífens. Seu comprimento
	// deve ser no mínimo 3 e no máximo 20 caracteres. ??{ Números arbitrários
	// }??
	// A cadeia {email} deve conter apenas caratcteres válidos em e-mails.
	// A cadeia {fullname} pode conter quaisquer caracteres UNICODE na
	// representação
	// UTF-8. A cadeia {password} deve ser a senha, por enquanto sem
	// criptografar.

	// -------------------------------------------------------------------------------
	// ATRIBUTOS BÁSICOS

	public String getLoginName();

	// Retorna o nome de login de {this}.

	public String getFullName();

	// Retorna o nome completo de {this} (Unicode na representação UTF-8).

	public String getEmail();

	// Retorna o e-mail de {this}.

	public void setEmail(String newEmail);

	// Altera o e-mail de {this} para {newEmail}.

	public void setPassword(String newPassword);

	// Altera a senha de {this} para {newPassword}.

	public boolean checkPassword(String password);

	// Retorna {true} sse a senha de {this} é {password}.

	public long getCreationTime();

	// Retorna o timestamp da data em que o usuário {this} foi acrescentado à
	// rede {Quack}.

	public long getDbIndex();

	// Retorna o índice do usuário {this} na base de dados persistente.

	// -------------------------------------------------------------------------------
	// CONTATOS
	//
	// Um contato é um par ordenado de usuários com mais alguns atributos,
	// {(A,B,...)},
	// isto é, uma aresta do grafo da rede {Quack}. É representado por uma
	// instância da classe {Contact}. Pode indicar que o /usuário de origem/ {A}
	// segue (ou bloqueia, ou tem alguma outra relação) com o /usuário alvo/
	// {B}.
	//
	// Há no máximo um contato, ativo ou inativo, para cada par de usuários
	// (origem e alvo).
	// Por enquanto, contatos nunca são eliminados, apenas podem ter seus
	// atributos
	// alterados de modo a se tornarem inativos.
	//
	// Cada instância {this} de {User} tem uma lista de /contatos diretos/, em
	// que o usuário {this} é origem; isto é, os usuários que ele
	// segue/bloqueou/etc..
	// Tem também uma lista de /contatos reversos/, em que ele é destino;
	// isto é, quem segue/bloqueou o usuário {this}. Sempre que um contato
	// {(A,B,...)} é acrescentado á lista de contatos diretos de {A},
	// deve ser acrescentado também à lista de contatos reversos de {B},
	// pata manter a consistência da rede.

	public List<Contact> getDirectContacts();

	// Retorna a lista de todos os contatos que tem como origem o usuário
	// {this}.

	public List<Contact> getReverseContacts();

	// Retorna a lista de todos os contatos que tem como destino o usuário
	// {this}.

	public Contact getDirectContact(User target);

	// Retorna o contato da lista {this.getDirectContacts()} que tem
	// como alvo o usuário {target}; ou {null} se não existir.

	public Contact getReverseContact(User origin);

	// Retorna o contato da lista {this.getReverseContacts()} que tem
	// como origem o usuário {origin}; ou {null} se não existir.

	public void addDirectContact(Contact contact);

	// Acrescenta à lista {this.getDirectContacts()} o contato dado, que deve
	// ter.
	// o usuário {this} como origem. Aborta, caso já exista nessa lista
	// algum contato com o mesmo alo que o alvo de {contact}.

	public void addReverseContact(Contact contact);

	// Acrescenta à lista {this.getDirectContacts()} o contato dado, que deve
	// ter.
	// o usuário {this} como alvo. Aborta, se já houver nessa lista
	// algum contato com o mesmo usuário origem que a origem de {contact}.

	// -------------------------------------------------------------------------------
	// MENSAGENS

	// Cada usuário tem uma lista de mensagens que ele postou (incluindo
	// re-postagens)

	public List<Message> getPostedMessages();

	// Retorna a lista de mensagens postadas pelo usuário {this}, em ordem
	// cronológica
	// inversa (mais recente primeiro).

	public List<Message> getPostedMessages(long startTime, long endTime,
			long maxN);
	// Retorna o trecho da lista de mensagens {this.getMessages()},
	// limitadas ao intervalo definido por {startTime,endTime,maxN}.
	// Veja a interpretação destes parâmetros na interface {Server}.

}
