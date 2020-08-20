/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backend.pojos;

import backend.enums.EstadoUsuarioCliente;
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
 * @author jesfrin
 */
public class UsuarioClienteTest {

    public UsuarioClienteTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of toString method, of class UsuarioCliente.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        UsuarioCliente instance = new UsuarioCliente("12345");
        String expResult = "12345";
        String result = instance.getDpiCliente();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        if (!result.equals(expResult)) {
            fail("The test case is a prototype.");
        }
    }

    /**
     * Test of generarUsuario method, of class UsuarioCliente.
     */
    @Test
    public void testGenerarUsuario() {
        System.out.println("generarUsuario");
        UsuarioCliente instance = new UsuarioCliente("12345");;
        int expResult = instance.getUsuarioCliente().length();
        int result = 8;
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        if (result != expResult) {
            fail("The test case is a prototype.");
        }
    }

    /**
     * Test of generarFechaDeCaducidad method, of class UsuarioCliente.
     */
    @Test
    public void testGenerarFechaDeCaducidad() {
        System.out.println("generarFechaDeCaducidad");
        UsuarioCliente instance = new UsuarioCliente("12345");;
        Calendar fecha = Calendar.getInstance();
        fecha.add(Calendar.MONTH, 3);
        Date expResult = new Date(fecha.getTime().getTime());
        System.out.println("FECHA:" + expResult.toString());
        Date result = instance.generarFechaDeCaducidad();
        System.out.println("Fecha2:" + result.toString());
        if (!expResult.toString().equalsIgnoreCase(result.toString())) {
            fail("The test case is a prototype.");
        }
    }

    /**
     * Test of generarContrasena method, of class UsuarioCliente.
     */
    @Test
    public void testGenerarContrasena() {
        System.out.println("generarContrasena");
        UsuarioCliente instance = new UsuarioCliente("12345");;
        int expResult = instance.getContrasenaCopia().length();
        int result = instance.generarContrasena().length();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        if (expResult != result) {
            fail("The test case is a prototype.");
        }
    }

}
