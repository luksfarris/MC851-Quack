package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import quack.Server;
import service.QuackService;

@WebServlet(description = "Logout", urlPatterns = {"/Logout"})
public class Logout extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	public Logout() {
		super();
	}
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Server server = QuackService.getServer(getServletContext());
		server.processLogoutReq(request, response, getServletContext());
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		
	}
	
}
