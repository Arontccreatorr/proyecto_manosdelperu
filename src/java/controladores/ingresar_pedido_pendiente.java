package controladores;


import Dao.DetallePedidoDao;
import Dao.PedidoDao;
import Dao.clienteDao;
import java.sql.*;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelos.Clientes;
import modelos.Conexion;
import modelos.Detallepedido;
import modelos.Pedidos;

@WebServlet("/ingresar_pedido_pendiente")
public class ingresar_pedido_pendiente extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/plain;charset=UTF-8");
        PrintWriter out = response.getWriter();

        int dni = Integer.parseInt(request.getParameter("dni"));
        int idProducto = Integer.parseInt(request.getParameter("id_producto"));
        int cantidad = Integer.parseInt(request.getParameter("cantidad"));
        double precio = Double.parseDouble(request.getParameter("precio"));
        double total = precio * cantidad;  // o como quieras calcularlo

        Conexion cn = new Conexion();
        try (Connection conn = cn.getConnection()) {
           
            conn.setAutoCommit(false);

        
            clienteDao cdao = new clienteDao();
            if (!cdao.existeCliente(dni)) {
                Clientes cli = new Clientes();
                cli.setDniCliente(dni);
                if (!cdao.insertarCliente(cli)) {
                    throw new SQLException("No se insertó cliente");
                }
            }

         
            PedidoDao pdao = new PedidoDao();
            int idPedido = pdao.obtenerPedidoPendientePorCliente(dni);  

            if (idPedido == -1) {
        
                Pedidos pedido = new Pedidos();
                pedido.setDniCliente(dni);
                pedido.setEstado("pendiente");
                pedido.setTotal(0);  
                idPedido = pdao.insertarPedido(pedido);
            }

            // 4. Detalle
            Detallepedido detalle = new Detallepedido();
            detalle.setIdPedido(idPedido);
            detalle.setIdProducto(idProducto);
            detalle.setCantidad(cantidad);
            detalle.setPrecioUnitario(precio);
            DetallePedidoDao dpdao = new DetallePedidoDao();
            dpdao.insertarDetallePedido(detalle);

       
            conn.commit();
            response.getWriter().write("OK");

        } catch (SQLException e) {
        
            try {
                cn.getConnection().rollback();
            } catch (Exception ex) {
            }
            e.printStackTrace();
            out.print("Error al registrar pedido: " + e.getMessage());
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
