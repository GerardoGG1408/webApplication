package com.ipn.mx.modelo.entidades;

public class destinatario {
    private String intercambioId;
    private String nombreIntercambio;
    
    private String userEmail;
    private String username;
    private String preferencias;
    private String img;
    
    private int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    
    public String getNombreIntercambio() {
        return nombreIntercambio;
    }

    public void setNombreIntercambio(String nombreIntercambio) {
        this.nombreIntercambio = nombreIntercambio;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getIntercambioId() {
        return intercambioId;
    }

    public void setIntercambioId(String intercambioId) {
        this.intercambioId = intercambioId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPreferencias() {
        return preferencias;
    }

    public void setPreferencias(String preferencias) {
        this.preferencias = preferencias;
    }

    public destinatario() {
    }
}
