/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backend.controladores;

import backend.pojos.CuentaHabiente;
import java.sql.Date;
import java.util.ArrayList;
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
public class ControladorCuentaHabienteTest {
    
    public ControladorCuentaHabienteTest() {
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
     * Test of eliminarCuentaHabiente method, of class ControladorCuentaHabiente.
     */
    @Test
    public void testEliminarCuentaHabiente() {
        System.out.println("eliminarCuentaHabiente");
        String dpi = "";
        ControladorCuentaHabiente instance = new ControladorCuentaHabiente();
        boolean expResult = false;
        boolean result = instance.eliminarCuentaHabiente(dpi);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of registroCuentaHabiente method, of class ControladorCuentaHabiente.
     */
    @Test
    public void testRegistroCuentaHabiente() {
        System.out.println("registroCuentaHabiente");
        CuentaHabiente cuentaHabiente = null;
        ControladorCuentaHabiente instance = new ControladorCuentaHabiente();
        boolean expResult = false;
        boolean result = instance.registroCuentaHabiente(cuentaHabiente);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of busquedaDeCunetaHabientes method, of class ControladorCuentaHabiente.
     */
    @Test
    public void testBusquedaDeCunetaHabientes() {
        System.out.println("busquedaDeCunetaHabientes");
        ControladorCuentaHabiente instance = new ControladorCuentaHabiente();
        ArrayList<CuentaHabiente> expResult = null;
        ArrayList<CuentaHabiente> result = instance.busquedaDeCunetaHabientes();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of actualizarCuentaHabiente method, of class ControladorCuentaHabiente.
     */
    @Test
    public void testActualizarCuentaHabiente() {
        System.out.println("actualizarCuentaHabiente");
        CuentaHabiente cuentaHabiente = null;
        ControladorCuentaHabiente instance = new ControladorCuentaHabiente();
        boolean expResult = false;
        boolean result = instance.actualizarCuentaHabiente(cuentaHabiente);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of verificarDatosDeCuentaHabiente method, of class ControladorCuentaHabiente.
     */
    @Test
    public void testVerificarDatosDeCuentaHabiente() {
        System.out.println("verificarDatosDeCuentaHabiente");
        String dpi = "";
        String nombres = "";
        String apellidos = "";
        Date fechaNacimeitno = null;
        String direccion = "";
        String telefono = "";
        String celular = "";
        String email = "";
        ControladorCuentaHabiente instance = new ControladorCuentaHabiente();
        CuentaHabiente expResult = null;
        CuentaHabiente result = instance.verificarDatosDeCuentaHabiente(dpi, nombres, apellidos, fechaNacimeitno, direccion, telefono, celular, email);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
