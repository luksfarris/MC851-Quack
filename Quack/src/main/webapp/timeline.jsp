<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
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
String loginName = "usra", fullName = "Usuário A";
String numPosts = request.getParameter("posts");
int maxposts = (numPosts == null) ? 5 : Integer.parseInt(numPosts);
List<String[]> list = new LinkedList<String[]>();
list.add(new String[] { "usrb", "Usuário B", "The quick brown fox jumps over the lazy dog.", "há 30 segundos", "34", "3" });
list.add(new String[] { "usrc", "Usuário C", " Jackdaws love my big sphinx of quartz.", "há 1 minuto", "12", "1" });
list.add(new String[] { "usrd", "Usuário D", "Luís argüia à Júlia que «brações, fé, chá, óxido, pôr, zângão» eram palavras do português.", "há 1 minuto", "22", "5" });
list.add(new String[] { "usre", "Usuário E", "('◇')ゞ", "há 5 minutos", "1", "2" });
list.add(new String[] { "usrc", "Usuário C", "Buffalo buffalo Buffalo buffalo buffalo buffalo Buffalo buffalo.", "há 19 minutos", "78", "9" });
list.add(new String[] { "usre", "Usuário E", "The beige hue on the waters of the loch impressed all, including the French queen, before she heard that symphony again, just as young Arthur wanted.", "há 46 minutos", "6", "0" });
list.add(new String[] { "usrj", "Usuário J", "健全なる魂は健全なる精神と健全なる肉体に宿る。", "há 1 hora", "120", "14" });
list.add(new String[] { "usrc", "Usuário C", "How u doin’? 😉", "há 2 horas", "134", "54" });
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
<% for (int i = 0; i < maxposts && i < list.size(); i++) { %>

<tr>
<th>
<img src="img/profilepics/<%= list.get(i)[0] %>.png" style="width: 48px;" />
</th>
<td><b><%= list.get(i)[1] %></b>&ensp;<a href="#">@<%= list.get(i)[0] %></a><br />
<%= list.get(i)[2] %><br/>
<span class="datetime">Postado <%= list.get(i)[3] %> 
&ndash; <%= list.get(i)[4] %> repostage<%= list.get(i)[4].equals("1") ? "m" : "ns" %> 
&ndash; <%= list.get(i)[5] %> favorito<%= list.get(i)[5].equals("1") ? "" : "s" %><br/>
<a href="#">➡ Repostar</a> &mdash; <a href="#">★ Marcar como favorito</a></span>
</td>

</tr>
<% } %>

</table>

<% if (maxposts <= list.size()) { %>
<p style="text-align: center;"><a href="timeline.jsp?posts=<%= maxposts + 5 %>">↓ Mostrar mais ↓</a></p>
<% } %>

</div>

</div>
</div>
</body>
</html>
