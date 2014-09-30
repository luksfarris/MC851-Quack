package quack;

public class ContactTableImpl implements ContactTable {
	@Override
	public void initialize(Database database) {
		// TODO Auto-generated method stub
	}

	public void add(Contact contac){

	}
    // Adiciona um contato  à tabela {this}
	// Aborta se ja existir um contato com o mesmo identificador
	// de {contact}

	public void remove(Contact contac, long id){

	}
	// Remove um contato  da tabela {this}
	// Aborta se não existir um contato com o mesmo identificador
	// de {contact} inserido na tabela

	public Contact getContactsByID(long id){
		return null;
	}
	// Procura na tabela {this} os contatos do usuário identificador {id}
	// Retorna null caso não tenha encontrado nenhum
}
