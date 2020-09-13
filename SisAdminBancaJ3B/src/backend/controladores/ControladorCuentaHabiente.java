/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backend.controladores;

import backend.database.ConexionBD;
import backend.pojos.Cuenta;
import backend.pojos.CuentaHabiente;
import backend.pojos.UsuarioCliente;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author jesfrin
 */
public class ControladorCuentaHabiente {

    public static Connection connection = null;
    private PreparedStatement prepState;
    private ResultSet res;
    //Consultas
    private static final String INGRESO_DE_CUENTA_HABIENTE = "INSERT INTO CUENTA_HABIENTE VALUES(?,?,?,?,?,?,?,?)";
    private static final String BUSQUEDA_DE_CUENTA_HABIENTES = "SELECT * FROM CUENTA_HABIENTE";
    private static final String MODIFICAR_CUENTA_HABIENTE = "UPDATE CUENTA_HABIENTE SET nombres=?,apellidos=?,fecha_nacimiento=?,direccion=?,telefono=?,celular=?,email=? WHERE dpi_cliente=?";
    private static final String ELIMINAR_CUENTA_HABIENTE = "DELETE FROM CUENTA_HABIENTE WHERE dpi_cliente=?";

    public ControladorCuentaHabiente() {
        connection = ConexionBD.getInstance();
    }

