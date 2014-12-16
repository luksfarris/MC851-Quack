<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="service.QuackService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="quack.*" %>
<%@ page import="java.util.List" %>
<%@ page import="service.CookieHelper" %>

<%
  String loginName = request.getParameter("u");
  User user;
  String cookie = CookieHelper.getCookieValue(request, CookieHelper.COOKIE_NAME);
  String imgURL;
  user = QuackService.getServer(getServletContext()).getUserFromCookie(cookie);
  pageContext.setAttribute("currentUserName", user.getLoginName());

  HTMLImpl formatador = new HTMLImpl();
  if (loginName == null || (user != null && user.getLoginName().equals(loginName))) {
    pageContext.setAttribute("isCurrentUserPage", true);
  }
  else {
    user = QuackService.getServer(getServletContext()).getUserFromLoginName(loginName);
    pageContext.setAttribute("isCurrentUserPage", false);
  }

  imgURL =  "pub/img/profilepics/" + String.valueOf(user.getDbIndex()) + ".jpg";
  
  pageContext.setAttribute("id", user.getDbIndex());
  pageContext.setAttribute("user", user);
  pageContext.setAttribute("userName", user.getLoginName());
  pageContext.setAttribute("messages", user.getPostedMessages());
  pageContext.setAttribute("formatador", formatador);
  pageContext.setAttribute("imgURL", imgURL);
%>

<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <title>Quack - Perfil de @${user.getLoginName()}</title>

    <!-- Bootstrap CDN -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.0/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.0/css/bootstrap-theme.min.css">
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>

    <!-- Font Awesome CDN -->
    <link href="//maxcdn.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css" rel="stylesheet">

    <!-- Custom style -->
    <link rel="stylesheet" type="text/css" href="pub/css/user-profile.css">
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
              <li><a href="user/${currentUserName}">Meu perfil</a></li>
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

    <div class="container">
      <div class="row">
        <div class="col-md-12 profile-title">
          <h2 class="fullname">${user.getFullName()}</h2>
          <h2 class="username"><small>@${userName}</small></h2>
        </div>
      </div>
      <div class="row">
        <div class="col-md-3 user-info">
          <div class="thumbnail">
            <img src="${imgURL}" />
          </div>
          <div class="profile-buttons">
            <c:choose>
              <c:when test="${isCurrentUserPage}">
                <a href="UpdateUserPage.jsp" class="btn btn-success btn-xs">
                  <i class="fa fa-edit"></i>
                  Editar perfil
                </a>
              </c:when>
              <c:otherwise>
                <a href="Contato?state=follow&userName=${userName}" class="btn btn-success btn-xs">
                  <i class="fa fa-plus"></i>
                  Seguir
                </a>
              </c:otherwise>
            </c:choose>
          </div>
          <div>
            <div class="list-group">
              <a href="user/${userName}" class="list-group-item active">Mensagens <span class="badge">${messages.size()}</span></a>
              <a href="Followers?id=${id}" class="list-group-item">Seguidores <span class="badge">${user.followersCount()}</span></a>
              <a href="Follows?id=${id}" class="list-group-item">Seguindo <span class="badge">${user.followsCount()}</span></a>
            </div>
          </div>
        </div>
        <div class="col-md-9">
          <div class="panel panel-default list">
            <c:choose>
              <c:when test="${messages.size() > 0}">
                <table class="table table-striped table-hover">
                  <tbody>
                    <c:choose>
                      <c:when test="${isCurrentUserPage}">
                        <c:forEach items="${messages}" var="m" varStatus="loop">
                          <tr id="row-${loop.index}" class="row">
                            <td><img src="img/profilepics/${m.getUser().getLoginName()}.png" style="width: 48px;" /></td>
                            <td>
                            <p><strong>${m.getUser().getFullName()}</strong>&emsp;<a href="user/${m.getUser().getLoginName()}">@${m.getUser().getLoginName()}</a></p>
								<p>${formatador.formatMessage(m)}</p>
								<p>
								<span class="label label-default">Postado em ${m.getFormattedDate()}</span> &ndash;
								<a class="label label-default" href="MessagePage.jsp?u=${m.getUser().getLoginName()}&id=${m.getDBIndex()}"><span class="glyphicon glyphicon-link"></span> Permalink</a>                            </td>
                          </tr>
                        </c:forEach>
                      </c:when>
                      <c:otherwise>
                        <c:forEach items="${messages}" var="m" varStatus="loop">
                          <tr id="row-${loop.index}" class="row">
                            <td><img src="img/profilepics/${m.getUser().getLoginName()}.png" style="width: 48px;" /></td>
                            <td>
                            <p><strong>${m.getUser().getFullName()}</strong>&emsp;<a href="user/${m.getUser().getLoginName()}">@${m.getUser().getLoginName()}</a></p>
								<p>${formatador.formatMessage(m)}</p>
								<p>
								<span class="label label-default">Postado em ${m.getFormattedDate()}</span> &ndash;
								<a class="label label-default" href="MessagePage.jsp?u=${m.getUser().getLoginName()}&id=${m.getDBIndex()}"><span class="glyphicon glyphicon-link"></span> Permalink</a> &ndash;
								<a class="label label-primary" href="RepostMessage?id=${m.getDBIndex()}&author=${m.getUser().getLoginName()}"><span class="glyphicon glyphicon-retweet"></span> Repostar</a></p>
                            </td>
                          </tr>
                        </c:forEach>
                      </c:otherwise>
                    </c:choose>
                  </tbody>
                </table>
              </c:when>
              <c:otherwise>
                <div class="panel-body text-center empty-result">
                  <i class="fa fa-pencil"></i>
                  <c:choose>
                    <c:when test="${isCurrentUserPage}">
                      <h4>Você ainda não possui nenhuma mensagem.</h4>
                    </c:when>
                    <c:otherwise>
                      <h4>${userName} ainda não possui nenhuma mensagem.</h4>
                    </c:otherwise>
                  </c:choose>
                </div>
              </c:otherwise>
            </c:choose>
          </div>
        </div>
      </div>
    </div>
  </body>
</html>
