<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="java.text.*" %>
<%@ page import="quack.*" %>
<%@ page import="service.*" %>

<%
String u = request.getParameter("u");
User user = QuackService.getServer(getServletContext()).getUserFromLoginName(u);
String id = request.getParameter("id");
Message message = user.getMessageByDBIndex(Long.parseLong(id));
HTMLImpl formatador = new HTMLImpl();
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
		<title>Quack - Mensagem de <%= user.getFullName() %></title>
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
	          <li><a href="Timeline">Timeline</a></li>
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
		<div class="panel panel-default list">
		<div style="margin: 5px;">
			<div class="row">
				<div class="col-md-2">
					<img src="img/profilepics/<%= user.getLoginName() %>.png" style="width: 96px;" />
				</div>
				<div class="col-md-10">
					<h3><%= user.getFullName() %><br />
					<small><a href="user/<%= user.getLoginName() %>">@<%= user.getLoginName() %></a></small></h3>
				</div>
			</div>
			<hr />
			<div class="row">
				<div class="col-md-12">
					<div><span style="font: 20pt 'Georgia',serif;"><%= formatador.formatMessage(message) %></span></div>
				</div>
			</div>
			<hr />
			<div class="row">
				<div class="col-md-12">
					<p>
					<span class="label label-default">Postado em <%= message.getFormattedDate() %></span> &ndash;
					<a class="label label-primary" href="RepostMessage?id=<%out.print(message.getDBIndex());%>&author=<%out.print(user.getLoginName());%>"><span class="glyphicon glyphicon-retweet"></span> Repostar</a></p>
				</div>
			</div>
		</div>
		</div>
	</div>
</body>
</html>