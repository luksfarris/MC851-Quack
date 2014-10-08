

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import quack.Server;
import quack.ServerImpl;

/**
 * Servlet implementation class UserPage
 */
@WebServlet(description = "UserPage", urlPatterns = {"/UserPage"})
public class UserPage extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final String HTML_START="<html><body>";
    public static final String HTML_END="</body></html>"; 
	private Server serv;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserPage() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
        out.println(HTML_START + "pagina de usuario (Implementação Inicial)<br><br>Valores setados manualmente:<br>" +
		"<table border='1'style='width:100%'><tr><td><center>NomeUsuario</center></td><td><center>Numero Tweets</center></td><td><center>Numero Seguidores</center></td><td><center>Numero Seguidos</center></td></tr>"
        		+ "<tr><td><center>Nome</center></td><td><center>10</center></td><td><center>1</center></td><td><center>2</center></td></tr></table>"+ 
		
        	"<br><br><br><font size='25'><center>FEED DE MENSAGENS</font></center>"	+HTML_END);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
