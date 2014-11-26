<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="java.text.*" %>
<%@ page import="quack.*" %>
<%@ page import="service.*" %>
<!DOCTYPE html>
<html>
	<!-- Página em teste -->
	<head>
	<meta charset="UTF-8" />
	<title>Mensagem</title>
	<style>
	body {
	background-color: #888;
	}
	
	.box {
	float: left;
	background-color: #FFF;
	padding: 10px;
	border-radius: 1em 1em 1em;
	
	}
	
	#fullname { 
	font: bold 20pt 'Helvetica Neue','Arial',sans-serif; 
	}
	
	#loginname {
	font: 14pt 'Helvetica Neue','Arial',sans-serif;
	color: #22F;
	}
	
	#message {
	font: 20pt 'Georgia',serif;
	text-align: left;
	}
	
	.datetime {
	font: 10pt 'Helvetica Neue','Arial',sans-serif;
	color: #808080;
	}
	
	th {
	font: 20pt 'Helvetica Neue','Arial',sans-serif;
	padding: 0 5px;
	}
	
	td {
	font: 8pt 'Helvetica Neue','Arial',sans-serif;
	padding: 0 5px;
	}
	
	li, p {
	font: 10pt 'Helvetica Neue','Arial',sans-serif;
	}
	
	a {
	color: #22F;
	text-decoration: none;
	}
	
	a:hover, a:active {
	text-decoration: underline;
	}
	</style>
<%
String u = request.getParameter("u");
User user;
user = QuackService.getServer(getServletContext()).getUserFromLoginName(u);
String id = request.getParameter("id");
Message message;
message = user.getMessageByDBIndex(Long.parseLong(id));
%>	
	</head>
	<body>
		<div style="width: 700px; margin-left: auto; margin-right: auto;">
			<div class="box">
			<% if (message != null) { %>
				<div style="float: left;">
					<img src="img/profilepics/<%= user.getLoginName() %>.png" style="width: 64px;" alt="Imagem de <%= user.getFullName() %>" />
				</div>
				<div style="float: left; padding-left: 8px; vertical-align: top;">
					<p>
						<span id="fullname"><%= user.getFullName() %></span><br />
						<a id="loginname" href="user/<%= user.getLoginName() %>">@<%= user.getLoginName() %></a>
					</p>
				</div>
				<div style="float: left; clear: left;">
					<hr />
					<p id="message"><%= message.getText() %></p>
					<p class="datetime">Postado em 
					<%
						DateFormat dateFormat = new SimpleDateFormat("d 'de' MMMMM 'de' yyyy, HH:mm:ss");
						out.println(dateFormat.format(new Date(message.getDate() * 1000)).toLowerCase());
					%>
					</p>
					
					<p>
						<a href="#">➡ Repostar</a>
					</p>
					
					<table style="margin-left: auto; margin-right: auto; color: #888;">
						<tr>
							<th>0</th>
						</tr>
						<tr>
							<td>REPOSTAGENS</td>
						</tr>
					</table>
					
				
					
					
				</div>
			<% } %>
			</div>
		</div>
	</body>
</html>
