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
	public void homePage(HttpServletResponse response) {
			try {
				response.sendRedirect("Quack/pub/LoginPage.jsp");
			} catch (IOException e) {
				this.errorPage(response, "Problemas ao gerar home page.");
				e.printStackTrace();
			}
	}

	@Override
	public void loginPage(HttpServletResponse response) {
			try {
				response.sendRedirect("LoginPage.jsp");
			} catch (IOException e) {
				this.errorPage(response, "Problemas ao gerar página de login.");
				e.printStackTrace();
			}	
	}

	@Override
	public void timelinePage(HttpServletResponse response) {
			try {
				response.sendRedirect("/Quack/Timeline");
			} catch (IOException e) {
				this.errorPage(response, "Problemas ao gerar timeline.");
				e.printStackTrace();
			}

	}

	@Override
	public void userProfilePage(HttpServletResponse response, String loginName) {
		try {
			response.sendRedirect("/Quack/UserPage.jsp"+"?u="+loginName);
		} catch (IOException e) {
			this.errorPage(response, "Problemas ao gerar página de usuário.");
			e.printStackTrace();
		}
	}

	@Override
	public void userProfilePage(HttpServletResponse response) {
		try {
			response.sendRedirect("/Quack/UserPage.jsp");
		} catch (IOException e) {
			this.errorPage(response, "Problemas ao gerar perfil do usuário.");
			e.printStackTrace();
		}
	}

	@Override
	public void followersPage(HttpServletResponse response, String username) {
		try {
			response.sendRedirect("/Quack/FollowersPage.jsp?user=" + username);
		} catch (IOException e) {
			this.errorPage(response, "Problemas ao gerar página de seguidores.");
			e.printStackTrace();
		}
	}

	@Override
	public void followsPage(HttpServletResponse response, String username) {
		try {
			response.sendRedirect("/Quack/FollowingPage.jsp?user=" + username);
		} catch (IOException e) {
			this.errorPage(response, "Problemas ao gerar página de seguidos.");
			e.printStackTrace();
		}	
	}

	@Override
	public String formatMessage(Message message) {
		return "<div align='left'>"+message.getText()+"</div>";
	}
}
