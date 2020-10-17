/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backend.controladores;

import backend.database.ConexionBD;
import backend.pojos.TablaModelo;
import backend.pojos.UsuarioAdministrador;
import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author jpmazate
 */
public class ControladorTarjeta {
    
    
    private Connection conexion;
    private PreparedStatement prepared;
    private ResultSet result;

    public ControladorTarjeta() {
        conexion = ConexionBD.getInstance();
    }
    
    public void llenarUsuarios(TablaModelo modelo) {
        PreparedStatement declaracion;
        try {
            declaracion = conexion.prepareStatement("SELECT NO_TARJETA,TIPO,LIMITE,DPI_CUENTA_HABIENTE,ESTADO,DEUDA_ACTUAL,TASA_INTERES FROM TARJETA ORDER BY ESTADO,NO_TARJETA");
            ResultSet resultado = declaracion.executeQuery();
            while (resultado.next()) {
                Object objeto[] = new Object[7];
                objeto[0] = resultado.getString(1);
                objeto[1] = resultado.getString(2);
                objeto[2] = resultado.getString(3);
                objeto[3] = resultado.getString(4);
                objeto[4] = resultado.getString(5);
                objeto[5] = resultado.getString(6);
                objeto[6] = resultado.getString(7);                
                modelo.addRow(objeto);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Problema al cargar los usuarios");
        }
    }
    
   
    
    public boolean cambiarEstadoTarjeta(String noTarjeta, String estado) {
        try {
            PreparedStatement declaracion;
            declaracion = conexion.prepareStatement("UPDATE TARJETA SET ESTADO=? WHERE NO_TARJETA=?");
            declaracion.setString(1, estado);
            declaracion.setString(2, noTarjeta);

            declaracion.executeUpdate();
            return true;
        } catch (HeadlessException | SQLException e) {
            e.printStackTrace();
            return false;
        }

    }
    
}
