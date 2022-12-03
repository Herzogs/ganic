<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<%@ include file="../vistas/header.jsp" %>
<main>
    <section>
        <div class="container py-5">
            <h3 class="pt-5 pb-3">Datos del Pedido</h3>
            <div class="row">
                <div class="col-6">
                    <div class="card" style="max-width: 400px;">
                        <img src="img/pedido.png" class="card-img-top img-fluid" alt="...">
                        <div class="card-body">
                            <c:forEach var="lista" items="${listaDetalle}">
                                <p class="card-text fw-bold">${lista.sandwich.nombre}</p>
                                <p class="card-text">Cantidad: <span class="fw-bold ps-5">${lista.cantidad}</span></p>
                                <p class="card-text">Precio: <span class="fw-bold ps-5">$${lista.calcularMonto()}</span></p>
                           <hr class="my-3"/>
                            </c:forEach>
                            <p class="card-text"><span class="fw-bold">Dirección Envio:</span> ${direccion}</p>
                            <p class="card-text fw-bold text-end">Monto de su pedido: <span
                                    class="text-primary">${montoFinal}</span></p>
                            <p class="card-text fw-bold text-end">Costo de envio: <span
                                    class="text-primary">${recargo}</span></p>
                            <p class="card-text fw-bold text-end">Total a Pagar: <span
                                    class="text-primary">${montoTotalPagar}</span></p>
                        </div>
                    </div>
                    <%--Bloque que es visible si el elemento error no esta vacio	--%>
                    <c:if test="${not empty msg}">
                        <%--<h4><span>${msg}</span></h4>
                        <br>--%>
                        <div class="modal fade" id="miModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                            <div class="modal-dialog">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h5 class="modal-title" id="exampleModalLabel">Informacion </h5>
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
                    </c:if>
                </div>
                <div class="col-6">
                    <h3 class="p-0 m-0">Confirmar pago</h3>
                    <div class="w3-col s9 w3-padding-top-64 w3-center">
                        <a class="cho-container w3-btn w3-blue ${est}"></a>
                        <img src="img/mercadoPago.png" alt="mpz" style="width: 250px" />
                    </div>
                    <div><a href="seguirComprando" class="btn btn-success px-5 my-5">Seguir Comprando</a></div>
                    <div><a href="vaciarCarro" class="btn btn-secondary px-5 my-3">Cancelar Compra</a></div>
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
<script src="${pageContext.request.contextPath}/js/jquery-1.11.3.min.js"></script>
<script src="https://sdk.mercadopago.com/js/v2"></script>
<script type="text/javascript">
    // Agrega credenciales de SDK
    // Acá va la Public Key de la cuenta de mercadopago que voy a usar como
    // vendedor
    $(document).ready(()=>{
        $("#miModal").modal('toggle')
        const mp = new MercadoPago("APP_USR-b80d8e26-f1e7-4970-9308-37f7159ae8d7", {
            locale : "es-AR",
        });
        // Inicializa el checkout
        mp.checkout({
            preference : {
                // Se le pasa el id de la preferencia de pago generada con el backend
                // desde spring
                id : '<c:out value="${preference.id}"/>',
            },
            render : {
                container : ".cho-container", // Indica el nombre de la clase donde se mostrará el botón de pago
                label : "Pagar", // Cambia el texto del botón de pago (opcional)
            },
        });
    });

</script>
</body>
</html>