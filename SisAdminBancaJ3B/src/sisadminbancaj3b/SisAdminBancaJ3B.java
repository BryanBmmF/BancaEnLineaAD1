/*
 * Clase Principal
 */
package sisadminbancaj3b;

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
    }
    
}
