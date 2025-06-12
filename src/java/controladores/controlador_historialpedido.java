
package controladores;

import Dao.DetallePedidoDao;
import Dao.PedidoDao;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelos.Detallepedido;
import modelos.Pedidos;
import java.sql.SQLException;



@WebServlet("/controlador_historialpedido")
public class controlador_historialpedido extends HttpServlet {
    PedidoDao pedidoDao = new PedidoDao();
    DetallePedidoDao dao     = new DetallePedidoDao();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if (action == null) action = "buscar_pedido";  // acción predeterminada

        switch (action) {
            
            case "buscar_pedido":
                buscarPedidoPorDni(request, response);
                break;

            case "actualizar_cantidad":
                      handleActualizarCantidad(request, response);
                break;

            default:
                request.setAttribute("mensaje", "Acción no válida.");
                request.getRequestDispatcher("/vistas/historia_de_pedidos.jsp")
                       .forward(request, response);
                break;
        }
    }

    private void buscarPedidoPorDni(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String dniS = request.getParameter("dni_buscar");
       if (dniS==null||dniS.isEmpty()) {
      request.setAttribute("mensaje","Debe ingresar un DNI");
    } else {
         try {
                int dni = Integer.parseInt(dniS);
                int idPed = pedidoDao.obtenerPedidoPendientePorCliente(dni);
                if (idPed == -1) {
                    request.setAttribute("mensaje", "No hay pedido pendiente para DNI " + dni);
                } else {
                    Pedidos ped = pedidoDao.buscarPorId(idPed);
                    List<Detallepedido> det = dao.listarPorPedido(idPed);
                    double total = det.stream()
                                      .mapToDouble(d -> d.getCantidad() * d.getPrecioUnitario())
                                      .sum();
                    request.setAttribute("pedido",   ped);
                    request.setAttribute("detalles", det);
                    request.setAttribute("total",    total);
                }
            } catch (NumberFormatException | SQLException e) {
                request.setAttribute("mensaje", "Error al buscar pedido: " + e.getMessage());
            }
        }
        request.getRequestDispatcher("/vistas/historia_de_pedidos.jsp")
           .forward(request, response);
    }
    
    

private void handleActualizarCantidad(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {
    String idDet    = req.getParameter("idDetalle");
    String oper     = req.getParameter("operacion");       
    String dniParam = req.getParameter("dni_buscar");

     if (idDet == null || oper == null || dniParam == null) {
        // Parámetros faltantes
        req.setAttribute("mensaje", "Faltan parámetros para actualizar cantidad.");
        req.getRequestDispatcher("/vistas/historia_de_pedidos.jsp").forward(req, resp);
        return;
    }

    try {
        int id = Integer.parseInt(idDet);
        int qtyActual = dao.obtenerCantidad(id);
        int nuevaQty;

        switch (oper) {
            case "incrementar":
                nuevaQty = qtyActual + 1;
                break;
            case "decrementar":
                nuevaQty = qtyActual - 1;
                break;
            default:
                // Si más adelante añades un input directo de cantidad:
                nuevaQty = Integer.parseInt(req.getParameter("nuevaCantidad"));
        }

        // Llamas actualizarCantidad, que internamente borrará si nuevaQty <= 0
        dao.actualizarCantidad(id, nuevaQty);

        int dni = Integer.parseInt(dniParam);
        int idPedido = pedidoDao.obtenerPedidoPendientePorCliente(dni);

        if (idPedido != -1) {
            // 3) Vuelve a leer todos los detalles vigentes
            List<Detallepedido> detalles = dao.listarPorPedido(idPedido);

            // 4) Recalcula el total
            double total = detalles.stream()
                                   .mapToDouble(d -> d.getCantidad() * d.getPrecioUnitario())
                                   .sum();

            // 5) Actualiza el pedido: si total<=0, se borrará en el DAO
            pedidoDao.actualizarTotal(idPedido, total);
        }

        // 6) Redirige para refrescar la vista con el pedido actualizado
        resp.sendRedirect(req.getContextPath()
            + "/controlador_historialpedido?action=buscar_pedido&dni_buscar=" + dniParam);

    } catch (NumberFormatException | SQLException e) {
        e.printStackTrace();
        req.setAttribute("mensaje", "Error al actualizar cantidad: " + e.getMessage());
        req.getRequestDispatcher("/vistas/historia_de_pedidos.jsp")
           .forward(req, resp);
    }
}
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}