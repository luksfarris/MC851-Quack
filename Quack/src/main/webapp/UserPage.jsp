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

    <!-- Custom style -->
    <link rel="stylesheet" type="text/css" href="pub/css/user-profile.css">

    <!-- Bootstrap CDN -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.0/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.0/css/bootstrap-theme.min.css">
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>

    <!-- Font Awesome CDN -->
    <link href="//maxcdn.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css" rel="stylesheet">
  </head>
  <body>
    <header>
      <nav class="navbar navbar-default navbar-fixed-top" role="navigation">
        <div class="container">
          <div class="navbar-header">
            <a class="navbar-brand" href="#">Quack</a>
          </div>
          <div class="navbar-collapse collapse">
            <ul class="nav navbar-nav">
              <li><a href="Timeline">Timeline</a></li>
              <li><a href="UserListPage.jsp">Usuários do Sistema</a></li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
              <li><a href="Logout">Logout</a></li>
            </ul>
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

    <div class="container">
      <div class="col-md-2 user-info">
        <h1><strong><%out.println(UserName);%></strong></h1>
        <div class="thumbnail">
          <img src="<%out.println(PicAddress);%>" alt="User Picture" />
        </div>
        <div class="profile-buttons">
          <a href="Contato?follow=true&userName=<%out.println(user.getLoginName());%>" class="btn btn-success btn-xs">
            <i class="fa fa-plus"></i>
            Seguir
          </a>
        </div>
        <div>
          <ul class="list-group">
            <li class="list-group-item">
              <a href="#">Posts</a> <span class="badge"><%out.println(numPosts);%></span>
            </li>
            <li class="list-group-item">
              <a href="Followers?id=<%out.print(id);%>">Seguidores</a> <span class="badge"><%out.println(followers);%></span>
            </li>
            <li class="list-group-item">
              <a href="Follows?id=<%out.print(id);%>">Seguindo</a> <span class="badge"><%out.println(follows);%></span>
            </li>
          </ul>
        </div>
      </div>
      <div class="col-md-9">
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
