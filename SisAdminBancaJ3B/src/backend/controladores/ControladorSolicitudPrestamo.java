/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backend.controladores;

import backend.database.ConexionBD;
import backend.enums.EstadoPagosPrestamo;
import backend.enums.EstadoSolicitudDeTarjeta;
import backend.enums.EstadoSolicitudPrestamo;
import backend.enums.TipoDeTarjetaSolicitud;
import backend.pojos.MovimientoMonetario;
import backend.pojos.Prestamo;
import backend.pojos.SolicitudPrestamo;
import backend.pojos.SolicitudTarjeta;
import backend.pojos.Tarjeta;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jpmazate
 */
public class ControladorSolicitudPrestamo {

    private Connection conexion;
    private PreparedStatement prepared;
    private ResultSet result;
    private SimpleDateFormat fechaTimestamp;

    private static final String CONSULTAR_SOLICITUDES = "SELECT "
            + "t1.id,t1.tipo_de_trabajo,t1.dpi_cliente,t1.empresa,t1.estado,t1.salario_mensual,t1.monto_solicitud,t1.tipo,t1.descripcion,t1.direccion_bien_raiz,t1.fecha_solicitud,t1.fecha_verificacion,t2.email"
            + " FROM SOLICITUD_PRESTAMO AS t1 JOIN CUENTA_HABIENTE AS t2 ON t1.dpi_cliente=t2.dpi_cliente WHERE estado=?";
    private static final String FILTRAR_SOLICITUDES_POR_DPI = "SELECT "
            + "t1.id,t1.tipo_de_trabajo,t1.dpi_cliente,t1.empresa,t1.estado,t1.salario_mensual,t1.monto_solicitud,t1.tipo,t1.descripcion,t1.direccion_bien_raiz,t1.fecha_solicitud,t1.fecha_verificacion,t2.email"
            + " FROM SOLICITUD_PRESTAMO AS t1 JOIN CUENTA_HABIENTE AS t2 ON t1.dpi_cliente=t2.dpi_cliente WHERE estado=? AND dpi=?";

    private static final String CAMBIAR_ESTADO_SOLICITUD = "UPDATE SOLICITUD_PRESTAMO"
            + " SET estado=?,fecha_verificacion=? WHERE id=?";

    private static final String INSERTAR_PRESTAMO = "INSERT INTO "
            + "PRESTAMO "
            + "VALUES(null,?,?,?,?,?,?,?,?,?)";
    private static final String INSERTAR_PAGO_PRESTAMO = "INSERT INTO "
            + "PAGO_PRESTAMO "
            + "VALUES(null,?,?,?,null)";

    public ControladorSolicitudPrestamo() {
        conexion = ConexionBD.getInstance();
        fechaTimestamp = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    }

