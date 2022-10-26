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
    <script src="https://unpkg.com/leaflet@1.9.2/dist/leaflet.js"
            integrity="sha256-o9N1jGDZrf5tS+Ft4gbIK7mYMipq9lqpVJ91xHSyKhg="
            crossorigin=""></script>
    <script src="https://npmcdn.com/leaflet-geometryutil"></script>
    <script src="${pageContext.request.contextPath}/js/jquery-1.11.3.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/confirmacion.js" type="text/javascript"></script>
    <title>Confirmación de pedido</title>
    <style type="text/css">
        .map{
            height: 400px;
            width: 100%;
        }
    </style>
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
            </div>
        </div>
    </nav>
</header>
<main>
    <section>
        <div class="container py-5">
            <h3 class="pt-5 pb-3">Datos de su pedido</h3>
            <div class="row">
                <div class="col-6">
                    <div class="card" style="max-width: 400px;">
                        <img src="img/pedido.png" class="card-img-top img-fluid" alt="...">
                        <div class="card-body">
                            <p class="card-text text-center text-succes">Tu Sandwich</p>
                            <c:forEach var="ing" items="${IngredientesQueElUsuarioSelecciono}">
                                <p class="card-text fw-bold">${ing.nombre}
                                    <c:choose>
                                        <c:when test="${ing.paso > 2}">
                                            <a href="eliminarIngrediente?ing=${ing.idIngrediente}" class="px-3 my-3"><i class="bi bi-trash-fill text-danger"></i></a>
                                        </c:when>
                                    </c:choose>
                                    <a href="modificarIngrediente?ing=${ing.idIngrediente}" class="px-3 my-3"><i class="bi bi-pencil-fill text-success"></i></a>
                                </p>
                                <p class="card-text">${ing.detalle}</p>
                                <p class="card-text">Precio por unidad $${ing.precio}</p>
                            </c:forEach>
                            <p class="card-text fw-bold text-end">Monto a pagar <span id="montoParcial"
                                    class="text-primary">$${montoFinal}</span></p>
                        </div>
                    </div>
                    <%--Bloque que es visible si el elemento error no esta vacio	--%>
                    <c:if test="${not empty error}">
                        <h4><span>${error}</span></h4>
                        <br>
                    </c:if>
                </div>
                <div class="col-6">
                    <h2>Mapa</h2>
                    <div id="map" class="map"></div>
                    <f:form action="setearMethodoPago" modelAttribute="formPago" method="post">
                        Cargo: <p id="cargo"></p> <br>
                        Monto Actualizado: <p id="montoTotal"></p>
                        Forma De Pgo:
                        <f:select  path="pago" id="pago" class="form-select">
                            <f:option value="enEfectivo">En Efectivo</f:option>
                            <f:option value="debito">Tarjeta Debito</f:option>
                            <f:option value="credito">Tarjeta Credito</f:option>
                        </f:select>
                        <button type="submit">Confirmar</button>
                    </f:form>
                    <h4 class="pb-3">Confirmar pago</h4>
                    <a href="exito" class="btn btn-success px-5 mb-5">Confirmar</a>
                    <div><a href="restablecer" class="btn btn-primary px-5 my-5">Volver al Home</a></div>
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
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.1/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-u1OknCvxWvY5kfmNBILK2hRnQC3Pr17a+RTT6rIHI7NnikvbZlHgTPOOmMi466C8"
        crossorigin="anonymous"></script>
</body>
</html>