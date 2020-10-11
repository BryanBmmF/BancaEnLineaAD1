/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backend.pojos;

import backend.enums.EstadosPrestamos;
import backend.enums.TiposPrestamos;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author jpmazate
 */
public class Prestamo {

    private Integer id;
    private String dpiCuentaHabiente;
    private Double montoTotal;
    private Double deudaRestante;
    private String tasaInteres;
    private Integer cantidadMeses;
    private String tipoPrestamo;
    private String descripcion;
    private Timestamp fechaVencimiento;
    private String estado;

    private final Double TASA_HIPOTECARIO = 0.05;
    private final Double TASA_PERSONAL = 0.03;

    public Prestamo(String dpiCuentaHabiente, Double montoDeseado, Integer meses, String tipoPrestamo, String descripcion) {
        double monto = calcularMontoTotal(montoDeseado, meses, tipoPrestamo);
        this.dpiCuentaHabiente = dpiCuentaHabiente;
        this.montoTotal = monto;
        this.deudaRestante = monto;
        this.tasaInteres = obtenerTasaInteres(tipoPrestamo);
        this.cantidadMeses = meses;
        this.tipoPrestamo = tipoPrestamo;
        this.descripcion = descripcion;
        this.fechaVencimiento = obtenerFechaVencimiento(meses);
        this.estado = EstadosPrestamos.ACTIVO.toString();
    }

    public String obtenerTasaInteres(String tipoPrestamo) {
        String tasa="";
        if (tipoPrestamo.equals(TiposPrestamos.HIPOTECARIO.toString())) {
            tasa = TASA_HIPOTECARIO+"";
        } else if (tipoPrestamo.equals(TiposPrestamos.PERSONAL.toString())) {
            tasa = TASA_PERSONAL+"";
        }
        return tasa;
    }
    
    
    public Timestamp obtenerFechaVencimiento(int meses){
        Date fechaActual =  new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fechaActual);
        calendar.add(Calendar.MONTH, meses);
        Date fechaVencimiento = calendar.getTime();
        Timestamp fechaTimestamp = new Timestamp(fechaVencimiento.getTime());
        return fechaTimestamp;   
    }

    public double calcularMontoTotal(double montoInicial, int meses, String tipoPrestamo) {
        Double resultado = 0.0;
        if (tipoPrestamo.equals(TiposPrestamos.HIPOTECARIO.toString())) {
            resultado = montoInicial + (montoInicial * meses * TASA_HIPOTECARIO);
        } else if (tipoPrestamo.equals(TiposPrestamos.PERSONAL.toString())) {
            resultado = montoInicial + (montoInicial * meses * TASA_PERSONAL);
        }

        return resultado;

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDpiCuentaHabiente() {
        return dpiCuentaHabiente;
    }

    public void setDpiCuentaHabiente(String dpiCuentaHabiente) {
        this.dpiCuentaHabiente = dpiCuentaHabiente;
    }

    public Double getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(Double montoTotal) {
        this.montoTotal = montoTotal;
    }

    public Double getDeudaRestante() {
        return deudaRestante;
    }

    public void setDeudaRestante(Double deudaRestante) {
        this.deudaRestante = deudaRestante;
    }

    public String getTasaInteres() {
        return tasaInteres;
    }

    public void setTasaInteres(String tasaInteres) {
        this.tasaInteres = tasaInteres;
    }

    public Integer getCantidadMeses() {
        return cantidadMeses;
    }

    public void setCantidadMeses(Integer cantidadMeses) {
        this.cantidadMeses = cantidadMeses;
    }

    public String getTipoPrestamo() {
        return tipoPrestamo;
    }

    public void setTipoPrestamo(String tipoPrestamo) {
        this.tipoPrestamo = tipoPrestamo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Timestamp getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(Timestamp fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

}
