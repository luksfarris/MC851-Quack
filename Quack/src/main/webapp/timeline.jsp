<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="java.text.*" %>
<%@ page import="quack.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<title>Linha do tempo</title>
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
List<Message> list = (List<Message>)request.getSession().getAttribute("timelineMessages");
String numPosts = request.getParameter("posts");
int maxposts = (numPosts == null) ? 10 : Integer.parseInt(numPosts);
%>
</head>
<body>
<div style="width: 700px; margin-left: auto; margin-right: auto;">

<div class="box">


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
<span class="datetime">Postado em
<% 
DateFormat dateFormat = new SimpleDateFormat("d 'de' MMMMM 'de' yyyy, HH:mm:ss");
long cal = list.get(i).getDate();
out.println(dateFormat.format(new Date(cal * 1000)).toLowerCase());
%> 
&ndash; <%= 0 %> repostage<%= "ns" %> 
&ndash; <%= 0 %> favorito<%=  "s" %><br/>
<a href="#">➡ Repostar</a> &mdash; <a href="#">★ Marcar como favorito</a></span>
</td>

</tr>
<% } %>

</table>

<% if (maxposts <= list.size()) { %>
<p style="text-align: center;"><a href="timeline.jsp?posts=<%= maxposts + 5 %>">↓ Mostrar mais ↓</a></p>
<% } %>
<% } %>
</div>

</div>
</div>
</body>
</html>
