<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <!-- CSS only -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.1/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-iYQeCzEYFbKjA/T2uDLTpkwGzCiq6soy8tYaI1GyVh/UjpbCx/TYkiZhlZB6+fzT" crossorigin="anonymous">
    <link href="css/styles.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.9.1/font/bootstrap-icons.css">

    <link rel="stylesheet" href="https://unpkg.com/leaflet@1.9.2/dist/leaflet.css"
          integrity="sha256-sA+zWATbFveLLNqWO2gtiw3HL/lh1giY/Inf1BJ0z14="
          crossorigin=""/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/leaflet.legend.css" />
    <link rel="stylesheet" href="https://unpkg.com/leaflet-control-geocoder/dist/Control.Geocoder.css"/>
    <script src="https://unpkg.com/leaflet@1.9.2/dist/leaflet.js"
            integrity="sha256-o9N1jGDZrf5tS+Ft4gbIK7mYMipq9lqpVJ91xHSyKhg="
            crossorigin=""></script>
    <script src="https://unpkg.com/leaflet-control-geocoder/dist/Control.Geocoder.js"></script>
    <script src="${pageContext.request.contextPath}/js/leaflet.legend.js"></script>
    <script src="${pageContext.request.contextPath}/js/jquery-1.11.3.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/confirmacion.js" type="text/javascript"></script>
    <title>Destino</title>
    <style type="text/css">
        .map {
            height: 500px;
            width: 100%;
        }
    </style>
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
								<li><a class="dropdown-item text-dark py-2" href="enPreparacion">En preparación</a></li>
                                <li><a class="dropdown-item text-dark py-2" href="historial">Historial</a></li>
								<li><a class="dropdown-item text-dark py-2" href="Salir">Salir</a></li>
							</ul>

                            <a href="verCarrito" class="btn btn-warning ms-4" type="button" style="padding-top: 2px; padding-bottom: 2px">
								<i class="bi bi-cart4 text-white fs-5"></i>
							</a>
						</span>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </nav>
</header>
<main>

    <section class="container">
        <h1 class="mt-5 mb-4">Elegí tu destino</h1>
        <c:if test="${not empty msg}">
            <h4 id="error" class="mb-3 text-danger">${msg}</h4>
        </c:if>
        <div class="row mb-5">
            <div class="col-8">
                <h5 class="fw-bold"><i class="bi bi-arrow-bar-down text-primary fs-4 pe-3"></i> Ingresá tu dirección </h5>
                <div id="map" class="map"></div>
            </div>
            <div class="col-md-4 ps-3 mt-3">
                <div class=" my-4">
                    <f:form method="post" action="seleccionarDestino" modelAttribute="formDestino">
                        <f:input path="destino" id="dest" type="hidden"/>
                        <f:input path="distance" id="dist" type="hidden"/>
                        <button class="btn btn-success px-5" type="submit">Confirmar Destino</button>
                        <div><a href="restablecer" class="btn btn-primary px-5 my-5">Volver al Home</a></div>
                    </f:form>
                </div>
                <img src="img/envios.jpg" class="img-fluid" alt="Logo envios" style="max-width: 230px">
            </div>

        </div>
    </section>
</main>
<%@ include file="../vistas/footer.jsp" %>
<!-- JavaScript Bundle with Popper -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.1/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-u1OknCvxWvY5kfmNBILK2hRnQC3Pr17a+RTT6rIHI7NnikvbZlHgTPOOmMi466C8"
        crossorigin="anonymous"></script>
</body>
</html>