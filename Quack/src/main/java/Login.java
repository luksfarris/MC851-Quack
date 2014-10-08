import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Login
 */
@WebServlet(description = "Login", urlPatterns = { "/Login" })
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final String HTML_START = "<!DOCTYPE html><html><body>";
	public static final String HTML_END = "</body></html>";
	private String passwordKey = "QUACK_PASSWORD";
	private String usernameKey = "QUACK_USERNAME";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Login() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// pega os dados da sessão
		HttpSession requestSession = request.getSession();
		String cookieId = requestSession.getId();
		long creationDate = requestSession.getCreationTime();

		String username;
		String password;
		if (request.getParameter("username") != null
				&& request.getParameter("password") != null) {
			// novo login
			username = request.getParameter("username");
			password = request.getParameter("password");
			requestSession.setAttribute(usernameKey, username);
			requestSession.setAttribute(passwordKey, password);
		} else {
			// dados da sessão
			username = (String) requestSession.getAttribute(usernameKey);
			password = (String) requestSession.getAttribute(passwordKey);
		}

		PrintWriter out = response.getWriter();
		if (username != null) {
			// constroi a pagina.
			out.println(HTML_START + "<h2>Login</h2><br/>Cookie ID: "
					+ cookieId + "<br/>Session Creation: "
					+ new Date(creationDate) + "<br/>Username: " + username
					+ "<br/>Password: " + password + HTML_END);
		} else {
			out.println(getLoginPage());
		}
	}

	private String getLoginPage() {
		return "<!DOCTYPE html><html><head><title>HTML5 Login</title>"
				+ "</head><body><section><form name=\"login\" action=\"Login\" method=\"get\" accept-charset=\"utf-8\">"
				+ "<ul><li><label for=\"username\">Email</label><input type=\"email\" name=\"username\" placeholder=\"yourname@email.com\""
				+ "required></li><li><label for=\"password\">Password</label><input type=\"password\" name=\"password\" placeholder=\"password\""
				+ "required></li><li><input type=\"submit\" value=\"Login\"></li></ul></form></section></body></html>";
	}
}
