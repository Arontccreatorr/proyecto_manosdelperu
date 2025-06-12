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
    public boolean insertarProveedor(Proveedores p) throws SQLException {
    String sql = "INSERT INTO proveedor (dni, img_provedor, nombres, apellidos, correo, contraseña, direccion, numero_contacto) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    try (Connection con = cn.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setString(1, p.getDni());
        ps.setString(2, p.getImg_provedor());
        ps.setString(3, p.getNombres());
        ps.setString(4, p.getApellidos());
        ps.setString(5, p.getCorreo());
        ps.setString(6, p.getContraseña()); // Aquí puedes encriptar antes de guardar (usa BCrypt)
        ps.setString(7, p.getDireccion());
        ps.setString(8, p.getNumero_contacto());
        return ps.executeUpdate() > 0;
    }
    }
}
    
