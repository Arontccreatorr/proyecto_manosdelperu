/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Test;

import Dao.DetallePedidoDao;
import Dao.clienteDao;
import modelos.Clientes;

/**
 *
 * @author tacov
 */
public class TestDetallePedidoDao {
  public static void main(String[] args) throws Exception {
    /*
      DetallePedidoDao dao = new DetallePedidoDao();
    int pruebaId = 34;           // Pon uno que exista en tu DB
    int qty = dao.obtenerCantidad(pruebaId);
    System.out.println("Cantidad original = " + qty);
    dao.actualizarCantidad(pruebaId, qty + 1);
    System.out.println("Cantidad +1 = " + dao.obtenerCantidad(pruebaId));
    dao.actualizarCantidad(pruebaId, qty);
    System.out.println("Cantidad restaurada = " + dao.obtenerCantidad(pruebaId));
   */
    
    
        int pruebaDni = 70903323;  

     
        clienteDao dao = new clienteDao();
        Clientes cliente = dao.buscarPorDni(pruebaDni);

        
        if (cliente != null) {
            System.out.println("=== Cliente encontrado ===");
            System.out.println("DNI:       " + cliente.getDniCliente());
            System.out.println("Nombres:   " + cliente.getNombres());
            System.out.println("Apellidos: " + cliente.getApellidos());
            System.out.println("Correo:    " + cliente.getCorreo());
            System.out.println("Dirección: " + cliente.getDireccion());
            System.out.println("Celular:   " + cliente.getNumeroContacto());
        } else {
            System.out.println("No se encontró ningún cliente con DNI " + pruebaDni);
        }
    }
    
}
