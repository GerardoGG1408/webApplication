/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ipn.mx.modelo.entidades;

/**
 *
 * @author gerardo
 */
public class relacionAmistad {
    private String email;
    private String amigo;
    private int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public relacionAmistad() {}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAmigo() {
        return amigo;
    }

    public void setAmigo(String amigo) {
        this.amigo = amigo;
    }
    
    
    
}
