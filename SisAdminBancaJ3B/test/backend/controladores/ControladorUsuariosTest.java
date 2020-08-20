/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backend.controladores;

import backend.manejadores.Md5;
import backend.pojos.TablaModelo;
import backend.pojos.UsuarioAdministrador;
import java.util.Date;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author jpmazate
 */
public class ControladorUsuariosTest {
    
    ControladorUsuarios controlador;
    
    public ControladorUsuariosTest() {
        controlador = new ControladorUsuarios();
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
     * Test of crearUsuario method, of class ControladorUsuarios.
     */
    @Test
    public void testCrearUsuario() {
        System.out.println("crearUsuario");
        UsuarioAdministrador usuario = new UsuarioAdministrador("B1", "9998987676765", Md5.getMD5("123"),"a","a", new Date());
        ControladorUsuarios instance = new ControladorUsuarios();
        boolean expResult = true;
        boolean result = instance.crearUsuario(usuario);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of llenarUsuarios method, of class ControladorUsuarios.
     */
    

    /**
     * Test of editarUsuario method, of class ControladorUsuarios.
     */
    @Test
    public void testEditarUsuario() {
        System.out.println("editarUsuario");
        UsuarioAdministrador usuario = new UsuarioAdministrador("B1", "9998987676765", Md5.getMD5("123"),"a","a", new Date());
        ControladorUsuarios instance = new ControladorUsuarios();
        boolean expResult = true;
        boolean result = instance.editarUsuario(usuario);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
//
//    /**
//     * Test of cambiarContrasenaUsuario method, of class ControladorUsuarios.
//     */
//    @Test
//    public void testCambiarContrasenaUsuario() {
//        System.out.println("cambiarContrasenaUsuario");
//        UsuarioAdministrador usuario = null;
//        ControladorUsuarios instance = new ControladorUsuarios();
//        boolean expResult = false;
//        boolean result = instance.cambiarContrasenaUsuario(usuario);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
    
}
