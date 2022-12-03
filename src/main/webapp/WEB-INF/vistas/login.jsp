<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<%@ include file="../vistas/header.jsp" %>
	<main>
		<div class = "container">
			<div id="loginbox">
				<div class="row justify-content-center">
					<div class="col-6 mt-5" style="background-color: #f7f7f7; padding: 46px;">
						<form:form action="validar-login" method="POST" modelAttribute="datosLogin">
							<h3>Bienvenido! ingresá tus datos</h3>
							<hr class="mb-4">
							<label class="form-label">Email</label>
							<form:input path="email" id="email" type="email" class="form-control" />
							<label class="form-label mt-4">Contraseña</label>
							<form:input path="password" type="password" id="password" class="form-control"/>
							<div class="d-grid gap-2">
								<button class="btn btn-success mt-5 mb-4" Type="Submit">Ingresar</button>
								<a href="registrar" class="btn btn-primary">Registrarme</a>
							</div>
						</form:form>
						<%--Bloque que es visible si el elemento error no esta vacio	--%>
						<c:if test="${not empty error}">
							<h4 class="text-danger my-4"><span>${error}</span></h4>
							<br>
						</c:if>
						${msg}
					</div>
				</div>
			</div>
		</div>

	</main>
<%@ include file="../vistas/footer.jsp" %>
		<!-- Placed at the end of the document so the pages load faster -->
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js" ></script>
		<script>window.jQuery || document.write('<script src="../../assets/js/vendor/jquery.min.js"><\/script>')</script>
		<script src="js/bootstrap.min.js" type="text/javascript"></script>
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-u1OknCvxWvY5kfmNBILK2hRnQC3Pr17a+RTT6rIHI7NnikvbZlHgTPOOmMi466C8" crossorigin="anonymous"></script>
	</body>
</html>
