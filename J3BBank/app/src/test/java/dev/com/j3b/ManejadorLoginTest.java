package dev.com.j3b;

import org.junit.Test;

import java.security.NoSuchAlgorithmException;

import dev.com.j3b.manejadorLogIn.ManejadorLogin;
import dev.com.j3b.modelos.Usuario;

import static org.junit.Assert.*;

public class ManejadorLoginTest {

    @Test
    public void generarMD5() throws NoSuchAlgorithmException {
        //arrage
        System.out.println("generarMD5");
        String textoOriginal = "3211";
        String expResult = "6b620aedfa4cf153467265629501dd61";
        //act
        String result = ManejadorLogin.generarMD5(textoOriginal);
        //assert
        assertEquals(expResult, result);
    }

    @Test
    public void comprobarSeguridadPassword() {
        //arrage
        System.out.println("comprobarSeguridadPassword");
        String cadena = "contraseña";
        int expResult = 40;
        //act
        int result = ManejadorLogin.comprobarSeguridadPassword(cadena);
        //assert
        assertEquals(expResult, result);
    }

    @Test
    public void verificarSiNuevasContraseñasCoinciden() {
        //arrage
        System.out.println("verificarSiNuevasContraseñasCoinciden");
        String contraseñaNueva = "";
        String confirmacionContraseña = "";
        Boolean expResult = true;
        //act
        Boolean result = ManejadorLogin.verificarSiNuevasContraseñasCoinciden(contraseñaNueva, confirmacionContraseña);
        //assert
        assertEquals(expResult, result);
    }
}