package quack;

import java.util.Calendar;

public interface Contact {
	// cada instancia dessa classe representa um relacionamento entre dois
	// usuarios. Se o usuario A segue o usuario B, entao A eh o source, e B eh o
	// target.

	public User source();
	// Usuario que esta seguindo o outro.

	public User target();
	// Usuario que esta sendo seguido.

	public Calendar lastModified();
	// Data da ultima modificacao nesse relacionamento

	public boolean blocked();
	// Se esse valor for <true> o relacionamento eh de bloqueio, 
	// caso contrario eh apenas um relacionamento.

	public void setStatus(String newStatus);
	// Muda o status do contato para {newStatus}
	// Se {newStatus}.equals("Follow"), passa a ter {this.blocked} = false
	// Senao passa a ser bloqueado
	
	public void initialize(User source, User target, Calendar instance,
			String newStatus);
	// Inicializa um objeto Contact com {source}, {target}, 
	// timestamp {instance} e status {newStatus}
	
}
