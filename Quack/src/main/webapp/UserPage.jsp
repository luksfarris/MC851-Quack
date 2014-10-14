<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Página de Usuário</title>

<style>

	div.container{
		width: 75%;
		height: 100%;
		margin-left: 10%;
	}

	div.mensagem{
    width: 75%;
    height: 100px;
    background-color:#eeeeee;
    align: center;
}
</style>
</head>
<body>
	<%
		int numPosts = 10, followers = 3, follows = 20;
		String UserName = "UserName";
	%>

	<div class='container'>
	pagina de usuario (Implementação Inicial)<br><br>
	<img src="https://www.wevi.com.br/static/img/placeholder/placeholder_user.png" alt="User Picture" style="width:150px;height:150px">
	<br>
	<a href="/FollowUser">Seguir</a> | <a href="BlockUser">Bloquear</a>
	<table border='1'style='width:100%'>
		<tr>
			<td><center>NomeUsuario</center></td>
			<td><center>Numero Posts</center></td>
			<td><center>Numero Seguidores</center></td>
			<td><center>Numero Seguidos</center></td>
		</tr>
		<tr>
			<td><center><%out.println(UserName);%></center></td>
			<td><center><%out.println(numPosts);%></center></td>
			<td><center><%out.println(followers);%></center></td>
			<td><center><%out.println(follows);%></center></td>
		</tr>
	</table>
	<br>
	<hr>
	<center>Feed de Mensagens</center>
	<br>
	<%
		for(int i = 0; i < numPosts; i++)
			
			out.println("<center> <div class='mensagem', align='center'><br><br>Mensagem " + i + "</div></center><br>");
	%>
	</div>
</body>
</html>