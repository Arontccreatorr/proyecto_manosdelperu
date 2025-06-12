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
                    <li><a href="sesion_proveedor.jsp">emprende tu negocio</a></li>
                </ul>
            </nav>
            <div class="icono-carro">
                <a class="carro" href="historial-de-compras.php"> <img src="${pageContext.request.contextPath}/img/logos/carrito.png" alt="icono de carro" class="carro"> </a>
            </div>
        </div>

      <div class="grids">
        <div class="grid1">

            <div class="full-width box">
                <h3>¿No Tienes cuenta?</h3>
                <p class="exp">
                    Para empezar a vender, solo necesitas registrarte como proveedor. Una vez que estés registrado,
                    podrás agregar tus productos y empezar a vender.
                    Te ofrecemos una plataforma fácil de usar y si tienes problemas, un equipo de soporte que te ayudará
                    en todo momento.
                </p>
            </div>
        </div>

        <div class="grid2">
            <div class="half-width box">
                <h3>Seeguridad al registro</h3>
                <p class="exp">
                    En nuestra plataforma "Manos del Peru" esta integrado con alta seguridad, ademas que las contraseñas
                    son encriptadas antes cualquier
                    vulnerabilidad de hackeo. Además, utilizamos protocolos de seguridad avanzados para proteger tu información personal y garantizar que tus datos estén siempre seguros.
                </p>
            </div>

            <div class="half-width box">
                <h3>¿Porque pedimos registro de cuenta?</h3>
                <p class="exp">
                    Solicitamos el registro de cuenta para garantizar la seguridad de tus datos y ofrecerte una
                    experiencia personalizada. Al registrarte, podrás gestionar tus productos, realizar un seguimiento
                    de tus ventas y recibir soporte especializado. Además, tu información estará protegida y solo tú
                    podrás acceder a tu cuenta y configuraciones. <br>
                    Por otra parte, es para identificar los productos con un unico id que tendran cada proovedor
                </p>
            </div>
        </div>
        <div class="grid3">
            <div class="icon-box">
                <img class="i" src="../../img/logos/seguridad.png" alt="seguridad">
            </div>
            <div class="icon-box">
                <img class="i" src="../../img/logos/tecnico.png" alt="soporte técnico">
            </div>
            <div class="icon-box">
                <img class="i" src="../../img/logos/apoyo.png" alt="apoyo">
            </div>
        </div>
    </div>

<span class="spann">Si no tienes un cuenta solo registrate ➤ <button id="abrirModal"> Registrarme</button></span>
    </body>
</html>
