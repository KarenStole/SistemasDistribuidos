/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientesv;

/**
 *
 * @author Karen
 */
public class ClienteSV {

    private String userID;
    private String nombre;
    private String direccion;
    private String clavePublica;
    private String clavePrivada;
    private int rol;
    
    public ClienteSV(String nombre, String ci){
    }

    public ClienteSV(String uid, String userName, String dirección, int rol) {
        this.userID = uid;
        this.nombre= userName;
        this.direccion = direccion;
        this.rol = rol;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getClavePublica() {
        return clavePublica;
    }

    public void setClavePublica(String clavePublica) {
        this.clavePublica = clavePublica;
    }

    public String getClavePrivada() {
        return clavePrivada;
    }

    public void setClavePrivada(String clavePrivada) {
        this.clavePrivada = clavePrivada;
    }

    public int getRol() {
        return rol;
    }

    public void setRol(int rol) {
        this.rol = rol;
    }


    
}
