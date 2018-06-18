/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servers;

import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * @author Karen
 */
@WebService(serviceName = "Firma")
public class Firma {
    
    private String formulario;
    private String ciValidador;
    private String ciCliente;

    /**
     * Web service operation
     */
    @WebMethod(operationName = "suma")
    public String suma(@WebParam(name = "num1") String num1, @WebParam(name = "num2") String num2) {
        //TODO write your implementation code here:
        return num1+num2;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "setFormulario")
    public Boolean setFormulario(@WebParam(name = "filePath") String filePath, @WebParam(name = "ci") String ciV, @WebParam(name = "ci2") String ci) {
        //TODO write your implementation code here:
        this.formulario= filePath;
        this.ciValidador= ciV;
        this.ciCliente= ci;
        System.out.println(this.formulario);
        return true;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "sendForm")
    public String sendForm(@WebParam(name = "ci") String ci) {
        //TODO write your implementation code here:
        return this.formulario;
    }
}
