<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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

<tr>
<th>
<svg xmlns="http://www.w3.org/2000/svg" version="1.1" width="48" height="48" viewBox="0 0 48 48" preserveAspectRatio="xMinYMin">
	<rect x="0" y="0" width="48" height="48" fill="#000" />
</svg>
</th>
<td><b>Usuário B</b>&ensp;@usrb<br />
The quick brown fox jumps over the lazy dog.<br/>
<span class="datetime">Postado em  21 de outubro de 2014, 19:38:00 &ndash; 34 repostagens &ndash; 3 favoritos<br/>
<a href="#">➡ Repostar</a> &mdash; <a href="#">★ Marcar como favorito</a></span>
</td>

</tr>
<tr>
<th>
<svg xmlns="http://www.w3.org/2000/svg" version="1.1" width="48" height="48" viewBox="0 0 48 48" preserveAspectRatio="xMinYMin">
	<rect x="0" y="0" width="48" height="48" fill="#F00" />
</svg>
</th>
<td><b>Usuário C</b>&ensp;@usrc<br />
Jackdaws love my big sphinx of quartz.<br/>
<span class="datetime">Postado em  21 de outubro de 2014, 19:36:20 &ndash; 12 repostagens &ndash; 1 favorito<br/>
<a href="#">➡ Repostar</a> &mdash; <a href="#">★ Marcar como favorito</a></span>
</td>
</tr>

</tr>
<tr>
<th>
<svg xmlns="http://www.w3.org/2000/svg" version="1.1" width="48" height="48" viewBox="0 0 48 48" preserveAspectRatio="xMinYMin">
	<rect x="0" y="0" width="48" height="48" fill="#0F0" />
</svg>
</th>
<td><b>Usuário D</b>&ensp;@usrd<br />
Luís argüia à Júlia que «brações, fé, chá, óxido, pôr, zângão» eram palavras do português.<br/>
<span class="datetime">Postado em  21 de outubro de 2014, 19:36:20 &ndash; 22 repostagens &ndash; 5 favoritos<br/>
<a href="#">➡ Repostar</a> &mdash; <a href="#">★ Marcar como favorito</a></span>
</td>
</tr>

</tr>
<tr>
<th>
<svg xmlns="http://www.w3.org/2000/svg" version="1.1" width="48" height="48" viewBox="0 0 48 48" preserveAspectRatio="xMinYMin">
	<rect x="0" y="0" width="48" height="48" fill="#00F" />
</svg>
</th>
<td><b>Usuário E</b>&ensp;@usre<br />
('◇')ゞ<br/>
<span class="datetime">Postado em  21 de outubro de 2014, 19:20:20 &ndash; 1 repostagem &ndash; 2 favoritos<br/>
<a href="#">➡ Repostar</a> &mdash; <a href="#">★ Marcar como favorito</a></span>
</td>
</tr>

</table>

<p style="text-align: center;"><a href="#">↓ Mostrar mais ↓</a></p>


</div>

</div>
</div>
</body>
</html>
