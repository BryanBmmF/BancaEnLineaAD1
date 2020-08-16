/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backend.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author jpmazate
 */
public class ConexionBD {
    public static Connection connection= null;
    
    
    public static Connection getInstance(){
        if(connection == null){
            try {
			String user = "ad1sysdba";
			String password = "Ad1sysdba$";
			// La url incluye el esquema a usar, en este caso 'mysql'
			String urlConnection ="jdbc:mysql://localhost:3306/db_j3bank";
			//abrimos una coneccion a la DB usando una url, el usuario y password (SUSTITUIR PASSWORD)
			connection = DriverManager.getConnection(urlConnection,user,password);
                        return connection;
			                        
                }catch(SQLException e){
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(null,"NO SE PUDO CONECTAR A LA BASE DE DATOS");
                    return null;
                }
        }else{
            return connection;
        }
    }
    
         
}
