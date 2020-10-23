package dev.com.j3b.ui.ingreso;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.safetynet.SafetyNet;
import com.google.android.gms.safetynet.SafetyNetApi;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONObject;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import dev.com.j3b.MainActivity;
import dev.com.j3b.R;
import dev.com.j3b.manejadorLogIn.ManejadorLogin;
import dev.com.j3b.modelos.ServidorSQL;
import dev.com.j3b.modelos.Usuario;
import dev.com.j3b.ui.aplicacion.VentanaPrincipal;

public class ActualizacionCredenciales extends AppCompatActivity {

    ManejadorLogin manejadorLogin = new ManejadorLogin();
    private Usuario usuarioRecibido = new Usuario();
    private ProgressBar seguridadProgressBar;
    private TextView nivelSeguridadTextView;
    private EditText nuevaContraseñaEditText, actualContraseñaEditText, confirmarContraseñaEditText;
    private Button guardarCambiosButton;
    private Boolean contraseñaNuevaSegura = false;
    //Variables para verificar clave
    String TAG = MainActivity.class.getSimpleName();
    String SITE_KEY="6Ld3qsMZAAAAAItneAEQoC5fttfLs8Bd2Mj63IKa";
    String SECRET_KEY="6Ld3qsMZAAAAADUOHvnoTFbERpbHOpuiLNbV4SyS";
    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizacion_credenciales);
        recibirDatos();
        //Iniciamos la variable
        queue = Volley.newRequestQueue(getApplicationContext());
        seguridadProgressBar = findViewById(R.id.nivelSeguridadProgressBar);
        nivelSeguridadTextView = findViewById(R.id.seguridadTextView);
        nuevaContraseñaEditText = findViewById(R.id.editNuevaTextPassword);
        actualContraseñaEditText = findViewById(R.id.editActualTextPassword);
        confirmarContraseñaEditText = findViewById(R.id.editConfirmarTextPassword);
        guardarCambiosButton = findViewById(R.id.guardarCambiosButton);
        //Con este metodo capturamos los eventos o cambios que suceden en el Edit Text de la contraseña nueva
        nuevaContraseñaEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                actualizarBarraSeguridad(manejadorLogin.comprobarSeguridadPassword(nuevaContraseñaEditText.getText().toString()));
            }
        });

        guardarCambiosButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    iniciarValidaciones();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
            }

        });
    }


    private void iniciarValidaciones() throws NoSuchAlgorithmException {
        //Primero verificamos que todos los campos posean informacion ingresada
        if (actualContraseñaEditText.getText().toString().isEmpty() || nuevaContraseñaEditText.getText().toString().isEmpty() || confirmarContraseñaEditText.getText().toString().isEmpty()) {
            Toast.makeText(ActualizacionCredenciales.this, "Debes ingresar toda la información que se solicita", Toast.LENGTH_SHORT).show();
        }
        //Si todos los campos fueron debidamente ingresados por el usuario pasamos a los siguientes filtros de seguridad
        else {
            //Verificamos si la nueva contraseña es segura
            if (contraseñaNuevaSegura) {
                //Verificamos si la nueva contraseña coincide con su confirmacion
                if (nuevaContraseñaEditText.getText().toString().equals(confirmarContraseñaEditText.getText().toString())) {
                    //Verificamos si existe alguna coincidencia con la contraseña actual o dos anteriores
                    if (manejadorLogin.verificarAntiguaCoincidencia(usuarioRecibido, nuevaContraseñaEditText.getText().toString())) {
                        //Si exite alguna coincidencia emitimos una alerta para el usuario
                        Toast.makeText(ActualizacionCredenciales.this, "No puedes utilizar tu contraseña actual u otra que hayas utilizado recientemente", Toast.LENGTH_SHORT).show();
                    } else {
                        //si no exiten coincidencia, procedemos a verificar que la contraseña actual coincida con la ingresada
                        if (usuarioRecibido.getContraseñaActual().equals(manejadorLogin.generarMD5(actualContraseñaEditText.getText().toString()))) {
                            //si la contraseña ingresada es la correcta entonces procedemos a realizar la actualizacion de credenciales
                            verificacionInicialCaptcha();

                        } else {
                            //Notificamos al usuario que la contraseña actual no coinicide con la ingresada
                            Toast.makeText(ActualizacionCredenciales.this, "Tu contraseña actual es incorrecta", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                //Emitimos un mensaje si las contraseñas nuevas no coinciden para su confirmacion
                else {
                    Toast.makeText(ActualizacionCredenciales.this, "La nueva contraseña no coincide con su confirmacion", Toast.LENGTH_SHORT).show();
                }
            }
            //Si la contraseña no es 100% segura, se emite una alerta al usuario
            else {
                Toast.makeText(ActualizacionCredenciales.this, "La nueva contraseña aún no es segura", Toast.LENGTH_SHORT).show();
            }
        }
    }

    //Con este metodo ejecutamos la consulta a BD para que realize la actualizacion de datos del usuario.
    public void actualizarUsuario(final String usuarioCliente, final String nuevaContraseña) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServidorSQL.SERVIDORSQL_SINRETORNO, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(ActualizacionCredenciales.this, "Tus credenciales se actualizaron correctamente", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Aqui entra si algo sale mal
            }
        }) {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                String consultaSQLUpdate = null;
                try {
                    consultaSQLUpdate = "UPDATE USUARIO_CLIENTE SET contrasena = '" + manejadorLogin.generarMD5(nuevaContraseña) + "', fecha_ultimo_cambio='" + manejadorLogin.calcularFechaActual() + "', fecha_caducidad = '" + manejadorLogin.calcularFechaCaducidad() + "', contrasena1 = '" + usuarioRecibido.getContraseñaActual() + "', contrasena2 = '" + usuarioRecibido.getContraseña1() + "', estado='ACTIVADO' WHERE usuario_cliente = '" + usuarioCliente + "'";
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("consultaSQL", consultaSQLUpdate);
                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void actualizarBarraSeguridad(Integer porcentajeSeguridad) {
        //Integer porcentajeSeguridad = manejadorLogin.comprobarPassword(nuevaContraseñaEditText.getText().toString());
        switch (porcentajeSeguridad) {
            case 0:
                contraseñaNuevaSegura = false;
                nivelSeguridadTextView.setText("");
                seguridadProgressBar.setProgress(0);
                break;
            case 20:
                contraseñaNuevaSegura = false;
                seguridadProgressBar.setProgress(porcentajeSeguridad);
                seguridadProgressBar.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#c72121")));
                nivelSeguridadTextView.setText("Nivel de seguridad: Ninguno");
                break;
            case 40:
                contraseñaNuevaSegura = false;
                seguridadProgressBar.setProgress(porcentajeSeguridad);
                seguridadProgressBar.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#f44949")));
                nivelSeguridadTextView.setText("Nivel de seguridad: Muy bajo");
                break;
            case 60:
                contraseñaNuevaSegura = false;
                seguridadProgressBar.setProgress(porcentajeSeguridad);
                seguridadProgressBar.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#ff7800")));
                nivelSeguridadTextView.setText("Nivel de seguridad: Bajo");
                break;
            case 80:
                contraseñaNuevaSegura = false;
                seguridadProgressBar.setProgress(porcentajeSeguridad);
                seguridadProgressBar.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#ffc600")));
                nivelSeguridadTextView.setText("Nivel de seguridad: Medio");
                break;
            case 100:
                contraseñaNuevaSegura = true;
                seguridadProgressBar.setProgress(porcentajeSeguridad);
                seguridadProgressBar.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#5ce512")));
                nivelSeguridadTextView.setText("Nivel de seguridad: Alto");
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            cerrarSesion();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void cerrarSesion() {
        new AlertDialog.Builder(this)
                .setIcon(R.drawable.padlock)
                .setTitle("¿Deseas cerrar sesión?")
                .setCancelable(false)
                .setNegativeButton(android.R.string.cancel, null)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {//Un listener que al pulsar, cierre la aplicacion
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent redireccionarLogin = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(redireccionarLogin);
                        //android.os.Process.killProcess(android.os.Process.myPid()); //Su funcion es algo similar a lo que se llama cuando se presiona el botón "Forzar Detención" o "Administrar aplicaciones", lo cuál mata la aplicación
                        finish();
                        //Si solo quiere mandar la aplicación a segundo plano
                    }
                }).show();
    }

    private void recibirDatos() {
        Bundle datos = getIntent().getExtras();
        if (datos != null) {
            usuarioRecibido = (Usuario) datos.getSerializable("usuario");
        }
    }

    private void limpiarCampos() {
        actualContraseñaEditText.setText("");
        nuevaContraseñaEditText.setText("");
        confirmarContraseñaEditText.setText("");
    }



    protected void verificacionInicialCaptcha(){
        SafetyNet.getClient(ActualizacionCredenciales.this).verifyWithRecaptcha(SITE_KEY)
                .addOnSuccessListener( ActualizacionCredenciales.this,
                        new OnSuccessListener<SafetyNetApi.RecaptchaTokenResponse>() {
                            @Override
                            public void onSuccess(SafetyNetApi.RecaptchaTokenResponse response) {
                                // Indicates communication with reCAPTCHA service was
                                // successful.
                                String userResponseToken = response.getTokenResult();
                                if (!userResponseToken.isEmpty()) {
                                    // Validate the user response token using the
                                    // reCAPTCHA siteverify API.
                                    handleSiteVerify(userResponseToken);
                                }
                            }
                        })
                .addOnFailureListener( ActualizacionCredenciales.this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        if (e instanceof ApiException) {
                            // An error occurred when communicating with the
                            // reCAPTCHA service. Refer to the status code to
                            // handle the error appropriately.
                            ApiException apiException = (ApiException) e;
                            int statusCode = apiException.getStatusCode();
                            Log.d(TAG, " O ACA->Error: " + CommonStatusCodes
                                    .getStatusCodeString(statusCode));
                        } else {
                            // A different, unknown type of error occurred.
                            Log.d(TAG, "AQUI->Error: " + e.getMessage());
                        }
                    }
                });
    }

    /**
     * Metodo para la verificacion de recaptcha, usando la verificacion de google
     * y el token generado localmente
     * @param responseToken
     */
    protected  void handleSiteVerify(final String responseToken){
        //it is google recaptcha siteverify server
        //you can place your server url
        String url = "https://www.google.com/recaptcha/api/siteverify";
        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if(jsonObject.getBoolean("success")){
                                //code logic when captcha returns true Toast.makeText(getApplicationContext(),String.valueOf(jsonObject.getBoolean("success")),Toast.LENGTH_LONG).show();
                                //Si pasa el captcha se procede a actualizar la base de datos
                                Intent nuevaVentanaPrincipal = new Intent(ActualizacionCredenciales.this,VentanaPrincipal.class);
                                actualizarUsuario(usuarioRecibido.getUsuarioCliente(), nuevaContraseñaEditText.getText().toString());
                                Bundle nuevoBundle = new Bundle();
                                nuevoBundle.putSerializable("usuario", usuarioRecibido);
                                nuevaVentanaPrincipal.putExtras(nuevoBundle);
                                startActivity(nuevaVentanaPrincipal);
                                limpiarCampos();
                                finish();
                            }
                            else{
                                Toast.makeText(getApplicationContext(),String.valueOf(jsonObject.getString("error-codes")),Toast.LENGTH_LONG).show();
                            }
                        } catch (Exception ex) {
                            Log.d(TAG, "JSON exception: " + ex.getMessage());

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, "Error message: " + error.getMessage());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("secret", SECRET_KEY);
                params.put("response", responseToken);
                return params;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(
                50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(request);
    }

}