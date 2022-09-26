<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
	<head>
		<!-- CSS only -->
		<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-iYQeCzEYFbKjA/T2uDLTpkwGzCiq6soy8tYaI1GyVh/UjpbCx/TYkiZhlZB6+fzT" crossorigin="anonymous">
		<link href="css/styles.css" rel="stylesheet">
	</head>
	<body>
	<header>
		<nav class="navbar navbar-expand-lg">
			<div class="container navbar_container">
				<a class="navbar-brand" href="#">GANIC</a>
				<button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
					<span class="navbar-toggler-icon"></span>
				</button>
				<div class="collapse navbar-collapse ps-5" id="navbarNav">
					<ul class="navbar-nav">
						<li class="nav-item">
							<a class="nav-link active" aria-current="page" href="#">Inicio</a>
						</li>
						<li class="nav-item">
							<a class="nav-link" href="#">Crear pedido</a>
						</li>
					</ul>
				</div>
			</div>
		</nav>
	</header>
	<main>
		<div class="container py-5">
			<div class="row">
				<div class="col-4">
					<img class="img_home" src="https://images.aws.nestle.recipes/resized/8638a5131881f8b58ef237047306f804_sandwich_1200_600.jpg" />
				</div>
				<div class="col-4">
					<img class="img_home" src="https://okdiario.com/img/2021/07/30/sandwich-club.jpg" />
				</div>
				<div class="col-4">
					<img class="img_home" src="https://okdiario.com/img/2021/07/30/sandwich-club.jpg" />
				</div>
			</div>
		</div>
		<section class="py-5 info_container">
			<div class="container">
				<div class="row">
					<div class="col-4">
						<img class="img_logo" src="https://i.pinimg.com/originals/4d/f8/52/4df85220a5b82bcff4432b7fd873fbbe.png" />
					</div>
					<div class="col-8 d-flex align-items-center">
						<p class="info_parrafo">
							Contamos con una gran variedad de sándwiches para complacer a los más exigentes paladares, sin descuidar su bolsillo.
							Hacemos nuestros productos con los ingredientes más frescos y de la mejor calidad del mercado.
							Y lo mejor de todo, es que son totalmente personalizables !</p>
					</div>
				</div>
			</div>
		</section>
	</main>
		<!-- JavaScript Bundle with Popper -->
		<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-u1OknCvxWvY5kfmNBILK2hRnQC3Pr17a+RTT6rIHI7NnikvbZlHgTPOOmMi466C8" crossorigin="anonymous"></script>
	</body>
</html>