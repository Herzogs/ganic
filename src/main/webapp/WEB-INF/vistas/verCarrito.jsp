<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
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

            <div class="row mb-5">
                <h2 class="mt-5 mb-3"><i class="bi bi-bag-heart text-primary ms-2"></i> ver mi carro:</h2>
                <c:forEach var="detalle" items="${listaDetalle}">
                    <div class="col-12 g-5">
                        <div class="p-3 bg-dark text-white rounded-top">

                            <h4 class="mb-3">Nro detalle: ${detalle.idDetalleCarro }
                                <a href="eliminarDetalle?idDetalle=${detalle.idDetalleCarro}" class="btn btn-secondary"
                                   type="button">Eliminar detalle</a></h4>
                            <span class="pe-5">Nombre del Sandwich: ${ detalle.sandwich.nombre}</span>
                            <span class="pe-5">Monto Total: ${ detalle.calcularMonto()}</span>
                            <span class="pe-5">cantidad: ${ detalle.cantidad }</span>
                            <a href="agregarAlCarrito?idSandwich=${detalle.sandwich.idSandwich}" class="btn btn-secondary"
                               type="button">Agregar</a>
                            <a href="quitarDelCarrito?idSandwich=${detalle.sandwich.idSandwich}" class="btn btn-secondary"
                               type="button">Quitar</a>
                        </div>
                    </div>
                </c:forEach>
                <c:if test="${not empty msg}">
                    <div class="text-danger my-4">
                        <span>${msg}</span>
                    </div>
                </c:if>

                    <c:if test="${empty msg}">
                        <div class="py-3 text-end">
                            <span class="pe-5">Monto Carrito: ${montoCarrito}</span>
                            <a href="salvarCarro" class="btn btn-secondary" type="button">Pagar</a>
                            <a href="vaciarCarro" class="btn btn-secondary" type="button">Vaciar carrito</a>
                        </div>
                    </c:if>

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