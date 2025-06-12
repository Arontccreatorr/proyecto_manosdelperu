<%@ page import="modelos.Proveedores" %>
<%@ page session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
    Proveedores prov = (Proveedores) session.getAttribute("loggedProvider");
    if (prov == null) {
        response.sendRedirect(request.getContextPath()
                + "/vistas/sesion_proveedor.jsp");
        return;
    }
%>
  
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
 
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/encabezado.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/registro-producto.css">
    </head>
    <body>
        <div class="encabezado">
            <div class="logo-nombre">
                <a href="INICIO.jsp">
                    <img src="${pageContext.request.contextPath}/img/logos/logo.png"
                         alt="logo manos del peru" class="logo">
                </a>
                <span class="nombre-tienda">manos del peru</span>
            </div>
            <nav class="menu-central">
                <ul>
                    <li><a href="INICIO.jsp">inicio</a></li>
                    <li><a href="#">quienes somos?</a></li>
                    <li><a href="#">emprende tu negocio</a></li>
                </ul>
            </nav>
            <div class="icono-carro">
                <p style="margin:0;">
                <p>BIENVENIDO: <strong><%= prov.getNombres()%> <%= prov.getApellidos()%></strong> </p>
                <img
        src="http://localhost/img/perfil_proveedor/<%= prov.getImg_provedor()%>"
         alt="Foto de <%= prov.getNombres() %>"
        class="user-avatar" id="userMenuBtn"
      />
                <div class="desplegable" id="userDropdown" style="display:none;">
                    <a href="perfil.jsp">Mi perfil</a>
                    <a href="<c:url value='/controlador_sesionproveedor?logout=true'/>">
                        Cerrar sesión
                    </a>
                </div>
            </div>
        </div>

        <c:if test="${not empty successMsg}">
            <div class="modal-exito" style="display:flex;">
                <div class="modal-exito-contenido">
                    <p>${successMsg}</p>
                    <button onclick="cerrarModalExito()">OK</button>
                </div>
            </div>
        </c:if>

        <h1>Formulario para Agregar Producto</h1>
        <form method="post"
              action="${pageContext.request.contextPath}/controlador_registrarproducto"
              enctype="multipart/form-data">
            <label>ID del Proveedor:</label><br>
            <input type="number" name="id_proveedor"
                   value="<%= prov.getId_proveedor() %>" readonly required><br><br>

            <label>Nombre del Producto:</label><br>
            <input type="text" name="nombre" required><br><br>

            <label>Imagen del Producto:</label><br>
            <input type="file" name="imagen" accept="image/*" required><br><br>

            <label>Descripción:</label><br>
            <textarea name="descripcion" rows="4" required></textarea><br><br>

            <label>Precio:</label><br>
            <input type="number" name="precio" step="0.01" required><br><br>

            <label>Stock:</label><br>
            <input type="number" name="stock" required><br><br>

            <button type="submit">Agregar Producto</button>
        </form>

        <script>
            // dropdown
            const btn = document.getElementById('userMenuBtn'),
                    menu = document.getElementById('userDropdown');
            btn.onclick = e => {
                e.stopPropagation();
                menu.style.display = menu.style.display === 'block' ? 'none' : 'block';
            };
            document.addEventListener('click', e => {
                if (!menu.contains(e.target) && e.target !== btn)
                    menu.style.display = 'none';
            });
            function cerrarModalExito() {
                document.querySelector('.modal-exito').style.display = 'none';
            }
        </script>
    </body>
</html>
