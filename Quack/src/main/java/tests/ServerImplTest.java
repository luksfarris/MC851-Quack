package tests;

import java.util.Calendar;

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
	private String cookie;

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
	
	@Override
	public void testLogout() {
		givenThatServerStarted();
		whenNewUserRegistersAndLogsIn();
		whenUserLogsOut();
		thenThereAreNoSessions();
	}

	private void thenThereAreNoSessions() {
		Assert.assertTrue(server.getNumSessions() == 0);
	}

	private void whenUserLogsOut() {
		server.processLogoutReq(cookie);
	}

	private void whenUserPostsMessage() {
		server.processSendMessageReq(cookie, "New message.", "", Calendar
				.getInstance().getTimeInMillis() / 1000);
	}

	private void thenThereIsAtLeastOneLoggedUser() {
		Assert.assertTrue(server.getNumUsers() > 0);
		Assert.assertTrue(server.getNumSessions() > 0);
	}

	private void thenThereIsAtLeastOneMessage() {
		Assert.assertTrue(server.getNumMessages() > 0);
	}

	private void whenNewUserRegistersAndLogsIn() {
		server.processRegistrationReq("Marcela", "marcela@supermail.br",
				"Marcela dos Santos Pereira", "mah123");
		server.processLoginReq("Marcela", "mah123");
		cookie = "123";
	}

	private void givenThatServerStarted() {
		server = new ServerImpl();
		server.initialize("", "", "");
	}

}
