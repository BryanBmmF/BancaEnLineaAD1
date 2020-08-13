/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backend.controladores;

import backend.database.ConexionBD;
import backend.pojos.UsuarioAdministrador;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author jpmazate
 */
public class ControladorInicioSesion {

    private Connection conexion;

    public ControladorInicioSesion() {
        conexion = ConexionBD.getInstance();
    }

    public boolean validarDatos(UsuarioAdministrador usuario) {
        try { 
            PreparedStatement declaracion = conexion.prepareStatement("SELECT ID_USUARIO_ADMIN FROM USUARIO_ADMINISTRADOR WHERE ID_USUARIO_ADMIN=? AND CONTRASENA=?");
            declaracion.setString(1, usuario.getUsuario());
            declaracion.setString(2, usuario.getContrasena());
            ResultSet resultado = declaracion.executeQuery();
            if(resultado.next()){
                return true;
            }else{
                return false;
            }          
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    

}
