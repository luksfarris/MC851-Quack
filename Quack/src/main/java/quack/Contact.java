package quack;

public interface Contact {
	// cada instancia dessa classe representa um relacionamento entre dois
	// usuarios. Se o usuario A segue o usuario B, entao A eh o source, e B eh o
	// target.

	public User source();

	// Usuario que esta seguindo o outro.

	public User target();

	// Usuario que esta sendo seguido.

	public long lastModified();

	// Data da ultima modificacao nesse relacionamento
	

	public String status();

	// Se esse valor for "Block" o relacionamento é de bloqueio,
	// Se for "Follow" é um relacionamento normal
	// Se for "Inactive" é um relacionamento inativo (desligado após unfollow ou unblock)

	public void setStatus(String newStatus);

	// Muda o status do contato para {newStatus}
	// Se {newStatus}.equals("Inactive") passa a ser inativo
	// Se {newStatus}.equals("Follow"), passa a ser de seguimento 
	// Se {newStatus}.equals("Block") passa a ser bloqueado
	// Qualquer outro valor é ignorado

	public void initialize(User source, User target, long instance,
			String newStatus);
	// Inicializa um objeto Contact com {source}, {target},
	// timestamp {instance} e status {newStatus} que pode ser "Follow" ou "Block"
	// status inicial "Inactive" não é aceito
}
