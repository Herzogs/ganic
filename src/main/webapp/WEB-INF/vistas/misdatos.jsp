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
                <a href="home"><img src="img/ganiclogo.png" class="img-fluid logo_style" alt="Logo"></a>
                <a href="home">Home</a>
                <a href="ingredientes">Menu</a>
                <a href="contacto">Contacto</a>
                <a href="nosotros">Nosotros</a>
						<span class="dropdown ps-5">
							<button class="btn btn-secondary dropdown-toggle" type="button" data-bs-toggle="dropdown" aria-expanded="false">
								Mi perfil
							</button>
							<ul class="dropdown-menu">
								<li><a class="dropdown-item text-dark py-2" href="misdatos">Mis datos</a></li>
								<li><a class="dropdown-item text-dark py-2" href="Salir">Salir</a></li>
							</ul>
						</span>
            </div>
        </div>
    </nav>
</header>

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
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-u1OknCvxWvY5kfmNBILK2hRnQC3Pr17a+RTT6rIHI7NnikvbZlHgTPOOmMi466C8" crossorigin="anonymous"></script>
</body>
</html>
