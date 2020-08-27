/*
 * Clase CuentaHabiente como Modelo
 */
package backend.pojos;

import static backend.pojos.Cuenta.PROP_NUM_CUENTA;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.sql.Date;

/**
 *
 * @author jesfrin
 */
public class CuentaHabiente implements Serializable{
    
    private String dpiCliente;
    private String nombres;
    private String apellidos;
    private Date fecha_nacimiento;
    private String direccion;
    private String telefono;
    private String celular;
    private String email;
    
    /**
     * Para creacion y recuperacion
     * @param dpiCliente
     * @param nombres
     * @param apellidos
     * @param fecha_nacimiento
     * @param direccion
     * @param telefono
     * @param celular
     * @param email 
     */
    
    public CuentaHabiente(String dpiCliente, String nombres, String apellidos, Date fecha_nacimiento, String direccion, String telefono, String celular, String email) {
        this.dpiCliente = dpiCliente;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.fecha_nacimiento = fecha_nacimiento;
        this.direccion = direccion;
        this.telefono = telefono;
        this.celular = celular;
        this.email = email;
    }
    
    public static final String PROP_DPI_CLIENTE = "dpiCliente";
    public static final String PROP_NOMBRES = "nombres";
    public static final String PROP_APELLIDOS = "apellidos";
    public static final String PROP_FECHA_NAC = "fecha_nacimiento";
    public static final String PROP_DIRECCION = "direccion";
    public static final String PROP_TELEFONO = "telefono";
    public static final String PROP_CELULAR = "celular";
    public static final String PROP_EMAIL = "email";
    //agregamos soporte para lanzar eventos al momento que se cambie valor de un miembro
    private PropertyChangeSupport propertySupport = new PropertyChangeSupport(this);

    public CuentaHabiente() {
    }
    
    
    public String getDpiCliente() {
        return dpiCliente;
    }

    public void setDpiCliente(String dpiCliente) {
        String anterior = this.dpiCliente;
	this.dpiCliente = dpiCliente;
	propertySupport.firePropertyChange(PROP_DPI_CLIENTE, anterior, dpiCliente);
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        String anterior = this.nombres;
	this.nombres = nombres;
	propertySupport.firePropertyChange(PROP_NOMBRES, anterior, nombres);
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        String anterior = this.apellidos;
	this.apellidos = apellidos;
	propertySupport.firePropertyChange(PROP_APELLIDOS, anterior, apellidos);
        
    }

    public Date getFecha_nacimiento() {
        return fecha_nacimiento;
    }

    public void setFecha_nacimiento(Date fecha_nacimiento) {
        Date anterior = this.fecha_nacimiento;
	this.fecha_nacimiento = fecha_nacimiento;
	propertySupport.firePropertyChange(PROP_FECHA_NAC, anterior, fecha_nacimiento);
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        String anterior = this.direccion;
	this.direccion = direccion;
	propertySupport.firePropertyChange(PROP_DIRECCION, anterior, direccion);
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        String anterior = this.telefono;
	this.telefono = telefono;
	propertySupport.firePropertyChange(PROP_TELEFONO, anterior, telefono);
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        String anterior = this.celular;
	this.celular = celular;
	propertySupport.firePropertyChange(PROP_CELULAR, anterior, celular);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        String anterior = this.email;
	this.email = email;
	propertySupport.firePropertyChange(PROP_EMAIL, anterior, email);
    }
    @Override//Puede ser cualquier otro metodo que copie los valores de los miembros a un nuevo objeto Empleado
    public CuentaHabiente clone() {
            return new CuentaHabiente(this.dpiCliente, this.nombres, this.apellidos, this.fecha_nacimiento, this.direccion, this.telefono, this.celular, this.email);
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
