<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Boleta</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/boleta.css">

    </head>
    <body>


        <div class="boleta-box">
            <div class="boleta-header">
                <c:if test="${not empty mensajeOk}">
                    <script>alert('${mensajeOk}');</script>
                </c:if>
                <h1>Boleta N° ${boleta.numeroBoleta}</h1>
            </div>

            <div class="boleta-info">
                <p>Fecha: 
                    <fmt:formatDate value="${boleta.fechaEmision}" pattern="yyyy-MM-dd HH:mm:ss"/>
                </p>
                <p>DNI Cliente: ${boleta.dniCliente}</p>
            </div>

            <table class="detalle-productos">
                <thead>
                    <tr>
                        <th>Producto</th>
                        <th>Cantidad</th>
                        <th>Precio Unitario</th>
                        <th>Subtotal</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="p" items="${productos}">
                        <tr>
                            <td>${p.nombreProducto}</td>
                            <td>${p.cantidad}</td>
                            <td>S/ <fmt:formatNumber value="${p.precioUnitario}" minFractionDigits="2"/></td>
                            <td>S/ <fmt:formatNumber value="${p.cantidad * p.precioUnitario}" minFractionDigits="2"/></td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>

            <div class="total-final">
                Total: S/ <fmt:formatNumber value="${boleta.total}" minFractionDigits="2"/>
            </div>

            <button class="print-btn" onclick="window.print()">Imprimir boleta</button>
            <c:url var="urlInicio" value="/controlador_mostrarproducto"/>
            <button
                class="back-btn"
                onclick="window.location.href = '${urlInicio}'">
                Inicio
            </button>
        </div>
    </body>
</html>
