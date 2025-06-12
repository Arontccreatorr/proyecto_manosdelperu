/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelos;

import java.util.Date;

/**
 *
 * @author tacov
 */
public class Boletas {
     private int idBoleta;
    private String numeroBoleta;
    private Date fechaEmision;
    private int idPedido;
    private int dniCliente;
    private double total;

     public Boletas() {}

    public Boletas(int idBoleta, String numeroBoleta, Date fechaEmision, int idPedido, int dniCliente, double total) {
        this.idBoleta = idBoleta;
        this.numeroBoleta = numeroBoleta;
        this.fechaEmision = fechaEmision;
        this.idPedido = idPedido;
        this.dniCliente = dniCliente;
        this.total = total;
    }

    public int getIdBoleta() {
        return idBoleta;
    }

    public void setIdBoleta(int idBoleta) {
        this.idBoleta = idBoleta;
    }

    public String getNumeroBoleta() {
        return numeroBoleta;
    }

    public void setNumeroBoleta(String numeroBoleta) {
        this.numeroBoleta = numeroBoleta;
    }

    public Date getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(Date fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public int getDniCliente() {
        return dniCliente;
    }

    public void setDniCliente(int dniCliente) {
        this.dniCliente = dniCliente;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

   
    
    
}

