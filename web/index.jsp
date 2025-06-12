<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
    <head>
        <title>TODO supply a title</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link href="css/encabezado.css" rel="stylesheet" type="text/css"/>
        <link href="css/inicio.css" rel="stylesheet" type="text/css"/>
    </head>
    <body>
        <div class="encabezado">
            <div class="logo-nombre">
                <a href="#"> <img src="${pageContext.request.contextPath}/img/logos/logo.png" alt="logo manos del peru" class="logo"> </a>
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
                <a class="carro" href="${pageContext.request.contextPath}/vistas/historia_de_pedidos.jsp"> <img src="${pageContext.request.contextPath}/img/logos/carrito.png" alt="icono de carro" class="carro"> </a>
            </div>
        </div>


        <div class="titulo-seccion">PRODUCTOS DESTACADOS</div>

        <div class="grid-productos">
            <c:forEach var="row" items="${productos}">
                <div class="producto">
                    <img src="http://localhost/img/producto/${row.imagenprodcuto}" alt="Cerámica" />
                    <div class="info">
                        <span>AUTOR: ${row.autor}</span>
                        <span>PRECIO: S/ ${row.precio}</span>
                        <span>${row.nombre}</span>
                    </div>

                    <div class="botones">
                        <button type="button">DETALLE</button>
                        <button type="button" class="btn-comprar" onclick="abrirModal(${row.idProducto})">
                            COMPRAR
                        </button>

                    </div>

                </div>
            </c:forEach>
        </div>

        <!-- Modal -->
        <div id="modal" class="modal" style="display:none;">
            <form id="pedidoForm" method="post" action="${pageContext.request.contextPath}/ingresar_pedido_pendiente">
                <h2 id="modal-nombre"></h2>

                <p>Autor: <span id="modal-autor"></span></p>
                <p>Precio: S/ <span id="modal-precio"></span></p>
                <p>Stock: <span id="modal-stock"></span></p>
                <img id="modal-imagen" src="" alt="Imagen producto" style="max-width:200px;">

                <!-- Inputs ocultos que llenaremos desde JS -->
                <input type="hidden" name="id_producto" id="input-id_producto">
                <input type="hidden" name="precio"       id="input-precio">

                <!-- Inputs que el usuario debe rellenar -->
                <div>
                    <label for="input-dni">DNI:</label>
                    <input type="text" name="dni" id="input-dni" required>
                </div>
                <div>
                    <label for="input-cantidad">Cantidad:</label>
                    <input type="number" name="cantidad" id="input-cantidad" min="1" required>
                </div>

                <button type="submit">Continuar</button>
                <button type="button" onclick="cerrarModal()">Cerrar</button>
            </form>
        </div>

    </body>
    <script>
        function abrirModal(idProducto) {
            // 1) Construyo la URL concatenando JS y JSP EL
            const url = '${pageContext.request.contextPath}/controlador_mostrarproducto'
                    + '?action=buscar&id='
                    + encodeURIComponent(idProducto);
            console.log(">>> Abrir modal, URL fetch:", url);

            fetch(url)
                    .then(resp => {
                        console.log(">>> Response status:", resp.status);
                        if (!resp.ok)
                            throw new Error('Producto no encontrado');
                        return resp.json();
                    })
                    .then(prod => {
                        console.log(">>> Datos recibidos:", prod);
                        // Rellenar campos visuales
                        document.getElementById('modal-nombre').textContent = prod.nombre;
                        document.getElementById('modal-autor').textContent = prod.autor;

                        // Precio
                        document.getElementById('modal-precio').textContent = prod.precio;

                        // Stock
                        document.getElementById('modal-stock').textContent = prod.stock;

                        // Imagen
                        const base = 'http://localhost/img/producto/';
                        document.getElementById('modal-imagen').src = base + prod.imagenprodcuto;

                        // Ocultos
                        document.getElementById('input-id_producto').value = prod.idProducto;
                        document.getElementById('input-precio').value = prod.precio;
                        document.getElementById('modal').style.display = 'block';
                    })
                    .catch(err => {
                        console.error(err);
                        alert(err.message);
                    });
        }

        function cerrarModal() {
            document.getElementById('modal').style.display = 'none';
        }

        document.addEventListener('DOMContentLoaded', () => {
            const form = document.querySelector('form'); // o si tu form tiene un id, úsalo: #form-pedido
            form.addEventListener('submit', function (e) {
                e.preventDefault();  // anula el envío tradicional

                // construyo los parámetros a partir de los campos ocultos / visibles
                const data = new URLSearchParams(new FormData(form));
                const url = `${pageContext.request.contextPath}/ingresar_pedido_pendiente`;

                fetch(url, {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'
                    },
                    body: data.toString()
                })
                        .then(resp => resp.text())
                        .then(text => {
                            if (text.trim() === 'OK') {
                                alert('Registro de pedido exitoso. Estado: pendiente a pagar.');
                                // opcional: cerrar modal o limpiar campos
                                cerrarModal();
                            } else {
                                throw new Error(text);
                            }
                        })
                        .catch(err => {
                            console.error(err);
                            alert('Error al registrar pedido: ' + err.message);
                        });
            });
        });
    </script>


</html>
