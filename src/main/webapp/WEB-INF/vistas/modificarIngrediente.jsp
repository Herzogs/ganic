
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<%@ include file="../vistas/header.jsp" %>
<main>
    <section class="text-center">
        <img src="img/${paso}.png" class="img-fluid" alt="bannerganccic">
    </section>

    <section>
        <div class="container py-5">
            <div class="row g-4">
                <h3>Seleccone ingrediente</h3>

                    <form:form action="actualizarPreferenciaMod" method="POST" modelAttribute="formPref">
                        <h4>Filtro por preferencia: </h4>
                        <form:input path="paso" value="${paso}" type="hidden"/>
                        <form:select  path="preferencia" id="preferencia" class="form-select">
                            <form:option value="SinRestriccion">SinRestriccion</form:option>
                            <form:option value="Vegano">Vegano</form:option>
                            <form:option value="sin_TACC">sin_TACC</form:option>
                            <c:set var="formPref.paso" value="${paso}"/>
                        </form:select>
                        <div class="d-grid gap-2">
                            <button class="btn btn-success mt-1 mb-4" Type="Submit">Filtrar</button>
                        </div>
                    </form:form>

                <c:forEach var="pan" items="${ListaDeIngredientes}" >
                    <div class="col-6">
                        <div class="card">
                            <div class="card-header bg-dark text-white">
                                    ${pan.nombre}
                            </div>
                            <ul class="list-group list-group-flush">
                                <li class="list-group-item">${pan.detalle}</li>
                                <li class="list-group-item"><span class="text-primary">Precio:</span> ${pan.precio}</li>
                                <li class="list-group-item"><a href="mod?id=${pan.idIngrediente}" class="btn btn-success px-3 my-3">Agregar</a></li>
                            </ul>
                        </div>
                    </div>
                </c:forEach>
            </div>
            <div class="toast align-items-center text-white bg-primary border-0" role="alert" aria-live="assertive" aria-atomic="true">
                <div class="d-flex">
                    <div class="toast-body">
                        <p>${error}</p>
                    </div>
                    <button type="button" class="btn-close btn-close-white me-2 m-auto" data-bs-dismiss="toast" aria-label="Close"></button>
                </div>
            </div>
            <a href="home" class="btn btn-primary px-5 my-5">Volver al Home</a>
            <a href="confirmar?paso=${paso}" class="btn btn-success px-5 my-5">Finalizar</a>
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