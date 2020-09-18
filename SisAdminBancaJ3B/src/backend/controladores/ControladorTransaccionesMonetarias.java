/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backend.controladores;

import backend.database.ConexionBD;
import backend.manejadores.Md5;
import backend.pojos.Cuenta;
import backend.pojos.CuentaTransaccionMonetaria;
import backend.pojos.MovimientoMonetario;
import backend.pojos.UsuarioCliente;
import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;

/**
 *
 * @author jpmazate
 */
public class ControladorTransaccionesMonetarias {

    private Connection conexion;
    private final String BUSCAR_CUENTA = "SELECT CUENTA.NO_CUENTA_BANCARIA,CUENTA.DPI_CLIENTE,CUENTA.TIPO_CUENTA,CUENTA.ESTADO, CUENTA.SALDO ,CUENTA_HABIENTE.NOMBRES, CUENTA_HABIENTE.APELLIDOS, CUENTA_HABIENTE.EMAIL    FROM CUENTA INNER JOIN CUENTA_HABIENTE WHERE CUENTA.DPI_CLIENTE=CUENTA_HABIENTE.DPI_CLIENTE AND CUENTA.NO_CUENTA_BANCARIA=?;";
    private final String CREAR_MOVIMIENTO_MONETARIO = "INSERT INTO MOVIMIENTO_MONETARIO (NO_CUENTA,MONTO,FECHA,TIPO,DESCRIPCION) VALUES(?,?,?,?,?); ";
    private final String ACTUALIZAR_SALDO_CUENTA = "CALL actualizarSaldoCuenta(?,?,?)";
    private final String REGISTRAR_SALDO_INICIAL = "CALL crearMovimientoMonetarioInicialCuenta(?,?)";
    private SimpleDateFormat fechaTimestamp;

    public ControladorTransaccionesMonetarias() {
        conexion = ConexionBD.getInstance();
        this.fechaTimestamp = new SimpleDateFormat("yyyy-MM-dd/hh:mm:ss");
    }

    public CuentaTransaccionMonetaria validarCuenta(CuentaTransaccionMonetaria cuenta) {
        try {
            CuentaTransaccionMonetaria cuentaDevolver = null;
            PreparedStatement declaracion = conexion.prepareStatement(BUSCAR_CUENTA);
            declaracion.setString(1, cuenta.getNumCuenta());
            ResultSet resultado = declaracion.executeQuery();
            while (resultado.next()) {
                cuentaDevolver = new CuentaTransaccionMonetaria(resultado.getString(1), resultado.getString(2), resultado.getString(3), resultado.getString(4),
                        resultado.getDouble(5), resultado.getString(6) + " " + resultado.getString(7), resultado.getString(8));
            }
            return cuentaDevolver;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public MovimientoMonetario realizarMovimientoMonetario(MovimientoMonetario datos) {
        try {
            this.conexion.setAutoCommit(false);
            PreparedStatement declaracion;
            MovimientoMonetario mandar = null;
            declaracion = conexion.prepareStatement(CREAR_MOVIMIENTO_MONETARIO, Statement.RETURN_GENERATED_KEYS);
            declaracion.setString(1, datos.getNoCuenta());
            declaracion.setDouble(2, datos.getMonto());
            declaracion.setTimestamp(3, new Timestamp(datos.getFecha().getTime()));
            declaracion.setString(4, datos.getTipo());
            declaracion.setString(5,datos.getDescripcion());
            
            declaracion.executeUpdate();
            ResultSet resultado = declaracion.getGeneratedKeys();
            if (resultado.next()) {
                int id = resultado.getInt(1);
                mandar = new MovimientoMonetario(datos.getNoCuenta(), datos.getMonto(), datos.getFecha(), datos.getTipo(),datos.getDescripcion());
                mandar.setId(Integer.toString(id));
            }
            this.conexion.commit();
            this.conexion.setAutoCommit(true);

            if (!actualizarSaldoCuenta(mandar)) {
                mandar = null;
            }
            return mandar;
        } catch (HeadlessException | SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean actualizarSaldoCuenta(MovimientoMonetario datos) {

        try {
            this.conexion.setAutoCommit(false);
            PreparedStatement declaracion;
            declaracion = conexion.prepareStatement(ACTUALIZAR_SALDO_CUENTA);
            declaracion.setString(1, datos.getTipo());
            declaracion.setDouble(2, datos.getMonto());
            declaracion.setString(3, datos.getNoCuenta());
            declaracion.executeUpdate();
            this.conexion.commit();
            this.conexion.setAutoCommit(true);
            return true;
        } catch (HeadlessException | SQLException e) {
            e.printStackTrace();
            return false;

        }

    }

    public boolean crearRegistroSaldoInicial(Cuenta cuenta) {
        try {
            this.conexion.setAutoCommit(false);
            PreparedStatement declaracion;
            declaracion = conexion.prepareStatement(REGISTRAR_SALDO_INICIAL);
            declaracion.setString(1, cuenta.getNumCuenta());
            declaracion.setDouble(2, cuenta.getSaldo());
            declaracion.executeUpdate();
            this.conexion.commit();
            this.conexion.setAutoCommit(true);
            return true;
        } catch (HeadlessException | SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    public void notificarCorreoCuentaHabiente(CuentaTransaccionMonetaria cuenta, MovimientoMonetario movimiento) {
       
        ControladorPeticionesHttp controlador = new ControladorPeticionesHttp();
        String idMovimiento = movimiento.getId();
        String numCuenta = cuenta.getNumCuenta();
        String monto = Double.toString(movimiento.getMonto());
        String fecha = fechaTimestamp.format(movimiento.getFecha());
        String tipoTransaccion = movimiento.getTipo();
        String tipoCuenta = cuenta.getTipo();

        //String params = "?idMovimiento=" + idMovimiento + "&noCuenta=" + numCuenta + "&monto=" + monto + "&fecha=" + fecha + "&tipo=" + tipoTransaccion + "&tipoCuenta=" + tipoCuenta + "&Email=" + cuenta.getCorreoElectronico();
        String params = "?idMovimiento=" + idMovimiento + "&noCuenta=" + numCuenta + "&monto=" + monto + "&fecha=" + fecha + "&tipo=" + tipoTransaccion + "&tipoCuenta=" + tipoCuenta + "&Email=" + cuenta.getCorreoElectronico();
        //String url = "http://192.168.20.5/j3b/servicios/switfMailer/envioDeCorreo.php"+params;
        //String url = "http://192.168.0.200/j3b/servicios/switfMailer/envioDeCorreo.php"+params;
        String url = "http://192.168.0.200/j3b/servicios/switfMailer/envioDeTransaccionesCorreo.php" + params;
        //String url = "http://192.168.1.18/j3b/servicios/switfMailer/envioDeTransaccionesCorreo.php" + params;

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
