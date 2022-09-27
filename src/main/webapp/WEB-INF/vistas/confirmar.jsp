<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
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
                <a href="">Menu</a>
                <a href="home"><img src="img/ganiclogo.png" class="img-fluid logo_style" alt="Logo"></a>
                <a href="">Contacto</a>
                <a href="">Nosotros</a>
            </div>
        </div>
    </nav>
</header>
<main>
    <section>
        <div class="container">
            <h3 class="pt-5 pb-3">Datos de su pedido</h3>
            <div class="row">
                <div class="col-6">
                    <div class="card" style="max-width: 400px;">
                        <img src="img/pedido.png" class="card-img-top img-fluid" alt="...">
                        <div class="card-body">
                            <p class="card-text">Su pedido</p>
                            <p class="card-text">xxx</p>
                            <p class="card-text">xxx</p>
                            <p class="card-text">xxx</p>
                            <p class="card-text fw-bold">Monto a pagar</p>
                        </div>
                    </div>
                </div>
                <div class="col-6">
                    <h4 class="pb-5">Confirmar pago</h4>
                    <button class="btn btn-primary px-5">Confirmar</button>
                </div>
            </div>
        </div>
    </section>
</main>
<!-- JavaScript Bundle with Popper -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-u1OknCvxWvY5kfmNBILK2hRnQC3Pr17a+RTT6rIHI7NnikvbZlHgTPOOmMi466C8" crossorigin="anonymous"></script>
</body>
</html>