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
                <h2 class="mt-5 mb-3"><i class="bi bi-bag-heart text-primary ms-2"></i> Mis pedidos:</h2>
                <c:forEach var="compra" items="${listaDeCompras}" >
                    <div class="col-12 g-5">
                            <div class="p-3 bg-dark text-white rounded-top">
                                <h4 class="mb-3">Nro de compra: ${ compra.idCompra } <span class="ms-5 text-primary">${ compra.estado }</span> </h4>
                                <span class="pe-5">Nombre del Sandwich: ${ compra.detalle.get(0).nombre }</span>
                                <span class="pe-5">Preferencia: ${ compra.detalle.get(0).esApto }</span>
                                <span class="pe-5">Fecha: ${ compra.fecha.withSecond(0).withNano(0) }</span>
                                <span class="pe-5">Precio: $${ compra.detalle.get(0).obtenerMonto()}</span>
                                <a href="cancelarCompra?idCompra=${compra.idCompra}" class="btn btn-secondary" type="button">Cancelar Compra</a>
                            </div>
                    </div>
                </c:forEach>
            </div>

            <div class="modal fade" id="Modal" tabindex="-1" aria-labelledby="modal" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="exampleModalLabel">Información</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            ${msg}
                        </div>
                    </div>
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