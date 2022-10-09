<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <!-- Bootstrap core CSS -->
    <link href="css/bootstrap.min.css" rel="stylesheet" >
    <!-- Bootstrap theme -->
    <link href="css/bootstrap-theme.min.css" rel="stylesheet">
</head>
<body>
<div class = "container">
    <div id="loginbox" style="margin-top:50px;" class="mainbox col-md-6 col-md-offset-3 col-sm-8 col-sm-offset-2">

        <form:form action="verificarDatos" method="POST" modelAttribute="datosUsuario">
            <h3 class="form-signin-heading">Verificar datos del perfil</h3>
            <hr class="colorgraph"><br>
            <form:input path="nombre" type="text" id="nombre" class="form-control" placeholder="Ingresa tu nombre" required="required" />
            <form:input path="apellido" type="text" id="apellido" class="form-control" placeholder="Ingresa tu apellido" required="required"/>
            <form:input path="direccion" type="text" id="direccion" class="form-control" placeholder="Ingresa tu direccion" required="required"/>
            <form:input path="preferencia" type="text" id="preferencia" class="form-control" placeholder="SinRestriccion Vegano SinTacc" required="required"/>
            <button class="btn btn-lg btn-primary btn-block" Type="Submit"/>Guardar</button>

        </form:form>

        <a href="home" class="mt-5">Volver a la home</a>
        <%--Bloque que es visible si el elemento error no esta vacio--%>
        <c:if test="${not empty error}">
            <h4>${error}</h4>
        </c:if>
    </div>
</div>

<!-- Placed at the end of the document so the pages load faster -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js" ></script>
<script>window.jQuery || document.write('<script src="../../assets/js/vendor/jquery.min.js"><\/script>')</script>
<script src="js/bootstrap.min.js" type="text/javascript"></script>
</body>
</html>
