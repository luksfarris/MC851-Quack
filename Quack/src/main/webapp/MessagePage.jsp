<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="java.text.*" %>
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
String msgText = "À noite, vovô Kowalsky vê o ímã cair no pé do pinguim queixoso e vovó põe açúcar no chá de tâmaras do jabuti feliz. ★";
String loginName = "usra", fullName = "Usuário A";
int reposts = 23, favorites = 5;
int maxposts = Integer.parseInt(request.getParameter("notes"));
List<String[]> notes = new LinkedList<String[]>();
notes.add(new String[] { "usrq", "Usuário Q", "Usuário K" });
notes.add(new String[] { "usro", "Usuário O", "Usuário K" });
notes.add(new String[] { "usrk", "Usuário K", null });
notes.add(new String[] { "usrk", "Usuário K", "Usuário D" });
notes.add(new String[] { "usrh", "Usuário H", "Usuário B" });
notes.add(new String[] { "usrg", "Usuário G", "Usuário E" });
notes.add(new String[] { "usre", "Usuário E", null });
notes.add(new String[] { "usre", "Usuário E", "Usuário D" });
notes.add(new String[] { "usrd", "Usuário D", "Usuário A" });
notes.add(new String[] { "usrb", "Usuário B", "Usuário A" });
%>	
	</head>
	<body>
		<div style="width: 700px; margin-left: auto; margin-right: auto;">
			<div class="box">
				<div style="float: left;">
					<img src="img/profilepics/<%= loginName %>.png" style="width: 64px;" alt="Imagem de <%= fullName %>" />
				</div>
				<div style="float: left; padding-left: 8px; vertical-align: top;">
					<p>
						<span id="fullname"><%= fullName %></span><br />
						<span id="loginname">@<%= loginName %></span>
					</p>
				</div>
				<div style="float: left; clear: left;">
					<hr />
					<p id="message"><%= msgText %></p>
					<p class="datetime">Postado em 
					<%
						DateFormat dateFormat = new SimpleDateFormat("d 'de' MMMMM 'de' yyyy, HH:mm:ss");
						Calendar cal = Calendar.getInstance();
						out.println(dateFormat.format(cal.getTime()).toLowerCase());
					%>
					</p>
					
					<p>
						<a href="#">➡ Repostar</a> &mdash; <a href="#">★ Marcar como favorito</a>
					</p>
					
					<table style="margin-left: auto; margin-right: auto; color: #888;">
						<tr>
							<th><%= reposts %></th>
							<th><%= favorites %></th>
						</tr>
						<tr>
							<td>REPOSTAGENS</td>
							<td>FAVORITOS</td>
						</tr>
					</table>
					<hr />
				
					<ul style="list-style-type: none;">
					<%
					for (int i = 0; i < maxposts; i++)
					{
					%>
						<li>
							<img src="img/profilepics/<%= notes.get(i)[0] %>.png" style="width: 16px;" />
							<p style="display: inline;">
								<a href="#"><%= notes.get(i)[1] %></a>
								<% if (notes.get(i)[2] == null) { %>
								marcou isto como favorito
								<% } else { %>
								repostou isto de <a href="#"><%= notes.get(i)[2] %></a>
								<% } %>
							</p>
						</li>
					<%
					}
					maxposts += 5;
					%>
					
					</ul>
					
					<p style="text-align: center;"><a href="#">↓ Mostrar mais ↓</a></p>
				</div>
			</div>
		</div>
	</body>
</html>
