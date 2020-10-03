/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backend.pojos;

import backend.enums.EstadoTarjeta;
import backend.enums.TipoDeTarjetaSolicitud;
import backend.enums.TipoTarjeta;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 *
 * @author jes
 */
public class Tarjeta {

    private static final String LIMITE_ORO = "100000";
    private static final String LIMITE_PLATA = "50000";
    private static final String LIMITE_BRONCE = "25000";

    private static final String TASA_INTERES_ORO = "0.05";
    private static final String TASA_INTERES_PLATA = "0.03";
    private static final String TASA_INTERES_BRONCE = "0.02";

    private String numeroDeTarjeta;
    private String numeroCuenta;
    private TipoTarjeta tipoTarjeta;
    private String limite;
    private String dpiCuentaHabiente;
    private EstadoTarjeta estadoTarjeta;
    private double deudaActual;
    private Timestamp fechaVencimiento;
    private String codigoCVC;
    private String tasaInteres;

    /***
     * Para creacion de tarjeta de CREDITO
     * @param tipo
     * @param dpiCuentaHabiente 
     */
    public Tarjeta(TipoDeTarjetaSolicitud tipo, String dpiCuentaHabiente) {
        this.numeroDeTarjeta = generarNumeroDeTarjeta();
        this.numeroCuenta = null;
        this.tipoTarjeta = TipoTarjeta.CREDITO;
        this.limite = establecerLimiteDeTarjeta(tipo);
        this.dpiCuentaHabiente = dpiCuentaHabiente;
        this.estadoTarjeta = EstadoTarjeta.ACTIVA;
        this.deudaActual = 0;
        this.fechaVencimiento = generarFechaDeCaducidad();
        this.codigoCVC = generarCodigoDeSeguridad();
        this.tasaInteres=establecerTasaDeInteres(tipo);
        
    }
    
    /**
     * Para creacion de tarjeta de DEBITO
     * @param dpiCuentaHabiente 
     * @param numeroDeCuenta 
     */
    public Tarjeta(String dpiCuentaHabiente, String numeroDeCuenta){
        this.numeroDeTarjeta = generarNumeroDeTarjeta();
        this.numeroCuenta = numeroDeCuenta;
        this.tipoTarjeta = TipoTarjeta.DEBITO;
        this.dpiCuentaHabiente = dpiCuentaHabiente;
        this.estadoTarjeta = EstadoTarjeta.ACTIVA;
        this.fechaVencimiento = generarFechaDeCaducidad();
        this.codigoCVC = generarCodigoDeSeguridad();
    }

    public String getNumeroDeTarjeta() {
        return numeroDeTarjeta;
    }

    public void setNumeroDeTarjeta(String numeroDeTarjeta) {
        this.numeroDeTarjeta = numeroDeTarjeta;
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public TipoTarjeta getTipoTarjeta() {
        return tipoTarjeta;
    }

    public void setTipoTarjeta(TipoTarjeta tipoTarjeta) {
        this.tipoTarjeta = tipoTarjeta;
    }

    public String getLimite() {
        return limite;
    }

    public void setLimite(String limite) {
        this.limite = limite;
    }

    public String getDpiCuentaHabiente() {
        return dpiCuentaHabiente;
    }

    public void setDpiCuentaHabiente(String dpiCuentaHabiente) {
        this.dpiCuentaHabiente = dpiCuentaHabiente;
    }

    public EstadoTarjeta getEstadoTarjeta() {
        return estadoTarjeta;
    }

    public void setEstadoTarjeta(EstadoTarjeta estadoTarjeta) {
        this.estadoTarjeta = estadoTarjeta;
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

    public String getTasaInteres() {
        return tasaInteres;
    }

    public void setTasaInteres(String tasaInteres) {
        this.tasaInteres = tasaInteres;
    }

    
    
    
    private String establecerLimiteDeTarjeta(TipoDeTarjetaSolicitud tipo) {
        switch (tipo) {
            case ORO:
                return LIMITE_ORO;
            case PLATA:
                return LIMITE_PLATA;
            default:
                return LIMITE_BRONCE;
        }
    }

    private String establecerTasaDeInteres(TipoDeTarjetaSolicitud tipo) {
        switch (tipo) {
            case ORO:
                return TASA_INTERES_ORO;
            case PLATA:
                return TASA_INTERES_PLATA;
            default:
                return TASA_INTERES_BRONCE;
        }
    }

    private String generarNumeroDeTarjeta() {
        int numero1, numero2;
        String numeroDeTarjeta = "";
        do {
            numero1 = (int) (100000000 * Math.random());
            numero2 = (int) (100000000 * Math.random());
            numeroDeTarjeta = String.valueOf(numero1) + String.valueOf(numero2);
        } while (numeroDeTarjeta.length() < 16);
        return numeroDeTarjeta;
    }

    private Timestamp generarFechaDeCaducidad() {
        LocalDate fecha = LocalDate.now();
        int dia = fecha.getDayOfMonth();
        int restaDias = dia-1;
        fecha=fecha.minusDays(restaDias);
        long years = 4L;
        fecha = fecha.plusYears(years);
        return new Timestamp(Date.valueOf(fecha).getTime());
    }

    private String generarCodigoDeSeguridad() {
        int numero;
        do {
            numero = (int) (1000 * Math.random());;
        } while (String.valueOf(numero).length() < 3);
        return String.valueOf(numero);

    }

}
