
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
                <a href="ingredientes">Menu</a>
                <a href="home"><img src="img/ganiclogo.png" class="img-fluid logo_style" alt="Logo"></a>
                <a href="contacto">Contacto</a>
                <a href="nosotros">Nosotros</a>
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
                <h3>Seleccone ingrediente</h3>
                <c:forEach var="ing" items="${ListaDeIngredientes}" >
                    <div class="col-6">
                        <div class="card">
                            <div class="card-header bg-dark text-white">
                                    ${ing.nombre}
                            </div>
                            <ul class="list-group list-group-flush">
                                <li class="list-group-item">${ing.detalle}</li>
                                <li class="list-group-item"><span class="text-primary">Precio:</span> ${ing.precio}</li>
                                <li class="list-group-item"><a href="mod?id=${ing.idIngrediente}" class="btn btn-success px-3 my-3">Agregar</a></li>
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
</body>
</html>