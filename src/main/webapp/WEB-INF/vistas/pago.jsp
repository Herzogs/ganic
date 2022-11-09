<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<%@ include file="../vistas/header.jsp" %>
<main>
    <section>
        <div class="container py-5">
            <h3 class="pt-5 pb-3">Datos de su Sandwich</h3>
            <div class="row">
                <div class="col-6">
                    <div class="card" style="max-width: 400px;">
                        <img src="img/pedido.png" class="card-img-top img-fluid" alt="...">
                        <div class="card-body">
                            <p class="card-text text-center text-succes">${nombre}</p>
                            <c:forEach var="ing" items="${IngredientesDelSandwich}">
                                <p class="card-text fw-bold">${ing.nombre}</p>
                                <p class="card-text">${ing.detalle}</p>
                                <p class="card-text">Precio por unidad $${ing.precio}</p>
                            </c:forEach>
                            <p class="card-text fw-bold text-end">Monto a pagar <span
                                    class="text-primary">$${montoFinal}</span></p>
                        </div>
                    </div>

                </div>
                <div class="col-6">
                    <h4 class="pb-3">Ingrese los datos para realizar el pago</h4>
                    <div class="w3-col s9 w3-padding-top-64 w3-center w3-">
                        <f:form action="validarPago" method="POST" modelAttribute="formPago" class="m-0">
                            <label class="form-label">Numero de la Tarjeta</label>
                            <f:input path="nroTarjeta" type="text" maxlength="16" size="16" class="form-control"  required="required"/>
                            <br/>
                           <label class="form-label">Vencimiento</label>
                            <f:input path="vencTarjeta" type="date" class="form-control"  required="required"/>
                            <br/>
                            <label class="form-label">Código de seguridad</label>
                            <f:input path="codSeguridad" type="number" size="3" class="form-control"  required="required"/>
                            <br/>
                            <div class="text-end">
                                <button class="btn btn-success px-5" Type="Submit">Pagar</button>
                            </div>
                        </f:form>

                        <%--Bloque que es visible si el elemento error no esta vacio	--%>
                        <c:if test="${not empty msg}">
                            <span class="text-danger my-4">${msg}</span>
                        </c:if>
                    </div>
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
<script src="https://sdk.mercadopago.com/js/v2"></script>
<script>
    // Agrega credenciales de SDK
    // Acá va la Public Key de la cuenta de mercadopago que voy a usar como
    // vendedor
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
</script>
</body>
</html>