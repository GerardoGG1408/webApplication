package com.ipn.mx.controlador;

import com.ipn.mx.modelo.dao.UsuarioDAO;
import com.ipn.mx.modelo.utilerias.mailSend;
import com.ipn.mx.modelo.dto.UsuarioDTO;
import com.ipn.mx.modelo.entidades.usuario;
import java.io.*;
import java.util.*;
import javax.servlet.RequestDispatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 *
 * @author gerardo
 */
public class fileServlet extends HttpServlet {

    HttpSession session;

    //File info
    private boolean isMultipart;
    private boolean fileFinish = false;
    private String filePath;
    private int maxFileSize = 50 * 1024 * 1024;
    private int maxMemSize = 4 * 1024;
    private File file;

    // Usuario info
    private String email;
    private String pass;
    private String user;
    private String ruta;
    private String nombre;
    private String apellido;

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, java.io.IOException {
        String fileName = null;

        session = request.getSession();

        // Check that we have a file upload request
        filePath = request.getRealPath("/");
        isMultipart = ServletFileUpload.isMultipartContent(request);
        response.setContentType("text/html");
        java.io.PrintWriter out = response.getWriter();

        if (!isMultipart) {
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet upload</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<p>No se subio el archivo</p>");
            out.println("</body>");
            out.println("</html>");
            return;
        }

        DiskFileItemFactory factory = new DiskFileItemFactory();

        // maximum size that will be stored in memory
        factory.setSizeThreshold(maxMemSize);

        // Location to save data that is larger than maxMemSize.
        factory.setRepository(new File(filePath));

        // Create a new file upload handler
        ServletFileUpload upload = new ServletFileUpload(factory);

        // maximum file size to be uploaded.
        upload.setSizeMax(maxFileSize);

        try {
            // Parse the request to get file items.
            List fileItems = upload.parseRequest(request);

            // Process the uploaded file items
            Iterator i = fileItems.iterator();

            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet upload</title>");
            out.println("</head>");
            out.println("<body>");

            UsuarioDAO dao = new UsuarioDAO();
            UsuarioDTO dto = new UsuarioDTO();
            usuario user_en = new usuario();

            while (i.hasNext()) {
                FileItem fi = (FileItem) i.next();
                if (!fi.isFormField()) {
                    // Get the uploaded file parameters
                    String fieldName = fi.getFieldName();
                    fileName = fi.getName();
                    String contentType = fi.getContentType();
                    boolean isInMemory = fi.isInMemory();
                    long sizeInBytes = fi.getSize();

                    // Write the file
                    if (fileName.lastIndexOf("\\") >= 0) {
                        file = new File(filePath + fileName.substring(fileName.lastIndexOf("\\")));
                    } else {
                        file = new File(filePath + fileName.substring(fileName.lastIndexOf("\\") + 1));
                    }
                    fi.write(file);
                    out.println("Archivo subido: " + fileName + "<br />");
                    fileFinish = true;
                } else {
                    String fieldName = fi.getFieldName();
                    String fieldValue = fi.getString();

                    switch (fieldName) {
                        case "nombreUsuario":
                            user_en.setName(fieldValue);
                            break;
                        case "apellidoUsuario":
                            user_en.setLastname(fieldValue);
                            break;
                        case "correUsuario":
                            user_en.setEmail(fieldValue);
                            break;
                        case "passUsuario":
                            user_en.setPassword(fieldValue);
                            break;
                        case "nickUsuario":
                            user_en.setUsername(fieldValue);
                            break;
                        default:
                            user_en.setRutaIMG(fileName);
                            break;
                    }
                }
            }

            String message = "";
            dto.setEntidad(user_en);
            message = dao.create(dto);

            if (message.equals("ok")) {
                session.setAttribute("userId", user_en.getEmail());
                mailSend.sendAsHtml(user_en.getEmail(), "Registro Exitoso", 0);
                session.setAttribute("img", fileName);
                session.setAttribute("username", user_en.getUsername());
                
                RequestDispatcher rd = request.getRequestDispatcher("index.jsp"); 
                rd.forward(request, response); 
            } else {                
                RequestDispatcher rd = request.getRequestDispatcher("register.jsp?message=" + message); 
                rd.forward(request, response); 
            }
            out.println("</body>");
            out.println("</html>");
        } catch (Exception ex) {
            out.println(ex.getMessage());
        }
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, java.io.IOException {

        throw new ServletException("GET method used with "
                + getClass().getName() + ": POST method required.");
    }
}
