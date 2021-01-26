/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ipn.mx.modelo.dto;

import com.ipn.mx.modelo.entidades.destinatario;

/**
 *
 * @author gerardo
 */
public class DestinatarioDTO {
    private destinatario dest;

    public DestinatarioDTO() {
        dest = new destinatario();
    }

    public destinatario getEntidad() {
        return dest;
    }

    public void setEntidad(destinatario amigo) {
        this.dest = amigo;
    }
}
