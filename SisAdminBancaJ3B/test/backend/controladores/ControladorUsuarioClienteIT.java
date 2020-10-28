/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backend.controladores;

import backend.enums.EstadoUsuarioCliente;
import backend.pojos.UsuarioCliente;
import java.sql.Date;
import java.util.Calendar;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author jes
 */
public class ControladorUsuarioClienteIT {

    public ControladorUsuarioClienteIT() {
    }

    /**
     * Test of buscarUsuarioCliente method, of class ControladorUsuarioCliente.
     */
    @Test
    public void testBuscarUsuarioCliente() {
        System.out.println("buscarUsuarioCliente");
        String dpiCliente = "1100000000000";
        EstadoUsuarioCliente estado = EstadoUsuarioCliente.ACTIVADO;
        ControladorUsuarioCliente instance = new ControladorUsuarioCliente();
        String expResult = dpiCliente;
        String result = instance.buscarUsuarioCliente(dpiCliente, estado).getDpiCliente();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }

    /**
     * Test of modificarContrasena method, of class ControladorUsuarioCliente.
     */
    @Test
    public void testModificarContrasena() {
        System.out.println("modificarContrasena");
        String dpi_cliente = "0012014784124";
        String contrasena = "12345678";
        java.sql.Date fechaCaducidad = new java.sql.Date(Calendar.getInstance().getTime().getTime());
        ControladorUsuarioCliente instance = new ControladorUsuarioCliente();
        boolean expResult = true;
        boolean result = instance.modificarContrasena(dpi_cliente, contrasena, fechaCaducidad);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }

    /**
     * Test of generarContrasena method, of class ControladorUsuarioCliente.
     */
    @Test
    public void testGenerarContrasena() {
        System.out.println("generarContrasena");
        int expResult = 8;
        int result = ControladorUsuarioCliente.generarContrasena().length();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }

}
