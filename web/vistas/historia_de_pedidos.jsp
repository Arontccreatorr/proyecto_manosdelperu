<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>



<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link href="${pageContext.request.contextPath}/css/encabezado.css" rel="stylesheet" type="text/css"/>
        <link href="${pageContext.request.contextPath}/css/historial.css" rel="stylesheet" type="text/css"/>

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
        <h1 class="h1">Buscar Pedido Pendiente</h1>
        <form method="GET" action="${pageContext.request.contextPath}/controlador_historialpedido" class="form-buscar-pedido">
            <input type="hidden" name="action" value="buscar_pedido">
            <input type="text" name="dni_buscar" placeholder="Ingrese DNI" required>
            <button type="submit">Buscar Pedido</button>
        </form>


        <c:if test="${not empty mensaje}">
            <p class="error">${mensaje}</p>
        </c:if>

        <c:if test="${not empty detalles}">

            <table class="tabla-historial">
                <thead>
                    <tr>
                        <th>Imagen</th>
                        <th>Producto</th>
                        <th>Cantidad</th>
                        <th>Precio Unitario</th>
                        <th>Subtotal</th>
                        <th>Acciones</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="d" items="${detalles}">
                        <tr>
                            <td>
                                <img
                                    src="http://localhost/img/producto/${fn:replace(d.imagenProducto, ' ', '%20')}"
                                    width="80"
                                    alt="${d.nombreProducto}" />

                            </td>
                            <td>${d.nombreProducto}</td>
                            <td>${d.cantidad}</td>
                            <td>S/ <fmt:formatNumber value="${d.precioUnitario}" type="number" minFractionDigits="2" /></td>
                            <td>S/ <fmt:formatNumber value="${d.cantidad * d.precioUnitario}" type="number" minFractionDigits="2" /></td>
                            <td>
                                <form method="get"
                                      action="${pageContext.request.contextPath}/controlador_historialpedido"
                                      class="inline-form">


                                    <input type="hidden" name="action"     value="actualizar_cantidad"/>
                                    <input type="hidden" name="dni_buscar" value="${param.dni_buscar}"/>
                                    <input type="hidden" name="idDetalle"  value="${d.idDetallePedido}"/>


                                    <button type="submit" 
                                            name="operacion" 
                                            value="decrementar" 
                                            class="btn-decrement">−
                                    </button>

                                    <span class="cantidad-actual">${d.cantidad}</span>

                                    <button type="submit" 
                                            name="operacion" 
                                            value="incrementar" 
                                            class="btn-increment">+
                                    </button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                    <tr style="font-weight: bold;">
                        <td colspan="4">TOTAL</td>
                        <td colspan="2">S/ <fmt:formatNumber value="${total}" type="number" minFractionDigits="2" /></td>
                    </tr>
                </tbody>
            </table>
        </c:if>


        <aside>
            <form method="GET" action="${pageContext.request.contextPath}/controlador_finalizar_compra">
                <input type="hidden" name="id_pedido" value="${pedido.idPedido}" />
                <button type="submit" class="comprar">Finalizar Compra</button>
            </form>
        </aside>




        <script>
            document.querySelectorAll('.btn-decrement').forEach(btn => {
                btn.addEventListener('click', e => {
                    // Por ejemplo: confirmar borrado si cantidad = 1
                    const cantidad = parseInt(btn.closest('form').querySelector('.cantidad-actual').textContent, 10);
                    if (cantidad === 1 && !confirm('¿Quieres eliminar este producto del pedido?')) {
                        e.preventDefault();  // cancela el submit
                    }
                });
            });
        </script>
    </body>
</html>
