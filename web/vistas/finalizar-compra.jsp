<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <title>Finalizar Compra</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/encabezado.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/final-compra.css">
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
                    <li><a href="vistas/sesion_proveedor.jsp">emprende tu negocio</a></li>
                </ul>
            </nav>
            <div class="icono-carro">
                <a class="carro" href="vistas/historia_de_pedidos.jsp"> <img src="${pageContext.request.contextPath}/img/logos/carrito.png" alt="icono de carro" class="carro"> </a>
            </div>
        </div>

        <c:if test="${not empty mensajeOk}">
            <script type="text/javascript">
                alert('${mensajeOk}');
            </script>
        </c:if>

        <div class="compra">

            <div class="productos">
                <h2>? Detalles del Pedido</h2>
                <c:if test="${not empty productos}">
                    <table>
                        <thead>
                            <tr>
                                <th>Imagen</th>
                                <th>Producto</th>
                                <th>Cantidad</th>
                                <th>Precio</th>
                                <th>Subtotal</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="p" items="${productos}">
                                <tr>
                                    <td>
                                        <img src="http://localhost/img/producto/${fn:replace(p.imagenProducto, ' ', '%20')}" width="80" alt="${p.nombreProducto}"/>
                                    </td>
                                    <td>${p.nombreProducto}</td>
                                    <td>${p.cantidad}</td>
                                    <td>S/ <fmt:formatNumber value="${p.precioUnitario}" minFractionDigits="2"/></td>
                                    <td>S/ <fmt:formatNumber value="${p.cantidad * p.precioUnitario}" minFractionDigits="2"/></td>
                                </tr>
                            </c:forEach>
                            <tr style="font-weight:bold;">
                                <td colspan="4">TOTAL</td>
                                <td>S/ <fmt:formatNumber value="${pedido.total}" minFractionDigits="2"/></td>
                            </tr>
                        </tbody>
                    </table>
                </c:if>
            </div>

            <div class="datos">
                <form method="POST" action="${pageContext.request.contextPath}/controlador_finalizar_compra">
                    <label>Ingrese su DNI:</label>
                    <input type="hidden" name="action"   value="validar_dni"/>
                    <input type="hidden" name="id_pedido" value="${pedido.idPedido}"/>
                    <input type="text" name="dni" value="${fn:escapeXml(param.dni)}" required/>
                    <button type="submit">Validar</button>
                </form>


                <c:if test="${not empty cliente and empty datosConfirmados}">
                    <h3>Por favor confirma tus datos</h3>
                    <c:if test="${not empty error}">
                        <p class="error">${error}</p>
                    </c:if>
                    <form method="post"
                          action="${pageContext.request.contextPath}/controlador_finalizar_compra">
                        <input type="hidden" name="action"     value="confirmar_datos"/>
                        <input type="hidden" name="id_pedido"  value="${pedido.idPedido}"/>
                        <!-- Pasamos el DNI real -->
                        <input type="hidden" name="dni"       value="${fn:escapeXml(param.dni)}"/>
                        <!-- Lo mostramos también -->
                        <label>DNI:</label>
                        <input type="text"
                               name="dniVisible"
                               value="${fn:escapeXml(param.dni)}"
                               readonly/>
                        <label>Nombres:</label>
                        <input name="nombres"   value="${cliente.nombres}" required/>

                        <label>Apellidos:</label>
                        <input name="apellidos" value="${cliente.apellidos}" required/>

                        <label>Correo:</label>
                        <input name="correo"    value="${cliente.correo}" required/>

                        <label>Dirección:</label>
                        <input name="direccion" value="${cliente.direccion}" required/>

                        <label>Celular:</label>
                        <input name="numero_contacto"
                               value="${cliente.numeroContacto}"
                               required/>

                        <button type="submit">Confirmar Datos</button>
                    </form>
                </c:if>


                <c:if test="${not empty datosConfirmados}">
                    <h3>Procesar Pago</h3>
                    <form method="post"
                          action="${pageContext.request.contextPath}/controlador_finalizar_compra">
                        <input type="hidden" name="action"    value="procesar_pago"/>
                        <input type="hidden" name="id_pedido" value="${pedido.idPedido}"/>
                          <!-- AÑADO el DNI confirmado al form pago -->
                          <input type="hidden" name="dni"       value="${cliente.dniCliente}"/>

                        <label>Número de Tarjeta:</label>
                        <input type="text"  name="tarjeta" required/>

                        <label>Fecha de Vencimiento:</label>
                        <input type="month" name="fecha_vencimiento" required/>

                        <label>CVV:</label>
                        <input type="text"  name="cvv" required/>

                        <button type="submit">Pagar</button>
                    </form>
                </c:if>

            </div>
        </div>
    </body>
</html>