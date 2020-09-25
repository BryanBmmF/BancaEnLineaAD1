/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backend.controladores;

import backend.database.ConexionBD;
import backend.enums.EstadoSolicitudDeTarjeta;
import backend.pojos.SolicitudTarjeta;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jesfrin
 */
public class ControladorSolicitudDeTarjeta {
    
    private static final String CONSULTAR_SOLICITUDES="";
    private Connection conexion;

    public  ControladorSolicitudDeTarjeta() {
        conexion = ConexionBD.getInstance();
    }
    
    
    public LinkedList<SolicitudTarjeta> consultarSolicitudesDeTarjeta(EstadoSolicitudDeTarjeta estado){
        try {
            PreparedStatement declaracion = conexion.prepareStatement("SELECT ID_USUARIO_ADMIN FROM USUARIO_ADMINISTRADOR WHERE ID_USUARIO_ADMIN=? AND CONTRASENA=?");
        } catch (SQLException ex) {
            Logger.getLogger(ControladorSolicitudDeTarjeta.class.getName()).log(Level.SEVERE, null, ex);
        }
                return new LinkedList<SolicitudTarjeta>();

    }
    
    
}
