/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backend.controladores;

import backend.database.ConexionBD;
import backend.enums.EstadoUsuarioCliente;
import backend.manejadores.Md5;
import backend.pojos.UsuarioCliente;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jesfrin
 */
public class ControladorUsuarioCliente {

    public static final String INSERTAR_USUARIO_CLIENTE = "INSERT INTO USUARIO_CLIENTE(usuario_cliente,dpi_cliente,contrasena,fecha_caducidad,estado,intento_de_ingresos) VALUES(?,?,?,?,?,?)";
    public static final String BUSCAR_CLIENTE = "SELECT t1.usuario_cliente,t1.dpi_cliente,t2.email FROM USUARIO_CLIENTE AS t1 JOIN CUENTA_HABIENTE AS t2 ON t1.dpi_cliente=t2.dpi_cliente WHERE t1.dpi_cliente=? AND t1.estado=?";
    public static final String ACTUALIZAR_CONTRASENA = "UPDATE USUARIO_CLIENTE SET contrasena=?,estado=?,fecha_caducidad=?,contrasena1=NULL,contrasena2=NULL WHERE dpi_cliente=? ";

    private PreparedStatement prepStatement;
    private ResultSet res;
    private Connection conexion;

    public ControladorUsuarioCliente() {
        this.conexion = ConexionBD.getInstance();
    }

    public boolean insertarUsuarioCliente(UsuarioCliente usuario) {
        try {
            prepStatement = conexion.prepareStatement(INSERTAR_USUARIO_CLIENTE);
            prepStatement.setString(1, usuario.getUsuarioCliente());
            prepStatement.setString(2, usuario.getDpiCliente());
            prepStatement.setString(3, usuario.getContrasena());
            prepStatement.setDate(4, usuario.getFechaCaducidad());
            prepStatement.setString(5, usuario.getEstado().toString());
            prepStatement.setInt(6, usuario.getIntentoDeIngresos());
            prepStatement.executeUpdate();
            prepStatement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Busca un usuario en especifico, de no encontrarlo devuelve null
     *
     * @param dpiCliente
     * @param estado
     * @return
     */
    public UsuarioCliente buscarUsuarioCliente(String dpiCliente, EstadoUsuarioCliente estado) {
        UsuarioCliente usuario = null;

        try {
            prepStatement = conexion.prepareStatement(BUSCAR_CLIENTE);
            prepStatement.setString(1, dpiCliente);
            prepStatement.setString(2, estado.toString());
            System.out.println(prepStatement.toString());
            res = prepStatement.executeQuery();
            while (res.next()) {
                usuario = new UsuarioCliente(res.getString("usuario_cliente"), res.getString("dpi_cliente"), estado, res.getString("email"));
            }
            prepStatement.close();
            res.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return usuario;
    }

    public boolean modificarContrasena(String dpi_cliente, String contrasena, Date fechaCaducidad) {
        try {
            prepStatement = conexion.prepareStatement(ACTUALIZAR_CONTRASENA);
            prepStatement.setString(1, contrasena);
            prepStatement.setString(2, EstadoUsuarioCliente.NUEVO.toString());
            prepStatement.setDate(3, fechaCaducidad);
            prepStatement.setString(4, dpi_cliente);
            prepStatement.executeUpdate();
            System.out.println(prepStatement.toString());

            prepStatement.close();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public static String generarContrasena() {
        SecureRandom random = new SecureRandom();
        String text = new BigInteger(130, random).toString(32);
        return text.substring(1, 9).toUpperCase();
    }

    public void notificarCambioDeContrasena(String contrasena, String email, Date fechaCaducidad, String usuario) {

        ControladorPeticionesHttp controlador = new ControladorPeticionesHttp();
        //String params = "?idMovimiento=" + idMovimiento + "&noCuenta=" + numCuenta + "&monto=" + monto + "&fecha=" + fecha + "&tipo=" + tipoTransaccion + "&tipoCuenta=" + tipoCuenta + "&Email=" + cuenta.getCorreoElectronico();

        String params = "?contrasena=" + contrasena + "&Email=" + email + "&FechaCaducidad=" + fechaCaducidad + "&Usuario=" + usuario;
        //String url = "http://192.168.20.5/j3b/servicios/switfMailer/envioDeCorreo.php"+params;
        //String url = "http://192.168.0.200/j3b/servicios/switfMailer/envioDeCorreo.php"+params;
        String url = "http://192.168.0.200/j3b/servicios/switfMailer/cambioDeContrasena.php" + params;
        //String url = "http://192.168.1.18/j3b/servicios/switfMailer/envioDeTransaccionesCorreo.php" + params;
        System.out.println(url);
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
