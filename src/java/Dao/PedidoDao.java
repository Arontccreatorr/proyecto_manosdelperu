
package Dao;

import java.sql.*;
import modelos.Conexion;
import modelos.Pedidos;


public class PedidoDao {
    
       Conexion cn = new Conexion();

    public int insertarPedido(Pedidos pedido) throws SQLException {
        String sql = 
          "INSERT INTO pedido (dni_cliente, estado, total) VALUES (?, ?, ?)";
        try (Connection con = cn.getConnection();
             PreparedStatement ps = con.prepareStatement(
                 sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, pedido.getDniCliente());
            ps.setString(2, pedido.getEstado());
            ps.setDouble(3, pedido.getTotal());

            int filas = ps.executeUpdate();
            if (filas == 0) 
                throw new SQLException("No se insertó ningún pedido");

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
                else throw new SQLException("No se generó id de pedido");
            }
        }
    }
    
    public int obtenerPedidoPendientePorCliente(int dniCliente) {
    int id = -1;
    String sql = "SELECT id_pedido FROM pedido WHERE dni_cliente = ? AND estado = 'pendiente' LIMIT 1";
    try (Connection con = new Conexion().getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setInt(1, dniCliente);
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                id = rs.getInt("id_pedido");
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return id;
}
    
   public Pedidos buscarPorId(int idPedido) {
        String sql = "SELECT id_pedido, fecha_pedido, dni_cliente, estado, total FROM pedido WHERE id_pedido = ?";
        try (Connection con = cn.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idPedido);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Pedidos pedido = new Pedidos();
                    pedido.setIdPedido(rs.getInt("id_pedido"));
                    pedido.setFechaPedido(rs.getString("fecha_pedido"));
                    pedido.setDniCliente(rs.getInt("dni_cliente"));
                    pedido.setEstado(rs.getString("estado"));
                    pedido.setTotal(rs.getDouble("total"));
                    return pedido;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
public void actualizarTotal(int idPedido, double nuevoTotal) throws SQLException {
        if (nuevoTotal <= 0) {
            eliminarPedido(idPedido);
            return;
        }
        String sql = "UPDATE pedido SET total = ? WHERE id_pedido = ?";
        try (Connection con = cn.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDouble(1, nuevoTotal);
            ps.setInt(2, idPedido);
            if (ps.executeUpdate() == 0) {
                throw new SQLException("No se pudo actualizar total del pedido " + idPedido);
            }
        }
    }

    /**
     * Borra el pedido completo por su ID.
     */
    public void eliminarPedido(int idPedido) throws SQLException {
        String sql = "DELETE FROM pedido WHERE id_pedido = ?";
        try (Connection con = cn.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idPedido);
            if (ps.executeUpdate() == 0) {
                throw new SQLException("No se pudo eliminar pedido " + idPedido);
            }
        }
    }
    
     /** Cambia el estado del pedido (por ejemplo a "pagado") */
    public void actualizarEstado(int idPedido, String nuevoEstado) throws SQLException {
        String sql = "UPDATE pedido SET estado = ? WHERE id_pedido = ?";
        try (Connection con = cn.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nuevoEstado);
            ps.setInt(2, idPedido);
            ps.executeUpdate();
        }
    }
}