    public LinkedList<SolicitudPrestamo> consultarSolicitudesDePrestamos(EstadoSolicitudPrestamo estado) {
        LinkedList<SolicitudPrestamo> listaSolicitudes = new LinkedList<>();
        try {
            prepared = conexion.prepareStatement(CONSULTAR_SOLICITUDES);
            prepared.setString(1, estado.toString());
            result = prepared.executeQuery();
            while (result.next()) {
                listaSolicitudes.add(new SolicitudPrestamo(
                        result.getInt("id"),
                        result.getString("tipo_de_trabajo"),
                        result.getString("empresa"),
                        result.getString("estado"),
                        result.getDouble("salario_mensual"),
                        result.getDouble("monto_solicitud"),
                        result.getString("tipo"),
                        result.getString("descripcion"),
                        result.getString("direccion_bien_raiz"),
                        result.getTimestamp("fecha_solicitud"),
                        result.getString("dpi_cliente"),
                        result.getString("email")));
            }
            prepared.close();
            result.close();
            return listaSolicitudes;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return listaSolicitudes;
    }

    public LinkedList<SolicitudPrestamo> filtrarSolicitudesPorDpi(EstadoSolicitudPrestamo estado, String numeroDpi) {
        LinkedList<SolicitudPrestamo> listaSolicitudes = new LinkedList<>();
        try {
            prepared = conexion.prepareStatement(FILTRAR_SOLICITUDES_POR_DPI);
            prepared.setString(1, estado.toString());
            prepared.setString(2, "%" + numeroDpi + "%");

            //System.out.println(prepared.toString());
            result = prepared.executeQuery();
            while (result.next()) {
//                    public SolicitudPrestamo(Integer id, String tipoTrabajo, String empresa, String estado, Double salarioMensual, Double montoSolicitud,
//String tipo, String descripcion, String direccionBienRaiz, String fechaSolicitud, String dpiCliente) {

                listaSolicitudes.add(new SolicitudPrestamo(
                        result.getInt("id"),
                        result.getString("tipo_de_trabajo"),
                        result.getString("empresa"),
                        result.getString("estado"),
                        result.getDouble("salario_mensual"),
                        result.getDouble("monto_solicitud"),
                        result.getString("tipo"),
                        result.getString("descripcion"),
                        result.getString("direccion_bien_raiz"),
                        result.getTimestamp("fecha_solicitud"),
                        result.getString("dpi_cliente"),
                        result.getString("email")));
            }
            prepared.close();
            result.close();

            return listaSolicitudes;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return listaSolicitudes;
    }

    public boolean cambiarEstadoSolicitud(EstadoSolicitudPrestamo estado, int idSolicitud, Prestamo prestamo) {
        try {
            this.conexion.setAutoCommit(false);
            prepared = conexion.prepareStatement(CAMBIAR_ESTADO_SOLICITUD);
            prepared.setString(1, estado.toString());
            prepared.setDate(2, new Date(System.currentTimeMillis()));
            prepared.setInt(3, idSolicitud);
            prepared.executeUpdate();

            if (estado.toString().equals(EstadoSolicitudPrestamo.APROBADO.toString())) {
                ingresarPrestamo(prestamo);
            }

            this.conexion.commit();
            this.conexion.setAutoCommit(true);
            prepared.close();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    private boolean ingresarPrestamo(Prestamo prestamo) {
        try {
            prepared = conexion.prepareStatement(INSERTAR_PRESTAMO, Statement.RETURN_GENERATED_KEYS);
            prepared.setString(1, prestamo.getDpiCuentaHabiente());
            prepared.setDouble(2, prestamo.getMontoTotal());
            prepared.setDouble(3, prestamo.getDeudaRestante());
            prepared.setString(4, prestamo.getTasaInteres());
            prepared.setInt(5, prestamo.getCantidadMeses());
            prepared.setString(6, prestamo.getTipoPrestamo());
            prepared.setString(7, prestamo.getDescripcion());
            prepared.setTimestamp(8, prestamo.getFechaVencimiento());
            prepared.setString(9, prestamo.getEstado());
            prepared.executeUpdate();
            ResultSet resultado = prepared.getGeneratedKeys();
            if (resultado.next()) {
                int id = resultado.getInt(1);
                prestamo.setId(id);
                crearPagosPendientesPrestamo(prestamo);
            }
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public boolean crearPagosPendientesPrestamo(Prestamo prestamo) throws SQLException {

        double cuotaMensual = prestamo.getMontoTotal() / prestamo.getCantidadMeses();
        for (int i = 0; i < prestamo.getCantidadMeses(); i++) {
            prepared = conexion.prepareStatement(INSERTAR_PAGO_PRESTAMO);
            prepared.setInt(1, prestamo.getId());
            prepared.setDouble(2, cuotaMensual);
            prepared.setString(3, EstadoPagosPrestamo.PENDIENTE.toString());
            prepared.executeUpdate();
        }
        return true;
    }

    public void notificarRechazoDePrestamo(String descripcion, SolicitudPrestamo solicitud, String resultado) throws IOException {
        ControladorPeticionesHttp controlador = new ControladorPeticionesHttp();
        String idSolicitud = solicitud.getId() + "";
        String estado = resultado;
        String salarioMensual = solicitud.getSalarioMensual() + "";
        String montoSolicitud = solicitud.getMontoSolicitud() + "";
        String tipo = solicitud.getTipo() + "";

        String correo = "&Email=" + solicitud.getEmail();
        URL url = new URL("http://192.168.0.200/j3b/servicios/switfMailer/respuestaRechazoSolicitudPrestamo.php");
        Map<String, Object> params = new LinkedHashMap<>();
        params.put("idSolicitud", idSolicitud);
        params.put("estado", estado);
        params.put("salarioMensual", salarioMensual);
        params.put("montoSolicitud", montoSolicitud);
        params.put("tipo", tipo);
        params.put("descripcion", descripcion);
        StringBuilder postData = new StringBuilder();
        // POST as urlencoded is basically key-value pairs, as with GET
        // This creates key=value&key=value&... pairs
        for (Map.Entry<String, Object> param : params.entrySet()) {
            if (postData.length() != 0) {
                postData.append('&');
            }
            postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
            postData.append('=');
            postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
        }
        postData.append(correo);
        System.out.println(postData.toString());
        // Convert string to byte array, as it should be sent
        byte[] postDataBytes = postData.toString().getBytes("UTF-8");

        // Connect, easy
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        // Tell server that this is POST and in which format is the data
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
        conn.setDoOutput(true);
        conn.getOutputStream().write(postDataBytes);

        // This gets the output from your server
        Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

        for (int c; (c = in.read()) >= 0;) {
            System.out.print((char) c);
        }

    }

    public void notificarAprobacionDePrestamo(String descripcion, SolicitudPrestamo solicitud, Prestamo prestamo, String resultado) throws IOException {
        ControladorPeticionesHttp controlador = new ControladorPeticionesHttp();
        String idSolicitud = solicitud.getId() + "";
        String estado = resultado;
        String salarioMensual = solicitud.getSalarioMensual() + "";
        String montoSolicitud = solicitud.getMontoSolicitud() + "";
        String tipo = solicitud.getTipo() + "";

        String correo = "&Email=" + solicitud.getEmail();
        URL url = new URL("http://192.168.0.200/j3b/servicios/switfMailer/respuestaAprobacionSolicitudPrestamo.php");
        Map<String, Object> params = new LinkedHashMap<>();
        params.put("idSolicitud", idSolicitud);
        params.put("estado", estado);
        params.put("salarioMensual", salarioMensual);
        params.put("montoSolicitud", montoSolicitud);
        params.put("tipo", tipo);
        params.put("descripcion", descripcion);

        params.put("meses", prestamo.getCantidadMeses());
        params.put("deuda", prestamo.getDeudaRestante());
        params.put("fechaVencimiento", fechaTimestamp.format(new Date(prestamo.getFechaVencimiento().getTime())));
        params.put("idPrestamo", prestamo.getId());
        params.put("montoTotal", prestamo.getMontoTotal());
        params.put("tasaInteres", prestamo.getTasaInteres());
        params.put("tipoPrestamo", prestamo.getTipoPrestamo());

        StringBuilder postData = new StringBuilder();
        // POST as urlencoded is basically key-value pairs, as with GET
        // This creates key=value&key=value&... pairs
        for (Map.Entry<String, Object> param : params.entrySet()) {
            if (postData.length() != 0) {
                postData.append('&');
            }
            postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
            postData.append('=');
            postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
        }
        postData.append(correo);
        System.out.println(postData.toString());
        // Convert string to byte array, as it should be sent
        byte[] postDataBytes = postData.toString().getBytes("UTF-8");

        // Connect, easy
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        // Tell server that this is POST and in which format is the data
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
        conn.setDoOutput(true);
        conn.getOutputStream().write(postDataBytes);

        // This gets the output from your server
        Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

        for (int c; (c = in.read()) >= 0;) {
            System.out.print((char) c);
        }

    }
}
