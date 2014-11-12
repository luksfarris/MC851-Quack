package quack;

public interface Contact {
	// Cada instancia dessa classe representa um relacionamento entre dois
	// usuarios, {A} (/origem/, {source}) e {B} (/alvo/, {target}).
	// Pode indicar que {A} segue {B}, bloqueia {B}, etc.
	// Em princípio, todo contato é criado por iniciativa do
	// usuário de origem.

	public User source();
	// Usuario origem do contato.

	public User target();
	// Usuario alvo do contato.

	public String status();
        // Estado (natureza) do contato. Pode ser:
	// "Follow" se o usuário fonte /segue/ o alvo, isto é, quer ver na sua caixa de entrada
	//   as mensagens postadas ou repostadas pelo usuário alvo.
	// "Block" se o usuário fonte /bloqueia/ o alvo, isto é, não quer ver 
	//   na sua caixa de entrada as mensagens postadas ou repostadas pelo alvo,
	//   mesmo se elas mencionarem o usuário fonte. 
	//   ??{ E se forem repostadas por usuários seguidos pelo fonte? }??.
	// "Inactive" se um relacionamento anterior foi cancelado.
	
	public void setStatus(String newStatus);
	// Muda o status do contato para {newStatus}, que deve ser 
	// "Inactive", "Follow", ou "Block".
	// Qualquer outro valor é ignorado

	public long lastModified();
	// Datahora da criação ou ultima modificacao no estado do relacionamento.

	public void initialize(User source, User target, long instance,
			String newStatus);
	// Inicializa um objeto Contact com {source}, {target},
	// datahora de modificação {instance} e status {newStatus} que 
	// pode ser "Follow" ou "Block" status inicial "Inactive" não é aceito. 
}
