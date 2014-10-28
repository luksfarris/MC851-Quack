package quack;

import java.util.List;

public class HTMLImpl implements HTML {
	public static final String HTML_START = "<!DOCTYPE html><html><body>";
	public static final String HTML_END = "</body></html>";
	
	@Override
	public String errorPage(String msg) {
		// TODO Auto-generated method stub
		return HTML_START + "<h1>ERROR!!</h1>\n"
				+ "<h2>"+msg+"</h2>" + HTML_END;
	}

	@Override
	public String loginPage() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override

	public String userProfilePage(String cookie, User source, User target) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override

	public String messageListPage(String cookie, String title, User user,
			List<Message> messages, int maxN) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String homePage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void initialize(Server server) {
	}

	@Override	
	public String loginSuccessfulPage(String cookie, User user) {
		// TODO Auto-generated method stub
		return null;
	}
}
