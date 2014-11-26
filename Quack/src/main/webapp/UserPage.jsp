<%@page import="service.QuackService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="quack.User" %>
<%@ page import="quack.Message" %>
<%@ page import="java.util.List" %>
<%@ page import="service.CookieHelper" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<title>Página de Usuário</title>

<style>
	header nav.navbar-fixed-top, div.container.panel {
  		background: rgba(255, 255, 255, 0.75);
	}

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
    border-radius: 15px;
}

td { text-align: center; }
</style>
</head>
<body>

	<header>
      <nav class="navbar navbar-default navbar-fixed-top" role="navigation">
        <div class="container-fluid">
          <div class="navbar-header">
            <a class="navbar-brand" href="Timeline">Timeline</a> |
            <a class="navbar-brand" href="UserListPage.jsp">Usuários do Sistema</a> |
            <a class="navbar-brand" href="Logout">Logout</a>
          </div>
        </div>
      </nav>
	</header>

	<%
		//DADOS DO USUARIO (pegar do BD)
		String loginName = request.getParameter("u");
		User user;	
		if(loginName == null){
            String cookie = CookieHelper.getCookieValue(request, CookieHelper.COOKIE_NAME);
            user = QuackService.getServer(getServletContext()).getUserFromCookie(cookie);        
		} else {
			user = QuackService.getServer(getServletContext()).getUserFromLoginName(loginName);
		}
		int numPosts = user.getPostedMessages().size();
		int followers = user.followersCount();
		int follows = user.followsCount(); 
		long id = user.getDbIndex();
		String UserName = user.getLoginName();
		String PicAddress = "https://www.wevi.com.br/static/img/placeholder/placeholder_user.png";
		List<Message> messages = user.getPostedMessages();
	%>

	<div id='container' class='container'>
	pagina de usuario (Implementação Inicial)<br><br>
	<img src= <%out.println(PicAddress);%> alt="User Picture" style="width:150px;height:150px">
	<br>
	
	<a href="Contato?follow=true&userName=<%out.println(user.getLoginName());%>">Seguir</a> | 
	<a href="Contato?follow=false&userName=<%out.println(user.getLoginName());%>">Bloquear</a> |
	<a href="CreateMessagePage.jsp">Postar Mensagem</a>
	<table border='1'style='width:100%'>
		<tr>
			<td>NomeUsuario</td>
			<td>Numero Posts</td>
			<td>Numero Seguidores</td>
			<td>Numero Seguidos</td>
		</tr>
		<tr>
			<td><center><%out.println(UserName);%></center></td>
			<td><center><%out.println(numPosts);%></center></td>
			<td><center><a href="Followers?id=<%out.print(id);%>"><%out.print(followers);%></a></center></td>
			<td><center><a href="Follows?id=<%out.print(id);%>"><%out.print(follows);%></a></center></td>
		</tr>
	</table>
	<br>
	<hr>
	<p style="text-align: center;">Feed de Mensagens</p>
	<% //Printa Mensagens
		int i = 0;
		for(Message m : messages){
			out.println("<center> <div id='msg"+ i + "'class='mensagem', align='center'>"+
		"<a onclick='hideMsg("+ i +")''><font color = blue>Esconder</font></a><br><br>" + m.getText()
			+ "<br><br><a href='RepostMessage?id="+ m.getDBIndex() +"&author="+ m.getUser().getLoginName() + "'>Repostar</a></div></center><br>");
			i++;
		}
	%>
	</div>
</body>

<script>
function hideMsg(i){
	var msg = document.getElementById('msg' + i);
	var container = msg.parentElement;
	container.removeChild(msg);
}

</script>

</html>