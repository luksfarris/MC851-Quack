<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="java.text.*" %>
<%@ page import="quack.*" %>
<%@ page import="service.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<title>Linha do tempo</title>
<style>

header nav.navbar-fixed-top, div.container.panel {
  background: rgba(255, 255, 255, 0.75);
}

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
	width: 600px;
	vertical-align: top;
	}
	
	th {
	width: 48px;
	vertical-align: top;
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
	
	.datetime {
	font: 10pt 'Helvetica Neue','Arial',sans-serif;
	color: #808080;
	}
	
</style>
<%
Server server = QuackService.getServer(getServletContext());
List<Message> list = server.processShowReceivedMessagesReq(request, response, getServletContext());
String numPosts = request.getParameter("maxN");
int maxposts = (numPosts == null) ? 30 : Integer.parseInt(numPosts);
%>
</head>
<body>

<header>
      <nav class="navbar navbar-default navbar-fixed-top" role="navigation">
        <div class="container-fluid">
          <div class="navbar-header">
            <a class="navbar-brand" href="UserPage.jsp">
             	Perfil
            </a> |
            <a class="navbar-brand" href="UserListPage.jsp">
             	Usuários do Sistema
            </a> |
            <a class="navbar-brand" href="Logout">
             	Logout
            </a>
          </div>
        </div>
      </nav>
</header>

<div style="width: 700px; margin-left: auto; margin-right: auto;">

	

	
<div class="box">

<p>
<span id="fullname">Postar Mensagem</span><br />
</p>
<form name="createMessage" action="CreateMessage" method="post" accept-charset="utf-8">
<div align="center">
	<textarea rows="4" cols="70" name="messageText" maxlength="100" required></textarea>
	<br>
	<input align="right" type="submit" value="Quack!">
</div>				
</form>

<hr /><hr />
<div style="float: left; padding-left: 8px; vertical-align: top;">
<p>
<span id="fullname">Linha do tempo</span><br />
</p>
</div>

<div style="float: left; clear: left;">
<hr />

<table style="width: 650px;">
<% if (list != null) { %>
<% for (int i = 0; i < maxposts && i < list.size(); i++) { %>

<tr>
<th>
<img src="img/profilepics/<%= list.get(i).getUser().getLoginName() %>.png" style="width: 48px;" />
</th>
<td><b><%= list.get(i).getUser().getFullName() %></b>&ensp;<a href="user/<%= list.get(i).getUser().getLoginName() %>">@<%= list.get(i).getUser().getLoginName() %></a><br />
<%= list.get(i).getText() %><br/>
<span class="datetime">
<a href="MessagePage.jsp?u=<%= list.get(i).getUser().getLoginName() %>&id=<%= list.get(i).getId() %>">Postado em
<% 
DateFormat dateFormat = new SimpleDateFormat("d 'de' MMMMM 'de' yyyy, HH:mm:ss", new Locale("pt"));
long cal = list.get(i).getDate();
out.println(dateFormat.format(new Date(cal * 1000)).toLowerCase());
%></a>
&ndash; <%= 0 %> repostage<%= "ns" %> 
&ndash; <%= 0 %> favorito<%=  "s" %><br/>
<a href="RepostMessage?id=<%out.print(list.get(i).getId());%>&author=<%out.print(list.get(i).getUser().getLoginName());%>">➡ Repostar</a> &mdash; <a href="#">★ Marcar como favorito</a></span>
</td>

</tr>
<% } %>

</table>

<% if (maxposts <= list.size()) { %>
<p style="text-align: center;"><a href="timeline.jsp?maxN=<%= maxposts + 30 %>">↓ Mostrar mais ↓</a></p>
<% } %>
<% } %>
</div>

</div>
</div>
</body>
</html>
