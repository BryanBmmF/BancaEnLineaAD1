/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backend.controladores;

import backend.database.ConexionBD;
import backend.pojos.UsuarioCliente;
import java.sql.Connection;
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
    
    public static final String INSERTAR_USUARIO_CLIENTE= "INSERT INTO USUARIO_CLIENTE(usuario_cliente,dpi_cliente,contrasena,fecha_caducidad,estado,intento_de_ingresos) VALUES(?,?,?,?,?,?)";
    
    private PreparedStatement prepStatement;
    private ResultSet res;
    private Connection conexion;

    public ControladorUsuarioCliente() {
        this.conexion = ConexionBD.getInstance();
    }
    
    
    public boolean insertarUsuarioCliente(UsuarioCliente usuario) {
        try {
            prepStatement=conexion.prepareStatement(INSERTAR_USUARIO_CLIENTE);
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
    
    
}
