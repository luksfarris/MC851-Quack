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

	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		Server server = QuackService.getServer(getServletContext());
		server.processSendMessageReq(request, response, getServletContext());
	}

}
