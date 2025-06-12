/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controladores;

import Dao.productoDao;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import modelos.Productos;
import modelos.Proveedores;

/**
 *
 * @author tacov
 */
@WebServlet("/controlador_registrarproducto")
@MultipartConfig(
  fileSizeThreshold = 1024*1024,
  maxFileSize       = 5*1024*1024,
  maxRequestSize    = 10*1024*1024
)
public class controlador_registrarproducto extends HttpServlet {

 String UPLOAD_DIR = "/xampp/htdocs/img/producto";
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // delega a JSP
    request.getRequestDispatcher("/vistas/registro_producto.jsp")
       .forward(request, response);
        
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
         // Muestra formulario
    request.getRequestDispatcher("/vistas/registro_producto.jsp")
           .forward(request, response);
    }

 
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
         HttpSession session = request.getSession(false);
    Proveedores prov = session != null
        ? (Proveedores) session.getAttribute("loggedProvider")
        : null;
    if (prov == null) {
      response.sendRedirect(request.getContextPath() + "/vistas/sesion_proveedor.jsp");
      return;
    }

    try {
      // 1) Recoge datos
      String nombre      = request.getParameter("nombre");
      String descripcion = request.getParameter("descripcion");
      double precio      = Double.parseDouble(request.getParameter("precio"));
      int stock          = Integer.parseInt(request.getParameter("stock"));

      // 2) Subida de archivo
      Part   filePart = request.getPart("imagen");
      String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
      File   uploads  = new File(UPLOAD_DIR);
      if (!uploads.exists()) uploads.mkdirs();
      filePart.write(new File(uploads, fileName).getAbsolutePath());

      // 3) Monta el bean
      Productos prod = new Productos();
      prod.setIdProveedor(prov.getId_proveedor());
      prod.setNombre(nombre);
      prod.setDescripcion(descripcion);
      prod.setPrecio(precio);
      prod.setStock(stock);
      prod.setImagenprodcuto(fileName);  // guardamos sólo el nombre

      // 4) Persiste
      productoDao dao = new productoDao();
      int newId = dao.insertarProducto(prod);

      // 5) Prepara feedback
      request.setAttribute("successMsg", "¡Producto agregado! ID=" + newId);
      request.getRequestDispatcher("/vistas/registro_producto.jsp")
         .forward(request, response);

    } catch (Exception e) {
      throw new ServletException("Error registrando producto", e);
    }
  }
    

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
