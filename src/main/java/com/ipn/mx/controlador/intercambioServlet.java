package com.ipn.mx.controlador;

import com.ipn.mx.modelo.dao.IntercambiosDAO;
import com.ipn.mx.modelo.dto.IntercambiosDTO;
import com.ipn.mx.modelo.entidades.intercambios;
import com.ipn.mx.modelo.utilerias.md5;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class intercambioServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ParseException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        String accion = request.getParameter("accion");

        if (accion.equals("crear")) {
            create(request, response);
        } else {
            if (accion.equals("addFriends")) {
                addFriends(request, response);
            } else {
                if (accion.equals("aceptarSolicitud")) {
                    aceptarSolicitud(request, response);
                } else {
                    if (accion.equals("removeSolicitud")) {
                        removeSolicitud(request, response);
                    } else {
                        errorPage(request, response);
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
        } catch (ParseException | SQLException ex) {
            Logger.getLogger(intercambioServlet.class.getName()).log(Level.SEVERE, null, ex);
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
        } catch (ParseException | SQLException ex) {
            Logger.getLogger(intercambioServlet.class.getName()).log(Level.SEVERE, null, ex);
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

    private void create(HttpServletRequest request, HttpServletResponse response) throws ParseException, IOException {
        IntercambiosDTO dto = new IntercambiosDTO();
        IntercambiosDAO dao = new IntercambiosDAO();
        intercambios intercambio = new intercambios();
        Date shipped = Date.valueOf(request.getParameter("dateShipped"));

        HttpSession session;
        session = request.getSession();

        String nombre = request.getParameter("nombreIntercambio");

        intercambio.setId(md5.getMd5((String) session.getAttribute("userId") + nombre));
        intercambio.setNombre(nombre);
        intercambio.setAdminEmail((String) session.getAttribute("userId"));
        intercambio.setDateShipped(shipped);
        intercambio.setComentario(request.getParameter("giftComment"));
        intercambio.setTypeGift(request.getParameter("giftType"));
        intercambio.setGiftValue(Float.parseFloat(request.getParameter("giftValue")));

        dto.setEntidad(intercambio);
        String message = dao.create(dto);

        if (message.equals("ok")) {
            response.sendRedirect("index.jsp");
        } else {
            response.sendRedirect("index.jsp?form=true&message=error");
        }

    }

    private void errorPage(HttpServletRequest request, HttpServletResponse response) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void addFriends(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        List<String> amigos;
        String folio = "";
        String count = "";
        String name = "";

        folio = request.getParameter("folio");
        amigos = Arrays.asList(request.getParameterValues("amigos"));

        for (int i = 0; i < amigos.size(); i++) {
            IntercambiosDAO dao = new IntercambiosDAO();
            dao.inviteFriends(folio, amigos.get(i));
        }
        response.sendRedirect("index.jsp");
    }

    private void aceptarSolicitud(HttpServletRequest request, HttpServletResponse response) throws IOException {
        IntercambiosDAO dao = new IntercambiosDAO();
        HttpSession session;
        session = request.getSession();
        
        String user = "";
        String intercambio = "";
        
        user = (String) session.getAttribute("userId");
        intercambio = request.getParameter("intercambioId");
        
        dao.aceptarSolicitud(user, intercambio);
        
        response.sendRedirect("index.jsp");
    }

    private void removeSolicitud(HttpServletRequest request, HttpServletResponse response) throws IOException {
        IntercambiosDAO dao = new IntercambiosDAO();
        HttpSession session;
        session = request.getSession();
        
        String user = "";
        String intercambio = "";
        
        user = (String) session.getAttribute("userId");
        intercambio = request.getParameter("intercambioId");
        
        dao.removeSolicitud(user, intercambio);
        
        response.sendRedirect("index.jsp");
    }

}
