<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="java.text.*" %>
<%@ page import="quack.*" %>
<%@ page import="service.*" %>
<%
Server server = QuackService.getServer(getServletContext());
List<Message> list = server.processShowReceivedMessagesReq(request, response, getServletContext());
String numPosts = request.getParameter("maxN");
int maxposts = (numPosts == null) ? 30 : Integer.parseInt(numPosts);
Timestamp t = new TimestampImpl();
HTMLImpl formatador = new HTMLImpl();
String imgURL;
User user;
String cookie = CookieHelper.getCookieValue(request, CookieHelper.COOKIE_NAME);
user = QuackService.getServer(getServletContext()).getUserFromCookie(cookie);
imgURL =  "pub/img/profilepics/" + String.valueOf(user.getDbIndex()) + ".jpg";

pageContext.setAttribute("imgURL", imgURL);
%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8" />
		<title>Quack - Timeline</title>
		<!-- Bootstrap CDN -->
	    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.0/css/bootstrap.min.css" />
	    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.0/css/bootstrap-theme.min.css" />
	    <script src="http://code.jquery.com/jquery-2.1.1.min.js"></script>
	    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>
	
	    <!-- Font Awesome CDN -->
	    <link href="//maxcdn.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css" rel="stylesheet" />
	
	    <!-- Custom style -->
	    <link rel="stylesheet" type="text/css" href="pub/css/user-profile.css" />
	</head>
	<body>
		<header>
	      <nav class="navbar navbar-default navbar-fixed-top" role="navigation">
	        <div class="container">
	          <div class="navbar-header">
	            <a class="navbar-brand" href="#">Quack</a>
	          </div>
	          <div class="navbar-collapse collapse">
	            <ul class="nav navbar-nav">
	              <li><a href="UserPage.jsp">Perfil</a></li>
	              <li><a href="UserListPage.jsp">Usuários do Sistema</a></li>
	            </ul>
	            <ul class="nav navbar-nav navbar-right">
	              <li><a href="Logout">Logout</a></li>
	            </ul>
	          </div>
	        </div>
	      </nav>
	    </header>
	    <div class="container">
	    	<h1>Timeline</h1>
	    	<div class="row">
	    		<div class="col-md-3 user-info">
          			<div class="thumbnail">
           				 <img src="${imgURL}" />
         			 </div>
         		</div>
		    	<div class="col-md-9">
			    	<div class="panel panel-default">
			    		<div class="panel-body">
				   			<form name="createMessage" action="CreateMessage" method="post" accept-charset="utf-8" role="form">
				   				<div class="form-group">
								<textarea class="form-control" rows="4" cols="70" name="messageText" required="required"></textarea>
								</div>
								<div class="form-group">
								<p class="text-right">
									<input class="btn btn-primary" type="submit" value="Postar mensagem" />
								</p>
								</div>			
							</form>
						</div>
					</div>
					<div class="panel panel-default list">
						<table class="table">
						<% if (list != null) { %>
						<% for (int i = 0; i < maxposts && i < list.size(); i++) { %>
							<tr>
								<td>
									<img src="img/profilepics/<%= list.get(i).getUser().getLoginName() %>.png" style="width: 48px;" />
								</td>
								<td>
									<p><strong><%= list.get(i).getUser().getFullName() %></strong>&emsp;<a href="user/<%= list.get(i).getUser().getLoginName() %>">@<%= list.get(i).getUser().getLoginName() %></a>
									
									<% if (list.get(i).getParent() != null) { %>
									&ndash; <span class="glyphicon glyphicon-retweet"></span> Repostado de <%= list.get(i).getParent().getUser().getFullName() %> 
									(<a href="user/<%= list.get(i).getParent().getUser().getLoginName() %>">@<%= list.get(i).getParent().getUser().getLoginName() %></a>)
									<% } %>
									
									</p>
									<p><%= formatador.formatMessage(list.get(i)) %></p>
									<p>
										<span class="label label-default">Postado em <%= list.get(i).getFormattedDate() %></span> &ndash;
										<a class="label label-default" href="MessagePage.jsp?u=<%= list.get(i).getUser().getLoginName() %>&id=<%= list.get(i).getDBIndex() %>"><span class="glyphicon glyphicon-link"></span> Permalink</a> &ndash;
										<a class="label label-primary" href="RepostMessage?id=<%out.print(list.get(i).getDBIndex());%>&author=<%out.print(list.get(i).getUser().getLoginName());%>"><span class="glyphicon glyphicon-retweet"></span> Repostar</a></p>
								</td>
							</tr>
							<% } %>
						</table>
						<% if (maxposts <= list.size()) { %>
						<p class="text-center" style="margin-top: 5px;">
							<a class="btn btn-default" href="timeline.jsp?maxN=<%= maxposts + 30 %>">
								<span class="glyphicon glyphicon-arrow-down"></span> Mostrar mais <span class="glyphicon glyphicon-arrow-down"></span>
							</a>
						</p>
						<% } %>
						<% } %>
					</div>
				</div>
			</div>
	    </div>
	</body>
</html>