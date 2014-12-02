package servlets;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import quack.Server;
import service.QuackService;


/**
 * Servlet implementation class Login
 */
@WebServlet(description = "CreateMessage", urlPatterns = { "/CreateMessage" })
public class SendMessageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final String HTML_START = "<!DOCTYPE html><html><body>";
	public static final String HTML_END = "</body></html>";


	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SendMessageServlet() {
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
