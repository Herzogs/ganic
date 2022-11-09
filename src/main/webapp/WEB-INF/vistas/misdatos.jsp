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
                <div class="col-12">
                    <h1 class="mb-4"> Datos de usuario </h1>

                    <div class="card mb-3">
                        <div class="row g-0">
                            <div class="col-md-4">
                                <img src="img/userdato.png" class="img-fluid rounded-start" alt="Perfil usuario">
                            </div>
                            <div class="col-md-8">
                                <div class="card-body">
                                    <h4 class="card-title text-uppercase mb-3">${ miUsuario.nombre } ${ miUsuario.apellido }</h4>
                                    <p class="card-text"><strong>Email registrado:</strong>  ${ miUsuario.email }</p>
                                    <p class="card-text"><strong>Dirección:</strong>  ${ miUsuario.direccion }</p>
                                    <p class="card-text"><strong>Preferencia de sandwich:</strong>  ${ miUsuario.preferencia }</p>
                                    <a href="verificar" class="btn btn-warning rounded-pill text-white mt-3">Actulizar mi información</a>
                                </div>
                            </div>
                        </div>
                    </div>

                </div>
            </div>

        </div>
    </div>

</main>

<%@ include file="../vistas/footer.jsp" %>

<!-- JavaScript Bundle with Popper -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-u1OknCvxWvY5kfmNBILK2hRnQC3Pr17a+RTT6rIHI7NnikvbZlHgTPOOmMi466C8" crossorigin="anonymous"></script>
</body>
</html>
