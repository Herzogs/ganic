<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
            <div class="alert alert-success mt-5" role="alert">
                <h4 class="alert-heading">Gracias por realizar su pedido!</h4>
                <p>Nuestro equipo ya esta trabajando en su compra</p>
                <hr>
                <p class="mb-0">Recibira un email con la información del estado de su pedido.</p>
            </div>
            <a href="home" class="btn btn-primary px-5 my-5">Volver al Home</a>
            <a href="seguimiento" class="btn btn-success px-5 my-5">Seguimiento del envio</a>
        </div>
    </section>
</main>
<%@ include file="../vistas/footer.jsp" %>
<!-- JavaScript Bundle with Popper -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-u1OknCvxWvY5kfmNBILK2hRnQC3Pr17a+RTT6rIHI7NnikvbZlHgTPOOmMi466C8" crossorigin="anonymous"></script>
</body>
</html>