package dev.com.j3b;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
import java.util.HashMap;
import java.util.Map;

import dev.com.j3b.manejadorLogIn.ManejadorLogin;
import dev.com.j3b.modelos.ServidorSQL;
import dev.com.j3b.modelos.Usuario;
import dev.com.j3b.ui.aplicacion.VentanaPrincipal;
import dev.com.j3b.ui.ingreso.ActualizacionCredenciales;
import dev.com.j3b.ui.ingreso.AvisoBloqueo;

public class MainActivity extends AppCompatActivity {

    private ManejadorLogin manejadorLogin = new ManejadorLogin();
    private EditText editUsuarioText, editContraseñaText;
    public static Usuario usuarioLogueado = new Usuario();
    private Button ingresarButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editContraseñaText = findViewById(R.id.editTextPassword);
        editUsuarioText = findViewById(R.id.editUsuarioTextNumber);
        ingresarButton = findViewById(R.id.loginButton);

        ingresarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    verificarCamposVacios();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /*
    Metodo que unicamente valida si los campos se encuentran vacios, es el primer filtro antes de ejecutar la consulta a base de datos.
     */
    private void verificarCamposVacios() throws NoSuchAlgorithmException {
        if (editUsuarioText.getText().toString().isEmpty() || editContraseñaText.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(), "Ingresa tu usuario y contraseña", Toast.LENGTH_SHORT).show();
        } else {
            buscarUsuario(editUsuarioText.getText().toString(), editContraseñaText.getText().toString());
            //iniciarVerificacionDeDatos();
        }
    }

    /*
    Se recibre como parametros los datos ingresados por el usuario para realizar la consulta a la base de datos y obtener
    cada una de las caracteristicas de este usuario.
     */
    private void buscarUsuario(String usuarioIngresado, final String contraseñaIngresada) throws NoSuchAlgorithmException {
        String consultaSQL = ServidorSQL.SERVIDORSQL_CONRETORNO+"SELECT * FROM USUARIO_CLIENTE WHERE usuario_cliente='"+usuarioIngresado+"'";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(consultaSQL, new Response.Listener<JSONArray>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(JSONArray response) {
                usuarioLogueado = new Usuario();
                JSONObject jsonObjectDatosUsuario = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObjectDatosUsuario = response.getJSONObject(i);
                        usuarioLogueado.setUsuarioCliente(jsonObjectDatosUsuario.getString("usuario_cliente"));
                        usuarioLogueado.setDpiCliente(jsonObjectDatosUsuario.getString("dpi_cliente"));
                        usuarioLogueado.setContraseñaActual(jsonObjectDatosUsuario.getString("contrasena"));
                        usuarioLogueado.setFechaUltimoCambio(jsonObjectDatosUsuario.getString("fecha_ultimo_cambio"));
                        usuarioLogueado.setFechaCaducidad(jsonObjectDatosUsuario.getString("fecha_caducidad"));
                        usuarioLogueado.setContraseña1(jsonObjectDatosUsuario.getString("contrasena1"));
                        usuarioLogueado.setContraseña2(jsonObjectDatosUsuario.getString("contrasena2"));
                        usuarioLogueado.setEstadoCuenta(jsonObjectDatosUsuario.getString("estado"));
                        String intentosRealizadosStr = jsonObjectDatosUsuario.getString("intento_de_ingresos");
                        int intentosRealizados = Integer.parseInt(intentosRealizadosStr);
                        usuarioLogueado.setIntentoIngresos(intentosRealizados);
                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                /*
                Si el usuario ingresado es valido, podemos ejecutar las instrucciones con dicha informacion a continuacion de este comentario:
                 */
                try {
                    validarUsuario(usuarioLogueado, manejadorLogin.generarMD5(contraseñaIngresada));
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //En caso de que la consulta SQL no encuentre ningún dato, el codigo ingresa en esta sección.
                usuarioLogueado = new Usuario();
                Toast.makeText(getApplicationContext(), "Por favor, verifica los datos ingresados", Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void validarUsuario(Usuario usuario, String contraseñaIngresada){
        //Verificamos que el usuario no haya pasado el limite de intentos de ingreso
        if (usuario.getIntentoIngresos() < 5){
            //Verificamos si se ha ingresado la contraseña correcta
            if (usuario.getContraseñaActual().equals(contraseñaIngresada)){
                //Si las credenciales del usuario con correctas, reseteamos los intentos fallidos en caso el usuario los hubiera cometido
                resetearIntentosDeIngreso(usuario.getUsuarioCliente());
                //Comprobamos la fecha de caducidad de la contraseña, si es TRUE es porque aun posee vigencia
                if (manejadorLogin.comprobarFecha(usuario.getFechaCaducidad())){
                    switch (usuario.getEstadoCuenta()){
                        //En el caso que sea un NUEVO usuario redirigimos a ventana de actualizacion de datos para que el usuario
                        //cambie la contraseña proporcionada por el banco por una diseñada por el mismo.
                        case "NUEVO":
                            Intent nuevoActualizarCredenciales = new Intent(this, ActualizacionCredenciales.class);
                            Bundle nuevoBundle = new Bundle();
                            nuevoBundle.putSerializable("usuario", usuario);
                            nuevoActualizarCredenciales.putExtras(nuevoBundle);
                            startActivity(nuevoActualizarCredenciales);
                            limpiarCampos();
                            finish();
                            break;
                        //Una vez pasados todos los filtros de seguridad redirigimos a la ventana principal de la aplicacion.
                        case "ACTIVADO":
                            Intent nuevaVentanaPrincipal = new Intent(this, VentanaPrincipal.class);
                            Bundle nuevoBundlePrincipal = new Bundle();
                            nuevoBundlePrincipal.putSerializable("usuario", usuario);
                            nuevaVentanaPrincipal.putExtras(nuevoBundlePrincipal);
                            startActivity(nuevaVentanaPrincipal);
                            limpiarCampos();
                            finish();
                            break;
                        //Redirigimos a la ventana de aviso que la cuenta se encuentra bloqueada.
                        case "BLOQUEADO":
                            Intent intent = new Intent(getApplicationContext(), AvisoBloqueo.class);
                            startActivity(intent);
                            limpiarCampos();
                            finish();
                            break;
                    }
                }
                //Si retorna un FALSE es porque la contraseña esta vencida y se solicita que se renueve la contraseña.
                else { //Se redirige a la ventana de actualizacion de credenciales.
                    Intent nuevoActualizarCredenciales = new Intent(this, ActualizacionCredenciales.class);
                    Bundle nuevoBundle = new Bundle();
                    nuevoBundle.putSerializable("usuario", usuario);
                    nuevoActualizarCredenciales.putExtras(nuevoBundle);
                    startActivity(nuevoActualizarCredenciales);
                    limpiarCampos();
                    finish();
                }
            }
            //Si en dado caso no se ingreso la contraseña correcta entonces aumentamos en 1 los intentos realizados.
            else {
                aumentarUnIntentoFallido(usuarioLogueado.getUsuarioCliente());
            }
        }
        //Si el usuario YA sobrepaso sus intentos se redirige a una ventana de aviso de cuenta bloqueada
        else {
            //Se ejecuta metodo el cual actualiza el estado de la cuenta a "BLOQUEADO"
            bloquearUsuario(usuarioLogueado.getUsuarioCliente());
            Intent intent = new Intent(getApplicationContext(), AvisoBloqueo.class);
            startActivity(intent);
            limpiarCampos();
            finish();
        }
    }

    //Resetea los intentos a cero si el usuario tuvo intentos fallidos previos a su ingreso.
    public void resetearIntentosDeIngreso(final String usuarioCliente){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServidorSQL.SERVIDORSQL_SINRETORNO, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }){ @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                String consultaSQLUpdate = "UPDATE USUARIO_CLIENTE SET intento_de_ingresos = '0' WHERE usuario_cliente = '"+usuarioCliente+"'";
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("consultaSQL", consultaSQLUpdate);
                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    //Cada vez que un usuario ingresa un usuario valido pero contraseña incorrecta, este aumenta un intento fallido
    //5 es limite de intentos fallidos.
    public void aumentarUnIntentoFallido(final String usuarioCliente){
        Integer intentosRealizados = usuarioLogueado.getIntentoIngresos() + 1;
        final String mensajeFallos = intentosRealizados+" intentos fallidos";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServidorSQL.SERVIDORSQL_SINRETORNO, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(MainActivity.this, mensajeFallos, Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Aqui entra si algo sale mal
                Toast.makeText(MainActivity.this, "¡Algo salió mal!", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                String consultaSQLUpdate = "UPDATE USUARIO_CLIENTE SET intento_de_ingresos = intento_de_ingresos + 1 WHERE usuario_cliente = '"+usuarioCliente+"'";
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("consultaSQL", consultaSQLUpdate);
                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    //Si un usuario ha intentado ingresar muchas veces sin exito a un usuario este pasa a un estado de BLOQUEADO.
    public void bloquearUsuario(final String usuarioCliente){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServidorSQL.SERVIDORSQL_SINRETORNO, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(MainActivity.this, "Tu usuario se ha bloqueado por seguridad", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Aqui entra si algo sale mal
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                String consultaSQLUpdate = "UPDATE USUARIO_CLIENTE SET estado = 'BLOQUEADO' WHERE usuario_cliente = '"+usuarioCliente+"'";
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("consultaSQL", consultaSQLUpdate);
                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    //Metodo para limpiar los campos de ingreso de usuario y contraseña
    public void limpiarCampos(){
        editUsuarioText.setText("");
        editContraseñaText.setText("");
    }

}