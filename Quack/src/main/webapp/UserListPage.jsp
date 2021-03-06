<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="service.QuackService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="quack.User" %>
<%@ page import="quack.Server" %>
<%@ page import="java.util.List" %>
<%@ page import="service.CookieHelper" %>

<%
  Server server = QuackService.getServer(getServletContext());
  pageContext.setAttribute("users", server.getAllUsers());
%>

<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <title>Usuários do Sistema</title>

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
              <li><a href="Timeline">Timeline</a></li>
              <li><a href="UserPage.jsp">Perfil</a></li>
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
        <div class="col-md-12">
          <h2>Usuários do Sistema</h2>
        </div>
      </div>
      <div class="row">
        <div class="col-md-9">
          <div class="panel panel-default msg-feed">
            <table class="table table-striped table-hover">
              <tbody>
                 <c:forEach items="${users}" var="u" varStatus="loop">
                   <tr id="msg-${loop.index}" class="msg">
                     <td><img src="https://www.wevi.com.br/static/img/placeholder/placeholder_user.png" height="42" width="42"/></td>
                     <td><a href="user/${u.getLoginName()}">@${u.getLoginName()}</a></td>
                     <td><a href="user/${u.getLoginName()}">${u.getFullName()}</a></td>
                   </tr>
                </c:forEach>
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </div>
  </body>
</html>

