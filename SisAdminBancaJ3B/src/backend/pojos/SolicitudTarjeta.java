/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backend.pojos;

import backend.enums.EstadoSolicitudDeTarjeta;
import backend.enums.TipoDeTarjeta;
import backend.enums.TipoDeTrabajoDeCliente;
import java.sql.Timestamp;

/**
 *
 * @author jesfrin
 */
public class SolicitudTarjeta {
    
    private int id;
    private TipoDeTrabajoDeCliente tipoDeTrabajo;
    private String dpi;
    private String empresa;
    private EstadoSolicitudDeTarjeta estado;
    private double salarioMensual;
    private TipoDeTarjeta tarjeta;
    private String descripcion;
    private Timestamp fechaSolicitud;
    private Timestamp fechaVerificacion;

    public SolicitudTarjeta(int id, TipoDeTrabajoDeCliente tipoDeTrabajo, String dpi, String empresa, EstadoSolicitudDeTarjeta estado, double salarioMensual, TipoDeTarjeta tarjeta, String descripcion, Timestamp fechaSolicitud, Timestamp fechaVerificacion) {
        this.id = id;
        this.tipoDeTrabajo = tipoDeTrabajo;
        this.dpi = dpi;
        this.empresa = empresa;
        this.estado = estado;
        this.salarioMensual = salarioMensual;
        this.tarjeta = tarjeta;
        this.descripcion = descripcion;
        this.fechaSolicitud = fechaSolicitud;
        this.fechaVerificacion = fechaVerificacion;
    }

    
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TipoDeTrabajoDeCliente getTipoDeTrabajo() {
        return tipoDeTrabajo;
    }

    public void setTipoDeTrabajo(TipoDeTrabajoDeCliente tipoDeTrabajo) {
        this.tipoDeTrabajo = tipoDeTrabajo;
    }

    public String getDpi() {
        return dpi;
    }

    public void setDpi(String dpi) {
        this.dpi = dpi;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public EstadoSolicitudDeTarjeta getEstado() {
        return estado;
    }

    public void setEstado(EstadoSolicitudDeTarjeta estado) {
        this.estado = estado;
    }

    public double getSalarioMensual() {
        return salarioMensual;
    }

    public void setSalarioMensual(double salarioMensual) {
        this.salarioMensual = salarioMensual;
    }

    public TipoDeTarjeta getTarjeta() {
        return tarjeta;
    }

    public void setTarjeta(TipoDeTarjeta tarjeta) {
        this.tarjeta = tarjeta;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Timestamp getFechaSolicitud() {
        return fechaSolicitud;
    }

    public void setFechaSolicitud(Timestamp fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }

    public Timestamp getFechaVerificacion() {
        return fechaVerificacion;
    }

    public void setFechaVerificacion(Timestamp fechaVerificacion) {
        this.fechaVerificacion = fechaVerificacion;
    }
    
    
    
    
}
