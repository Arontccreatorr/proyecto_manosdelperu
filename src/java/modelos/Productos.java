package modelos;

import java.io.InputStream;

public class Productos {

    int idProducto;
    int idProveedor;
    String nombre;
    String imagenprodcuto;
    String descripcion;
    double precio;
    int stock;
    String autor;

    public Productos() {

    }

    public Productos(int idProducto, int idProveedor, String nombre, String imagenprodcuto, String descripcion, double precio, int stock, String autor) {
        this.idProducto = idProducto;
        this.idProveedor = idProveedor;
        this.nombre = nombre;
        this.imagenprodcuto = imagenprodcuto;
        this.descripcion = descripcion;
        this.precio = precio;
        this.stock = stock;
        this.autor = autor;
      
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public int getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(int idProveedor) {
        this.idProveedor = idProveedor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getImagenprodcuto() {
        return imagenprodcuto;
    }

    public void setImagenprodcuto(String imagenprodcuto) {
        this.imagenprodcuto = imagenprodcuto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

}
