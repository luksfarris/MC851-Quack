package quack;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

public class HTMLImpl implements HTML {
	public static final String HTML_START = "<!DOCTYPE html><html><body>";
	public static final String HTML_END = "</body></html>";
	
	@Override
	public void errorPage(HttpServletResponse response, String msg) {
		response.setContentType("text/html");
		System.out.println("Erro: " + msg);
		try {
			response.getWriter().println("<script type=\"text/javascript\">history.back(alert('"+msg+"'));</script>");
		} catch (IOException e) {
			System.out.println("Erro ao enviar pagina de erro.");
			e.printStackTrace();
		}
	}

	@Override
	public String messageListPage(String cookie, String title, User user,
			List<Message> messages, int maxN) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void initialize(Server server) {
	}

	@Override	
	public String loginSuccessfulPage(String cookie, User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void homePage(HttpServletResponse response) throws IOException {
			response.sendRedirect("Quack/pub/LoginPage.jsp");
	}

	@Override
	public void loginPage(HttpServletResponse response) throws IOException {
			response.sendRedirect("Quack/pub/LoginPage.jsp");	
	}

	@Override
	public void timelinePage(HttpServletResponse response) throws IOException {
			response.sendRedirect("/Quack/Timeline");

	}

	@Override
	public void userProfilePage(HttpServletResponse response, String loginName) throws IOException {
		response.sendRedirect("/Quack/UserPage.jsp"+"?u="+loginName);
	}

	@Override
	public void userProfilePage(HttpServletResponse response) throws IOException {
		response.sendRedirect("/Quack/UserPage.jsp");
	}

	@Override
	public void followersPage(HttpServletResponse response, String username) throws IOException {
		response.sendRedirect("/Quack/FollowersPage.jsp?user=" + username);
	}

	@Override
	public void followsPage(HttpServletResponse response, String username) throws IOException {
		response.sendRedirect("/Quack/FollowingPage.jsp?user=" + username);	
	}

	@Override
	public String formatMessage(MessageImpl message) {
		//Fazer formatações adicionais aqui
		return message.getText();
	}
}
