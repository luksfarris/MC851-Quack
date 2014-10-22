<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="java.text.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Create Message</title>
	</head>
	
	<style>
	body {
	background-color: #888;
	}
	
	.box {
	float: center;
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
	
	<body>
		<div style="width: 700px; margin-left: auto; margin-right: auto;">
			<div class="box">
				<div align="center">Message</div>
				<form name="createMessage" action="CreateMessage" method="get" accept-charset="utf-8">
				<div align="right">
					<textarea rows="3" cols="70" name="messageText" required></textarea>
					<input align="right" type="submit" value="Quack!">
				</div>
				
				</form>
			</div>
		</div>

	</body>
</html>

