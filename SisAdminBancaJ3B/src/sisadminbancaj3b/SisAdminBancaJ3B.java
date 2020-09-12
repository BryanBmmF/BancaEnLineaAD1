/*
 * Clase Principal
 */
package sisadminbancaj3b;

import backend.controladores.ControladorCuenta;
import backend.controladores.ControladorPeticionesHttp;
import backend.manejadores.Md5;
import frontend.vistas.Login.Login;

/**
 *
 * @author bryan
 */
public class SisAdminBancaJ3B {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        Login login = new Login();
        login.setVisible(true);
        
        /*por alguna razon el cuentahabiente solo puede tener un cuenta, si no deberia ser asi hay que canbiar el campo unique de dpi en Cuenta*/
    }
    
}
