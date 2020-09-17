/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backend.pojos;

import java.util.Date;



/**
 *
 * @author jpmazate
 */
public class MovimientoMonetario {
    private String id;
    private String noCuenta;
    private double monto;
    private Date fecha;
    private String tipo;
    private String descripcion;

    public MovimientoMonetario(  String noCuenta, double monto, Date fecha, String tipo, String descripcion) {
        this.noCuenta = noCuenta;
        this.monto = monto;
        this.fecha = fecha;
        this.tipo = tipo;
        this.descripcion = descripcion;
    } 

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNoCuenta() {
        return noCuenta;
    }

    public void setNoCuenta(String noCuenta) {
        this.noCuenta = noCuenta;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
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
    
  
}


