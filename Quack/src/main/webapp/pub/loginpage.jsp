<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Faça seu Login</title>
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
	<center>
	<div class="container">
		<h1>Login Quack</h1>
		<form name="login" action="Login" method="post" accept-charset="utf-8">
			<label for="username">Email ou Usuário</label><input type="text"
					name="login" placeholder="username or yourname@email.com" required><br>
			<label for="password">Senha</label><input type="password"
					name="password" placeholder="password" required><br>
			<label for="remember">Lembrar de mim?</label><input type="checkbox" 
					name="remember" value="false" /><br>
			<input type="submit" value="Entrar"></li>
		</form>
		<form name="register" action="registerpage.jsp" method="get">
			<input type="submit" value="Registrar">
		</form>
	</div>
</center>	
</body>
</html>
