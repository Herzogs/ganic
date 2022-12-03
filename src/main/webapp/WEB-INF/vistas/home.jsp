<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<%@ include file="../vistas/header.jsp" %>
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
					<c:choose>
						<c:when test="${sessionScope.id == null}">
							<h4 class="pt-5 pb-3">Comenzá a prepararlo <span class="text-success">YA</span> !</h4>
							<a href="login" class="btn btn-success rounded-pill px-5">CREAR PEDIDO</a>
						</c:when>
						<c:otherwise>
							<h4 class="pt-5 pb-3">Comenzá a prepararlo <span class="text-success">YA</span> !</h4>
							<a href="generarPedido?paso=1" class="btn btn-success rounded-pill px-5">CREAR PEDIDO</a>
						</c:otherwise>

					</c:choose>
				</div>
				<div class="col-6 text-center">
					<h4 class="pt-5 pb-3">Ver <span class="text-success">TODOS</span> los ingredientes !</h4>
					<a href="ingredientes" class="btn btn-success rounded-pill px-5">VER INGREDIENTES</a>
				</div>
			</div>
		</div>

		<section class="bg-dark">
			<div class="container">
				<div class="row my-5 py-5">
					<h2 class="text-center text-white"> Sandwichs de la Casa</h2>
					<c:forEach var="sandwich" items="${listaEnPromocion}" >
						<div class="col-4 g-5">
							<div class="card h-100" >
								<img src="img/homex.png" class="card-img-top" alt="banner home sandwich">
								<div class="card-body">
									<h5 class="mb-3">${sandwich.nombre}</h5>
									<p>${sandwich.descripcion}</p>
									<p>Es apto: <span class="text-primary">${sandwich.esApto}</p>
									<p>Precio: <span class="fw-bold">$${sandwich.obtenerMonto()}</span></p>
									<p class="text-end mt-4"><a href="confirmarSandwich?idSandwich=${sandwich.idSandwich}" class="btn btn-success" type="button">Comprar</a></p>
								</div>
							</div>
						</div>
					</c:forEach>
				</div>
			</div>
		</section>

			<div class="container">

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

<%@ include file="../vistas/footer.jsp" %>

<!-- JavaScript Bundle with Popper -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-u1OknCvxWvY5kfmNBILK2hRnQC3Pr17a+RTT6rIHI7NnikvbZlHgTPOOmMi466C8" crossorigin="anonymous"></script>
</body>
</html>