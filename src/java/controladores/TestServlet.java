package controladores;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import javax.servlet.annotation.WebServlet;

@WebServlet("/TestServlet")
public class TestServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().println("<h1>TestServlet está funcionando</h1>");
    }
}