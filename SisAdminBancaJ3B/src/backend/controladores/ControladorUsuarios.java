package backend.controladores;

import backend.database.ConexionBD;
import backend.manejadores.Md5;
import backend.pojos.TablaModelo;
import backend.pojos.UsuarioAdministrador;
import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;

/**
 *
 * @author jpmazate
 */
public class ControladorUsuarios {

    private Connection conexion;
    private SimpleDateFormat formateador;

    public ControladorUsuarios() {
        conexion = ConexionBD.getInstance();
        formateador = new SimpleDateFormat("yyyy-MM-dd");
    }

    public boolean crearUsuario(UsuarioAdministrador usuario) {
        try {
            PreparedStatement declaracion;
            declaracion = conexion.prepareStatement("INSERT INTO USUARIO_ADMINISTRADOR(ID_USUARIO_ADMIN,DPI_ADMIN,CONTRASENA,NOMBRES,APELLIDOS, FECHA_NACIMIENTO) VALUES (?,?,?,?,?,?);");
            declaracion.setString(1, usuario.getUsuario());// maneja el resultado 
            declaracion.setString(2, usuario.getDpi());
            declaracion.setString(3, Md5.getMD5(usuario.getContrasena()));
            declaracion.setString(4, usuario.getNombre());
            declaracion.setString(5, usuario.getApellido());
            declaracion.setString(6, formateador.format(usuario.getFechaNacimiento()));

            declaracion.executeUpdate();// maneja el resultado 
            return true;
        } catch (HeadlessException | SQLException e) {
            return false;
        }
    }

    public void llenarUsuarios(TablaModelo modelo) {
        PreparedStatement declaracion;
        try {
            declaracion = conexion.prepareStatement("SELECT ID_USUARIO_ADMIN,DPI_ADMIN,NOMBRES,APELLIDOS,FECHA_NACIMIENTO FROM USUARIO_ADMINISTRADOR WHERE ID_USUARIO_ADMIN!='Adminusr' ORDER BY ID_USUARIO_ADMIN");
            ResultSet resultado = declaracion.executeQuery();
            while (resultado.next()) {
                Object objeto[] = new Object[5];
                objeto[0] = resultado.getString(1);
                objeto[1] = resultado.getString(2);
                objeto[2] = resultado.getString(3);
                objeto[3] = resultado.getString(4);
                objeto[4] = resultado.getDate(5).toString();
                modelo.addRow(objeto);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Problema al cargar los usuarios");
        }
    }

    public boolean editarUsuario(UsuarioAdministrador usuario) {
        try {
            PreparedStatement declaracion;
            declaracion = conexion.prepareStatement("UPDATE USUARIO_ADMINISTRADOR SET DPI_ADMIN=?,NOMBRES=?,APELLIDOS=?,FECHA_NACIMIENTO=? WHERE ID_USUARIO_ADMIN=?");
            declaracion.setString(1, usuario.getDpi());
            declaracion.setString(2, usuario.getNombre());
            declaracion.setString(3, usuario.getApellido());
            declaracion.setString(4, formateador.format(usuario.getFechaNacimiento()));
            declaracion.setString(5, usuario.getUsuario());

            declaracion.executeUpdate();
            return true;
        } catch (HeadlessException | SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    public boolean cambiarContrasenaUsuario(UsuarioAdministrador usuario) {
        try {
            PreparedStatement declaracion;
            System.out.println(usuario.getContrasena());
            declaracion = conexion.prepareStatement("UPDATE USUARIO_ADMINISTRADOR SET CONTRASENA=? WHERE ID_USUARIO_ADMIN=?");
            declaracion.setString(1, Md5.getMD5(usuario.getContrasena()));
            declaracion.setString(2, usuario.getUsuario());

            declaracion.executeUpdate();
            return true;
        } catch (HeadlessException | SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}

/*
SuperUsuario del sistema
INSERT INTO USUARIO_ADMINISTRADOR VALUES('Adminusr','1','0192023a7bbd73250516f069df18b500','Administrador','Del Sistema','2000-03-10');
usuario = Adminusr
Contrasena = admin123
 */
