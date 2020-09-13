package dev.com.j3b.modelos;

import dev.com.j3b.enums.EstadoDeCuenta;
import dev.com.j3b.enums.PeriodoDeInteresDeCuenta;
import dev.com.j3b.enums.TipoDeCuenta;

public class Cuenta {

    private String noCuentaBancaria;
    private String dpiCliente;
    private TipoDeCuenta tipoDeCuenta;
    private double tasaInteres;
    private PeriodoDeInteresDeCuenta periodoInteres;
    private EstadoDeCuenta estadoDeCuenta;
    private double saldo;


    public Cuenta(){

    }

    /*public Cuenta(String noCuentaBancaria, double saldo){
        this.noCuentaBancaria=noCuentaBancaria;
        this.saldo=saldo;
    }*/

    public String getNoCuentaBancaria() {
        return noCuentaBancaria;
    }

    public void setNoCuentaBancaria(String noCuentaBancaria) {
        this.noCuentaBancaria = noCuentaBancaria;
    }

    public String getDpiCliente() {
        return dpiCliente;
    }

    public void setDpiCliente(String dpiCliente) {
        this.dpiCliente = dpiCliente;
    }

    public TipoDeCuenta getTipoDeCuenta() {
        return tipoDeCuenta;
    }

    public void setTipoDeCuenta(TipoDeCuenta tipoDeCuenta) {
        this.tipoDeCuenta = tipoDeCuenta;
    }

    public double getTasaInteres() {
        return tasaInteres;
    }

    public void setTasaInteres(double tasaInteres) {
        this.tasaInteres = tasaInteres;
    }

    public PeriodoDeInteresDeCuenta getPeriodoInteres() {
        return periodoInteres;
    }

    public void setPeriodoInteres(PeriodoDeInteresDeCuenta periodoInteres) {
        this.periodoInteres = periodoInteres;
    }

    public EstadoDeCuenta getEstadoDeCuenta() {
        return estadoDeCuenta;
    }

    public void setEstadoDeCuenta(EstadoDeCuenta estadoDeCuenta) {
        this.estadoDeCuenta = estadoDeCuenta;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    @Override
    public String toString(){
        return "No.cuenta:"+noCuentaBancaria+"\n" +
                "Saldo:"+saldo;
    }
}
