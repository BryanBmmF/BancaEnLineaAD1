/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backend.controladores;

import backend.database.ConexionBD;
import backend.enums.TipoCuentaHabiente;
import backend.pojos.CuentaHabiente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jesfrin
 */
public class ControladorCuentaHabiente {

    public static Connection connection = null;
    private PreparedStatement prepState;
    private ResultSet res;
    //Consultas
    private static final String INGRESO_DE_CUENTA_HABIENTE = "INSERT INTO CUENTA_HABIENTE VALUES(?,?,?,?,?,?,?,?,?)";
    private static final String BUSQUEDA_DE_CUENTA_HABIENTES = "SELECT * FROM CUENTA_HABIENTE";

    public ControladorCuentaHabiente() {
        connection = ConexionBD.getInstance();
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
            prepState.setString(9, TipoCuentaHabiente.NUEVO.toString());
            prepState.executeUpdate();
            prepState.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }
/**
 * Devuelve todos los cuenta Habientes del sistema
 * @return 
 */
    public ArrayList<CuentaHabiente> busquedaDeCunetaHabientes() {
        ArrayList<CuentaHabiente> cuentas = new ArrayList<>();
        TipoCuentaHabiente tipo = TipoCuentaHabiente.NUEVO;
        try {
            prepState = connection.prepareStatement(BUSQUEDA_DE_CUENTA_HABIENTES);
            res = prepState.executeQuery();
            while (res.next()) {
                if (res.getString(9).equalsIgnoreCase(TipoCuentaHabiente.NUEVO.toString())) {
                    tipo = TipoCuentaHabiente.NUEVO;
                }
                cuentas.add(new CuentaHabiente(res.getString(1), res.getString(2), res.getString(3), res.getDate(4), res.getString(5), res.getString(6), res.getString(7), res.getString(8), tipo));
            }
            prepState.close();
            res.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
        return cuentas;
    }

}
