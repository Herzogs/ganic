<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<!-- CSS only -->
		<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-iYQeCzEYFbKjA/T2uDLTpkwGzCiq6soy8tYaI1GyVh/UjpbCx/TYkiZhlZB6+fzT" crossorigin="anonymous">
		<link href="css/styles.css" rel="stylesheet">
		<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.9.1/font/bootstrap-icons.css">
	</head>
	<body>
	<header>
		<nav class="nav_style d-flex align-items-center">
			<div class="container">
				<div class="text-center">
					<a href="home"><img src="img/ganiclogo.png" class="img-fluid logo_style" alt="Logo"></a>
					<a href="home">Home</a>
					<a href="ingredientes">Menu</a>
					<a href="contacto">Contacto</a>
					<a href="nosotros">Nosotros</a>
					<c:choose>
						<c:when test="${sessionScope.id == null}">
						<span class="dropdown ps-5">
							<button class="btn btn-secondary dropdown-toggle" type="button" data-bs-toggle="dropdown" aria-expanded="false">
								Mi perfil
							</button>
							<ul class="dropdown-menu">
								<li><a class="dropdown-item text-dark py-2" href="login">Login</a></li>
								<li><a class="dropdown-item text-dark py-2" href="registrar">Registrar</a></li>
							</ul>
						</span>
						</c:when>
						<c:otherwise>
						<span class="dropdown ps-5">
							<button class="btn btn-secondary dropdown-toggle" type="button" data-bs-toggle="dropdown" aria-expanded="false">
								Mi perfil
							</button>
							<ul class="dropdown-menu">
								<li><a class="dropdown-item text-dark py-2" href="verificar">Actulizar datos</a></li>
								<li><a class="dropdown-item text-dark py-2" href="Salir">Salir</a></li>
							</ul>
						</span>
						</c:otherwise>
					</c:choose>
				</div>
			</div>
		</nav>
	</header>
		<div class = "container">
			<div id="loginbox">
				<div class="row justify-content-center">
					<div class="col-6 mt-5" style="background-color: #f7f7f7; padding: 46px;">
							<form:form action="validar-login" method="POST" modelAttribute="datosLogin">
								<h3>Bienvenido! ingresá tus datos</h3>
								<hr class="mb-4">
								<label class="form-label">Email</label>
								<form:input path="email" id="email" type="email" class="form-control" />
								<label class="form-label mt-4">Contraseña</label>
								<form:input path="password" type="password" id="password" class="form-control"/>
								<div class="d-grid gap-2">
									<button class="btn btn-success mt-5 mb-4" Type="Submit">Ingresar</button>
									<a href="registrar" class="btn btn-primary">Registrarme</a>
								</div>
							</form:form>
						<%--Bloque que es visible si el elemento error no esta vacio	--%>
						<c:if test="${not empty error}">
							<h4><span>${error}</span></h4>
							<br>
						</c:if>
						${msg}
					</div>
				</div>
			</div>
		</div>
		
		<!-- Placed at the end of the document so the pages load faster -->
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js" ></script>
		<script>window.jQuery || document.write('<script src="../../assets/js/vendor/jquery.min.js"><\/script>')</script>
		<script src="js/bootstrap.min.js" type="text/javascript"></script>
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-u1OknCvxWvY5kfmNBILK2hRnQC3Pr17a+RTT6rIHI7NnikvbZlHgTPOOmMi466C8" crossorigin="anonymous"></script>
	</body>
</html>
