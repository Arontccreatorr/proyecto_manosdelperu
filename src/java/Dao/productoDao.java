
package Dao;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import modelos.Conexion;
import modelos.Productos;
import Dao.interfaces.mostrar;


public class productoDao implements mostrar<Productos> {

    Conexion cn = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    /*mostrar productos*/
    @Override
    public List<Productos> MostrarProducto() {
        List<Productos> lista = new ArrayList<>();
        String sql = "SELECT * FROM producto";

        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Productos p = new Productos();
                p.setIdProducto(rs.getInt("id_producto"));
                p.setIdProveedor(rs.getInt("id_proveedor"));
                p.setNombre(rs.getString("nombre"));
                p.setImagenprodcuto(rs.getString("imagen"));
                p.setDescripcion(rs.getString("descripcion"));
                p.setPrecio(rs.getDouble("precio"));
                p.setStock(rs.getInt("stock"));

                lista.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ERROR EN DAO: " + e.getMessage());
            e.printStackTrace();
        }

        return lista;
    }

    public Productos buscarporId(int idProducto) throws SQLException {
        Productos producto = null;
        String sql = "SELECT p.*, "
                + "       pr.nombres, "
                + "       pr.apellidos "
                + "  FROM producto p "
                + "  JOIN proveedor pr "
                + "    ON p.id_proveedor = pr.id_proveedor "
                + " WHERE p.id_producto = ?";

     
        try (Connection conexion = cn.getConnection(); PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, idProducto);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    producto = new Productos();
                    producto.setIdProducto(rs.getInt("id_producto"));
                    producto.setNombre(rs.getString("nombre"));
                    producto.setPrecio(rs.getDouble("precio"));
                    producto.setStock(rs.getInt("stock"));
                    producto.setImagenprodcuto(rs.getString("imagen"));
                    producto.setIdProveedor(rs.getInt("id_proveedor"));
                    // aquí recogemos el nombre y apellido del proveedor
                    String nombres = rs.getString("nombres");
                    String apellidos = rs.getString("apellidos");
                    producto.setAutor(nombres + " " + apellidos);
                }
            }
            if (producto == null) {
                System.out.println(">>> DAO.buscarporId: no existe producto con id " + idProducto);
            } else {
                System.out.println(">>> DAO.buscarporId: hallado " + producto.getNombre());
            }

        }
    
        return producto;
    }
   public void reducirStock(int idProducto, int cantidad) throws SQLException {
        // 1) Verificar stock actual
        String sel = "SELECT stock FROM producto WHERE id_producto = ?";
        int stockActual;
        try (Connection con = cn.getConnection();
             PreparedStatement ps = con.prepareStatement(sel)) {
            ps.setInt(1, idProducto);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    throw new SQLException("Producto inexistente: " + idProducto);
                }
                stockActual = rs.getInt("stock");
            }
        }

        if (stockActual < cantidad) {
            throw new SQLException("Stock insuficiente para producto " + idProducto);
        }

        // 2) Descontar stock
        String upd = "UPDATE producto SET stock = stock - ? WHERE id_producto = ?";
        try (Connection con = cn.getConnection();
             PreparedStatement ps = con.prepareStatement(upd)) {
            ps.setInt(1, cantidad);
            ps.setInt(2, idProducto);
            if (ps.executeUpdate() == 0) {
                throw new SQLException("No se pudo actualizar stock de producto " + idProducto);
            }
        }
    }
   
   /** Inserta un nuevo producto por jeremy poma */
public int insertarProducto(Productos prod) throws SQLException {
    String sql = "INSERT INTO producto (id_proveedor, nombre, descripcion, precio, stock, imagen) " +
                 "VALUES (?, ?, ?, ?, ?, ?)";
    try (Connection con = cn.getConnection();
          PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
        ps.setInt(1, prod.getIdProveedor());
        ps.setString(2, prod.getNombre());
        ps.setString(3, prod.getDescripcion());
        ps.setDouble(4, prod.getPrecio());
        ps.setInt(5, prod.getStock());
        ps.setString(6, prod.getImagenprodcuto());
       int affected = ps.executeUpdate();
     if (affected == 0) {
            throw new SQLException("No se insertó ningún producto");
        }

        try (ResultSet rs = ps.getGeneratedKeys()) {
            if (rs.next()) {
                return rs.getInt(1);
            } else {
                throw new SQLException("No se generó ID de producto");
            }
        }
    }
}
}