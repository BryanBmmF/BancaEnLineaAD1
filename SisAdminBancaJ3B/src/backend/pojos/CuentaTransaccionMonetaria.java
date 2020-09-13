package backend.pojos;

import backend.enums.EstadoCuenta;
import backend.enums.PeriodoInteres;
import backend.enums.TipoCuenta;

/**
 *
 * @author jpmazate
 */
public class CuentaTransaccionMonetaria {

    private String numCuenta;
    private String dpiCliente;
    private String tipo;
    private String estado;
    private double saldo;
    private String nombre;
    private String correoElectronico;
    

    public CuentaTransaccionMonetaria(String numCuenta, String dpiCliente, String tipo, String estado, double saldo, String nombre, String correo) {
        this.numCuenta = numCuenta;
        this.dpiCliente = dpiCliente;
        this.tipo = tipo;
        this.estado = estado;
        this.saldo = saldo;
        this.nombre = nombre;
        this.correoElectronico = correo;
    }

    public CuentaTransaccionMonetaria(String numCuenta) {
        this.numCuenta = numCuenta;
        this.estado="";
    }

    
    

    public String getNumCuenta() {
        return numCuenta;
    }

    public void setNumCuenta(String numCuenta) {
        this.numCuenta = numCuenta;
    }

    public String getDpiCliente() {
        return dpiCliente;
    }

    public void setDpiCliente(String dpiCliente) {
        this.dpiCliente = dpiCliente;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

     

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

   
    
    
    

}
