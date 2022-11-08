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
								<li><a class="dropdown-item text-dark py-2" href="misdatos">Mis datos</a></li>
                                <li><a class="dropdown-item text-dark py-2" href="historial">Mis pedidos</a></li>
								<li><a class="dropdown-item text-dark py-2" href="Salir">Salir</a></li>
							</ul>
						</span>
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

            <div class="row">
                <h2 class="mt-5 mb-3">Mis pedidos:</h2>
                <c:forEach var="compra" items="${listaDeCompras}" >
                    <div class="col-12 g-5">
                            <div class="p-3 bg-primary text-white rounded-top">
                                    <h3>${ compra.idCompra } <span class="ms-4">${ compra.estado }</span> </h3>

                                <span class="p-5 "> NOMBRE :::   ${ compra.detalle.get(0).nombre }</span>

                                <span class="p-5 "> PREFERENCIA :::   ${ compra.detalle.get(0).esApto }</span>

                                <span class="p-5 "> PRECIO :::   ${ compra.detalle.get(0).obtenerMonto()}</span>

                                <span class="p-5"> FECHA :::   ${ compra.fecha.withSecond(0).withNano(0) }</span>
                            </div>
                            <c:choose>
                                <c:when test="${compra.comentario == null}">
                                   <div class="p-3 text-end">
                                       <a href="comentarios?idCompra=${compra.idCompra}" class="btn btn-secondary" type="button">Agregar comentario</a>
                                   </div>
                                </c:when>
                                <c:otherwise>
                                    <div class="p-3 bg-secondary text-white">
                                            ${ compra.comentario }
                                    </div>
                                </c:otherwise>
                            </c:choose>
                    </div>
                </c:forEach>
            </div>

            <c:if test="${not empty msg}">
                <div class="text-danger my-4">
                    <span>${msg}</span>
                </div>
            </c:if>


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