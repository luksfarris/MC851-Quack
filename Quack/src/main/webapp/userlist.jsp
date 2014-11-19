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
List<User> users = server.processShowAllUsersReq(request, response, getServletContext());
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
            <a class="navbar-brand" href="Timeline">
             	Timeline
            </a> |
            <a class="navbar-brand" href="#">
             	Logout
            </a>
          </div>
        </div>
      </nav>
</header>

<div style="width: 700px; margin-left: auto; margin-right: auto;">

<div class="box">


<div style="float: left; padding-left: 8px; vertical-align: top;">
<p>
<span id="fullname">Lista de usuarios</span><br />
</p>
</div>

<div style="float: left; clear: left;">
<hr />

<table style="width: 650px;">
<% if (users != null) { %>
<% for(User u: users){%>
<tr>
<td>
<a href="user/<%out.println(u.getLoginName());%>"><%out.println(u.getLoginName());%></a>
</td>
</tr>
<%}}%>
</table>
<center><a href="timeline.jsp">Voltar</a></center>
</div>

</div>
</div>
</body>
</html>
