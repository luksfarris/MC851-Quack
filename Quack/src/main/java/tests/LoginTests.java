package tests;
import static org.junit.Assert.assertTrue;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.mockito.Mockito;

import servlets.Cadastro;
import servlets.Login;


public class LoginTests extends Mockito{

	
	@Test
	public void testInvalidUser() throws Exception {
		
		// cria requests e responses falsas
		HttpServletRequest request = mock(HttpServletRequest.class);       
        HttpServletResponse response = mock(HttpServletResponse.class);

        when(request.getParameter("username")).thenReturn("notRealUser");
        when(request.getParameter("password")).thenReturn("password");
        
        StringWriter writer = new StringWriter();
        // cria um writer falso para a resposta
        when(response.getWriter()).thenReturn(new PrintWriter(writer));
        Login loginServlet = new Login();
       
        loginServlet.testDoPost(request, response);
        // assegura que o usuario recebeu a mensagem de falha
        assertTrue(writer.toString().contains("Falha ao realizar login"));
	}
	
	@Test
	public void testRegisterUser() throws Exception {

		// cria requests e responses falsas
		HttpServletRequest request = mock(HttpServletRequest.class);       
		HttpServletResponse response = mock(HttpServletResponse.class);
		when(request.getParameter("username")).thenReturn("testUser");
		when(request.getParameter("password")).thenReturn("testPassword");

        StringWriter writer = new StringWriter();
        // cria um writer falso para a resposta
        when(response.getWriter()).thenReturn(new PrintWriter(writer));
        Cadastro cadastroServlet = new Cadastro();
        cadastroServlet.testDoGet(request, response);
        
        verify(response, times(1)).sendRedirect("/Quack/loginpage.jsp");
       
	}
	
}
