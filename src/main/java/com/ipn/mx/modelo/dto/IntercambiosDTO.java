package com.ipn.mx.modelo.dto;

import com.ipn.mx.modelo.entidades.intercambios;
import java.io.Serializable;

public class IntercambiosDTO implements Serializable{
    private intercambios intercambio;

    public IntercambiosDTO(){
        intercambio = new intercambios();
    }
    
    public intercambios getEntidad(){
        return intercambio;
    }
    
    public void setEntidad(intercambios intercambio){
        this.intercambio = intercambio;
    }
}
