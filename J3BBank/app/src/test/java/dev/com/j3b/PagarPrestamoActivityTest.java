package dev.com.j3b;

import org.junit.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;

import dev.com.j3b.manejadorLogIn.CuentaSaldo;
import dev.com.j3b.modelos.Cuenta;
import dev.com.j3b.ui.prestamos.PagarPrestamoActivity;

import static org.junit.Assert.*;

public class PagarPrestamoActivityTest {

    
    @Test
    public void obtenerMontoDeCuentaQueNoExiste() {
        System.out.println("cambiarEstadoTarjeta");
        //arrage
        String numeroCuenta = "1999926551891481";
        ArrayList<CuentaSaldo>  listado = new ArrayList<>();
        PagarPrestamoActivity instance = new PagarPrestamoActivity();
        Double expResult = -1.0;
        //act
        Double result = instance.obtenerMontoDeCuenta(numeroCuenta,listado);
        //assert
        assertEquals(expResult, result);
    }

    @Test
    public void obtenerMontoDeCuentaQueSiExiste() {
        System.out.println("cambiarEstadoTarjeta");
        //arrage
        String numeroCuenta = "9074367112";
        ArrayList<CuentaSaldo>  listado = new ArrayList<>();
        listado.add(new CuentaSaldo("9074367112",4000.0));
        PagarPrestamoActivity instance = new PagarPrestamoActivity();
        Double expResult = 4000.0;
        //act
        Double result = instance.obtenerMontoDeCuenta(numeroCuenta,listado);
        //assert
        assertEquals(expResult, result);
    }









}