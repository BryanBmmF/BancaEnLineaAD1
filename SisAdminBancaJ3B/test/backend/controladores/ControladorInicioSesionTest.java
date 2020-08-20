/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backend.controladores;

import backend.manejadores.Md5;
import backend.pojos.UsuarioAdministrador;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import sun.security.provider.MD5;

/**
 *
 * @author jpmazate
 */
public class ControladorInicioSesionTest {
    
    public ControladorInicioSesionTest() {
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
     * Test of validarDatos method, of class ControladorInicioSesion.
     */
    @Test
    public void testValidarDatos() {
        System.out.println("validarDatos");
        UsuarioAdministrador usuario = new UsuarioAdministrador("adminusr", Md5.getMD5("admin123"));
        ControladorInicioSesion instance = new ControladorInicioSesion();
        boolean expResult = true;
        boolean result = instance.validarDatos(usuario);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
}
