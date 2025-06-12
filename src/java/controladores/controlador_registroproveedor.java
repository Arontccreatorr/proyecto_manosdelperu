/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controladores;

import Dao.proveedorDao;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import modelos.Proveedores;

@WebServlet("/controlador_registroproveedor")
@MultipartConfig(
  fileSizeThreshold = 1024*1024,
  maxFileSize       = 5*1024*1024,
  maxRequestSize    = 10*1024*1024
)
public class controlador_registroproveedor extends HttpServlet {
      String    UPLOAD_DIR = "C:/xampp/htdocs/img/perfil_proveedor";
  
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet controlador_registroproveedor</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet controlador_registroproveedor at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
         // Recoge datos
        String dni             = request.getParameter("dni");
        String nombres         = request.getParameter("nombres");
        String apellidos       = request.getParameter("apellidos");
        String correo          = request.getParameter("correo");
        String contraseña      = request.getParameter("contraseña");
        String direccion       = request.getParameter("direccion");
        String numeroContacto  = request.getParameter("numero_contacto");

        // Maneja imagen de perfil
        Part filePart = request.getPart("imagen");
        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
        File uploads = new File(UPLOAD_DIR);
        if (!uploads.exists()) uploads.mkdirs();
        filePart.write(new File(uploads, fileName).getAbsolutePath());

        // Monta bean
        Proveedores proveedor = new Proveedores();
        proveedor.setDni(dni);
        proveedor.setNombres(nombres);
        proveedor.setApellidos(apellidos);
        proveedor.setCorreo(correo);
        proveedor.setContraseña(contraseña); // Puedes encriptar aquí si usas BCrypt
        proveedor.setDireccion(direccion);
        proveedor.setNumero_contacto(numeroContacto);
        proveedor.setImg_provedor(fileName);

        proveedorDao dao = new proveedorDao();
        String mensaje;
        String mensajeColor;
        try {
            boolean ok = dao.insertarProveedor(proveedor);
            if (ok) {
                mensaje = "¡Registro realizado con éxito!";
                mensajeColor = "green";
            } else {
                mensaje = "No se pudo registrar. Verifica tus datos o si ya existe ese correo/DNI.";
                mensajeColor = "red";
            }
        } catch (Exception e) {
            mensaje = "Error al registrar: " + e.getMessage();
            mensajeColor = "red";
            e.printStackTrace();
        }
        request.setAttribute("mensaje", mensaje);
        request.setAttribute("mensajeColor", mensajeColor);
        request.getRequestDispatcher("/vistas/registro_proveedor.jsp").forward(request, response);
  
    }

   
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
