package com.ipn.mx.modelo.entidades;

import java.sql.Date;

public class intercambios {
    private String id;
    private String nombre;
    private String adminEmail;
    private Date dateShipped;
    private String status;
    private String comentario;
    private String typeGift;
    private float giftValue;
    
    private int status_sol;

    public intercambios() {
    }

    public String getId() {
        return id;
    }

    public int getStatus_sol() {
        return status_sol;
    }

    public void setStatus_sol(int status_sol) {
        this.status_sol = status_sol;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "intercambios{" + "id=" + id + ", nombre=" + nombre + ", adminEmail=" + adminEmail + ", dateShipped=" + dateShipped + ", status=" + status + ", comentario=" + comentario + ", typeGift=" + typeGift + ", giftValue=" + giftValue + '}';
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getAdminEmail() {
        return adminEmail;
    }

    public void setAdminEmail(String adminEmail) {
        this.adminEmail = adminEmail;
    }

    public Date getDateShipped() {
        return dateShipped;
    }

    public void setDateShipped(Date dateShipped) {
        this.dateShipped = dateShipped;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getTypeGift() {
        return typeGift;
    }

    public void setTypeGift(String typeGift) {
        this.typeGift = typeGift;
    }

    public float getGiftValue() {
        return giftValue;
    }

    public void setGiftValue(float giftValue) {
        this.giftValue = giftValue;
    }
    
    
}
