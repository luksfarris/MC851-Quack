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
	public String loginPage() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override

	public String userProfilePage(String cookie, User source, User target) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override

	public String messageListPage(String cookie, String title, User user,
			List<Message> messages, int maxN) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String homePage() {
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

	
}
