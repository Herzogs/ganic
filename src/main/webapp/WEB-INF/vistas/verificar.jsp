<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<%@ include file="../vistas/header.jsp" %>

<main>
    <div class = "container">
        <div id="loginbox">

            <div class="row justify-content-center my-5">
                <div class="col-6">
                    <form:form action="verificarDatos" method="POST" modelAttribute="datosUsuario">
                        <h3 class="text-center">Actualizar datos del perfil</h3>
                        <hr class="mb-4"><br>
                        <label class="form-label">Nombre</label>
                        <form:input path="nombre" type="text" id="nombre" class="form-control" required="required" />
                        <label class="form-label mt-3">Apellido</label>
                        <form:input path="apellido" type="text" id="apellido" class="form-control" required="required"/>
                        <label class="form-label mt-3">Dirección</label>
                        <form:input path="direccion" type="text" id="direccion" class="form-control" required="required"/>
                        <label class="form-label mt-3">Preferencia</label>
                        <form:select  path="preferencia" id="preferencia" class="form-select">
                            <form:option value="SinRestriccion">SinRestriccion</form:option>
                            <form:option value="Vegano">Vegano</form:option>
                            <form:option value="sin_TACC">sin_TACC</form:option>
                        </form:select>

                        <div class="d-grid gap-2">
                            <button class="btn btn-success mt-5 mb-4" Type="Submit">Guardar</button>
                        </div>

                    </form:form>

                    <a href="home" class="btn btn-primary px-5 mt-5">Volver a la home</a>
                    <%--Bloque que es visible si el elemento error no esta vacio--%>
                    <c:if test="${not empty error}">
                        <h4>${error}</h4>
                    </c:if>
                </div>
            </div>

        </div>
    </div>
</main>

<%@ include file="../vistas/footer.jsp" %>
<!-- Placed at the end of the document so the pages load faster -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js" ></script>
<script>window.jQuery || document.write('<script src="../../assets/js/vendor/jquery.min.js"><\/script>')</script>
<script src="js/bootstrap.min.js" type="text/javascript"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-u1OknCvxWvY5kfmNBILK2hRnQC3Pr17a+RTT6rIHI7NnikvbZlHgTPOOmMi466C8" crossorigin="anonymous"></script>

</body>
</html>