    /**
     * Se elimina la cuenta de un CUnetaHabiente, solo si es completamente nuevo(no posee relacion con otras tablas)
     * @param dpi
     * @return 
     */
    public boolean eliminarCuentaHabiente(String dpi) {
        try {
            prepState = connection.prepareStatement(ELIMINAR_CUENTA_HABIENTE);
            prepState.setString(1, dpi);
            prepState.executeUpdate();
            prepState.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Registra un cuenta habiente, devuelve false si no se ha podido crear
     *
     * @param cuentaHabiente
     * @return
     */
    public boolean registroCuentaHabiente(CuentaHabiente cuentaHabiente) {
        try {
            prepState = connection.prepareStatement(INGRESO_DE_CUENTA_HABIENTE);
            prepState.setString(1, cuentaHabiente.getDpiCliente());
            prepState.setString(2, cuentaHabiente.getNombres());
            prepState.setString(3, cuentaHabiente.getApellidos());
            prepState.setDate(4, cuentaHabiente.getFecha_nacimiento());
            prepState.setString(5, cuentaHabiente.getDireccion());
            prepState.setString(6, cuentaHabiente.getTelefono());
            prepState.setString(7, cuentaHabiente.getCelular());
            prepState.setString(8, cuentaHabiente.getEmail());
            prepState.executeUpdate();
            prepState.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "No se pudo ingresar la cuenta, error:" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    /**
     * Devuelve todos los cuenta Habientes del sistema
     *
     * @return
     */
    public ArrayList<CuentaHabiente> busquedaDeCunetaHabientes() {
        ArrayList<CuentaHabiente> cuentas = new ArrayList<>();
        try {
            prepState = connection.prepareStatement(BUSQUEDA_DE_CUENTA_HABIENTES);
            res = prepState.executeQuery();
            while (res.next()) {
                cuentas.add(new CuentaHabiente(res.getString(1), res.getString(2), res.getString(3), res.getDate(4), res.getString(5), res.getString(6), res.getString(7), res.getString(8)));
            }
            prepState.close();
            res.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
        return cuentas;
    }

    public boolean actualizarCuentaHabiente(CuentaHabiente cuentaHabiente) {
        try {
            prepState = connection.prepareStatement(MODIFICAR_CUENTA_HABIENTE);
            prepState.setString(1, cuentaHabiente.getNombres());
            prepState.setString(2, cuentaHabiente.getApellidos());
            prepState.setDate(3, cuentaHabiente.getFecha_nacimiento());
            prepState.setString(4, cuentaHabiente.getDireccion());
            prepState.setString(5, cuentaHabiente.getTelefono());
            prepState.setString(6, cuentaHabiente.getCelular());
            prepState.setString(7, cuentaHabiente.getEmail());
            prepState.setString(8, cuentaHabiente.getDpiCliente());
            System.out.println(prepState.toString());
            prepState.executeUpdate();
            prepState.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * *
     * Se verifican si los datos de un cuentaHabiente cuenta con los requisitos
     * para cada campo
     *
     * @param dpi
     * @param nombres
     * @param apellidos
     * @param fechaNacimeitno
     * @param direccion
     * @param telefono
     * @param celular
     * @param email
     * @return
     */
    public CuentaHabiente verificarDatosDeCuentaHabiente(String dpi, String nombres, String apellidos, Date fechaNacimeitno, String direccion, String telefono, String celular, String email) {
        if (dpi.isEmpty() || nombres.isEmpty() || apellidos.isEmpty() || fechaNacimeitno == null || direccion.isEmpty() || telefono.isEmpty() || celular.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Faltan campos obligatorios", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return null;
        }

        if (dpi.length() != 13) {
            JOptionPane.showMessageDialog(null, "Dpi debe tener 13 valores", "Error", JOptionPane.ERROR_MESSAGE);
            return null;

        }
        if (nombres.length() > 35) {
            JOptionPane.showMessageDialog(null, "Nombres solo puede tener 35 caracteres", "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }

        if (apellidos.length() > 35) {
            JOptionPane.showMessageDialog(null, "Apellidos solo puede tener 35 caracteres", "Error", JOptionPane.ERROR_MESSAGE);
            return null;

        }

        if (fechaNacimeitno == null) {
            JOptionPane.showMessageDialog(null, "Fecha incorrecta", "Error", JOptionPane.ERROR_MESSAGE);
            return null;

        }

        if (direccion.length() > 100) {
            JOptionPane.showMessageDialog(null, "Direccion solo puede tener 100 caracteres", "Error", JOptionPane.ERROR_MESSAGE);
            return null;

        }

        if (telefono.length() != 8) {
            JOptionPane.showMessageDialog(null, "Telefono debe tener 8 caracteres", "Error", JOptionPane.ERROR_MESSAGE);
            return null;

        }

        if (celular.length() != 8) {
            JOptionPane.showMessageDialog(null, "Celular debe tener 8 caracteres", "Error", JOptionPane.ERROR_MESSAGE);
            return null;

        }

        if (email.length() > 40 || !email.contains("@") || !email.contains(".")) {
            JOptionPane.showMessageDialog(null, "Email debe contener @ y .", "Error", JOptionPane.ERROR_MESSAGE);
            return null;

        }
        return new CuentaHabiente(dpi, nombres, apellidos, fechaNacimeitno, direccion, telefono, celular, email);

    }
    
    /**
     * Notifica por correo al Usuario registrado sus credenciales de acceso obtenidas por el banco 
     *
     * @param cuenta
     * @param cliente
     * @param correo
     */
    public void notificarCorreoCuentaHabiente(Cuenta cuenta, UsuarioCliente cliente, String correo){
        ControladorPeticionesHttp controlador = new ControladorPeticionesHttp();
        String numCuenta = cuenta.getNumCuenta();
        String tipo = cuenta.getTipo().toString();
        String saldo = "Q."+cuenta.getSaldo()+"0";
        String usuario = "";
        String pass = "";
        String fechaCaducidad = "";
        if (cliente!=null) {
            /*Registro por primera vez*/
            usuario = cliente.getUsuarioCliente();
            pass = cliente.getContrasenaCopia();
            fechaCaducidad = cliente.getFechaCaducidad().toString();
        } else {
            /*ya possee un usuario*/
            usuario = "Confirmado";
            pass = "Confirmado";
            fechaCaducidad = "Confirmada";
        }
        String params= "?Usuario="+usuario+"&Pass="+pass+"&FechaCaducidad="+fechaCaducidad+"&NumCuenta="+numCuenta+"&Tipo="+tipo+"&Saldo="+saldo+"&Email="+correo;
        //String url = "http://192.168.20.5/j3b/servicios/switfMailer/envioDeCorreo.php"+params;
        String url = "http://192.168.0.200/j3b/servicios/switfMailer/envioDeCorreo.php"+params;
        //String url = "http://192.168.1.18/j3b/servicios/switfMailer/envioDeCorreo.php"+params;
        
		String respuesta = "";
		try {
			respuesta = controlador.peticionHttpGetEnvioCorreo(url);
			System.out.println("La respuesta es:\n" + respuesta);
		} catch (Exception e) {
			// Manejar excepción
			e.printStackTrace();
		}
        
    }
    
}
