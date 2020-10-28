package dev.com.j3b;

import org.junit.Test;

import dev.com.j3b.manejadorLogIn.ManejadorCuentaAjena;
import dev.com.j3b.ui.transaccionesAjenas.EvaluadorCodigoTransferencia;

import static org.junit.Assert.*;

public class EvaluadorCodigoTransferenciaTest {

    @Test
    public void consultarDatosDeUsuarioAcreedorExistentes() {
        //Arrange
        String numCuenta = "1319618244";
        String resultExpect = "car@gmail.com";
        //Act
        EvaluadorCodigoTransferencia eval = new EvaluadorCodigoTransferencia();
        String result = eval.consultarDatosDeUsuarioAcreedor(numCuenta);
        //Assert
        assertEquals("El usuario si existe",resultExpect, result);
    }

    @Test
    public void consultarDatosDeUsuarioAcreedorNoExistentes() {
        //Arrange
        String numCuenta = "1111111111";
        //Act
        EvaluadorCodigoTransferencia eval = new EvaluadorCodigoTransferencia();
        String result = eval.consultarDatosDeUsuarioAcreedor(numCuenta);
        //Assert
        assertNull("No se encontro el registro",result);
    }

    @Test
    public void consultarDatosDeUsuarioAcreedorCuentaInvalida() {
        //Arrange
        String numCuenta = "Cuenta_Invalida";
        //Act
        EvaluadorCodigoTransferencia eval = new EvaluadorCodigoTransferencia();
        String result = eval.consultarDatosDeUsuarioAcreedor(numCuenta);
        //Assert
        assertNull("La cuenta a consultar es invalida",result);

    }

    @Test
    public void generarCodigoAleatorio() {
        //Not Arrange
        //Act
        ManejadorCuentaAjena mc = new ManejadorCuentaAjena();
        String result = mc.generarCodigo();
        //Assert
        assertNotNull("El metodo genera correctamente el codigo",result);
    }

    @Test
    public void evaluraMontoMayorACero() {
        //Not Arrange
        double monto = 100;
        //Act
        ManejadorCuentaAjena mc = new ManejadorCuentaAjena();
        boolean result = mc.validarMonto(monto);
        //Assert
        assertTrue("El monto si es mayor a 0",result);
    }

    @Test
    public void evaluraMontoCero() {
        //Not Arrange
        double monto = 0;
        //Act
        ManejadorCuentaAjena mc = new ManejadorCuentaAjena();
        boolean result = mc.validarMonto(monto);
        //Assert
        assertFalse("El monto es 0",result);
    }


}