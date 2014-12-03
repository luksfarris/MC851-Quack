<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="service.QuackService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="quack.User" %>
<%@ page import="quack.TimestampImpl" %>
<%@ page import="quack.Timestamp" %>
<%@ page import="quack.Message" %>
<%@ page import="java.util.List" %>
<%@ page import="service.CookieHelper" %>

<%
  User user;
  String cookie = CookieHelper.getCookieValue(request, CookieHelper.COOKIE_NAME);
  user = QuackService.getServer(getServletContext()).getUserFromCookie(cookie);
  Timestamp time = new TimestampImpl();

  pageContext.setAttribute("id", user.getDbIndex());
  pageContext.setAttribute("user", user);
  pageContext.setAttribute("userName", user.getLoginName());
  pageContext.setAttribute("time", time);
%>

<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <title>Quack - Perfil de @${user.getLoginName()}</title>

	<script>
		function checkPass() {
			//Store the password field objects into variables ...
			var pass1 = document.getElementById('pass1');
			var pass2 = document.getElementById('pass2');
			//Store the Confimation Message Object ...
			var message = document.getElementById('confirmMessage');
			//Set the colors we will be using ...
			var goodColor = "#FFFFFF";
			var badColor = "#ff6666";
			document.getElementById('botao');
			//Compare the values in the password field
			//and the confirmation field
			if (pass1.value == pass2.value) {
				//The passwords match.
				//Set the color to the good color and inform
				//the user that they have entered the correct password
				pass2.style.backgroundColor = goodColor;
				message.innerHTML = ""
				document.getElementById('botao').disabled = false;

			} else {
				//The passwords do not match.
				//Set the color to the bad color and
				//notify the user.
				pass2.style.backgroundColor = badColor;
				message.style.color = badColor;
				message.innerHTML = "Senhas não batem!"
				document.getElementById('botao').disabled = true;
			}
		}
	</script>

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
            <img src="https://www.wevi.com.br/static/img/placeholder/placeholder_user.png" />
          </div>
        </div>
        <div class="col-md-9">
          <div class="panel panel-default list">
            <form role="form" name="Update" action="Update" method="get" accept-charset="utf-8">
            		<div class="form-group">
   						<label>Nome de usuário</label>
      					<p class="form-control-static">@${user.getLoginName()}</p>
  					</div>
  					<div class="form-group">
   						<label>Criado em</label>
      					<p class="form-control-static">${time.toString(user.getCreationTime(), "UTC")}</p>
  					</div>
  					

            		<div class="form-group">
    					<label for="fullName">Nome completo</label>
    					<input type="text" class="form-control" name="fullName" value="${user.getFullName()}" id="fullName" placeholder="Digite seu novo nome completo">
  					</div>
  					<br><br>
					
					<div class="form-group">
    					<label for="profileImage">Imagem de perfil</label>
    					<input type="file" id="profileImage">
    					<p class="help-block">Escolha sua nova imagem de perfil (tamanho máximo 600 KB)</p>
  					</div>
					<br><br>
					
					<div class="form-group">
    					<label for="oldPass">Senha antiga</label>
    					<input type="password" class="form-control" name="oldPassword" id="oldPassword" 
    					placeholder="Digite sua antiga senha">
  					</div>
  					<div class="form-group">
    					<label for="newPass">Nova senha</label>
    					<input type="password" class="form-control" name="password" id="pass1" 
    					placeholder="Digite sua nova senha" onKeyUp='checkPass(); return false;'>
  					</div>
  					<div class="form-group">
    					<label for="newPass1">Redigite sua nova senha</label>
    					<input type="password" class="form-control" name="passwordcheck" id="pass2" 
    					placeholder="Digite sua nova senha novamente" onKeyUp='checkPass(); return false;'>
  					</div>
  					
					<span id="confirmMessage" class="confirmMessage"></span><br>
					<input type="submit" class="btn btn-success" value="Atualizar Dados" id="botao">
					<input type="button" class="btn btn-info" value="Voltar" onClick="history.back();">
				</form>
          </div>
        </div>
      </div>
    </div>
  </body>
</html>