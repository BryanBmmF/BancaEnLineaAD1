package dev.com.j3b.modelos;

import java.io.Serializable;

public class Usuario implements Serializable {

    String usuarioCliente, dpiCliente, contraseñaActual, fechaUltimoCambio, fechaCaducidad, contraseña1, contraseña2, estadoCuenta;
    Integer intentoIngresos;

    public Usuario() {
    }

    public Usuario(String usuarioCliente, String dpiCliente, String contraseñaActual, String fechaUltimoCambio, String fechaCaducidad, String contraseña1, String contraseña2, String estadoCuenta, Integer intentoIngresos) {
        this.usuarioCliente = usuarioCliente;
        this.dpiCliente = dpiCliente;
        this.contraseñaActual = contraseñaActual;
        this.fechaUltimoCambio = fechaUltimoCambio;
        this.fechaCaducidad = fechaCaducidad;
        this.contraseña1 = contraseña1;
        this.contraseña2 = contraseña2;
        this.estadoCuenta = estadoCuenta;
        this.intentoIngresos = intentoIngresos;
    }

    public String getFechaUltimoCambio() {
        return fechaUltimoCambio;
    }

    public void setFechaUltimoCambio(String fechaUltimoCambio) {
        this.fechaUltimoCambio = fechaUltimoCambio;
    }

    public String getUsuarioCliente() {
        return usuarioCliente;
    }

    public void setUsuarioCliente(String usuarioCliente) {
        this.usuarioCliente = usuarioCliente;
    }

    public String getDpiCliente() {
        return dpiCliente;
    }

    public void setDpiCliente(String dpiCliente) {
        this.dpiCliente = dpiCliente;
    }

    public String getContraseñaActual() {
        return contraseñaActual;
    }

    public void setContraseñaActual(String contraseñaActual) {
        this.contraseñaActual = contraseñaActual;
    }

    public String getFechaCaducidad() {
        return fechaCaducidad;
    }

    public void setFechaCaducidad(String fechaCaducidad) {
        this.fechaCaducidad = fechaCaducidad;
    }

    public String getContraseña1() {
        return contraseña1;
    }

    public void setContraseña1(String contraseña1) {
        this.contraseña1 = contraseña1;
    }

    public String getContraseña2() {
        return contraseña2;
    }

    public void setContraseña2(String contraseña2) {
        this.contraseña2 = contraseña2;
    }

    public String getEstadoCuenta() {
        return estadoCuenta;
    }

    public void setEstadoCuenta(String estadoCuenta) {
        this.estadoCuenta = estadoCuenta;
    }

    public Integer getIntentoIngresos() {
        return intentoIngresos;
    }

    public void setIntentoIngresos(Integer intentoIngresos) {
        this.intentoIngresos = intentoIngresos;
    }
}
