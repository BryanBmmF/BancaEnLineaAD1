/*
 * Clase para manejar peticiones Http por medio de GET o POST php
 */
package backend.controladores;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 *
 * @author bryan
 */
public class ControladorPeticionesHttp {

    public ControladorPeticionesHttp() {
    }

    /**
     * Peticion http para el envio de un correo electronico desde el servidor
     * Fuentes: https://parzibyte.me/blog/2019/02/14/peticion-http-get-java-consumir-html-json/
     * https://www.it-swarm.dev/es/java/java-enviando-parametros-http-traves-de-post-metodo-facilmente/970368784/
     *
     * @param urlParaVisitar
     * @return respuesta del servidor puede serun JSON
     * @throws java.lang.Exception
     */
    public String peticionHttpGetEnvioCorreo(String urlParaVisitar) throws Exception{
        // Esto es lo que vamos a devolver
        StringBuilder resultado = new StringBuilder();
        // Crear un objeto de tipo URL
        URL url = new URL(urlParaVisitar);

        // Abrir la conexión e indicar que será de tipo GET
        HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
        conexion.setRequestMethod("GET");
        // Búferes para leer
        BufferedReader rd = new BufferedReader(new InputStreamReader(conexion.getInputStream()));
        String linea;
        // Mientras el BufferedReader se pueda leer, agregar contenido a resultado
        while ((linea = rd.readLine()) != null) {
            resultado.append(linea);
        }
        // Cerrar el BufferedReader
        rd.close();
        // Regresar resultado, pero como cadena, no como StringBuilder
        return resultado.toString();
    }

}
