package service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieHelper {
	// Esta classe lida com o gerenciamento de cookies.
	
	// Nome do nosso cookie.
	public static final String COOKIE_NAME = "COOKIE_QUACK";
	// Tempo da validade do cookie, em segundos.
	public static final int COOKIE_AGE = 2592000;
	// Chave para recuperarmos o usuario logado do browser.
	public static final String LOGGED_USER = "QUACK_LOGGED_USER";
	
	
	public static String getCookieValue(HttpServletRequest request, String name) {
	    // Este metodo recupera um cookie dada uma requisicao.
		Cookie[] cookies = request.getCookies();
	    if (cookies != null) {
	        for (Cookie cookie : cookies) {
	            if (name.equals(cookie.getName())) {
	                return cookie.getValue();
	            }
	        }
	    }
	    return null;
	}

	public static void addCookie(HttpServletResponse response, String name, String value, int maxAge) {
	    // este metodo adiciona um cookie novo.
		Cookie cookie = new Cookie(name, value);
	    cookie.setPath("/");
	    cookie.setMaxAge(maxAge);
	    response.addCookie(cookie);
	}

	public static void removeCookie(HttpServletResponse response, String name) {
	    // este metodo remove um cookie.
		addCookie(response, name, null, 0);
	}
}
