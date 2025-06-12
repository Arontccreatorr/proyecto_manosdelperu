
package Dao;

import modelos.Conexion;
import java.sql.*;
import modelos.Detalleboleta;


public class DetalleBoletaDao {
     Conexion cn = new Conexion();

    /** Inserta una línea de boleta */
    public void insertarDetalle(Detalleboleta det) throws SQLException {
        String sql = "INSERT INTO detalleboleta "
                   + "(id_boleta, id_producto, cantidad, precio_unitario, monto) "
                   + "VALUES (?, ?, ?, ?, ?)";
        try (Connection con = cn.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt   (1, det.getIdBoleta());
            ps.setInt   (2, det.getIdProducto());
            ps.setInt   (3, det.getCantidad());
            ps.setDouble(4, det.getPrecioUnitario());
            ps.setDouble(5, det.getMonto());  // cantidad * precio_unitario

            if (ps.executeUpdate() == 0) {
                throw new SQLException("No se insertó detalle de boleta");
            }
        }
    }
}

