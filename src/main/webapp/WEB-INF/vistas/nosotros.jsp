<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
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
                <c:choose>
                    <c:when test="${sessionScope.id == null}">
						<span class="dropdown ps-5">
							<button class="btn btn-secondary dropdown-toggle" type="button" data-bs-toggle="dropdown" aria-expanded="false">
								Mi perfil
							</button>
							<ul class="dropdown-menu">
								<li><a class="dropdown-item text-dark py-2" href="login">Login</a></li>
								<li><a class="dropdown-item text-dark py-2" href="registrar">Registrar</a></li>
							</ul>
						</span>
                    </c:when>
                    <c:otherwise>
						<span class="dropdown ps-5">
							<button class="btn btn-secondary dropdown-toggle" type="button" data-bs-toggle="dropdown" aria-expanded="false">
								Mi perfil
							</button>
							<ul class="dropdown-menu">
								<li><a class="dropdown-item text-dark py-2" href="verificar">Actulizar datos</a></li>
								<li><a class="dropdown-item text-dark py-2" href="Salir">Salir</a></li>
							</ul>
						</span>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </nav>
</header>
<main>
    <section>
        <div class="container">
            <div class="row my-5">
                <div class="col-6">
                    <h2 class="text-center">CONOCÉ GANIC SANDWICH</h2>
                    <hr>
                    <p class="text-center">
                        En GANIC SANDWICH reinventamos los sandiwch, homenajeando a su creador Cristian FELDMAN, un
                        inmigrante alemán, quien crea en 1895 el primer sandiwch de Argentina. Reformulamos la receta
                        original, desarrollando sandiwch gourmet, llenos de sabor, de estilo casero, usando los mejores
                        ingredientes frescos y naturales. Presentadas en un pan casero especial de elaboración propia.
                        Tenemos una propuesta innovadora de Pop-up Stores montados en contenedores reciclados, totalmente
                        equipados, que prometen al visitante una experiencia gastronómica única, garantizando la calidad y
                        la originalidad de nuestros productos.
                    </p>
                </div>
                <div class="col-6 text-end">
                    <img src="img/team01.png" class="img-fluid" alt="Team">
                </div>
                <div class="col-12 my-4">
                    <img src="img/team02.png" class="img-fluid" alt="Team">
                </div>
                <div class="col-12">
                    <h2 class="mt-4 text-center">EL EQUIPO</h2>
                    <hr>
                    <p class="text-center mb-5">Somos una empresa seria y responsable en el rubro gastronómico conformada por emprendedores con más
                        de 20 años de experiencia. Tenemos una propuesta inovadora de Pop-up Stores montados en contenedores
                        reciclandos totalmente equipados, que prometen al visitante una experiencia única, garantizando la
                        calidad y la originalidad de nuestros productos.</p>
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
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-u1OknCvxWvY5kfmNBILK2hRnQC3Pr17a+RTT6rIHI7NnikvbZlHgTPOOmMi466C8" crossorigin="anonymous"></script>
</body>
</html>