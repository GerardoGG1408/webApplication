package com.ipn.mx.modelo.dto;

import com.ipn.mx.modelo.entidades.relacionAmistad;
import java.io.Serializable;

/**
 *
 * @author gerardo
 */
public class AmistadDTO implements Serializable{
    private relacionAmistad amigo;

    public AmistadDTO() {
        amigo = new relacionAmistad();
    }

    public relacionAmistad getEntidad() {
        return amigo;
    }

    public void setEntidad(relacionAmistad amigo) {
        this.amigo = amigo;
    }
}
