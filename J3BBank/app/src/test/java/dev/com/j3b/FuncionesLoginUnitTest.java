package dev.com.j3b;

import android.os.Build;

import androidx.annotation.RequiresApi;

import org.junit.Test;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Function Login local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */


public class FuncionesLoginUnitTest {

    /**
     * Test 1. Metodos para comprobar si una fecha es antes o despues de la actual
     */
    @Test
    public void solicitarComprobacionFecha(){
        //declarando fecha de caducidad
        String fechaCaducidad = "2020-08-17";
        System.out.println("La fecha es despues de hoh: "+comprobarFecha(fechaCaducidad));
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public boolean comprobarFecha(String fechaCaducidad){
        //se recupera la fecha actual
        LocalDate dateActual = LocalDate.now();
        /*parseando fechas a local date*/
        LocalDate fechaCaduc = LocalDate.parse(fechaCaducidad, DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        /*validando que la fecha no haya vencido*/
        if (fechaCaduc.isAfter(dateActual)){
            //System.out.println("La fecha de caducidad aun sigue valida ");
            return true;
        }
        return false;
    }

    /**
     * Test 2. Metodos para calcular una nueva fecha respecto de la actual agregandole mas meses
     */
    @Test
    public void solicitarFechaCaducidad(){
        System.out.println(calcularFechaCaducidad());
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public String calcularFechaCaducidad(){
        //se recupera la fecha actual
        LocalDate dateActual = LocalDate.now();
        /*Sumamos 3 meses a la fecha actual para obtener la nueva fecha de vencimiento*/
        LocalDate newDate = dateActual.plusMonths(3);
        return newDate.toString();
    }

    /**
     * Test 3. Metodos para evaluar correcta estructura de un Password
     */
    @Test
    public void solicitarSeguridadPassword(){
        String cadena = "BmmF0497";
        System.out.println("La  estructura cumple con un "+comprobarSeguridadPassword(cadena)+" %");
    }
    public int comprobarSeguridadPassword(String cadena){
        //String cadena = "BmmF0497";
        int porcentaje = 0; //iniciamos porcentaje en 0

        if (cadena.length()>=8){
            porcentaje+=20; //mas 20 si cumple con 8 caracteres
        }
        if (cadena.matches(".*[A-Z]+.*")){
            porcentaje+=20; //mas 20 si contiene almenos una mayúscula
        }
        if (cadena.matches(".*[a-z]+.*")){
            porcentaje+=20; //mas 20 si contine almenos una minúscula
        }
        if (cadena.matches(".*[0-9]+.*")){
            porcentaje+=20; //mas 20 si contiene al menos un nuemro
        }
        if (cadena.matches(".*[-*?!@#$/(){}=.,;:]+.*")){
            porcentaje+=20; //mas 20 si contiene al menos un caracter especial
        }
        return porcentaje;
    }

    /**
     * Test 4. Metodos para convertir una cadena de texto en Hash MD5
     */
    @Test
    public void solicitarMD5(){
        try {
            System.out.println("El md5 es: "+generarMD5("password1"));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
    public String generarMD5(String textoOriginal) throws NoSuchAlgorithmException {
        String original = textoOriginal;
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(original.getBytes());
        byte[] digest = md.digest();
        StringBuffer stringBufferPassword = new StringBuffer();
        for (byte b : digest) {
            stringBufferPassword.append(String.format("%02x", b & 0xff));
        }
        return stringBufferPassword.toString();
    }

}