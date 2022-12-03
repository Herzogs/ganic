<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<!DOCTYPE html>
<html>
<%@ include file="../vistas/header.jsp" %>
<main>

    <style>

        /* TABLA MINISTERIO CSS */

        .tabla-ministerio {
            border-spacing: 0px 10px !important;
            border-collapse: separate !important;
        }

        .tabla-ministerio th {
            font-size: 14px;
            font-weight: 600;
            padding: 0px 10px;
            border: none;
        }

        .tabla-ministerio td {
            font-size: 14px;
            font-weight: 400;
            padding: 14px 10px;
            white-space: nowrap;
            border-top: 1px solid #e1e1e1;
            border-bottom: 1px solid #e1e1e1;
        }

        .tabla-ministerio tr td:last-child {
            border-right: 1px solid #e1e1e1;
            border-radius: 0px 6px 6px 0px;
            font-weight: 500;
        }

        .tabla-ministerio tr td a {
            text-decoration: none;
            color: #0695d5;
        }

        .tabla-ministerio tr td:first-child {
            border-left: 1px solid #e1e1e1;
            border-radius: 6px 0px 0px 6px;
        }

    </style>
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

            <div class="mb-5">
                <h2 class="mt-5 mb-4"><i class="bi bi-bag-heart text-primary pe-3"></i> Mi historial de pedidos:</h2>
                    <article class="row">
                        <div class="col-12 table-responsive">

                            <table class="table table-striped tabla-ministerio">
                                <thead>
                                <tr>
                                    <th>Nro de Compra</th>
                                    <th>Sandwichs</th>
                                    <th>Total abonado</th>
                                    <th>Fecha</th>
                                    <th>Estado</th>
                                    <th></th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="compra" items="${listaDeCompras}" >
                                <tr>
                                    <td>${ compra.idCompra }</td>
                                    <td>${ compra.cant }</td>
                                    <td>$${ compra.montoTotal}</td>
                                    <td>${ compra.fecha.withSecond(0).withNano(0) }</td>
                                    <td class="fw-bold text-primary">${ compra.estado }</td>
                                    <c:choose>
                                        <c:when test="${compra.comentario == null && compra.estado == 'ENTREGADO' }">
                                            <td><a href="comentarios?idCompra=${compra.idCompra}"><i class="bi bi-plus-square-fill text-success pe-2"></i>Comentario</a></td>
                                        </c:when>
                                        <c:otherwise>

                                        </c:otherwise>
                                    </c:choose>
                                </tr>
                                <tr>
                                    <td style="border: none !important; padding-top: 0px !important;"> ${ compra.comentario }</td>
                                </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </article>

            </div>

            <c:if test="${not empty msg}">
                <div class="text-danger my-4">
                    <span>${msg}</span>
                </div>
            </c:if>


        </div>
    </section>
</main>

<%@ include file="../vistas/footer.jsp" %>

<!-- JavaScript Bundle with Popper -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-u1OknCvxWvY5kfmNBILK2hRnQC3Pr17a+RTT6rIHI7NnikvbZlHgTPOOmMi466C8" crossorigin="anonymous"></script>
</body>
</html>