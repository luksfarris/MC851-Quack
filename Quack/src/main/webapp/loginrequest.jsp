<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Faça seu Login</title>
<style type="text/css">
body {
	background-color: #FFFACD;
}

ul {
	padding: 0;
	margin: 0;
}

label {
	display: block;
	color: #999;
}

.cf:before,.cf:after {
	content: "";
	display: table;
}

.cf:after {
	clear: both;
}

.cf {
	*zoom: 1;
}

:focus {
	outline: 0;
}

.loginform input:not ([type=submit] ) {
	padding: 5px;
	margin-right: 10px;
	border: 1px solid rgba(0, 0, 0, 0.3);
	border-radius: 3px;
	box-shadow: inset 0px 1px 3px 0px rgba(0, 0, 0, 0.1), 0px 1px 0px 0px
		rgba(250, 250, 250, 0.5);
}

.loginform input[type=submit] {
	border: 1px solid rgba(0, 0, 0, 0.3);
	background: #64c8ef; /* Old browsers */
	background: -moz-linear-gradient(top, #64c8ef 0%, #00a2e2 100%);
	/* FF3.6+ */
	background: -webkit-gradient(linear, left top, left bottombottom, color-stop(0%,
		#64c8ef), color-stop(100%, #00a2e2)); /* Chrome,Safari4+ */
	background: -webkit-linear-gradient(top, #64c8ef 0%, #00a2e2 100%);
	/* Chrome10+,Safari5.1+ */
	background: -o-linear-gradient(top, #64c8ef 0%, #00a2e2 100%);
	/* Opera 11.10+ */
	background: -ms-linear-gradient(top, #64c8ef 0%, #00a2e2 100%);
	/* IE10+ */
	background: linear-gradient(to bottombottom, #64c8ef 0%, #00a2e2 100%);
	/* W3C */
	filter: progid:DXImageTransform.Microsoft.gradient( startColorstr='#64c8ef',
		endColorstr='#00a2e2', GradientType=0); /* IE6-9 */
	color: #fff;
	padding: 5px 15px;
	margin-right: 0;
	margin-top: 15px;
	border-radius: 3px;
	text-shadow: 1px 1px 0px rgba(0, 0, 0, 0.3);
}
</style>
</head>
<body>
	<section class="loginform cf"> <img src="img/ic_duck.png"
		alt="Icon of a rubber duck" />
	<form name="login" action="Login" method="post" accept-charset="utf-8">
		<ul>
			<li><label for="username">Email</label><input type="email"
				name="username" placeholder="yourname@email.com" required></li>
			<li><label for="password">Password</label><input type="password"
				name="password" placeholder="password" required></li>
			<li><label for="remember">Lembrar de mim</label><input type="checkbox" 
				name="remember" value="false" />
			<li><input type="submit" value="Quack!"></li>
		</ul>
	</form>
	</section>
</body>
</html>
