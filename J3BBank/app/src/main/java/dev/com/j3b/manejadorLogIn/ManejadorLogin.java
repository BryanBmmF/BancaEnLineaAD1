package dev.com.j3b.manejadorLogIn;

import android.os.Build;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import dev.com.j3b.modelos.ServidorSQL;
import dev.com.j3b.modelos.Usuario;

public class ManejadorLogin {


    public ManejadorLogin() {
    }

    /*
    Este metodo recibe como parametro la contraseña en texto plano ingresada por el usuario y retorna
    su equivalente en Hash MD5 para realizar la busqueda en base de datos.
     */
    public static String generarMD5(String textoOriginal) throws NoSuchAlgorithmException {
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

    //*************Metodo validador de fechas:  Recibe la fecha String en formato "yyyy-MM-dd" **************/
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

    //*******************Funcion que devuelve la fecha actual mas 3 meses******************/
    @RequiresApi(api = Build.VERSION_CODES.O)
    public String calcularFechaCaducidad(){
        //se recupera la fecha actual
        LocalDate dateActual = LocalDate.now();
        /*Sumamos 3 meses a la fecha actual para obtener la nueva fecha de vencimiento*/
        LocalDate newDate = dateActual.plusMonths(3);
        return newDate.toString();
    }

    //*******************Funcion que devuelve la fecha actual mas 3 meses******************/
    @RequiresApi(api = Build.VERSION_CODES.O)
    public String calcularFechaActual(){
        //se recupera la fecha actual
        LocalDate dateActual = LocalDate.now();
        return dateActual.toString();
    }

    /*
     * Función para validar que un password cumpla con la estructura alfanumerica
     * @param cadena
     * @return int: porcentage de la evaluación
     */
    public static int comprobarSeguridadPassword(String cadena){
        //String cadena = "BmmF0497";
        int porcentaje = 0; //iniciamos porcentaje en 0

        if (cadena.length()>=8){
            porcentaje+=20; //mas 25 si cumple con 8 caracteres
        }
        if (cadena.matches(".*[A-Z]+.*")){
            porcentaje+=20; //mas 25 si contiene almenos una mayúscula
        }
        if (cadena.matches(".*[a-z]+.*")){
            porcentaje+=20; //mas 25 si contine almenos una minúscula
        }
        if (cadena.matches(".*[0-9]+.*")){
            porcentaje+=20; //mas 25 si contiene al menos un nuemro
        }
        if (cadena.matches(".*[-*?!@#$/(){}=.,;:]+.*")){
            porcentaje+=20; //mas 25 si contiene al menos un caracter especial
        }
        return porcentaje;
    }

    public static boolean verificarSiNuevasContraseñasCoinciden(String contraseñaNueva, String confirmacionContraseña){
        if (contraseñaNueva.equals(confirmacionContraseña)){
            return true;
        }
        return false;
    }

    public boolean verificarAntiguaCoincidencia(Usuario usuario, String nuevaContraseña) throws NoSuchAlgorithmException {
        String nuevaContraseñaMD5 = generarMD5(nuevaContraseña);
        //Si se detecta una coincidencia con la contraseña actual o dos anteriores retornamos un TRUE
        if (usuario.getContraseña1().equals(nuevaContraseñaMD5) || usuario.getContraseña2().equals(nuevaContraseñaMD5) || usuario.getContraseñaActual().equals(nuevaContraseñaMD5)){
            return true;
        }
        //Si no se encuentra coincidencia con la contraseña actual o las dos anteriores retornamos un FALSE
        return false;
    }




}
