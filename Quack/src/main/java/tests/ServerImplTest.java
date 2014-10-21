package tests;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Assert;
import org.junit.Test;

import quack.Server;
import quack.ServerImpl;

public class ServerImplTest implements ServerTest {
	// Esta classe contem uma serie de testes automaticos que podem ser
	// executados a qualquer momento. Para execurar, clique com o botao direito
	// sobre o arquivo .java, e selecione "Run As" -> "JUnit Test"

	/** Servidor que sera testado. */
	private Server server;
	private String cookie = "123";

	@Test
	public void testRegisterUser() {
		givenThatServerStarted();
		whenNewUserRegisters();
		thenThereIsAtLeastOneRegisteredUser();
	}

	@Test
	public void testLogin() {
		givenThatServerStarted();
		whenNewUserRegistersAndLogsIn();
		thenThereIsAtLeastOneLoggedUser();
	}

	@Test
	public void testSentMessage() {
		givenThatServerStarted();
		whenNewUserRegistersAndLogsIn();
		whenUserPostsMessage();
		thenThereIsAtLeastOneMessage();
	}
	
	@Test
	public void testLogout() {
		givenThatServerStarted();
		whenNewUserRegistersAndLogsIn();
		whenUserLogsOut();
		thenThereAreNoSessions();
	}

	@Test
	public void testFollowUser() {
		givenThatServerStarted();
		whenNewUserRegisters();
		whenNewUserRegistersAndLogsIn();
		whenUserFollowAnotherUser();
		thenThereAreTwoContacts();
	}

	@Test
	public void testBlockUser() {
		givenThatServerStarted();
		whenNewUserRegisters();
		whenNewUserRegistersAndLogsIn();
		whenUserBlockAnotherUser();
		thenThereAreTwoContacts();
	}
	
	
	
	private void givenThatServerStarted() {
		server = new ServerImpl();
		server.initialize("", "", "");
	}
	
	
	
	private void whenUserLogsOut() {
		server.processLogoutReq(cookie);
	}

	private void whenUserPostsMessage() {
		server.processSendMessageReq(cookie, "New message.", "", Calendar
				.getInstance().getTimeInMillis() / 1000);
	}
	
	private void whenNewUserRegistersAndLogsIn() {
		server.processRegistrationReq("Marcela", "marcela@supermail.br",
				"Marcela dos Santos Pereira", "mah123");
		server.processLoginReq("Marcela", "mah123", cookie);
	}
	
	private void whenNewUserRegisters() {
		server.processRegistrationReq("Marcelo", "marcelo@supermail.br",
				"Marcelo dos Santos Pereira", "mah123");
	}
	
	private void whenUserFollowAnotherUser() {
		server.processModifyContactReq(cookie, "Marcelo", "following");	
	}

	private void whenUserBlockAnotherUser() {
		server.processModifyContactReq(cookie, "Marcelo", "blocking");	
	}

	
	
	private void thenThereAreNoSessions() {
		Assert.assertTrue("A sessao deve ser removida", server.getNumSessions() == 0);
	}

	private void thenThereIsAtLeastOneLoggedUser() {
		Assert.assertTrue("Tem que haver ao menos um usuario logado", server.getNumUsers() > 0);
		Assert.assertTrue("Tem que haver ao menos uma sessao iniciada", server.getNumSessions() > 0);
	}

	private void thenThereIsAtLeastOneMessage() {
		Assert.assertTrue("Deve haver ao menos uma mensagem", server.getNumMessages() > 0);
	}

	private void thenThereAreTwoContacts() {
		Assert.assertTrue("Deve haver 2 contatos no servidor", server.getNumContacts() == 2);
	}

	private void thenThereIsAtLeastOneRegisteredUser() {
		Assert.assertTrue("Tem que haver ao menos um usuario registrado", server.getNumUsers() > 0);
	}

}
