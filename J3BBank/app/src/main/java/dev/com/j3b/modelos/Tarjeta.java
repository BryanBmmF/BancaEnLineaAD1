package dev.com.j3b.modelos;

import java.sql.Timestamp;

import dev.com.j3b.enums.EstadoDeTarjeta;
import dev.com.j3b.enums.LimiteInteresDeTarjeta;
import dev.com.j3b.enums.TipoDeTarjetaCreada;

public class Tarjeta {

    private String noTarjeta;
    private String noCuenta;
    private TipoDeTarjetaCreada tipoDeTarjeta;
    private String dpiCuentaHabiente;
    private EstadoDeTarjeta estado;
    private double deudaActual;
    private Timestamp fechaVencimiento;
    private String codigoCVC;
    private LimiteInteresDeTarjeta limiteInteres;


    public Tarjeta(){

    }

    public Tarjeta(String noTarjeta, String noCuenta, TipoDeTarjetaCreada tipoDeTarjeta, String dpiCuentaHabiente, EstadoDeTarjeta estado, double deudaActual, Timestamp fechaVencimiento, String codigoCVC, LimiteInteresDeTarjeta limiteInteres) {
        this.noTarjeta = noTarjeta;
        this.noCuenta = noCuenta;
        this.tipoDeTarjeta = tipoDeTarjeta;
        this.dpiCuentaHabiente = dpiCuentaHabiente;
        this.estado = estado;
        this.deudaActual = deudaActual;
        this.fechaVencimiento = fechaVencimiento;
        this.codigoCVC = codigoCVC;
        this.limiteInteres = limiteInteres;
    }


    public String getNoTarjeta() {
        return noTarjeta;
    }

    public void setNoTarjeta(String noTarjeta) {
        this.noTarjeta = noTarjeta;
    }

    public String getNoCuenta() {
        return noCuenta;
    }

    public void setNoCuenta(String noCuenta) {
        this.noCuenta = noCuenta;
    }

    public TipoDeTarjetaCreada getTipoDeTarjeta() {
        return tipoDeTarjeta;
    }

    public void setTipoDeTarjeta(TipoDeTarjetaCreada tipoDeTarjeta) {
        this.tipoDeTarjeta = tipoDeTarjeta;
    }

    public String getDpiCuentaHabiente() {
        return dpiCuentaHabiente;
    }

    public void setDpiCuentaHabiente(String dpiCuentaHabiente) {
        this.dpiCuentaHabiente = dpiCuentaHabiente;
    }

    public EstadoDeTarjeta getEstado() {
        return estado;
    }

    public void setEstado(EstadoDeTarjeta estado) {
        this.estado = estado;
    }

    public double getDeudaActual() {
        return deudaActual;
    }

    public void setDeudaActual(double deudaActual) {
        this.deudaActual = deudaActual;
    }

    public Timestamp getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(Timestamp fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public String getCodigoCVC() {
        return codigoCVC;
    }

    public void setCodigoCVC(String codigoCVC) {
        this.codigoCVC = codigoCVC;
    }

    public LimiteInteresDeTarjeta getLimiteInteres() {
        return limiteInteres;
    }

    public void setLimiteInteres(LimiteInteresDeTarjeta limiteInteres) {
        this.limiteInteres = limiteInteres;
    }
}
