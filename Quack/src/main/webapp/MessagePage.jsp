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
	background-color: #BBB;
	}
	
	.box {
	width: 60%;
	margin-left: auto;
	margin-right: auto;
	background-color: #FFF;
	padding: 10px;
	border-radius: 1em 1em 1em;
	}
	
	#fullname { 
	font: bold 16pt 'Helvetica Neue','Arial',sans-serif; 
	}
	
	#loginname {
	font: 12pt 'Helvetica Neue','Arial',sans-serif;
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
	</style>
	</head>
	<body>
		<div class="box">
			<div style="float: left; vertical-align: bottom;">
				<svg xmlns="http://www.w3.org/2000/svg" version="1.1" width="64" height="64" viewBox="0 0 64 64" preserveAspectRatio="xMinYMin">
					<rect x="0" y="0" width="64" height="64" fill="#0FF" />
				</svg>
			</div>
			<p style="padding: 5px;">
				<span id="fullname">Nome de usuário</span><br />
				<span id="loginname">@user</span>
			</p>
			<hr />
			<p id="message">À noite, vovô Kowalsky vê o ímã cair no pé do pinguim queixoso e 
			vovó põe açúcar no chá de tâmaras do jabuti feliz. ★</p>
			<p class="datetime">Postado em 
			<%
				DateFormat dateFormat = new SimpleDateFormat("d 'de' MMMMM 'de' yyyy, HH:mm:ss");
				Calendar cal = Calendar.getInstance();
				out.println(dateFormat.format(cal.getTime()).toLowerCase());
			%>
			</p>
		</div>
	</body>
</html>
