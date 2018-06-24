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
    
    /**
     *
     * @param nombre
     * @param ci
     */
    public ClienteSV(String nombre, String ci){
    }

    /**
     *Constructor del objeto cliente 
     * @param uid
     * @param userName
     * @param dirección
     * @param clavePublica
     * @param rol
     */
    public ClienteSV(String uid, String userName, String dirección, String clavePublica, int rol) {
        this.userID = uid;
        this.nombre= userName;
        this.direccion = dirección;
        this.clavePublica= clavePublica;
        this.rol = rol;
    }

    /**
     *
     * @return
     */
    public String getUserID() {
        return userID;
    }

    /**
     *
     * @param userID
     */
    public void setUserID(String userID) {
        this.userID = userID;
    }

    /**
     *
     * @return
     */
    public String getNombre() {
        return nombre;
    }

    /**
     *
     * @param nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     *
     * @return
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     *
     * @param direccion
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    /**
     *
     * @return
     */
    public String getClavePublica() {
        return clavePublica;
    }

    /**
     *
     * @param clavePublica
     */
    public void setClavePublica(String clavePublica) {
        this.clavePublica = clavePublica;
    }

    /**
     *
     * @return
     */
    public String getClavePrivada() {
        return clavePrivada;
    }

    /**
     *
     * @param clavePrivada
     */
    public void setClavePrivada(String clavePrivada) {
        this.clavePrivada = clavePrivada;
    }

    /**
     *
     * @return
     */
    public int getRol() {
        return rol;
    }

    /**
     *
     * @param rol
     */
    public void setRol(int rol) {
        this.rol = rol;
    }


    
}
