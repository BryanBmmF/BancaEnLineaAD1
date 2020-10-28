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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import dev.com.j3b.MainActivity;
import dev.com.j3b.R;
import dev.com.j3b.enums.EstadoDeCuenta;
import dev.com.j3b.manejadorLogIn.ManejadorCuentaAjena;
import dev.com.j3b.modelos.Cuenta;
import dev.com.j3b.modelos.ServidorSQL;
import dev.com.j3b.ui.aplicacion.VentanaPrincipal;

public class EvaluadorCodigoTransferencia extends AppCompatActivity {

    private Button btnRealizarTransferencia;
    private EditText txtCodigo;

    public String cuentaOrigen;
    public String cuentaDestino;
    public String monto;
    public String motivo;
    public String codigoGenerado;
    public static String emailRecep;

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
        cuentaDestino = "";
        cuentaOrigen = "";
        monto = "";
        motivo = "";
        codigoGenerado = "";
        emailRecep = "";
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
                registrarTransferenciaTerceros();
            }
        });



    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void iniciarEvaluacion(){
        //generacion de codigo automatico
        codigoGenerado = manejadorCuentaAjena.generarCodigo();
        enviarCodigoTransferencia(codigoGenerado); //se envia el codigo de transferencia y se inicia el conteo

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
        Intent intent = new Intent(this,TransaccionCuentasAjenas.class);
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
            cuentaOrigen = datos.getString("CuentaOrigen");
            System.out.println("************ Cuenta Origen: "+cuentaOrigen);
            cuentaDestino = datos.getString("CuentaDestino");
            System.out.println("************ Cuenta Destino: "+cuentaDestino);
            monto = datos.getString("Monto");
            System.out.println("************ Monto: "+monto);
            motivo = datos.getString("Motivo");
            System.out.println("************ Motivo: "+motivo);
        }

    }

    /**
     * Metodo para registrar una trenasferencia a cuentas de terceros
     * */
    public void registrarTransferenciaTerceros(){
        double montoTrans = Double.parseDouble(monto);
        final String montoEnviar = "Q."+montoTrans+"0";
        String consultaSQL = ServidorSQL.SERVIDORSQL_INSERCION_TRANSFERENCIA_TERCEROS+
                "SELECT transferir_cuenta_ajena" +
                "('"+cuentaOrigen+"','"+cuentaDestino+"','"+monto+"','"+motivo+"')";

        //verificamos el codigo ingresado y que no este vacio
        if (txtCodigo.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(), "No se a ingresado ningun código para verificar", Toast.LENGTH_SHORT).show();
        } else {
            if (txtCodigo.getText().toString().equals(codigoGenerado)){
                //paramos el timer y registramos la transferencia
                timer.cancel();
                JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(consultaSQL,
                        new Response.Listener<JSONArray>() {
                            @RequiresApi(api = Build.VERSION_CODES.O)
                            @Override
                            public void onResponse(JSONArray response) {
                                JSONObject jsonObjectDatosTrans=null;
                                for (int i = 0; i < response.length(); i++) {
                                    try {
                                        jsonObjectDatosTrans = response.getJSONObject(i);
                                        String respuesta = jsonObjectDatosTrans.getString("respuesta");

                                        if (respuesta.equalsIgnoreCase("valida")){
                                            Toast.makeText(getApplicationContext(), "La transferencia se realizo correctamente", Toast.LENGTH_SHORT).show();
                                            notificarTransferenciaTerceros(montoEnviar);
                                            volverAPaginaAnterior();
                                        } else {
                                            String error = jsonObjectDatosTrans.getString("error");
                                            //la transferencia no se pudo realizar debido a un error
                                            Toast.makeText(getApplicationContext(), "Ocurrio el siguiente error: "+error, Toast.LENGTH_SHORT).show();
                                            volverAPaginaAnterior();
                                        }
                                    }catch (JSONException e){
                                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //en caso de no recuperar nada
                        System.out.println("ERROR AL GENERAR JSON");
                        Toast.makeText(getApplicationContext(), "Es posible que tu cuenta personal no tenga fondos suficientes para hacer la transferencia. Porfavor verifica", Toast.LENGTH_SHORT).show();
                        volverAPaginaAnterior();
                    }
                });

                requestQueue.add(jsonArrayRequest);

            } else{
                Toast.makeText(getApplicationContext(), "El Código ingresado no es correcto, porfavor intenta nuevamente", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * Metodo para enviar notificacion despues de registrar la transfrencia
     *
     * */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void enviarCodigoTransferencia(final String codigo){
        final LocalDateTime fecha = LocalDateTime.now();
        fecha.minusDays(1); //se restan 24 horas adelantadas en el sistema
        fecha.minusHours(6);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        final String fechaEnviar = fecha.format(formatter);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServidorSQL.SERVIDORSQL_NOTIFICACION_CODIGO_TRANSFERENCIA, new Response.Listener<String>() {
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
                parametros.put("CuentaDestino",cuentaDestino);
                parametros.put("Codigo",codigo);
                parametros.put("Fecha",fechaEnviar);
                parametros.put("Email", VentanaPrincipal.cuentaHabienteLogueado.getEmail());

                return parametros;
            }
        };

        requestQueue.add(stringRequest);

    }

    /**
     * Metodo para enviar notificacion despues de registrar la transfrencia
     *
     * */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void notificarTransferenciaTerceros(final String montoEnviar){
        final LocalDateTime fecha = LocalDateTime.now();
        fecha.minusDays(1); //se restan 24 horas adelantadas en el sistema
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        final String fechaEnviar = fecha.format(formatter);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServidorSQL.SERVIDORSQL_NOTIFICACION_TRANSFERENCIA_TERCEROS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(getApplicationContext(), "Se envio una notificacion a su correo", Toast.LENGTH_SHORT).show();
                // En el caso de que la aplicación sí que esté funcionando, la
                // pausamos, cancelamos el timer y actualizamos el texto del
                //working = false;

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
                parametros.put("Usuario",MainActivity.usuarioLogueado.getUsuarioCliente());
                parametros.put("CuentaPersonal",cuentaOrigen);
                parametros.put("CuentaDestino",cuentaDestino);
                parametros.put("Monto",montoEnviar);
                parametros.put("Fecha",fechaEnviar);
                parametros.put("EmailEmisor", VentanaPrincipal.cuentaHabienteLogueado.getEmail());
                parametros.put("EmailReceptor", consultarDatosDeUsuarioAcreedor(cuentaDestino));

                return parametros;
            }
        };

        requestQueue.add(stringRequest);

    }

    /**
     * Se consultan los datos del usuario acreedor y se retorna su email para ser notificado
     * @param numCuenta
     */
    public String consultarDatosDeUsuarioAcreedor(String numCuenta){
        RequestQueue  requestQueue = Volley.newRequestQueue(this);
        String consultaSQL = ServidorSQL.SERVIDORSQL_CONRETORNO+"" +
                "SELECT * FROM cuenta c JOIN cuenta_habiente ch " +
                " ON c.dpi_cliente = ch.dpi_cliente WHERE c.no_cuenta_bancaria ='"+numCuenta+"'";
        System.out.println("\n\nCONSULTA:"+consultaSQL);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(consultaSQL,
                new Response.Listener<JSONArray>() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onResponse(JSONArray response) {
                        JSONObject jsonObjectDatosCuenta=null;
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                jsonObjectDatosCuenta = response.getJSONObject(i);
                                emailRecep = jsonObjectDatosCuenta.getString("email");
                                //System.out.println("1.++++++++++++++++++++++++++++++++++++++++++ email recup:  "+ emailRecep);

                            }catch (JSONException e){
                                Toast.makeText(null, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //En caso de que la consulta SQL no encuentre ningún dato, el codigo ingresa en esta sección.
                System.out.println("ERROR AL CONSULTAR LOS DATOS DEL USUARIO ACREEDOR");
                Toast.makeText(null, "ERROR AL CONSULTAR LOS DATOS DEL USUARIO ACREEDOR", Toast.LENGTH_SHORT).show();
            }
        });
        //RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
        //System.out.println("2.++++++++++++++++++++++++++++++++++++++++++ email recup:  "+ emailRecep);
        return emailRecep;
    }


}
