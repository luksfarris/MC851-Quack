<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<!-- Custom style -->
    <link rel="stylesheet" type="text/css" href="css/registerpage.css">

	<!-- Bootstrap CDN -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.0/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.0/css/bootstrap-theme.min.css">
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>

	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Quack - Cadastro</title>

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

	<style type="text/css">
	.container {
		width: 250px;
		clear: both;
	}

	.container input {
		width: 100%;
		clear: both;
	}
	</style>
</head>
<body>
	<header>
      <nav class="navbar navbar-default navbar-fixed-top" role="navigation">
        <div class="container-fluid">
          <div class="navbar-header">
            <a class="navbar-brand" href="#">
              <img alt="Cadastro" src="...">
            </a>
          </div>
        </div>
      </nav>
    </header>
	<center>
		<h1>Cadastro Quack</h1>
		<div class="container col-md-3 panel panel-default">
			<div class="page-body">
		        <div class="panel-body">
					<div class="container">
						<form name="Login" action="Cadastro" method="get" accept-charset="utf-8">
							Nome Completo:
							<input type="text" name="fullName" required><br>
							Email:
							<input type="email" name="email" required><br>
							Nome de Usuario:
							<input type="text" name="username" required><br>
							Senha:
							<input type="password" name="password" id="pass1" required><br>
							Confirme a senha:
							<input type="password" name="passwordcheck" id="pass2" onKeyUp='checkPass(); return false;' required> 
							<span id="confirmMessage" class="confirmMessage"></span><br> 
							<input type="submit" class="btn btn-success" value="Cadastrar" id="botao" disabled> 
							<input type="button" class="btn btn-info" value="Voltar" onClick="history.back();">
						</form>
					</div>
				</div>
			</div>
		</div>
	</center>
</body>
</html>