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
	</head>
	<body>
		<div style="width: 700px; margin-left: auto; margin-right: auto;">
			<div class="box">
				<div style="float: left;">
					<svg xmlns="http://www.w3.org/2000/svg" version="1.1" width="64" height="64" viewBox="0 0 64 64" preserveAspectRatio="xMinYMin">
						<rect x="0" y="0" width="64" height="64" fill="#0FF" />
					</svg>
				</div>
				<div style="float: left; padding-left: 8px; vertical-align: top;">
					<p>
						<span id="fullname">Usuário A</span><br />
						<span id="loginname">@usra</span>
					</p>
				</div>
				<div style="float: left; clear: left;">
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
					
					<p>
						<a href="#">➡ Repostar</a> &mdash; <a href="#">★ Marcar como favorito</a>
					</p>
					
					<table style="margin-left: auto; margin-right: auto; color: #888;">
						<tr>
							<th>23</th>
							<th>5</th>
						</tr>
						<tr>
							<td>REPOSTAGENS</td>
							<td>FAVORITOS</td>
						</tr>
					</table>
					<hr />
				
					<ul style="list-style-type: none;">
						<li>
							<svg xmlns="http://www.w3.org/2000/svg" version="1.1" width="16" height="16" viewBox="0 0 16 16" preserveAspectRatio="xMinYMin">
								<rect x="0" y="0" width="16" height="16" fill="#808" />
							</svg>
							<p style="display: inline;">
								<a href="#">Usuário Q</a> repostou isto de <a href="#">Usuário K</a>
							</p>
						</li>
						
						<li>
							<svg xmlns="http://www.w3.org/2000/svg" version="1.1" width="16" height="16" viewBox="0 0 16 16" preserveAspectRatio="xMinYMin">
								<rect x="0" y="0" width="16" height="16" fill="#088" />
							</svg>
							<p style="display: inline;">
								<a href="#">Usuário O</a> repostou isto de <a href="#">Usuário K</a>
							</p>
						</li>
						
						<li>
							<svg xmlns="http://www.w3.org/2000/svg" version="1.1" width="16" height="16" viewBox="0 0 16 16" preserveAspectRatio="xMinYMin">
								<rect x="0" y="0" width="16" height="16" fill="#880" />
							</svg>
							<p style="display: inline;">
								<a href="#">Usuário K</a> marcou isto como favorito
							</p>
						</li>
						
						<li>
							<svg xmlns="http://www.w3.org/2000/svg" version="1.1" width="16" height="16" viewBox="0 0 16 16" preserveAspectRatio="xMinYMin">
								<rect x="0" y="0" width="16" height="16" fill="#880" />
							</svg>
							<p style="display: inline;">
								<a href="#">Usuário K</a> repostou isto de <a href="#">Usuário D</a>
							</p>
						</li>
						
						<li>
							<svg xmlns="http://www.w3.org/2000/svg" version="1.1" width="16" height="16" viewBox="0 0 16 16" preserveAspectRatio="xMinYMin">
								<rect x="0" y="0" width="16" height="16" fill="#FF0" />
							</svg>
							<p style="display: inline;">	
								<a href="#">Usuário H</a> repostou isto de <a href="#">Usuário B</a>
							</p>
						</li>
						
						<li>
							<svg xmlns="http://www.w3.org/2000/svg" version="1.1" width="16" height="16" viewBox="0 0 16 16" preserveAspectRatio="xMinYMin">
								<rect x="0" y="0" width="16" height="16" fill="#F0F" />
							</svg>
							<p style="display: inline;">
								<a href="#">Usuário G</a> repostou isto de <a href="#">Usuário E</a>
							</p>
						</li>
						
						<li>
							<svg xmlns="http://www.w3.org/2000/svg" version="1.1" width="16" height="16" viewBox="0 0 16 16" preserveAspectRatio="xMinYMin">
								<rect x="0" y="0" width="16" height="16" fill="#00F" />
							</svg>
							<p style="display: inline;">
								<a href="#">Usuário E</a> marcou isto como favorito</a>
							</p>
						</li>
						
						<li>
							<svg xmlns="http://www.w3.org/2000/svg" version="1.1" width="16" height="16" viewBox="0 0 16 16" preserveAspectRatio="xMinYMin">
								<rect x="0" y="0" width="16" height="16" fill="#00F" />
							</svg>
							<p style="display: inline;">
								<a href="#">Usuário E</a> repostou isto de <a href="#">Usuário D</a>
							</p>
						</li>
						
						<li>
							<svg xmlns="http://www.w3.org/2000/svg" version="1.1" width="16" height="16" viewBox="0 0 16 16" preserveAspectRatio="xMinYMin">
								<rect x="0" y="0" width="16" height="16" fill="#0F0" />
							</svg>
							<p style="display: inline;">
								<a href="#">Usuário D</a> repostou isto de <a href="#">Usuário A</a>
							</p>
						</li>
						
						<li>
							<svg xmlns="http://www.w3.org/2000/svg" version="1.1" width="16" height="16" viewBox="0 0 16 16" preserveAspectRatio="xMinYMin">
								<rect x="0" y="0" width="16" height="16" fill="#F00" />
							</svg>
							<p style="display: inline;">
								<a href="#">Usuário B</a> repostou isto de <a href="#">Usuário A</a>
							</p>
						</li>
					</ul>
					
					<p style="text-align: center;"><a href="#">↓ Mostrar mais ↓</a></p>
				</div>
			</div>
		</div>
	</body>
</html>
