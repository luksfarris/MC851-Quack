<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<!DOCTYPE html>
<html>
<head>
<%
String loginName = "usra", fullName = "Usuário A";
String numUsers = request.getParameter("users");
int maxusers = (numUsers == null) ? 9 : Integer.parseInt(numUsers);
List<String[]> list = new LinkedList<String[]>();
list.add(new String[] { "usrb", "Usuário B" });
list.add(new String[] { "usrc", "Usuário C" });
list.add(new String[] { "usrd", "Usuário D" });
list.add(new String[] { "usre", "Usuário E" });
list.add(new String[] { "usrf", "Usuário F" });
list.add(new String[] { "usrg", "Usuário G" });
list.add(new String[] { "usrh", "Usuário H" });
list.add(new String[] { "usri", "Usuário I" });
list.add(new String[] { "usrj", "Usuário J" });
list.add(new String[] { "usrk", "Usuário K" });
list.add(new String[] { "usrl", "Usuário L" });
list.add(new String[] { "usrm", "Usuário M" });
%>
<meta charset="UTF-8" />
<title>Usuários que <%= fullName %> segue</title>
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
	
	h1 {
	font: bold 16pt 'Helvetica Neue','Arial',sans-serif; 
	}
	
	td {
	font: 12pt 'Helvetica Neue','Arial',sans-serif;
	width: 165px;
	}
	
	th {
	width: 48px;
	}
	
	#loginname {
	font: 14pt 'Helvetica Neue','Arial',sans-serif;
	color: #22F;
	}
	
	p {
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
<h1>Usuários que <%= fullName %> segue</h1>

<table style="width: 650px;">
<% int i; for (i = 0; i < maxusers && i < list.size(); i++) { %>

<% if (i % 3 == 0) { %><tr><% } %>
<th>
<img src="img/profilepics/<%= list.get(i)[0] %>.png" style="width: 48px;" />
</th>
<td><b><%= list.get(i)[1] %></b><br /><small>@<%= list.get(i)[0] %></small></td>

<% if (i % 3 == 2) { %></tr><% } %>

<% } %>


<% while (i % 3 != 0) { %>
<th>&nbsp;</th>
<td>&nbsp;</td>
<% if (i % 3 == 2) { %></tr><% } i++; %>
<% } %>

</table>

<p style="text-align: center;"><a href="FollowingPage.jsp?users=<%= maxusers + 9 %>">↓ Mostrar mais ↓</a></p>


</div>

</div>
</div>
</body>
</html>
