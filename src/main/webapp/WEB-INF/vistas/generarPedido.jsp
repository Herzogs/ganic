
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <!-- CSS only -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.1/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-iYQeCzEYFbKjA/T2uDLTpkwGzCiq6soy8tYaI1GyVh/UjpbCx/TYkiZhlZB6+fzT" crossorigin="anonymous">
    <link href="css/styles.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.9.1/font/bootstrap-icons.css">
</head>

<body>
<header>
    <nav class="nav_style d-flex align-items-center">
        <div class="container">
            <div class="text-center">
                <a href="home">Home</a>
                <a href="">Menu</a>
                <a href="home"><img src="img/ganiclogo.png" class="img-fluid logo_style" alt="Logo"></a>
                <a href="">Contacto</a>
                <a href="">Nosotros</a>
            </div>
        </div>
    </nav>
</header>
<main>
    <section class="text-center">
        <img src="img/${paso}.png" class="img-fluid" alt="bannerganccic">
    </section>

    <section>
        <div class="container py-5">
            <div class="row g-4">
                <h3>Genere su pedido</h3>
                <h4>Seleccone ingrediente</h4>
                <c:forEach var="pan" items="${ListaDePanes}" >
                    <div class="col-6">
                        <div class="card">
                            <div class="card-header bg-dark text-white">
                                    ${pan.nombre}
                            </div>
                            <ul class="list-group list-group-flush">
                                <li class="list-group-item">${pan.detalle}</li>
                                <li class="list-group-item"><span class="text-primary">Precio:</span> ${pan.precio}</li>
                                <li class="list-group-item"><a href="agregarIngrediente?id=${pan.idIngrediente}&paso=${paso}" class="btn btn-success px-3 my-3">Agregar</a></li>
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
<!-- JavaScript Bundle with Popper -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.1/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-u1OknCvxWvY5kfmNBILK2hRnQC3Pr17a+RTT6rIHI7NnikvbZlHgTPOOmMi466C8"
        crossorigin="anonymous"></script>
</body>
</html>