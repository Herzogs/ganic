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
                <h2 class="mt-5 mb-3"><i class="bi bi-bag-heart text-primary mx-2"></i> Mis productos:</h2>
                <c:forEach var="detalle" items="${listaDetalle}">
                    <div class="col-4 g-5 h-100">
                        <div class="card h-100" >
                            <img src="img/carritox.png" class="card-img-top" alt="banner compra">
                            <div class="card-body">
                                <h5 class="mb-3"><i class="bi bi-cart-plus pe-2"></i> Nro de Sandwich: ${detalle.idDetalleCarro }</h5>
                                <p class="fw-bold">${ detalle.sandwich.nombre}</p>
                                <p>Monto Total: <span class="ps-5">$${ detalle.calcularMonto()}</span></p>
                                <div class="mb-3">
                                    <span class="pe-5">Cantidad: ${ detalle.cantidad }</span>
                                    <a href="agregarAlCarrito?idSandwich=${detalle.sandwich.idSandwich}" class="btn btn-success p-1"
                                       type="button"><i class="bi bi-plus-circle"></i></a>
                                    <a href="quitarDelCarrito?idSandwich=${detalle.sandwich.idSandwich}" class="btn btn-danger p-1"
                                       type="button"><i class="bi bi-dash-circle"></i></a>
                                </div>
                                <a href="eliminarDetalle?idDetalle=${detalle.idDetalleCarro}" class="btn btn-danger mt-3"
                                   type="button">Eliminar del carrito</a>
                            </div>
                        </div>


                    </div>
                </c:forEach>

                <c:if test="${not empty msg}">
                    <div class="text-danger my-4">
                        <span>${msg}</span>
                    </div>
                </c:if>

                    <c:if test="${empty msg}">
                        <div class="text-end pt-5">
                            <h4 class="fw-bold">Total a pagar: $${montoCarrito}</h4>
                        </div>
                        <div class="pb-5 pt-3 d-flex justify-content-between">
                            <a href="vaciarCarro" class="btn btn-danger px-5" type="button">Vaciar carrito</a>
                            <a href="home" class="btn btn-primary ms-4 px-5" type="button">Seguir comprando</a>
                            <a href="salvarCarro" class="btn btn-success px-5 ms-4" type="button">Pagar</a>
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