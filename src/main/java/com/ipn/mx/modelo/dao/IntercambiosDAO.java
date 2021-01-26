package com.ipn.mx.modelo.dao;

import com.ipn.mx.modelo.dto.DestinatarioDTO;
import com.ipn.mx.modelo.dto.IntercambiosDTO;
import com.ipn.mx.modelo.dto.UsuarioDTO;
import com.ipn.mx.modelo.entidades.destinatario;
import com.ipn.mx.modelo.entidades.intercambios;
import com.ipn.mx.modelo.entidades.usuario;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class IntercambiosDAO {

    // JDBC driver
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://jhdjjtqo9w5bzq2t.cbetxkdyhwsb.us-east-1.rds.amazonaws.com:3306/kxu377ur947k5ne1?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC";

    //  Database credenciales
    static final String USER = "sy9fbnddkd69lxtm";
    static final String PASS = "sdjelioiykq4rgr3";

    // SQL 
    private final String SQL_INSERT = "INSERT INTO intercambio(id, nombreintercambio, adminEmail, dateShipped, status, comment, typeGift, giftValue) VALUES (?,?,?,?,?,?,?,?)";
    private final String SQL_READ = "SELECT * FROM intercambio WHERE adminEmail = ?";
    private final String SQL_INVATE = "INSERT INTO listaParticipantes(intercambioId, userEmail, status) VALUES (?,?,?)";
    private final String SQL_READ_SOLICITUDES = "SELECT * FROM listaParticipantes WHERE userEmail = ?";
    private final String SQL_READ_ID = "SELECT * FROM intercambio WHERE id = ?";
    private final String SQL_ACCEPT_INVITACION = "UPDATE listaParticipantes SET status = ? WHERE intercambioId = ? and userEmail = ?";
    private final String SQL_DELETE_INVITACION = "DELETE FROM listaParticipantes WHERE intercambioId = ? and userEmail = ?";
    private final String SQL_READ_FRIEND = "SELECT * FROM usuario WHERE email = ?";

    private Connection conn = null;

    public void obtenerConexion() {
        conn = null;
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (ClassNotFoundException | SQLException ex) {
            obtenerConexion();
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String create(IntercambiosDTO dto) {
        obtenerConexion();
        CallableStatement cs = null;
        String completado = null;
        try {
            long time = dto.getEntidad().getDateShipped().getTime();

            cs = conn.prepareCall(SQL_INSERT);
            cs.setString(1, dto.getEntidad().getId());
            cs.setString(2, dto.getEntidad().getNombre());
            cs.setString(3, dto.getEntidad().getAdminEmail());
            cs.setTimestamp(4, new Timestamp(time));
            cs.setString(5, "CREATED");
            cs.setString(6, dto.getEntidad().getComentario());
            cs.setString(7, dto.getEntidad().getTypeGift());
            cs.setFloat(8, dto.getEntidad().getGiftValue());
            cs.executeUpdate();
        } catch (SQLException ex) {
            completado = "error";
        } finally {
            if (cs != null) {
                try {
                    cs.close();
                } catch (SQLException ex) {
                    Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (completado == null) {
                completado = "ok";
            }
            return completado;
        }
    }

    public List read(UsuarioDTO dto) throws SQLException {
        obtenerConexion();
        CallableStatement cs = null;
        ResultSet rs = null;
        cs = conn.prepareCall(SQL_READ);
        cs.setString(1, dto.getEntidad().getEmail());
        rs = cs.executeQuery();
        List resultados = obtenerResultados(rs);
        if (resultados.size() > 0) {
            rs.close();
            cs.close();
            conn.close();
            return resultados;
        } else {
            rs.close();
            cs.close();
            conn.close();
            return null;
        }
    }
    
    public IntercambiosDTO readIntercambio(IntercambiosDTO dto) throws SQLException {
        obtenerConexion();
        CallableStatement cs = null;
        ResultSet rs = null;
        cs = conn.prepareCall(SQL_READ_ID);
        cs.setString(1, dto.getEntidad().getId());
        rs = cs.executeQuery();
        List resultados = obtenerResultados(rs);
        if (resultados.size() > 0) {
            rs.close();
            cs.close();
            conn.close();
            return (IntercambiosDTO) resultados.get(0);
        } else {
            rs.close();
            cs.close();
            conn.close();
            return null;
        }
    }

    public void inviteFriends(String folio, String amigo) throws SQLException {

        obtenerConexion();
        CallableStatement cs = null;

        cs = conn.prepareCall(SQL_INVATE);
        cs.setString(1, folio);
        cs.setString(2, amigo);
        cs.setInt(3, 0);
        cs.executeUpdate();

        cs.close();
        conn.close();

    }
    
    public List obtenerSolicitudesIntercambio(UsuarioDTO dto) throws SQLException {
        obtenerConexion();
        CallableStatement cs = null;
        ResultSet rs = null;
        cs = conn.prepareCall(SQL_READ_SOLICITUDES);
        cs.setString(1, dto.getEntidad().getEmail());
        rs = cs.executeQuery();
        List resultados = null;
        resultados = obtenerSolicitudes(rs);
        if (resultados.size() > 0) {
            rs.close();
            cs.close();
            conn.close();
            return resultados;
        } else {
            rs.close();
            cs.close();
            conn.close();
            return null;
        }
    }
    
    private List obtenerSolicitudes(ResultSet rs) throws SQLException {
        List resultados = new ArrayList();
        while (rs.next()) {
            IntercambiosDTO dto = new IntercambiosDTO();
            intercambios inter = new intercambios();
            IntercambiosDTO inter_search = null;
            int status = 0;
            status = rs.getInt("status");
            inter.setId(rs.getString("intercambioId"));
            
            dto.setEntidad(inter);
            inter_search = readIntercambio(dto);
            inter_search.getEntidad().setStatus_sol(status);

            resultados.add(inter_search);
        }
        return resultados;
    }

    private List obtenerResultados(ResultSet rs) throws SQLException {
        List resultados = new ArrayList();
        while (rs.next()) {
            IntercambiosDTO dto = new IntercambiosDTO();
            dto.getEntidad().setId(rs.getString("id"));
            dto.getEntidad().setNombre(rs.getString("nombreintercambio"));
            dto.getEntidad().setDateShipped(rs.getDate("dateShipped"));
            dto.getEntidad().setStatus(rs.getString("status"));
            dto.getEntidad().setComentario(rs.getString("comment"));
            dto.getEntidad().setTypeGift(rs.getString("typeGift"));
            dto.getEntidad().setGiftValue(Float.parseFloat(rs.getString("giftValue")));

            resultados.add(dto);
        }
        return resultados;
    }

    public String aceptarSolicitud(String user, String id) {
        obtenerConexion();
        CallableStatement cs = null;
        String completado = null;
        try {
            cs = conn.prepareCall(SQL_ACCEPT_INVITACION);
            cs.setInt(1, 1);
            cs.setString(2, id);
            cs.setString(3, user);
            cs.executeUpdate();
        } catch (SQLException ex) {
            completado = "error";
        } finally {
            if (cs != null) {
                try {
                    cs.close();
                } catch (SQLException ex) {
                    Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (completado == null) {
                completado = "ok";
            }
            return completado;
        }
    }

    public String removeSolicitud(String user, String intercambio) {
        obtenerConexion();
        CallableStatement cs = null;
        String completado = null;
        try {
            cs = conn.prepareCall(SQL_DELETE_INVITACION);
            cs.setString(1, intercambio);
            cs.setString(2, user);
            cs.executeUpdate();
        } catch (SQLException ex) {
            completado = "error";
        } finally {
            if (cs != null) {
                try {
                    cs.close();
                } catch (SQLException ex) {
                    Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (completado == null) {
                completado = "ok";
            }
            return completado;
        }
    }
    
    public List obtenerDestinatario(UsuarioDTO dto) throws SQLException {
        obtenerConexion();
        CallableStatement cs = null;
        ResultSet rs = null;
        cs = conn.prepareCall(SQL_READ_SOLICITUDES);
        cs.setString(1, dto.getEntidad().getEmail());
        rs = cs.executeQuery();
        List resultados = null;
        resultados = obtenerDatosDestinatario(rs);
        if (resultados.size() > 0) {
            rs.close();
            cs.close();
            conn.close();
            return resultados;
        } else {
            rs.close();
            cs.close();
            conn.close();
            return null;
        }
    }
    
    private List obtenerDatosDestinatario(ResultSet rs) throws SQLException {
        List resultados = new ArrayList();
        while (rs.next()) {
            DestinatarioDTO dto = new DestinatarioDTO();
            DestinatarioDTO inter = new DestinatarioDTO();
            DestinatarioDTO inter_search = null;
            usuario user = new usuario();
            UsuarioDAO daoUsuario = new UsuarioDAO();
            UsuarioDTO dtoUsuario = new UsuarioDTO();
            IntercambiosDAO daoInt = new IntercambiosDAO();
            IntercambiosDTO dtoInt = new IntercambiosDTO();
            IntercambiosDTO dtoInt_buscado = new IntercambiosDTO();
            
            
            String intercambioId = rs.getString("intercambioId");
            
            dtoInt.getEntidad().setId(intercambioId);
            dtoInt_buscado.setEntidad(daoInt.readIntercambio(dtoInt).getEntidad());
            
            int status = 0;
            status = rs.getInt("status");
            String giftTo = rs.getString("giftTo");
            if(giftTo == null){
                inter.getEntidad().setUserEmail("");
                inter.getEntidad().setUsername("Aun no se asigna un destinatario para ti");
                inter.getEntidad().setIntercambioId(intercambioId);
                inter.getEntidad().setNombreIntercambio(dtoInt_buscado.getEntidad().getNombre());
                inter.getEntidad().setPreferencias("");
                inter.getEntidad().setImg("");
                inter.getEntidad().setStatus(status);
            }else {
                user.setEmail(giftTo);
                dtoUsuario.setEntidad(user);
                dtoUsuario = daoUsuario.readFriend(dtoUsuario);
                
                inter.getEntidad().setUserEmail(dtoUsuario.getEntidad().getEmail());
                inter.getEntidad().setUsername(dtoUsuario.getEntidad().getUsername());
                inter.getEntidad().setIntercambioId(intercambioId);
                inter.getEntidad().setNombreIntercambio(dtoInt_buscado.getEntidad().getNombre());
                inter.getEntidad().setPreferencias(dtoUsuario.getEntidad().getPreferences());
                inter.getEntidad().setImg(dtoUsuario.getEntidad().getRutaIMG());
                inter.getEntidad().setStatus(status);
            }
            resultados.add(inter);
        }
        return resultados;
    }

    public UsuarioDTO readDestinatario(UsuarioDTO dto) throws SQLException {
        obtenerConexion();
        CallableStatement cs = null;
        ResultSet rs = null;
        cs = conn.prepareCall(SQL_READ_FRIEND);
        cs.setString(1, dto.getEntidad().getEmail());
        rs = cs.executeQuery();
        List resultados = obtenerResultados(rs);
        if (resultados.size() > 0) {
            rs.close();
            cs.close();
            conn.close();
            return (UsuarioDTO) resultados.get(0);
        } else {
            rs.close();
            cs.close();
            conn.close();
            return null;
        }
    }
}
