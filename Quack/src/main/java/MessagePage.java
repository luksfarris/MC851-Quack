

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.w3c.dom.html.HTMLUListElement;

/**
 * Servlet implementation class MessagePage
 */
@WebServlet(description = "MessagePage", urlPatterns = { "/MessagePage"})
public class MessagePage extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final String htmlHeader = "<!DOCTYPE html>\n<html>\n<head>\n"
			+ "<title>Mensagem</title>\n</head>\n<body>\n";
	private static final String htmlFooter = "</body>\n</html>";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MessagePage() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		
		
		out.println(htmlHeader + "<p style=\"color: #22F;\">@autor</p>\n"
				+ "<h1>À noite, vovô Kowalsky vê o ímã cair no pé do pinguim queixoso e vovó põe açúcar no chá de tâmaras do jabuti feliz.</h1>" + htmlFooter);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
