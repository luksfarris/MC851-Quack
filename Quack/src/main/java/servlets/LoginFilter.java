package servlets;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import quack.Server;
import quack.User;
import service.CookieHelper;
import service.QuackService;

/**
 * Servlet Filter implementation class LoginFilter
 */
@WebFilter("/*")
public class LoginFilter implements Filter {

    /**
     * Default constructor. 
     */
    public LoginFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		String path = request.getRequestURI();
		
		if (path.contains("/pub/") || path.endsWith(".png")){
			// senão não faz nada.
		    chain.doFilter(req, res);
		    return;
		}
		// recupera o usuario salvo no browser.
		User user = (User) request.getSession().getAttribute("user");
		// se nao havia usuario, vamos tentar recuperar do cookie.
		if (user == null) {
		    String cookie = CookieHelper.getCookieValue(request, CookieHelper.COOKIE_NAME);
		    // se havia um cookie salvo
		    if (cookie != null) {
		        // checa se ele é válido no servidor
		    	Server server = QuackService.getServer(req.getServletContext());
		    	user = server.getUserFromCookie(cookie);
		    	//se o cookie for válido
		        if (user != null) {
		        	// realiza o login internamente
		            request.login(user.getLoginName(), user.getPassword());
		            request.getSession().setAttribute("user", user);
		            CookieHelper.addCookie(response, CookieHelper.COOKIE_NAME, 
		            		cookie, CookieHelper.COOKIE_AGE);
		        } else {
		        	// senão remove o cookie do browser
		            CookieHelper.removeCookie(response, CookieHelper.COOKIE_NAME);
		        }
		    }
		}
		// se não havia usuário, envia para a págna de login.
		if (user == null) {
		    response.sendRedirect("/Quack/pub/loginpage.jsp");
		} else {
			// senão não faz nada.
		    chain.doFilter(req, res);
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
