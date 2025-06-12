
package Dao;

import java.sql.*;  // tu clase modelo Boleta
import modelos.Boletas;
import modelos.Conexion;


public class BoletaDao {
    Conexion cn = new Conexion();
    /** Inserta la boleta y devuelve el id generado */
    public int insertarBoleta(Boletas boleta) throws SQLException {
        String sql = "INSERT INTO boleta (numero_boleta, id_pedido, dni_cliente, total) "
                   + "VALUES (?, ?, ?, ?)";
       try (Connection con = cn.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, "");                          // placeholder
            ps.setInt(   2, boleta.getIdPedido());
            ps.setInt(   3, boleta.getDniCliente());
            ps.setDouble(4, boleta.getTotal());
            if (ps.executeUpdate() == 0) 
                throw new SQLException("No se insertó la boleta");

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    int idBoleta = rs.getInt(1);
                    boleta.setIdBoleta(idBoleta);
                    return idBoleta;
                }
                throw new SQLException("No se generó ID de boleta");
            }
        }
    }

        
        public void actualizarNumeroBoleta(int idBoleta, String numero) throws SQLException {
        String sql = "UPDATE boleta SET numero_boleta = ? WHERE id_boleta = ?";
        try (Connection con = cn.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, numero);
            ps.setInt(2, idBoleta);
            ps.executeUpdate();
        }
    }
        
        public Boletas buscarPorId(int idBoleta) throws SQLException {
    String sql = """
        SELECT id_boleta, numero_boleta, fecha_emision, dni_cliente, total
          FROM boleta
         WHERE id_boleta = ?
    """;
    try (Connection con = cn.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setInt(1, idBoleta);
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                Boletas b = new Boletas();
                b.setIdBoleta(rs.getInt("id_boleta"));
                b.setNumeroBoleta(rs.getString("numero_boleta"));
                b.setFechaEmision(rs.getTimestamp("fecha_emision"));
                b.setDniCliente(rs.getInt("dni_cliente"));
                b.setTotal(rs.getDouble("total"));
                return b;
            }
            throw new SQLException("No existe boleta con id " + idBoleta);
        }
    }
}

}
    
    
    
