package quack;

import java.util.Calendar;

public class Server {

	public void addNewUser() {
		User user = new UserImpl();
		user.changeEmail("teste@tes.te");
		user.changeLocation("Campinas");
		user.changePassword("etSet");
		user.changePrivacy(UserPrivacy.Public);
		user.changeProfileMsg("Computeiro.");
		user.changeProfilePic("http://tinypic.com/35152d");
		user.changeWebsite("http://meusite.com");
	}

	public void sendNewTestMessage() {
		Message message = new MessageImpl(Calendar.getInstance(),
				"Teste de mensagem", new UserImpl());
	}

	public void saveUserSession() {
		Session session = new SessionImpl();
		session.createNewSession("usuarioTeste");
		session.isUserSessionActive("usuarioTeste");
		session.terminateUserSession("usuarioTeste");
	}
}
