package tests;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.mockito.Mockito;


public class LoginTests extends Mockito{

	@Test
	public void test() throws Exception {
		
		HttpServletRequest request = mock(HttpServletRequest.class);       
        HttpServletResponse response = mock(HttpServletResponse.class);
        
        when(request.getParameter("username")).thenReturn("notRealUser");
        when(request.getParameter("password")).thenReturn("password");

        PrintWriter writer = new PrintWriter("test.txt");
        
        when(response.getWriter()).thenReturn(writer);
        
        //new Login().doPost(request, response);

        verify(request, atLeast(1)).getParameter("username"); 
        writer.flush(); // it may not have been flushed yet...
        
	}

}
