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
                <c:if test="${not empty msg}">
                        <input type="hidden" name="msg" id="msg" value="${msg}" />
                            <input type="hidden" name="estado" id="estado" value="${error}"/>

                </c:if>
                <c:forEach var="detalle" items="${listaDetalle}">
                    <div class="col-6 g-5 h-100">
                        <div class="p-3 bg-dark text-white rounded-top h-100 shadow-sm">
                            <h4 class="mb-3"><i class="bi bi-cart-plus pe-2"></i> Nro de
                                Sandwich: ${detalle.idDetalleCarro }</h4>
                            <p class="fw-bold">${ detalle.sandwich.nombre}</p>
                            <p>Monto Total: <span class="ps-5">$${ detalle.calcularMonto()}</span></p>
                            <div class="mb-3">
                                <span class="pe-5">Cantidad: ${ detalle.cantidad }</span>
                                <a href="agregarAlCarrito?idSandwich=${detalle.sandwich.idSandwich}"
                                   class="btn btn-success p-1"
                                   type="button"><i class="bi bi-plus-circle"></i></a>
                                <a href="quitarDelCarrito?idSandwich=${detalle.sandwich.idSandwich}"
                                   class="btn btn-danger p-1"
                                   type="button"><i class="bi bi-dash-circle"></i></a>
                            </div>
                            <a href="eliminarDetalle?idDetalle=${detalle.idDetalleCarro}" class="btn btn-danger mt-3"
                               type="button">Eliminar del carrito</a>
                        </div>
                    </div>
                </c:forEach>

                <div class="py-5 text-end">
                    <h4 class="pe-5">Total a pagar: $${montoCarrito}</h4>
                    <a href="vaciarCarro" class="btn btn-danger px-5" type="button">Vaciar carrito</a>
                    <a href="salvarCarro" class="btn btn-success px-5 ms-4" type="button">Pagar</a>
                </div>

            </div>

        </div>
    </section>
    <div class="modal fade" id="miModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">

        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLabel">Error al quitar un sandwich</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    ${msg}
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                    <button type="button" class="btn btn-primary" data-bs-dismiss="modal">Aceptar</button>
                </div>
            </div>
        </div>
    </div>
</main>

<%@ include file="../vistas/footer.jsp" %>

<!-- JavaScript Bundle with Popper -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.1/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-u1OknCvxWvY5kfmNBILK2hRnQC3Pr17a+RTT6rIHI7NnikvbZlHgTPOOmMi466C8"
        crossorigin="anonymous"></script>
<script src="${pageContext.request.contextPath}/js/jquery-1.11.3.min.js"></script>
<script type="text/javascript">
    $(document).ready(()=>{
        if($("#estado").val() === '1'){
            $("#miModal").modal('show');
        }
    });
</script>

</body>
</html>