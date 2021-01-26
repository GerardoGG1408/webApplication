package com.ipn.mx.modelo.dto;

import com.ipn.mx.modelo.entidades.usuario;
import java.io.Serializable;

/**
 *
 * @author gerardo
 */
public class UsuarioDTO implements Serializable{
    private usuario user;
    
    public UsuarioDTO(){
        user = new usuario();
    }
    
    public usuario getEntidad(){
        return user;
    }
    
    public void setEntidad(usuario user){
        this.user = user;
    }
}
