/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelos;

import java.sql.*;


/**
 *
 * @author tacov
 */
public class Conexion {

    Connection con;

    public Connection getConnection() {
        try {
            // Carga el driver de MySQL (para versiones antiguas; en versiones recientes ya no es estrictamente necesario)
            Class.forName("com.mysql.jdbc.Driver");

            // Cambia la URL con el nombre correcto de tu BD, usuario y contraseña
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/manos_del_peru", "PROYECTO", "");

        } catch (Exception e) {

        }
        return con;
    }
}
