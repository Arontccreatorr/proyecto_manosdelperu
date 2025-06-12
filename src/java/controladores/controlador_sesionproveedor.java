
package controladores;

import Dao.proveedorDao;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelos.Proveedores;


@WebServlet("/controlador_sesionproveedor")
public class controlador_sesionproveedor extends HttpServlet {
proveedorDao provDao = new proveedorDao();
/*creado por jose bojorquez*/
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
          // si vienen con logout
        if ("true".equals(request.getParameter("logout"))) {
            request.getSession().invalidate();
            response.sendRedirect(request.getContextPath() + "/vistas/sesion_proveedor.jsp");
            return;
        }
        // mostrar JSP de login
        request.getRequestDispatcher("/vistas/sesion_proveedor.jsp")
           .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email    = request.getParameter("email");
        String password = request.getParameter("password");

        try {
            Proveedores prov = provDao.buscarPorCorreoYClave(email, password);
            if (prov != null) {
                HttpSession session = request.getSession();
                session.setAttribute("loggedProvider", prov);
                response.sendRedirect(request.getContextPath()
                                + "/vistas/registro_producto.jsp");
            } else {
                request.setAttribute("loginError", "Correo o contraseña incorrectos");
                request.getRequestDispatcher("/vistas/sesion_proveedor.jsp")
                   .forward(request, response);
            }
        } catch (Exception e) {
            throw new ServletException("Error validando proveedor", e);
        }
    }
}