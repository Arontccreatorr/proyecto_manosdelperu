
package controladores;

import Dao.productoDao;
import com.google.gson.Gson;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelos.Productos;


@WebServlet("/controlador_mostrarproducto")
public class controlador_mostrarproducto extends HttpServlet {

      productoDao dao = new productoDao();
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
          String action = request.getParameter("action");
           if (action == null) action = "mostrar";  // default

        switch(action) {
            case "mostrar":
                mostrarProductos(request, response);
                break;

            case "buscar":
                buscarProductoPorId(request, response);
                break;

            default:
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Acción no válida");
                break;
        }
    }
        
    

 private void mostrarProductos(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Productos> lista = dao.MostrarProducto();
        request.setAttribute("productos", lista);
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }


 private void buscarProductoPorId(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String idStr = request.getParameter("id");
            System.out.println("<<< buscarProductoPorId idStr = " + idStr);
        try {
            int idProducto = Integer.parseInt(idStr);
            Productos producto = dao.buscarporId(idProducto);
            if (producto != null) {
              
                Gson gson = new Gson();
                String json = gson.toJson(producto);

                response.setContentType("application/json");
                response.getWriter().write(json);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Producto no encontrado");
            }
        } catch (NumberFormatException | SQLException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Parámetro inválido");
        }
    }


    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

  
    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
