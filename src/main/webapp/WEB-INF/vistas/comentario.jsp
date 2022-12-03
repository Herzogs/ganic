<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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
            <h2 class="mt-5 text-center">Agregar comentario</h2>

                <div class="text-center">
                    <img src="img/coment.png" class="img-fluid" alt="coments" style="max-width: 300px">
                </div>


            <form:form action="agregarComentario" method="POST" modelAttribute="formularioComentario">
                <h3 class="mb-3">Dejanos tu opinión sobre nuestros productos y atención !</h3>
                <form:input path="comentario" type="text" class="form-control" />
                <form:input path="idCompra" type="text" class="form-control d-none" value="${compra.idCompra}" />
                   <div class="text-end my-5">
                       <button class="btn btn-success px-5" Type="Submit">Enviar</button>
                   </div>
            </form:form>


        </div>
    </section>
</main>

<%@ include file="../vistas/footer.jsp" %>

<!-- JavaScript Bundle with Popper -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-u1OknCvxWvY5kfmNBILK2hRnQC3Pr17a+RTT6rIHI7NnikvbZlHgTPOOmMi466C8" crossorigin="anonymous"></script>
</body>
</html>