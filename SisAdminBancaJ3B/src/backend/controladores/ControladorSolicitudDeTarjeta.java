/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backend.controladores;

import backend.database.ConexionBD;
import backend.enums.EstadoSolicitudDeTarjeta;
import backend.enums.TipoDeTarjetaSolicitud;
import backend.pojos.SolicitudTarjeta;
import backend.pojos.Tarjeta;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jesfrin
 */
public class ControladorSolicitudDeTarjeta {

    private static final String CONSULTAR_SOLICITUDES = "SELECT "
            + "t1.id_solicitud_tarjeta,t1.tipo_de_trabajo,t1.dpi_cliente,t1.empresa,t1.estado,t1.salario_mensual,t1.tarjeta,t1.descripcion,t1.fecha_solicitud,t1.fecha_verificacion,t2.email"
            + " FROM SOLICITUD_TARJETA AS t1 JOIN CUENTA_HABIENTE AS t2 ON t1.dpi_cliente=t2.dpi_cliente WHERE estado=?";
    private static final String FILTRAR_SOLICITUDES_POR_DPI = "SELECT "
            + "t1.id_solicitud_tarjeta,t1.tipo_de_trabajo,t1.dpi_cliente,t1.empresa,t1.estado,t1.salario_mensual,t1.tarjeta,t1.descripcion,t1.fecha_solicitud,t1.fecha_verificacion,t2.email"
            + " FROM SOLICITUD_TARJETA AS t1 JOIN CUENTA_HABIENTE AS t2 ON t1.dpi_cliente=t2.dpi_cliente WHERE estado=? AND t1.dpi_cliente LIKE ?";

    private static final String APROBAR_SOLICITUD = "UPDATE SOLICITUD_TARJETA"
            + " SET estado=?,tarjeta=?,fecha_verificacion=? WHERE id_solicitud_tarjeta=?";

    private static final String RECHAZAR_SOLICITUD = "UPDATE SOLICITUD_TARJETA"
            + " SET estado=?,fecha_verificacion=? WHERE id_solicitud_tarjeta=?";
    private static final String INSERTAR_TARJETA = "INSERT INTO "
            + "TARJETA(no_tarjeta,tipo,limite,dpi_cuenta_habiente,estado,deuda_actual,fecha_vencimiento,codigoCVC,tasa_interes) "
            + "VALUES(?,?,?,?,?,?,?,?,?)";

    private Connection conexion;
    private PreparedStatement prepared;
    private ResultSet result;

    public ControladorSolicitudDeTarjeta() {
        conexion = ConexionBD.getInstance();
    }

    public LinkedList<SolicitudTarjeta> filtrarSolicitudesPorDpi(EstadoSolicitudDeTarjeta estado, String numeroDpi) {
        LinkedList<SolicitudTarjeta> listaSolicitudes = new LinkedList<>();
        try {
            prepared = conexion.prepareStatement(FILTRAR_SOLICITUDES_POR_DPI);
            prepared.setString(1, estado.toString());
            prepared.setString(2, "%" + numeroDpi + "%");

            //System.out.println(prepared.toString());
            result = prepared.executeQuery();
            while (result.next()) {
                listaSolicitudes.add(new SolicitudTarjeta(result.getInt("id_solicitud_tarjeta"), result.getString("tipo_de_trabajo"), result.getString("dpi_cliente"), result.getString("empresa"), result.getString("estado"), result.getDouble("salario_mensual"), result.getString("tarjeta"), result.getString("descripcion"), result.getTimestamp("fecha_solicitud"), result.getTimestamp("fecha_verificacion"), result.getString("email")));
            }
            prepared.close();
            result.close();
            return listaSolicitudes;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return listaSolicitudes;
    }

    public LinkedList<SolicitudTarjeta> consultarSolicitudesDeTarjeta(EstadoSolicitudDeTarjeta estado) {
        LinkedList<SolicitudTarjeta> listaSolicitudes = new LinkedList<>();
        try {
            prepared = conexion.prepareStatement(CONSULTAR_SOLICITUDES);
            prepared.setString(1, estado.toString());
            //System.out.println("CONSULTA:"+prepared.toString());
            result = prepared.executeQuery();
            while (result.next()) {
                listaSolicitudes.add(new SolicitudTarjeta(result.getInt("id_solicitud_tarjeta"), result.getString("tipo_de_trabajo"), result.getString("dpi_cliente"), result.getString("empresa"), result.getString("estado"), result.getDouble("salario_mensual"), result.getString("tarjeta"), result.getString("descripcion"), result.getTimestamp("fecha_solicitud"), result.getTimestamp("fecha_verificacion"), result.getString("email")));
            }
            prepared.close();
            result.close();
            return listaSolicitudes;
        } catch (SQLException ex) {
            ex.printStackTrace();
            //Logger.getLogger(ControladorSolicitudDeTarjeta.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaSolicitudes;
    }

    public boolean aprobarSolicitud(EstadoSolicitudDeTarjeta estado, int idSolicitud, TipoDeTarjetaSolicitud tipoDeTarjeta,Tarjeta tarjeta) {
        try {
            this.conexion.setAutoCommit(false);
            prepared = conexion.prepareStatement(APROBAR_SOLICITUD);
            prepared.setString(1, estado.toString());
            prepared.setString(2, tipoDeTarjeta.toString());
            prepared.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
            prepared.setInt(4, idSolicitud);
            //System.out.println("CONSULTA:"+prepared.toString());
            prepared.executeUpdate();
            ingresarTarjeta(tarjeta);
            this.conexion.commit();
            this.conexion.setAutoCommit(true);
            prepared.close();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ControladorSolicitudDeTarjeta.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean rechazarSolicitud(EstadoSolicitudDeTarjeta estado, int idSolicitud) {
        try {
            prepared = conexion.prepareStatement(RECHAZAR_SOLICITUD);
            prepared.setString(1, estado.toString());
            prepared.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
            prepared.setInt(3, idSolicitud);
            prepared.executeUpdate();
            prepared.close();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ControladorSolicitudDeTarjeta.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    private boolean ingresarTarjeta(Tarjeta tarjeta) {
        try {
            prepared = conexion.prepareStatement(INSERTAR_TARJETA);
            prepared.setString(1, tarjeta.getNumeroDeTarjeta());
            prepared.setString(2, tarjeta.getTipoTarjeta().toString());
            prepared.setString(3, tarjeta.getLimite());
            prepared.setString(4, tarjeta.getDpiCuentaHabiente());
            prepared.setString(5, tarjeta.getEstadoTarjeta().toString());
            prepared.setDouble(6, tarjeta.getDeudaActual());
            prepared.setTimestamp(7, tarjeta.getFechaVencimiento());
            prepared.setString(8, tarjeta.getCodigoCVC());
            prepared.setString(9, tarjeta.getTasaInteres());
            prepared.executeUpdate();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ControladorSolicitudDeTarjeta.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

}
