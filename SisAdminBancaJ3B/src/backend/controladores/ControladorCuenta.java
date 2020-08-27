/*
 * Controlador de Cuentas
 */
package backend.controladores;

import static backend.controladores.ControladorCuentaHabiente.connection;
import backend.database.ConexionBD;
import backend.enums.EstadoCuenta;
import backend.enums.PeriodoInteres;
import backend.enums.TipoCuenta;
import backend.pojos.Cuenta;
import backend.pojos.CuentaHabiente;
import static java.lang.Math.random;
import static java.lang.StrictMath.random;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import javax.swing.JOptionPane;

/**
 *
 * @author bryan
 */
public class ControladorCuenta {
    public static Connection connection = null;
    private PreparedStatement prepState;
    private ResultSet res;
    //Consultas
    private static final String INGRESO_DE_CUENTA = "INSERT INTO CUENTA VALUES(?,?,?,?,?,?,?)";
    private static final String BUSQUEDA_DE_CUENTAS_CLIENTE = "SELECT *FROM CUENTA WHERE dpi_cliente=? AND estado=?";
    private static final String CANCELAR_CUENTA = "CALL cancelar_cuenta(?,?,?)";

    public ControladorCuenta() {
         connection = ConexionBD.getInstance();
    }
    
    /**
     * Registra una cuenta, devuelve false si no se ha podido crear
     *
     * @param cuenta
     * @return true or false
     */
    public boolean registroCuenta(Cuenta cuenta) {
        try {
            prepState = connection.prepareStatement(INGRESO_DE_CUENTA);
            prepState.setString(1, cuenta.getNumCuenta());
            prepState.setString(2, cuenta.getDpiCliente());
            prepState.setString(3, cuenta.getTipo().toString());
            prepState.setDouble(4, cuenta.getTasaInteres());
            prepState.setString(5, cuenta.getPeriodoInteres().toString());
            prepState.setString(6, cuenta.getEstado().toString());
            prepState.setDouble(7, cuenta.getSaldo());
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
     * Cancela una cuenta, devuelve false si no se ha podido cancelar
     *
     * @param cuenta
     * @return true or false
     */
    public boolean cancelarCuenta(String numCuenta, String motivo) {
        try {
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            prepState = connection.prepareStatement(CANCELAR_CUENTA);
            prepState.setString(1, numCuenta);
            prepState.setString(2, motivo);
            prepState.setTimestamp(3, timestamp);
            prepState.executeUpdate();
            prepState.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "No se pudo cancelar la cuenta, error:" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }
    
    /**
     * Devuelve todos las cuentas de un  CuentaHabientes del sistema
     *
     * @return
     */
    public ArrayList<Cuenta> busquedaDeCunetasCliente(String dpiCliente, String estado) {
        ArrayList<Cuenta> cuentas = new ArrayList<>();
        try {
            prepState = connection.prepareStatement(BUSQUEDA_DE_CUENTAS_CLIENTE);
            prepState.setString(1, dpiCliente);
            prepState.setString(2, estado);
            res = prepState.executeQuery();
            while (res.next()) {
                cuentas.add(new Cuenta(res.getString(1), res.getString(2), Enum.valueOf(TipoCuenta.class, res.getString(3)), res.getDouble(4), 
                        Enum.valueOf(PeriodoInteres.class, res.getString(5)), Enum.valueOf(EstadoCuenta.class, res.getString(6)), res.getDouble(7)));
            }
            prepState.close();
            res.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
        return cuentas;
    }
    
    /* Función para generar un nuemero de cuenta de 10 numeros*/
    public String generarCuenta() {
        String numeroDev = "";
        // Conjunto de números ya usados
        Set<Integer> alreadyUsedNumbers = new HashSet<>();
        Random random = new Random();
        // Vamos a generar 10 números aleatorios sin repetición
        while (alreadyUsedNumbers.size()<5) {
           // Número aleatorio entre 0 y 40, excluido el 40.  
           int randomNumber = (int)Math.floor(Math.random()*(90-10+1)+10);
           // Si no lo hemos usado ya, lo usamos y lo metemos en el conjunto de usados.
           if (!alreadyUsedNumbers.contains(randomNumber)){
              alreadyUsedNumbers.add(randomNumber);
              numeroDev +=""+randomNumber;
           }
        }
        return numeroDev;
    
    }
    
}
