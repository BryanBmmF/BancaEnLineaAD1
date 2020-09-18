package dev.com.j3b.ui.transaccionesAjenas;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import dev.com.j3b.MainActivity;
import dev.com.j3b.R;
import dev.com.j3b.manejadorLogIn.ManejadorCuentaAjena;
import dev.com.j3b.modelos.ServidorSQL;
import dev.com.j3b.ui.aplicacion.VentanaPrincipal;

public class EvaluadorCodigoRegistroCuenta extends AppCompatActivity {

    private Button btnRealizarTransferencia;
    private EditText txtCodigo;

    public String cuentaOrigen;
    public String codigoGenerado;

    private ManejadorCuentaAjena manejadorCuentaAjena;
    private RequestQueue requestQueue ;

    // TextViews que mostrarán el tiempo restante
    private TextView tvTime;
    // Cronómetro de la aplicación.
    private CountDownTimer timer;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_espera_transferencia_terceros);
        cuentaOrigen = "";
        codigoGenerado = "";
        //voley global
        requestQueue = Volley.newRequestQueue(this);
        //campos recibidos
        txtCodigo = (EditText) findViewById(R.id.txtCodigo);
        btnRealizarTransferencia=(Button) findViewById(R.id.btnCompletarTransferencia);
        manejadorCuentaAjena = new ManejadorCuentaAjena();
        try { recibirDatos(); } catch (NoSuchAlgorithmException e) { e.printStackTrace(); }

        // Iniciamos variable de espera del cronometro.
        tvTime = (TextView) findViewById(R.id.tvTime);
        iniciarEvaluacion();

        //Evento para realizar transferencia
        btnRealizarTransferencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registrarCuentaTerceros();
            }
        });


    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void iniciarEvaluacion(){
        //generacion de codigo automatico
        codigoGenerado = manejadorCuentaAjena.generarCodigo();
        enviarCodigoRegistroCuenta(codigoGenerado); //se envia el codigo de transferencia y se inicia el conteo

    }

    private void aTrabajar() {
        // Si no esta funcionando, la iniciamos.
        //working = true;
        // Iniciamos el timer, como parámetros pasaremos el número de
        // minutos que hemos establecido en la aplicación, multiplicado
        // por 60 y por 1000 para obtener el valor en milisegundos, el
        // segúndo parámetro es el que nos dirá cada cuánto se produce el
        // "tick".
        timer = new CountDownTimer(1 * 60 * 1000, 100) {
            // Al declarar un nuevo CountDownTimer nos obliga a
            // sobreescribir algunos de sus eventos.
            @Override
            public void onTick(long millisUntilFinished) {
                // Este método se lanza por cada lapso de tiempo
                // transcurrido,
                tvTime.setText(String.valueOf(millisUntilFinished / 1000) + "s");
            }
            @Override
            public void onFinish() {
                // Este método se lanza cuando finaliza el contador.
                // Indicamos que la aplicación ya no está funcionando.
                //working = false;
                tvTime.setText("Brew Up!");
                // Mostramos el aviso de que ha finalizado el tiempo.
                Toast.makeText(getApplicationContext(), "Tiempo terminado", Toast.LENGTH_SHORT).show();
                //volver a la pantalla anterio
                volverAPaginaAnterior();
            }
        };

        // Una vez configurado el timer, lo iniciamos.
        timer.start();

    }
    public void volverAPaginaAnterior(){
        Intent intent = new Intent(this,RegistroCuentaConfianza.class);
        startActivity(intent);
        finish();
    }

    /**
     * Metodo para recibir de la ventana anterior
     *
     * */
    private void recibirDatos() throws NoSuchAlgorithmException {
        Bundle datos = getIntent().getExtras();
        if (datos != null) {
            cuentaOrigen = datos.getString("Cuenta");
            System.out.println("************ Cuenta a registrar: "+cuentaOrigen);
        }

    }

    /**
     * Metodo para registrar una cuentas de terceros
     * */
    public void registrarCuentaTerceros(){
        //verificamos el codigo ingresado y que no este vacio
        if (txtCodigo.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(), "No se a ingresado ningun código para verificar", Toast.LENGTH_SHORT).show();
        } else {
            if (txtCodigo.getText().toString().equals(codigoGenerado)) {
                //paramos el timer y registramos la transferencia
                timer.cancel();
                StringRequest stringRequest = new StringRequest(Request.Method.POST, ServidorSQL.SERVIDORSQL_SINRETORNO, new Response.Listener<String>() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(), "La cuenta se registro satisfactoriamente", Toast.LENGTH_SHORT).show();
                        notificarRegistroCuetaTerceros();
                        volverAPaginaAnterior();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Aqui entra si algo sale mal
                        Toast.makeText(getApplicationContext(), "Ocurrio un error en el Registro", Toast.LENGTH_SHORT).show();
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        String consultaSQL = "INSERT INTO CUENTA_DE_CONFIANZA VALUES (null,now(),'"+cuentaOrigen+"','"+VentanaPrincipal.cuentaHabienteLogueado.getDpiCliente()+"')";
                        System.out.println(consultaSQL);
                        Map<String, String> parametros = new HashMap<String, String>();
                        parametros.put("consultaSQL", consultaSQL);
                        return parametros;
                    }
                };

                requestQueue.add(stringRequest);
            } else {
                Toast.makeText(getApplicationContext(), "El Código ingresado no es correcto, porfavor intenta nuevamente", Toast.LENGTH_SHORT).show();
            }
        }


    }

    /**
     * Metodo para enviar notificacion despues de registrar la transfrencia
     *
     * */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void enviarCodigoRegistroCuenta(final String codigo){
        final LocalDateTime fecha = LocalDateTime.now();
        fecha.minusDays(1); //se restan 24 horas adelantadas en el sistema
        fecha.minusHours(6);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        final String fechaEnviar = fecha.format(formatter);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServidorSQL.SERVIDORSQL_NOTIFICACION_CODIGO_REGISTRO_TERCEROS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(getApplicationContext(), "Se envio una notificacion a su correo", Toast.LENGTH_SHORT).show();
                aTrabajar();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Aqui entra si algo sale mal
                Toast.makeText(getApplicationContext(), "ERROR al enviar la notificacion de codigo de transferencia", Toast.LENGTH_SHORT).show();
                volverAPaginaAnterior();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                /*Parametros a enviar por POST*/
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("CuentaDestino",cuentaOrigen);
                parametros.put("Codigo",codigo);
                parametros.put("Fecha",fechaEnviar);
                parametros.put("Email", VentanaPrincipal.cuentaHabienteLogueado.getEmail());

                return parametros;
            }
        };

        requestQueue.add(stringRequest);

    }

    /**
     * Metodo para enviar notificacion despues de registrar la nueva cuenta
     *
     * */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void notificarRegistroCuetaTerceros(){
        final LocalDateTime fecha = LocalDateTime.now();
        fecha.minusDays(1); //se restan 24 horas adelantadas en el sistema
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        final String fechaEnviar = fecha.format(formatter);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServidorSQL.SERVIDORSQL_NOTIFICACION_REGISTRO_CUENTA_TERCEROS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(getApplicationContext(), "Se envio una notificacion a su correo", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Aqui entra si algo sale mal
                Toast.makeText(getApplicationContext(), "ERROR al enviar la notificacion", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                /*Parametros a enviar por POST*/
                Map<String, String> parametros = new HashMap<String, String>();
                //parametros.put("Usuario", MainActivity.usuarioLogueado.getUsuarioCliente());
                parametros.put("NuevaCuenta",cuentaOrigen);
                parametros.put("Fecha",fechaEnviar);
                parametros.put("Email", VentanaPrincipal.cuentaHabienteLogueado.getEmail());

                return parametros;
            }
        };

        requestQueue.add(stringRequest);

    }


}