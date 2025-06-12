package controladores;

import Dao.BoletaDao;
import Dao.DetalleBoletaDao;
import Dao.DetallePedidoDao;
import Dao.PedidoDao;
import Dao.clienteDao;
import Dao.productoDao;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelos.Boletas;
import modelos.Clientes;
import modelos.Detalleboleta;
import modelos.Detallepedido;
import modelos.Pedidos;

@WebServlet("/controlador_finalizar_compra")
public class controlador_finalizar_compra extends HttpServlet {

    PedidoDao pedidoDao = new PedidoDao();
    DetallePedidoDao detalleDao = new DetallePedidoDao();
    final clienteDao clienteDao = new clienteDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String idP = req.getParameter("id_pedido");
        if (idP == null) {
            resp.sendRedirect(req.getContextPath() + "/error.jsp");
            return;
        }
        try {
            int idPedido = Integer.parseInt(idP);
            Pedidos pedido = pedidoDao.buscarPorId(idPedido);
            List<Detallepedido> productos = detalleDao.listarPorPedido(idPedido);

            req.setAttribute("pedido", pedido);
            req.setAttribute("productos", productos);
            // **No** pongo cliente ni datosConfirmados aquí
            req.getRequestDispatcher("/vistas/finalizar-compra.jsp")
                    .forward(req, resp);
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String action = req.getParameter("action");
        int idPedido = Integer.parseInt(req.getParameter("id_pedido"));

        try {

            Pedidos pedido = pedidoDao.buscarPorId(idPedido);
            List<Detallepedido> productos = detalleDao.listarPorPedido(idPedido);
            req.setAttribute("pedido", pedido);
            req.setAttribute("productos", productos);

            switch (action) {
                case "validar_dni":
                    int dni = Integer.parseInt(req.getParameter("dni"));
                    Clientes c = clienteDao.buscarPorDni(dni);
                    req.setAttribute("cliente", c);
                    req.getRequestDispatcher("/vistas/finalizar-compra.jsp")
                            .forward(req, resp);
                    return;   // <- importante

                case "confirmar_datos":

                    int dniCliente = Integer.parseInt(req.getParameter("dni"));
                    Clientes cli = new Clientes();
                    cli.setDniCliente(dniCliente);
                    cli.setNombres(req.getParameter("nombres"));
                    cli.setApellidos(req.getParameter("apellidos"));
                    cli.setCorreo(req.getParameter("correo"));
                    cli.setDireccion(req.getParameter("direccion"));
                    cli.setNumeroContacto(req.getParameter("numero_contacto"));
                    try {
                        boolean ok = clienteDao.actualizarCliente(cli);
                        if (ok) {

                            req.setAttribute("mensajeOk", "Datos guardados correctamente");
                        } else {
                            req.setAttribute("error", "No se pudo actualizar los datos del cliente.");
                        }
                    } catch (SQLException ex) {
                        throw new ServletException("Error al actualizar cliente", ex);
                    }
                    req.setAttribute("cliente", cli);
                    req.setAttribute("datosConfirmados", true);
                    req.getRequestDispatcher("/vistas/finalizar-compra.jsp").forward(req, resp);
                    return;

                case "procesar_pago":
                    // 1) Lee el DNI (¡no lo tenías aquí!)
                    int dniC = Integer.parseInt(req.getParameter("dni"));

                    // 2) Genera número provisional (se sobreescribirá con el ID)
                    Boletas boleta = new Boletas();
                    boleta.setNumeroBoleta("");        // placeholder
                    boleta.setIdPedido(idPedido);
                    boleta.setDniCliente(dniC);
                    boleta.setTotal(pedido.getTotal());

                    // 3) Inserta boleta y recupera ID
                    BoletaDao bDao = new BoletaDao();
                    int idBoleta = bDao.insertarBoleta(boleta);

                    // 4) Usa ese ID como número de boleta
                    String numeroBoleta = "BOL-" + idBoleta;
                    boleta.setNumeroBoleta(numeroBoleta);
                    // **Importante**: guarda el numero en la BD
                    bDao.actualizarNumeroBoleta(idBoleta, numeroBoleta);

                    // recargo el bean completo para tener la fecha
                    boleta = bDao.buscarPorId(idBoleta);

                    // 5) Inserta detalles de boleta
                    DetalleBoletaDao dbDao = new DetalleBoletaDao();
                      productoDao productoDao = new productoDao();
                    List<Detalleboleta> detallesBoleta = new ArrayList<>();
                    for (Detallepedido dp : productos) {
                        productoDao.reducirStock(dp.getIdProducto(), dp.getCantidad());
                        Detalleboleta db = new Detalleboleta();
                        db.setIdBoleta(idBoleta);
                        db.setIdProducto(dp.getIdProducto());
                        db.setCantidad(dp.getCantidad());
                        db.setPrecioUnitario(dp.getPrecioUnitario());
                        db.setMonto(dp.getPrecioUnitario() * dp.getCantidad());
                        dbDao.insertarDetalle(db);
                        detallesBoleta.add(db);
                    }

                    // 6) Pone atributos para la JSP
                    pedidoDao.actualizarEstado(idPedido, "pagado");

                    // 7) Forward a la vista de boleta
                    req.setAttribute("mensajeOk", "¡Compras pagadas! Boleta: " + numeroBoleta);
                    req.setAttribute("boleta", boleta);
                    req.setAttribute("detallesBoleta", detallesBoleta);

                    req.getRequestDispatcher("/vistas/Boleta.jsp")
                            .forward(req, resp);
                    return;

                default:
                    resp.sendError(HttpServletResponse.SC_BAD_REQUEST,
                            "Acción desconocida: " + action);
                    return;
            }

        } catch (SQLException e) {
            throw new ServletException(e);
        }

    }
}
