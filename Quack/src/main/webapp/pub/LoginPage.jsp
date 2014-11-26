<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <title>Quack - Home page</title>

    <!-- Custom style -->
    <link rel="stylesheet" type="text/css" href="css/login.css">

    <!-- Bootstrap CDN -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.0/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.0/css/bootstrap-theme.min.css">
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>

    <!-- FontAwesome CDN -->
    <link href="//maxcdn.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css" rel="stylesheet">
  </head>

  <body>
    <header>
      <nav class="navbar navbar-default navbar-fixed-top" role="navigation">
        <div class="container-fluid">
          <div class="navbar-header">
            <a class="navbar-brand" href="#">
              <img alt="Quack" src="...">
            </a>
          </div>
        </div>
      </nav>
    </header>
    <div class="page-body">
      <div class="container col-md-8">
        <div class="col-md-6 col-md-offset-3 welcome-text">
          <h1><strong>Bem vindo ao Quack.</strong></h1>
          <br/>
          <h3>Aqui você pode expressar-se livremente e enviar mensagens para quem quiser e quando quiser.</h3>
          <h3>Saiba usar com sabedoria seus 256 caracteres (mais caracteres que a concorrência) e aproveite!</h3>
          <h3>Um grande quack a todos!</h3>
        </div>
      </div>
      <div class="container col-md-3 panel panel-default">
        <div class="panel-body">
          <h4><i class="fa fa-user"></i> Login</h4>
          <form name="login" action="Login" method="post" role="form" accept-charset="utf-8">
            <div class="form-group">
              <input type="text" name="login" class="form-control" placeholder="usuário or seu-email@email.com" required>
            </div>
            <div class="form-group">
              <input type="password" name="password" class="form-control" placeholder="senha" required>
            </div>
            <div class="form-group">
              <input type="submit" class="btn btn-info" value="Entrar">
            </div>
          </form>
          <hr/>
          <form name="register" action="RegisterPage.jsp" role="form" method="get">
            <div class="form-group">
              <h4><i class="fa fa-pencil"></i> Ainda não possui uma conta?</h4>
              <input type="submit" class="btn btn-success" value="Registrar">
            </div>
          </form>
        </div>
      </div>
    </div>
  </body>
</html>
