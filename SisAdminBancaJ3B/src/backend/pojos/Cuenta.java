/*
 * Clase Cuenta como Modelo
 */
package backend.pojos;

import backend.enums.EstadoCuenta;
import backend.enums.PeriodoInteres;
import backend.enums.TipoCuenta;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;

/**
 *
 * @author bryan
 */
public class Cuenta implements Serializable{
    
    private String numCuenta;
    private String dpiCliente;
    private TipoCuenta tipo;
    private double tasaInteres;
    private PeriodoInteres periodoInteres;
    private EstadoCuenta estado;
    private double saldo;
    
    public static final String PROP_NUM_CUENTA = "numCuenta";
    public static final String PROP_DPI_CLIENTE = "dpiCliente";
    public static final String PROP_TIPO = "tipo";
    public static final String PROP_TASA_INTERES = "tasaInteres";
    public static final String PROP_PERIODO_INTERES = "periodoInteres";
    public static final String PROP_ESTADO = "estado";
    public static final String PROP_SALDO = "saldo";
    //agregamos soporte para lanzar eventos al momento que se cambie valor de un miembro
    private PropertyChangeSupport propertySupport = new PropertyChangeSupport(this);

    public Cuenta() {
    }

    public Cuenta(String numCuenta, String dpiCliente, TipoCuenta tipo, double tasaInteres, PeriodoInteres periodoInteres, EstadoCuenta estado, double saldo) {
        this.numCuenta = numCuenta;
        this.dpiCliente = dpiCliente;
        this.tipo = tipo;
        this.tasaInteres = tasaInteres;
        this.periodoInteres = periodoInteres;
        this.estado = estado;
        this.saldo = saldo;
    }
    
    @Override
    public String toString() {
        return "\nNo. Cuenta:" + this.numCuenta + "\nTipo:" + this.tipo.toString()
                + "\nSaldo:" + this.saldo;
    }

    public String getNumCuenta() {
        return numCuenta;
    }

    public void setNumCuenta(String numCuenta) {
        String anterior = this.numCuenta;
	this.numCuenta = numCuenta;
	propertySupport.firePropertyChange(PROP_NUM_CUENTA, anterior, numCuenta);
    }

    public String getDpiCliente() {
        return dpiCliente;
    }

    public void setDpiCliente(String dpiCliente) {
        String anterior = this.dpiCliente;
	this.dpiCliente = dpiCliente;
	propertySupport.firePropertyChange(PROP_DPI_CLIENTE, anterior, dpiCliente);
    }

    public TipoCuenta getTipo() {
        return tipo;
    }

    public void setTipo(TipoCuenta tipo) {
        TipoCuenta anterior = this.tipo;
	this.tipo = tipo;
	propertySupport.firePropertyChange(PROP_TIPO, anterior, tipo);
    }

    public double getTasaInteres() {
        return tasaInteres;
    }

    public void setTasaInteres(double tasaInteres) {
        double anterior = this.tasaInteres;
	this.tasaInteres = tasaInteres;
	propertySupport.firePropertyChange(PROP_TASA_INTERES, anterior, tasaInteres);
    }

    public PeriodoInteres getPeriodoInteres() {
        return periodoInteres;
    }

    public void setPeriodoInteres(PeriodoInteres periodoInteres) {
        PeriodoInteres anterior = this.periodoInteres;
	this.periodoInteres = periodoInteres;
	propertySupport.firePropertyChange(PROP_PERIODO_INTERES, anterior, periodoInteres);
    }

    public EstadoCuenta getEstado() {
        return estado;
    }

    public void setEstado(EstadoCuenta estado) {
        EstadoCuenta anterior = this.estado;
	this.estado = estado;
	propertySupport.firePropertyChange(PROP_ESTADO, anterior, estado);
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        double anterior = this.saldo;
	this.saldo = saldo;
	propertySupport.firePropertyChange(PROP_SALDO, anterior, saldo);
    }
    
    @Override//Puede ser cualquier otro metodo que copie los valores de los miembros a un nuevo objeto Empleado
    public Cuenta clone() {
            return new Cuenta(this.numCuenta, this.dpiCliente, this.tipo, this.tasaInteres, this.periodoInteres, this.estado, this.saldo);
    }
    
    /**
     * Metodo especifico para agregar un escucha de cambios
     *
     * @param listener
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
            propertySupport.addPropertyChangeListener(listener);
    }

    /**
     * Metodo especifico para quitar un escucha de cambios
     *
     * @param listener
     */
    public void removePropertyChangeListener(PropertyChangeListener listener) {
            propertySupport.removePropertyChangeListener(listener);
    }
    
    
}
