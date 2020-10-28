package dev.com.j3b.manejadorLogIn;

public class CuentaSaldo {

    private String numeroCuenta;
    private Double saldoActual;

    public CuentaSaldo(String numeroCuenta, Double saldoActual) {
        this.numeroCuenta = numeroCuenta;
        this.saldoActual = saldoActual;
    }

    public String toString() {
        return String.format("Cuenta: "+ numeroCuenta+" - Saldo: Q"+saldoActual);
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public Double getSaldoActual() {
        return saldoActual;
    }

    public void setSaldoActual(Double saldoActual) {
        this.saldoActual = saldoActual;
    }
}
