/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dao;

import modelos.Clientes;
import modelos.Conexion;
import java.sql.*;

/**
 *
 * @author tacov
 */
public class clienteDao {

      Conexion conexion = new Conexion();

    public boolean insertarCliente(Clientes cliente) {
        String sql = "INSERT INTO cliente (dni_cliente) VALUES (?)";
        try (Connection con = conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, cliente.getDniCliente());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean existeCliente(int dni) {
        String sql = "SELECT dni_cliente FROM cliente WHERE dni_cliente = ?";
        try (Connection con = conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, dni);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
   public Clientes buscarPorDni(int dniCliente) {
        String sql = "SELECT * FROM cliente WHERE dni_cliente = ?";
        try (Connection con = conexion.getConnection();  // Corregido aquí
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, dniCliente);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Clientes c = new Clientes(); // Asegúrate que la clase modelo sea Clientes
                c.setDniCliente(rs.getInt("dni_cliente"));
                c.setNombres(rs.getString("nombres"));
                c.setApellidos(rs.getString("apellidos"));
                c.setCorreo(rs.getString("correo"));
                c.setDireccion(rs.getString("direccion"));
                c.setNumeroContacto(rs.getString("numero_contacto"));
                return c;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

 public boolean actualizarCliente(Clientes cliente) throws SQLException {
        String sql = "UPDATE cliente "
                   + "SET nombres = ?, apellidos = ?, correo = ?, "
                   + "    direccion = ?, numero_contacto = ? "
                   + "WHERE dni_cliente = ?";
        try (Connection con = conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, cliente.getNombres());
            ps.setString(2, cliente.getApellidos());
            ps.setString(3, cliente.getCorreo());
            ps.setString(4, cliente.getDireccion());
            ps.setString(5, cliente.getNumeroContacto());
            ps.setInt(6,    cliente.getDniCliente());

            return ps.executeUpdate() > 0;
        }
    }
}
