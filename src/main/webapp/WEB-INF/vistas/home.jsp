<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
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
				<a href="home">Home</a>
				<a href="ingredientes">Menu</a>
				<a href="home"><img src="img/ganiclogo.png" class="img-fluid logo_style" alt="Logo"></a>
				<a href="contacto">Contacto</a>
				<a href="nosotros">Nosotros</a>
				<c:choose>
					<c:when test="${sessionScope.id == null}">
						<a class="bg-warning rounded-pill text-white" href="login">Login</a>
						<a class="bg-warning rounded-pill text-white" href="registrar">Registrar</a>
					</c:when>
					<c:otherwise>
						<a class="bg-warning rounded-pill text-white" href="verificar">Perfil</a>
						<a class="bg-warning rounded-pill text-white" href="Salir">Salir</a>
					</c:otherwise>

				</c:choose>
			</div>
		</div>
	</nav>
</header>
<main>
	<section>
		<div id="banner" class="d-flex justify-content-center">
			<img src="img/bannerganccic222.png" class="img-fluid" alt="bannerganccic">
			<div id="bannerTitulo">
				<h1>GANIC</h1>
			</div>
			<div id="bannerSubTitulo">
				<h2>Tu Sandwich a medida</h2>
			</div>
		</div>
	</section>

	<section>
		<div class="container">
			<div class="row py-5">
				<div class="col-6 text-center">
					<h4 class="pt-5 pb-3">Comenzá a prepararlo <span class="text-success">YA</span> !</h4>
					<a href="generarPedido?paso=1" class="btn btn-success rounded-pill px-5">CREAR PEDIDO</a>
				</div>
				<div class="col-6 text-center">
					<h4 class="pt-5 pb-3">Ver <span class="text-success">TODOS</span> los ingredientes !</h4>
					<a href="ingredientes" class="btn btn-success rounded-pill px-5">VER INGREDIENTES</a>
				</div>
			</div>

			<div class="row my-5 py-5">
				<div class="col-6">

					<div id="carouselExampleInterval" class="carousel slide" data-bs-ride="carousel">
						<div class="carousel-inner">
							<div class="carousel-item active" data-bs-interval="3000">
								<img src="img/s01.png" class="d-block w-100" alt="...">
							</div>
							<div class="carousel-item" data-bs-interval="3000">
								<img src="img/s02.png" class="d-block w-100" alt="...">
							</div>
							<div class="carousel-item" data-bs-interval="3000">
								<img src="img/s03.png" class="d-block w-100" alt="...">
							</div>
							<div class="carousel-item" data-bs-interval="3000">
								<img src="img/s04.png" class="d-block w-100" alt="...">
							</div>
						</div>
						<button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleInterval" data-bs-slide="prev">
							<span class="carousel-control-prev-icon" aria-hidden="true"></span>
							<span class="visually-hidden">Previous</span>
						</button>
						<button class="carousel-control-next" type="button" data-bs-target="#carouselExampleInterval" data-bs-slide="next">
							<span class="carousel-control-next-icon" aria-hidden="true"></span>
							<span class="visually-hidden">Next</span>
						</button>
					</div>

				</div>
				<div class="col-6">
					<h3 class="text-center mt-4">NUESTRO MENÚ</h3>
					<hr/>
					<p class="text-center mt-4 p-5">
						Nuestro menú ofrece variedad de sandiwchs caseros de estilo gourmet, llenos de sabor preparados con
						ingredientes frescos y naturales, satisfaciendo hasta los más exigentes paladares. Ofrecemos un servicio de
						sandiwchs gourmet manteniendo la calidad tanto en nuestros locales como en eventos masivos.
					</p>
				</div>
			</div>


		</div>
	</section>
</main>

<footer style="background-color: #000000; height: 100px">
	<div class="container">
		<div class="row pt-3 text-white">
			<div class="col-6 pt-3">
				<span class="ms-5">Seguinos en</span>
				<i class="bi bi-facebook ms-3" style="font-size: 20px"></i>
				<i class="bi bi-instagram ms-3" style="font-size: 20px"></i>
			</div>
			<div class="col-3 text-end pt-3">
				<h2>GANIC</h2>
			</div>
			<div class="col-3 text-end">
				<img src="img/ganiclogo.png" class="img-fluid logo_style" alt="Logo">
			</div>
		</div>
	</div>
</footer>

<!-- JavaScript Bundle with Popper -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-u1OknCvxWvY5kfmNBILK2hRnQC3Pr17a+RTT6rIHI7NnikvbZlHgTPOOmMi466C8" crossorigin="anonymous"></script>
</body>
</html>