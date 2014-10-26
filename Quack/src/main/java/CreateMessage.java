import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import quack.Server;
import service.QuackService;


/**
 * Servlet implementation class Login
 */
@WebServlet(description = "CreateMessage", urlPatterns = { "/CreateMessage" })
public class CreateMessage extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final String HTML_START = "<!DOCTYPE html><html><body>";
	public static final String HTML_END = "</body></html>";


	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CreateMessage() {
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
		
		String message;
		
		if (request.getParameter("messageText") != null) {
			// nova mensagem
			message = request.getParameter("messageText");
			
			Server server = QuackService.getServer(getServletContext());
			
			server.processSendMessageReq(cookieId, message, "", Calendar.getInstance().getTimeInMillis()/1000);
			
			PrintWriter out = response.getWriter();
			//TODO pegar username da sessao
			out.println(HTML_START + "<p style=\"color: #22F;\">@"+"</p>\n"
					+ "<h1>"+message+"</h1>" + HTML_END);
			//response.sendRedirect("/Quack/CreateMessage");
			return;
		}

	}

}
