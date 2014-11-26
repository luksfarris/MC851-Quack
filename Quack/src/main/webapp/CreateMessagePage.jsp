<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="java.text.*" %>
<!DOCTYPE html>

<html>
	<head>
		<meta charset="UTF-8" />
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
				<div align="center"><h2>Message</h2></div>
				<form name="createMessage" action="CreateMessage" method="post" accept-charset="utf-8">
				<div align="center">
					<textarea rows="4" cols="70" name="messageText" maxlength="100" required></textarea>
					<br>
	<p style="text-align: right;"><input type="submit" value="Quack!"></p>
				</div>
					
				</form>
			</div>
		</div>

	</body>
</html>

