/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backend.controladores;

import backend.database.ConexionBD;
import backend.enums.EstadoSolicitudDeTarjeta;
import backend.enums.TipoDeTarjeta;
import backend.pojos.SolicitudTarjeta;
import java.sql.ResultSet;
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

    private static final String CONSULTAR_SOLICITUDES = "SELECT "
            + "id_solicitud_tarjeta,tipo_de_trabajo,dpi_cliente,empresa,estado,salario_mensual,tarjeta,descripcion,fecha_solicitud,fecha_verificacion "
            + "FROM SOLICITUD_TARJETA WHERE estado=?";
    private Connection conexion;
    private PreparedStatement prepared;
    private ResultSet result;

    public ControladorSolicitudDeTarjeta() {
        conexion = ConexionBD.getInstance();
    }

    public LinkedList<SolicitudTarjeta> consultarSolicitudesDeTarjeta(EstadoSolicitudDeTarjeta estado) {
        LinkedList<SolicitudTarjeta> listaSolicitudes= new LinkedList<>();
        try {
            prepared = conexion.prepareStatement(CONSULTAR_SOLICITUDES);
            prepared.setString(1, estado.toString());
            result = prepared.executeQuery();
            while(result.next()){
                listaSolicitudes.add(new SolicitudTarjeta(result.getInt("id_solicitud_tarjeta"), result.getString("tipo_de_trabajo"), result.getString("dpi_cliente"), result.getString("empresa"), result.getString("estado"), result.getDouble("salario_mensual"), result.getString("tarjeta"), result.getString("descripcion"), result.getTimestamp("fecha_solicitud"), result.getTimestamp("fecha_verificacion")));
            }
            return listaSolicitudes;
        } catch (SQLException ex) {
            ex.printStackTrace();
            //Logger.getLogger(ControladorSolicitudDeTarjeta.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaSolicitudes;
    }

}
