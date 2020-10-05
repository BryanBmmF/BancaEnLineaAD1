/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backend.pojos;

import java.sql.Timestamp;

/**
 *
 * @author jpmazate
 */
public class SolicitudPrestamo {
    
    private Integer id;
    private String tipoTrabajo;
    private String empresa;
    private String estado;
    private Double salarioMensual;
    private Double montoSolicitud;
    private String tipo;
    private String descripcion;
    private String direccionBienRaiz;
    private Timestamp fechaSolicitud;
    private String dpiCliente;
    private String email;

    public SolicitudPrestamo(Integer id, String tipoTrabajo, String empresa, String estado, Double salarioMensual, Double montoSolicitud, String tipo, String descripcion, String direccionBienRaiz, Timestamp fechaSolicitud, String dpiCliente, String email) {
        this.id = id;
        this.tipoTrabajo = tipoTrabajo;
        this.empresa = empresa;
        this.estado = estado;
        this.salarioMensual = salarioMensual;
        this.montoSolicitud = montoSolicitud;
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.direccionBienRaiz = direccionBienRaiz;
        this.fechaSolicitud = fechaSolicitud;
        this.dpiCliente = dpiCliente;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    

    public String getTipoTrabajo() {
        return tipoTrabajo;
    }

    public void setTipoTrabajo(String tipoTrabajo) {
        this.tipoTrabajo = tipoTrabajo;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Double getSalarioMensual() {
        return salarioMensual;
    }

    public void setSalarioMensual(Double salarioMensual) {
        this.salarioMensual = salarioMensual;
    }

    public Double getMontoSolicitud() {
        return montoSolicitud;
    }

    public void setMontoSolicitud(Double montoSolicitud) {
        this.montoSolicitud = montoSolicitud;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDireccionBienRaiz() {
        return direccionBienRaiz;
    }

    public void setDireccionBienRaiz(String direccionBienRaiz) {
        this.direccionBienRaiz = direccionBienRaiz;
    }

    public Timestamp getFechaSolicitud() {
        return fechaSolicitud;
    }

    public void setFechaSolicitud(Timestamp fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }

     

    public String getDpiCliente() {
        return dpiCliente;
    }

    public void setDpiCliente(String dpiCliente) {
        this.dpiCliente = dpiCliente;
    }
    
    
    
    
    
    
}
