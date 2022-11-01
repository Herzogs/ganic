<%--
  Created by IntelliJ IDEA.
  User: h3rz
  Date: 1/11/22
  Time: 17:34
  To change this template use File | Settings | File Templates.
--%>
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
    <section>
        <div class="container py-5">
            <h3 class="pt-5 pb-3">Datos de su Sandwich</h3>
            <div class="row">
                <div class="col-6">
                    <div class="card" style="max-width: 400px;">
                        <img src="img/pedido.png" class="card-img-top img-fluid" alt="...">
                        <div class="card-body">
                            <p class="card-text text-center text-succes">${nombre}</p>
                            <c:forEach var="ing" items="${IngredientesDelSandwich}">
                                <p class="card-text fw-bold">${ing.nombre}</p>
                                <p class="card-text">${ing.detalle}</p>
                                <p class="card-text">Precio por unidad $${ing.precio}</p>
                            </c:forEach>
                            <p class="card-text fw-bold text-end">Monto a pagar <span
                                    class="text-primary">$${montoFinal}</span></p>
                        </div>
                    </div>
                    <%--Bloque que es visible si el elemento error no esta vacio	--%>
                    <c:if test="${not empty error}">
                        <h4><span>${error}</span></h4>
                        <br>
                    </c:if>
                </div>
                <div class="col-6">
                    <h4 class="pb-3">Confirmar pago</h4>
                    <div class="w3-col s9 w3-padding-top-64 w3-center w3-">
                        <a class="cho-container w3-btn w3-blue"></a>
                        <img src="img/mercadoPago.png" alt="mpz" />
                    </div>
                    <div><a href="restablecer" class="btn btn-primary px-5 my-5">Volver al Home</a></div>
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
<script src="https://sdk.mercadopago.com/js/v2"></script>
<script>
    // Agrega credenciales de SDK
    // Acá va la Public Key de la cuenta de mercadopago que voy a usar como
    // vendedor
    const mp = new MercadoPago("TEST-f1370bf5-0b83-467e-9c19-d87e78137ecd", {
        locale : "es-AR",
    });
    // Inicializa el checkout
    mp.checkout({
        preference : {
            // Se le pasa el id de la preferencia de pago generada con el backend
            // desde spring
            id : '<c:out value="${preference.id}"/>',
        },
        render : {
            container : ".cho-container", // Indica el nombre de la clase donde se mostrará el botón de pago
            label : "Pagar", // Cambia el texto del botón de pago (opcional)
        },
    });
</script>
</body>
</html>