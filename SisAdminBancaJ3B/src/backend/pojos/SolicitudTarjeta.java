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
    private EstadoSolicitudDeTarjeta estadoSolicitud;
    private double salarioMensual;
    private TipoDeTarjeta tipoDeTarjeta;
    private String descripcion;
    private Timestamp fechaSolicitud;
    private Timestamp fechaVerificacion;
    private String email;

    public SolicitudTarjeta(int id, String tipoDeTrabajo, String dpi, String empresa, String estado, double salarioMensual, String tarjeta, String descripcion, Timestamp fechaSolicitud, Timestamp fechaVerificacion,String email) {
        this.id = id;
        convertirTipoDeTrabajo(tipoDeTrabajo);
        this.dpi = dpi;
        this.empresa = empresa;
        convertirEstado(estado);
        this.salarioMensual = salarioMensual;
        convertirTipoDeTarjeta(tarjeta);
        this.descripcion = descripcion;
        this.fechaSolicitud = fechaSolicitud;
        this.fechaVerificacion = fechaVerificacion;
        this.email = email;
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
        return estadoSolicitud;
    }

    public void setEstado(EstadoSolicitudDeTarjeta estado) {
        this.estadoSolicitud = estado;
    }

    public double getSalarioMensual() {
        return salarioMensual;
    }

    public void setSalarioMensual(double salarioMensual) {
        this.salarioMensual = salarioMensual;
    }

    public TipoDeTarjeta getTarjeta() {
        return tipoDeTarjeta;
    }

    public void setTarjeta(TipoDeTarjeta tarjeta) {
        this.tipoDeTarjeta = tarjeta;
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

    private void convertirTipoDeTrabajo(String tipoDeTrabajo) {
        if (tipoDeTrabajo.equalsIgnoreCase(TipoDeTrabajoDeCliente.INDEPENDIENTE.toString())) {
            this.tipoDeTrabajo = TipoDeTrabajoDeCliente.INDEPENDIENTE;
        } else {
            this.tipoDeTrabajo = TipoDeTrabajoDeCliente.DEPENDIENTE;
        }
    }

    private void convertirEstado(String estado) {
        if (estado.equalsIgnoreCase(EstadoSolicitudDeTarjeta.APROBADO.toString())) {
            this.estadoSolicitud = EstadoSolicitudDeTarjeta.APROBADO;
        } else if (estado.equalsIgnoreCase(EstadoSolicitudDeTarjeta.EN_ESPERA.toString())) {
            this.estadoSolicitud = EstadoSolicitudDeTarjeta.EN_ESPERA;
        } else {
            this.estadoSolicitud = EstadoSolicitudDeTarjeta.RECHAZADO;
        }
    }

    private void convertirTipoDeTarjeta(String tarjeta) {
        if (tarjeta.equalsIgnoreCase(TipoDeTarjeta.ORO.toString())) {
            this.tipoDeTarjeta = TipoDeTarjeta.ORO;
        } else if (tarjeta.equalsIgnoreCase(TipoDeTarjeta.PLATA.toString())) {
            this.tipoDeTarjeta = TipoDeTarjeta.PLATA;
        } else {
            this.tipoDeTarjeta = TipoDeTarjeta.BRONCE;
        }
    }

    public EstadoSolicitudDeTarjeta getEstadoSolicitud() {
        return estadoSolicitud;
    }

    public void setEstadoSolicitud(EstadoSolicitudDeTarjeta estadoSolicitud) {
        this.estadoSolicitud = estadoSolicitud;
    }

    public TipoDeTarjeta getTipoDeTarjeta() {
        return tipoDeTarjeta;
    }

    public void setTipoDeTarjeta(TipoDeTarjeta tipoDeTarjeta) {
        this.tipoDeTarjeta = tipoDeTarjeta;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    
    
}
