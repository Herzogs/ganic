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
                    <c:if test="${not empty ok}">
                        <%--<h4><span>${error}</span></h4>
                        <br>--%>
                        <div class="modal fade" id="miModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">

                            <div class="modal-dialog">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h5 class="modal-title" id="exampleModalLabel">Informacion </h5>
                                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                    </div>
                                    <div class="modal-body">
                                            ${ok}
                                    </div>
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                                        <button type="button" class="btn btn-primary" data-bs-dismiss="modal">Aceptar</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:if>
                </div>
                <div class="col-6">
                    <h4 class="pb-3">Continuar comprando</h4>
                    <a href="salvarSandwichCarrito" class="btn btn-success px-5 mb-5"><i class="bi bi-cart-plus pe-2"></i> Agregar al carrito</a>
                    <h4 class="pb-3">Envio y Metodos de pago</h4>
                    <a href="salvarSandwich" class="btn btn-success px-5 mb-5">Continuar</a>
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
<script src="${pageContext.request.contextPath}/js/jquery-1.11.3.min.js"></script>
<script type="text/javascript">
    $(document).ready(()=>{
        $("#miModal").modal('toggle')
    });
</script>
</body>
</html>