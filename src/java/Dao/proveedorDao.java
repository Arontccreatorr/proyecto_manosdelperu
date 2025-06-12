/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dao;

import modelos.Proveedores;
import java.sql.*; 
import modelos.Conexion;

/**
 *
 * @author tacov
 */
public class proveedorDao {
       Conexion cn = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    /* creador por jose borjorquez*/
    public Proveedores buscarPorCorreoYClave(String correo, String clave) throws SQLException {
        String sql = "SELECT * FROM proveedor WHERE correo = ? AND contraseña = ?";
        try (Connection con = cn.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setString(1, correo);
        ps.setString(2, clave);
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                Proveedores p = new Proveedores();
                p.setId_proveedor(rs.getInt("id_proveedor"));
                p.setDni(rs.getString("dni"));
                p.setNombres(rs.getString("nombres"));
                p.setApellidos(rs.getString("apellidos"));
                p.setCorreo(rs.getString("correo"));
                p.setContraseña(rs.getString("contraseña"));
                p.setDireccion(rs.getString("direccion"));
                p.setNumero_contacto(rs.getString("numero_contacto"));
                p.setImg_provedor( rs.getString("img_provedor") );
                return p;
            }
        }
    }
    return null;
}
}
