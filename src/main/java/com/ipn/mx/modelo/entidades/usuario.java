package com.ipn.mx.modelo.entidades;

import java.sql.Date;

/**
 *
 * @author gerardo
 */
public class usuario {
    private String email;    
    private String username;
    private String name;
    private String lastname;
    private String loginStatus;
    private Date registerDate;
    private String preferences;
    private String rutaIMG;
    private String password;
    
    private int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public usuario() {
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public String getLastname() {
        return lastname;
    }

    public String getLoginStatus() {
        return loginStatus;
    }

    public Date getRegisterDate() {
        return registerDate;
    }

    public String getPreferences() {
        return preferences;
    }

    public String getRutaIMG() {
        return rutaIMG;
    }

    public String getPassword() {
        return password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setLoginStatus(String loginStatus) {
        this.loginStatus = loginStatus;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    public void setPreferences(String preferences) {
        this.preferences = preferences;
    }

    public void setRutaIMG(String rutaIMG) {
        this.rutaIMG = rutaIMG;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
