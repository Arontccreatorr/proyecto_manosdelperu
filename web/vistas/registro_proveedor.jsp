<%-- 
    Document   : registro_proveedor
    Created on : 10 jun 2025, 2:55:57
    Author     : tacov
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/encabezado.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/registro-de-sesion.css">
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

        <span class="spann">Si no tienes un cuenta solo registrate ➤ <button id="abrirModal"> Registrarme</button></span>

        <div class="formulario">
            <div id="modalSesion" class="modal" style="display:none;">
                <form method="post"
                      action="${pageContext.request.contextPath}/controlador_registroproveedor"
                      class="formulario-registro"
                      enctype="multipart/form-data">
                    <span id="cerrarModal" style="float:right; font-size:28px; cursor:pointer;">&times;</span>
                    <h1 class="regis">REGISTRATE</h1>
                    <c:if test="${not empty mensaje}">
                        <div style="color:${mensajeColor};margin-bottom:10px; font-size: 18px; ; padding: 10px; border-radius: 15px;">
                            ${mensaje}
                        </div>
                    </c:if>
                    <div class="grid4">
                        <div>
                            <input type="text" name="dni" placeholder="DNI" required>
                            <input type="text" name="nombres" placeholder="Nombres" required>
                        </div>
                        <div class="img">
                            <span>Imagen de perfil</span>
                            <input type="file" id="imagen" name="imagen" accept="image/*" required>
                        </div>
                        <div>
                            <input type="text" name="apellidos" placeholder="Apellidos" required>
                            <input type="email" name="correo" placeholder="Correo" required>
                        </div>
                        <div>
                            <input type="password" name="contraseña" placeholder="Contraseña" required>
                            <input type="text" name="direccion" placeholder="Dirección">
                        </div>
                    </div>
                    <input type="text" name="numero_contacto" placeholder="Número de contacto">
                    <div class="botones">
                        <button type="submit">Registrate</button>
                        <button type="button" onclick="window.location.href = '${pageContext.request.contextPath}/vistas/sesion_proveedor.jsp'">Iniciar sesión</button>
                    </div>
                </form>
            </div>
        </div>

        <script>
            document.getElementById('abrirModal').onclick = function () {
                document.getElementById('modalSesion').style.display = 'flex';
            };
            document.getElementById('cerrarModal').onclick = function () {
                document.getElementById('modalSesion').style.display = 'none';
            };
            window.onclick = function (event) {
                var modal = document.getElementById('modalSesion');
                if (event.target === modal) {
                    modal.style.display = "none";
                }
            };
        </script>
    </body>
</html>
