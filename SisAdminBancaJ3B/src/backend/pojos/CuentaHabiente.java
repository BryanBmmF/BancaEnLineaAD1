/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backend.pojos;

import backend.enums.TipoCuentaHabiente;
import java.sql.Date;

/**
 *
 * @author jesfrin
 */
public class CuentaHabiente {
    
    private String dpiCliente;
    private String nombres;
    private String apellidos;
    private Date fecha_nacimiento;
    private String direccion;
    private String telefono;
    private String celular;
    private String email;
    private TipoCuentaHabiente tipo;
    
    /**
     * Para creacion y recuperacion
     * @param dpiCliente
     * @param nombres
     * @param apellidos
     * @param fecha_nacimiento
     * @param direccion
     * @param telefono
     * @param celular
     * @param email 
     */
    
    public CuentaHabiente(String dpiCliente, String nombres, String apellidos, Date fecha_nacimiento, String direccion, String telefono, String celular, String email,TipoCuentaHabiente tipo) {
        this.dpiCliente = dpiCliente;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.fecha_nacimiento = fecha_nacimiento;
        this.direccion = direccion;
        this.telefono = telefono;
        this.celular = celular;
        this.email = email;
        this.tipo=tipo;
    }
    
    public String getDpiCliente() {
        return dpiCliente;
    }

    public void setDpiCliente(String dpiCliente) {
        this.dpiCliente = dpiCliente;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public Date getFecha_nacimiento() {
        return fecha_nacimiento;
    }

    public void setFecha_nacimiento(Date fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public TipoCuentaHabiente getTipo() {
        return tipo;
    }

    public void setTipo(TipoCuentaHabiente tipo) {
        this.tipo = tipo;
    }
    
    
    
    
}
