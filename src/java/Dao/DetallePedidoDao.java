package Dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import modelos.Conexion;
import modelos.Detallepedido;

public class DetallePedidoDao {

    Conexion cn = new Conexion();

    public void insertarDetallePedido(Detallepedido detalle) throws SQLException {
        String sql
                = "INSERT INTO detallepedido "
                + "(id_pedido, id_producto, cantidad, precio_unitario) "
                + "VALUES (?, ?, ?, ?)";
        try (Connection con = cn.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, detalle.getIdPedido());
            ps.setInt(2, detalle.getIdProducto());
            ps.setInt(3, detalle.getCantidad());
            ps.setDouble(4, detalle.getPrecioUnitario());
            if (ps.executeUpdate() == 0) {
                throw new SQLException("No se insertó detalle de pedido");
            }
        }
    }

    public List<Detallepedido> listarPorPedido(int idPedido) throws SQLException {

        String sql
                = "SELECT d.id_detalle_pedido, d.id_pedido, d.id_producto, d.cantidad, d.precio_unitario, "
                + " p.nombre AS nombreProducto, p.imagen AS imagenProducto "
                + "FROM detallepedido d "
                + "LEFT JOIN producto p ON d.id_producto = p.id_producto "
                + // MEJOR usar LEFT JOIN
                "WHERE d.id_pedido = ?";

        try (Connection c = cn.getConnection(); 
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, idPedido);
            ResultSet rs = ps.executeQuery();
            List<Detallepedido> lista = new ArrayList<>();
            while (rs.next()) {
                Detallepedido d = new Detallepedido();
                d.setIdDetallePedido(rs.getInt("id_detalle_pedido"));
                d.setIdPedido(rs.getInt("id_pedido"));
                d.setIdProducto(rs.getInt("id_producto"));
                d.setCantidad(rs.getInt("cantidad"));
                d.setPrecioUnitario(rs.getDouble("precio_unitario"));
                d.setNombreProducto(rs.getString("nombreProducto"));
                d.setImagenProducto(rs.getString("imagenProducto"));
                lista.add(d);
            }
            return lista;
        }

    }

 
    public int obtenerCantidad(int idDetalle) throws SQLException {
        String sql = "SELECT cantidad FROM detallepedido WHERE id_detalle_pedido = ?";
        try (Connection con = cn.getConnection(); 
             PreparedStatement ps = con.prepareStatement(sql)) {
              ps.setInt(1, idDetalle);
                ResultSet rs= ps.executeQuery();
                if (!rs.next()) throw new SQLException("Detalle no encontrado");
            return rs.getInt("cantidad");
            
        }
    }

    
    public void actualizarCantidad(int idDetalle, int nuevaCantidad) throws SQLException {
        if (nuevaCantidad <= 0) {
        eliminarDetalle(idDetalle);
        return;
    }
        String sql = "UPDATE detallepedido SET cantidad = ? WHERE id_detalle_pedido = ?";
        try (Connection con = cn.getConnection(); 
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, nuevaCantidad);
            ps.setInt(2, idDetalle);
            if (ps.executeUpdate() == 0) {
                throw new SQLException("No se pudo actualizar detalle " + idDetalle);
            }
        }
    }

    public void eliminarDetalle(int idDetalle) throws SQLException {
        String sql = "DELETE FROM detallepedido WHERE id_detalle_pedido = ?";
        try (Connection con = cn.getConnection(); 
              PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idDetalle);
            if (ps.executeUpdate() == 0) {
                throw new SQLException("No se pudo eliminar detalle " + idDetalle);
            }
        }
    }
}
