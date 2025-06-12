<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Proveedores</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/encabezado.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/inicio_de_sesion.css">
    </head>
    <body>
        <div class="encabezado">
            <div class="logo-nombre">
                <c:url var="urlInicio" value="/controlador_mostrarproducto"/>
                <a href="${urlInicio}"> <img src="${pageContext.request.contextPath}/img/logos/logo.png" alt="logo manos del peru" class="logo"> </a>
                <span class="nombre-tienda">manos del peru</span>
            </div>
            <nav class="menu-central">
                <ul>
                     <c:url var="urlInicio" value="/controlador_mostrarproducto"/>
                    <li><a href="${urlInicio}">inicio</a></li>
                    <li><a href="#">quienes somos?</a></li>
                    <li><a href="#">emprende tu negocio</a></li>
                </ul>
            </nav>
            <div class="icono-carro">
                <a class="carro" href="historial-de-compras.php"> <img src="${pageContext.request.contextPath}/img/logos/carrito.png" alt="icono de carro" class="carro"> </a>
            </div>
        </div>

        <div class="grids">
            <div class="grid1">

                <div class="full-width box">
                    <h3>¿Por qué trabajar con nosotros?</h3>
                    <p class="exp">
                        En manos del Perú, buscamos ayudar a los emprendedores a crecer y expandir su negocio.
                        Te ofrecemos una plataforma para que puedas vender tus productos de manera fácil y rápida.
                        Además, contamos con un equipo de soporte que te ayudará en todo momento.
                    </p>
                </div>
            </div>

            <div class="grid2">
                <div class="half-width box">
                    <h3>¿Cómo funciona?</h3>
                    <p class="exp">
                        Para empezar a vender, solo necesitas registrarte como proveedor.
                        Una vez que estés registrado, podrás agregar tus productos y empezar a vender.
                        Te ofrecemos una plataforma fácil de usar y un equipo de soporte que te ayudará en todo momento.
                    </p>
                </div>

                <div class="half-width box">
                    <h3>¿Qué necesitas para empezar?</h3>
                    <p class="exp">
                        Solo necesitas tener un producto para vender y registrarte como proveedor.
                        Una vez que estés registrado, podrás agregar tus productos y empezar a vender.
                        Te ofrecemos una plataforma fácil de usar y un equipo de soporte que te ayudará en todo momento.
                    </p>
                </div>
            </div>

            <div class="grid3">
                <div class="icon-box">
                    <img class="i" src="${pageContext.request.contextPath}/img/logos/seguridad.png" alt="seguridad">
                </div>
                <div class="icon-box">
                    <img class="i" src="${pageContext.request.contextPath}/img/logos/tecnico.png" alt="soporte técnico">
                </div>
                <div class="icon-box">
                    <img class="i" src="${pageContext.request.contextPath}/img/logos/apoyo.png" alt="apoyo">
                </div>
            </div>
        </div>

        <span class="spann">Si ya tienes una cuenta solo ➤ <button id="abrirModal"> Iniciar sesion</button></span>
    
    
  <!-- Modal de login hecho por jose borjorquez -->
  <div id="modalSesion" class="modal" style="display:none;">
    <div class="formulario">
      <div class="formulario-registro">
        <span id="cerrarModal" style="float:right; font-size:28px; cursor:pointer;">
          &times;
        </span>
        <h2>Iniciar Sesión</h2>
        <form method="post" action="${pageContext.request.contextPath}/controlador_sesionproveedor">
          <input type="email"    name="email"    placeholder="Correo electrónico" required>
          <input type="password" name="password" placeholder="Contraseña"       required>
          <div class="botones">
            <button type="submit">Ingresar</button>
            <button type="button"
                    onclick="window.location.href='registro_proveedor.jsp'">
              Registrar mi cuenta
            </button>
          </div>
        </form>
        <c:if test="${not empty loginError}">
          <div style="color:red; margin-top:5px; font-size:15px;">
            ${loginError}
          </div>
        </c:if>
        <a href="RECUPERAR_CONTRASEÑA.jsp" style="color:black;">
          Olvidé mi contraseña
        </a>
      </div>
    </div>
  </div>

  <script>
    document.getElementById('abrirModal').onclick = () =>
      document.getElementById('modalSesion').style.display = 'flex';
    document.getElementById('cerrarModal').onclick = () =>
      document.getElementById('modalSesion').style.display = 'none';
    window.onclick = e => {
      if (e.target === document.getElementById('modalSesion'))
        document.getElementById('modalSesion').style.display = 'none';
    };
    <c:if test="${not empty loginError}">
    window.onload = () =>
      document.getElementById('modalSesion').style.display = 'flex';
    </c:if>
  </script>

    </body>
</html>
