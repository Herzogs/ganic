<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<%@ include file="../vistas/header.jsp" %>
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
                    <h4 class="pb-3">Confirmar pago</h4>
                    <a href="salvarSandwich" class="btn btn-success px-5 mb-5">Confirmar Destino</a>
                    <a href="salvarSandwichCarrito" class="btn btn-success px-5 mb-5">Agregar al carrito</a>
                    <div><a href="restablecer" class="btn btn-primary px-5 my-5">Volver al Home</a></div>
                </div>
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