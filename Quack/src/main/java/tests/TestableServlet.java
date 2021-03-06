package tests;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.EventListener;
import java.util.Map;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterRegistration;
import javax.servlet.FilterRegistration.Dynamic;
import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.SessionCookieConfig;
import javax.servlet.SessionTrackingMode;
import javax.servlet.descriptor.JspConfigDescriptor;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class TestableServlet
 */
public class TestableServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private boolean testing = false;   
	private static ServletContext testContext = null;
	private Object savedObject = null;
    
	public void testDoGet (HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		testing = true;
		doGet(request, response);
	}
	
	public void testDoPost (HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		testing = true;
		doPost(request, response);
	}
	
	@Override
	public ServletContext getServletContext() {
		if (!testing) {
			return super.getServletContext();
		} else {
			if (testContext != null) {
				return testContext;
			}
			testContext = new ServletContext() {
				
				@Override
				public void setSessionTrackingModes(Set<SessionTrackingMode> arg0)
						throws IllegalStateException, IllegalArgumentException {
				}
				
				@Override
				public boolean setInitParameter(String arg0, String arg1) {
					return false;
				}
				
				@Override
				public void setAttribute(String arg0, Object arg1) {
					savedObject = arg1;
				}
				
				@Override
				public void removeAttribute(String arg0) {
					savedObject = null;
				}
				
				@Override
				public void log(String arg0, Throwable arg1) {
				}
				
				@Override
				public void log(Exception arg0, String arg1) {
				}
				
				@Override
				public void log(String arg0) {
				}
				
				@Override
				public SessionCookieConfig getSessionCookieConfig() {
					return null;
				}
				
				@Override
				public Enumeration<Servlet> getServlets() {
					return null;
				}
				
				@Override
				public Map<String, ? extends ServletRegistration> getServletRegistrations() {
					return null;
				}
				
				@Override
				public ServletRegistration getServletRegistration(String arg0) {
					return null;
				}
				
				@Override
				public Enumeration<String> getServletNames() {
					return null;
				}
				
				@Override
				public String getServletContextName() {
					return null;
				}
				
				@Override
				public Servlet getServlet(String arg0) throws ServletException {
					return null;
				}
				
				@Override
				public String getServerInfo() {
					return null;
				}
				
				@Override
				public Set<String> getResourcePaths(String arg0) {
					return null;
				}
				
				@Override
				public InputStream getResourceAsStream(String arg0) {
					return null;
				}
				
				@Override
				public URL getResource(String arg0) throws MalformedURLException {
					return null;
				}
				
				@Override
				public RequestDispatcher getRequestDispatcher(String arg0) {
					return null;
				}
				
				@Override
				public String getRealPath(String arg0) {
					return null;
				}
				
				@Override
				public RequestDispatcher getNamedDispatcher(String arg0) {
					return null;
				}
				
				@Override
				public int getMinorVersion() {
					return 0;
				}
				
				@Override
				public String getMimeType(String arg0) {
					return null;
				}
				
				@Override
				public int getMajorVersion() {
					return 0;
				}
				
				@Override
				public JspConfigDescriptor getJspConfigDescriptor() {
					return null;
				}
				
				@Override
				public Enumeration<String> getInitParameterNames() {
					return null;
				}
				
				@Override
				public String getInitParameter(String arg0) {
					return null;
				}
				
				@Override
				public Map<String, ? extends FilterRegistration> getFilterRegistrations() {
					return null;
				}
				
				@Override
				public FilterRegistration getFilterRegistration(String arg0) {
					return null;
				}
				
				@Override
				public Set<SessionTrackingMode> getEffectiveSessionTrackingModes() {
					return null;
				}
				
				@Override
				public int getEffectiveMinorVersion() {
					return 0;
				}
				
				@Override
				public int getEffectiveMajorVersion() {
					return 0;
				}
				
				@Override
				public Set<SessionTrackingMode> getDefaultSessionTrackingModes() {
					return null;
				}
				
				@Override
				public String getContextPath() {
					return null;
				}
				
				@Override
				public ServletContext getContext(String arg0) {
					return null;
				}
				
				@Override
				public ClassLoader getClassLoader() {
					return null;
				}
				
				@Override
				public Enumeration<String> getAttributeNames() {
					return null;
				}
				
				@Override
				public Object getAttribute(String arg0) {
					return savedObject;
				}
				
				@Override
				public void declareRoles(String... arg0) {
					
				}
				
				@Override
				public <T extends Servlet> T createServlet(Class<T> arg0)
						throws ServletException {
					return null;
				}
				
				@Override
				public <T extends EventListener> T createListener(Class<T> arg0)
						throws ServletException {
					return null;
				}
				
				@Override
				public <T extends Filter> T createFilter(Class<T> arg0)
						throws ServletException {
					return null;
				}
				
				@Override
				public javax.servlet.ServletRegistration.Dynamic addServlet(String arg0,
						Class<? extends Servlet> arg1) {
					return null;
				}
				
				@Override
				public javax.servlet.ServletRegistration.Dynamic addServlet(String arg0,
						Servlet arg1) {
					return null;
				}
				
				@Override
				public javax.servlet.ServletRegistration.Dynamic addServlet(String arg0,
						String arg1) {
					return null;
				}
				
				@Override
				public void addListener(Class<? extends EventListener> arg0) {
					
				}
				
				@Override
				public <T extends EventListener> void addListener(T arg0) {
					
				}
				
				@Override
				public void addListener(String arg0) {
					
				}
				
				@Override
				public Dynamic addFilter(String arg0, Class<? extends Filter> arg1) {
					return null;
				}
				
				@Override
				public Dynamic addFilter(String arg0, Filter arg1) {
					return null;
				}
				
				@Override
				public Dynamic addFilter(String arg0, String arg1) {
					return null;
				}
			};
			return testContext;
		}
	}
}
