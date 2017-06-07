/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pragma.frameworksoa.web.sesion.mgbeans;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author andresfelipegarciaduran
 */
@ManagedBean
@RequestScoped
public class SingInOutBean implements Serializable{

    private String usuario, contrasena;

    public SingInOutBean() {
    }

    public String eventoIniciarSesion() {
        return null;
    }
    
    public String eventoCerrarSesion(){
        return null;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

}
