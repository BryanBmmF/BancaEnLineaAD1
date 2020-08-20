/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backend.pojos;

import backend.enums.EstadoUsuarioCliente;
import backend.manejadores.Md5;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 *
 * @author jesfrin
 */
public class UsuarioCliente {

    private String usuarioCliente;
    private String dpiCliente;
    private String contrasena;
    private Date fechaUltimoCambio;
    private Date fechaCaducidad;
    private String contrasena1;
    private String contrasena2;
    private EstadoUsuarioCliente estado;
    private int intentoDeIngresos;
    //Extra
    private String contrasenaCopia;

    public UsuarioCliente(String dpi) {
        this.usuarioCliente = generarUsuario();
        this.dpiCliente = dpi;
        this.contrasena = Md5.getMD5(generarContrasena());
        this.fechaCaducidad = generarFechaDeCaducidad();
        this.estado = EstadoUsuarioCliente.NUEVO;
        this.intentoDeIngresos = 0;
    }

    @Override
    public String toString() {
        return "Dpi:" + this.dpiCliente + "\nUsuario:" + this.usuarioCliente
                + "\nContrasena:" + this.contrasenaCopia
                + "\nFechaDeCaducidad:" + this.fechaCaducidad;
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

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public Date getFechaUltimoCmabio() {
        return fechaUltimoCambio;
    }

    public void setFechaUltimoCmabio(Date fechaUltimoCmabio) {
        this.fechaUltimoCambio = fechaUltimoCmabio;
    }

    public Date getFechaCaducidad() {
        return fechaCaducidad;
    }

    public void setFechaCaducidad(Date fechaCaducidad) {
        this.fechaCaducidad = fechaCaducidad;
    }

    public String getContrasena1() {
        return contrasena1;
    }

    public void setContrasena1(String contrasena1) {
        this.contrasena1 = contrasena1;
    }

    public String getContrasena2() {
        return contrasena2;
    }

    public void setContrasena2(String contrasena2) {
        this.contrasena2 = contrasena2;
    }

    public EstadoUsuarioCliente getEstado() {
        return estado;
    }

    public void setEstado(EstadoUsuarioCliente estado) {
        this.estado = estado;
    }

    public int getIntentoDeIngresos() {
        return intentoDeIngresos;
    }

    public void setIntentoDeIngresos(int intentoDeIngresos) {
        this.intentoDeIngresos = intentoDeIngresos;
    }

    public String getContrasenaCopia() {
        return contrasenaCopia;
    }

    public void setContrasenaCopia(String contrasenaCopia) {
        this.contrasenaCopia = contrasenaCopia;
    }
    
    

    public String generarUsuario() {
        Integer numero;
        do {
            numero = (int) (100000000 * Math.random());;
        } while (String.valueOf(numero).length() < 8);
        return String.valueOf(numero);
    }

    public Date generarFechaDeCaducidad() {
        LocalDate fecha = LocalDate.now();
        long meses = 3L;
        fecha = fecha.plusMonths(meses);
        return Date.valueOf(fecha);
    }

    public String generarContrasena() {
        SecureRandom random = new SecureRandom();
        String text = new BigInteger(130, random).toString(32);
        this.contrasenaCopia = text.substring(1, 9);
        return this.contrasenaCopia;
    }
}
