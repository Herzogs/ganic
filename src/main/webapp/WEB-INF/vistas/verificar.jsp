<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <!-- CSS only -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-iYQeCzEYFbKjA/T2uDLTpkwGzCiq6soy8tYaI1GyVh/UjpbCx/TYkiZhlZB6+fzT" crossorigin="anonymous">
    <link href="css/styles.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.9.1/font/bootstrap-icons.css">
</head>
<body>
<header>
    <nav class="nav_style d-flex align-items-center">
        <div class="container">
            <div class="text-center">
                <a href="home">Home</a>
                <a href="ingredientes">Menu</a>
                <a href="home"><img src="img/ganiclogo.png" class="img-fluid logo_style" alt="Logo"></a>
                <a href="contacto">Contacto</a>
                <a href="nosotros">Nosotros</a>
            </div>
        </div>
    </nav>
</header>
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

<!-- Placed at the end of the document so the pages load faster -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js" ></script>
<script>window.jQuery || document.write('<script src="../../assets/js/vendor/jquery.min.js"><\/script>')</script>
<script src="js/bootstrap.min.js" type="text/javascript"></script>
</body>
</html>
