package com.ipn.mx.controlador;

import com.ipn.mx.modelo.dao.UsuarioDAO;
import com.ipn.mx.modelo.dto.AmistadDTO;
import com.ipn.mx.modelo.dto.UsuarioDTO;
import com.ipn.mx.modelo.entidades.relacionAmistad;
import com.ipn.mx.modelo.entidades.usuario;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author gerardo
 */
public class userServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        String accion = request.getParameter("accion");

        if (accion.equals("login")) {
            login(request, response);
        } else {
            if (accion.equals("listarUsuarios")) {
                lista(request, response);
            } else {
                if (accion.equals("logout")) {
                    logout(request, response);
                } else {
                    if (accion.equals("usuario")) {
                        verUsuario(request, response);
                    } else {
                        if (accion.equals("removeUser")) {
                            removeUser(request, response);
                        } else {
                            if (accion.equals("addFriend")) {
                                addFriend(request, response);
                            } else {
                                if (accion.equals("removeFriend")) {
                                    removeFriend(request, response);
                                } else {
                                    if (accion.equals("acceptFriend")) {
                                        acceptFriend(request, response);
                                    } else {
                                        if (accion.equals("preferencias")) {
                                            preferencias(request, response);
                                        } else {
                                            errorPage(request, response);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(userServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(userServlet.class.getName()).log(Level.SEVERE, null, ex);
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

    private void login(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        usuario user = new usuario();
        UsuarioDAO dao = new UsuarioDAO();
        UsuarioDTO dto = new UsuarioDTO();
        UsuarioDTO res;

        user.setUsername(request.getParameter("correUsuario"));
        user.setPassword(request.getParameter("passUsuario"));

        dto.setEntidad(user);
        res = dao.read(dto);

        if (res == null) {
            response.sendRedirect("login.jsp?message=error");
        } else {
            HttpSession session;
            session = request.getSession();
            session.setAttribute("userId", res.getEntidad().getEmail());
            session.setAttribute("img", res.getEntidad().getRutaIMG());
            session.setAttribute("username", res.getEntidad().getUsername());
            response.sendRedirect("index.jsp");
        }
    }

    private void lista(HttpServletRequest request, HttpServletResponse response) throws ServletException, SQLException {
        String cadena = "";
        List usuarios;
        List amigos;

        cadena = request.getParameter("friendSearch");

        usuario user = new usuario();
        UsuarioDAO dao = new UsuarioDAO();
        UsuarioDTO dto = new UsuarioDTO();

        user.setUsername(cadena);
        dto.setEntidad(user);

        try {
            usuarios = dao.readUsuarios(dto);
            amigos = dao.obtenerAmigos(dto);

            request.removeAttribute("usuarios");
            request.setAttribute("usuarios", usuarios);
            request.setAttribute("amigos", amigos);

            RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
            rd.forward(request, response);
        } catch (IOException ex) {
            Logger.getLogger(userServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        try {
            HttpSession session;
            session = request.getSession();
            session.removeAttribute("userId");
            session.removeAttribute("img");

            RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
            rd.forward(request, response);
        } catch (IOException ex) {
            Logger.getLogger(userServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void verUsuario(HttpServletRequest request, HttpServletResponse response) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void errorPage(HttpServletRequest request, HttpServletResponse response) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void removeUser(HttpServletRequest request, HttpServletResponse response) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void addFriend(HttpServletRequest request, HttpServletResponse response) throws IOException {
        relacionAmistad user = new relacionAmistad();
        UsuarioDAO dao = new UsuarioDAO();
        AmistadDTO dto = new AmistadDTO();

        HttpSession session;
        session = request.getSession();

        user.setEmail((String) session.getAttribute("userId"));
        user.setAmigo((String) request.getParameter("friendId"));
        user.setStatus(0);

        dto.setEntidad(user);

        dao.addFriend(dto);

        request.removeAttribute("usuarios");

        response.sendRedirect("index.jsp");
    }

    private void removeFriend(HttpServletRequest request, HttpServletResponse response) throws IOException {
        relacionAmistad user = new relacionAmistad();
        UsuarioDAO dao = new UsuarioDAO();
        AmistadDTO dto = new AmistadDTO();

        HttpSession session;
        session = request.getSession();

        user.setEmail((String) session.getAttribute("userId"));
        user.setAmigo((String) request.getParameter("friendId"));

        dto.setEntidad(user);

        dao.removeFriend(dto);

        response.sendRedirect("index.jsp");
    }

    private void acceptFriend(HttpServletRequest request, HttpServletResponse response) throws IOException {
        relacionAmistad user = new relacionAmistad();
        UsuarioDAO dao = new UsuarioDAO();
        AmistadDTO dto = new AmistadDTO();

        HttpSession session;
        session = request.getSession();

        user.setEmail((String) session.getAttribute("userId"));
        user.setAmigo((String) request.getParameter("friendId"));
        user.setStatus(0);

        dto.setEntidad(user);

        dao.acceptFriend(dto);

        response.sendRedirect("index.jsp");
    }

    private void preferencias(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        usuario user = new usuario();
        UsuarioDAO dao = new UsuarioDAO();
        UsuarioDTO dto = new UsuarioDTO();
        String res = "";
        
        HttpSession session;
        session = request.getSession();

        user.setEmail((String) session.getAttribute("userId"));
        user.setPreferences(request.getParameter("preferencias"));

        dto.setEntidad(user);
        res = dao.update(dto);

        if (res.equals("error")) {
            response.sendRedirect("login.jsp?message=error");
        } else {
            response.sendRedirect("index.jsp");
        }
    }

}
