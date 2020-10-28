/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backend.controladores;

import backend.pojos.TablaModelo;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.fail;
import org.junit.Test;

 
/**
 *
 * @author jpmazate
 */
public class ControladorTarjetaTest {
    
    public ControladorTarjetaTest() {
    }
     
    @Test //funciona
    public void testLlenarUsuarios() {
        System.out.println("llenarUsuarios");
        TablaModelo modelo = new TablaModelo();
        ControladorTarjeta instance = new ControladorTarjeta();
        instance.llenarUsuarios(modelo);
    }

    @Test //funciona
    public void testCambiarEstadoTarjetaActiva() {
        System.out.println("cambiarEstadoTarjeta");
        String noTarjeta = "1999926551891481";
        String estado = "ACTIVA";
        ControladorTarjeta instance = new ControladorTarjeta();
        boolean expResult = true;
        boolean result = instance.cambiarEstadoTarjeta(noTarjeta, estado);
        assertEquals(expResult, result);
    }
    
    @Test //funciona
    public void testCambiarEstadoTarjetaDesactivada() {
        System.out.println("cambiarEstadoTarjeta");
        String noTarjeta = "1999926551891481";
        String estado = "DESACTIVADA";
        ControladorTarjeta instance = new ControladorTarjeta();
        boolean expResult = true;
        boolean result = instance.cambiarEstadoTarjeta(noTarjeta, estado);
        assertEquals(expResult, result);   
    }
    
    
    
}
