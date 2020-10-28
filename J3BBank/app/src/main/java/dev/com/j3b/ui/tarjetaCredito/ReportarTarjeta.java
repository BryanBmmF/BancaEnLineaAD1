package dev.com.j3b.ui.tarjetaCredito;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
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

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import dev.com.j3b.MainActivity;
import dev.com.j3b.R;
import dev.com.j3b.modelos.ServidorSQL;
import dev.com.j3b.ui.ingreso.ActualizacionCredenciales;

public class ReportarTarjeta extends AppCompatActivity {

    private Spinner spinnerTarjetas, spinnerMotivos;
    private String dpiUsusario = "";
    private Button botonBloquearTarjeta;
    private ArrayList<String> arrayListTarjetas = new ArrayList<>();
    private static int tamañoLista = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reportar_tarjeta);
        spinnerTarjetas = (Spinner) findViewById(R.id.spinnerTarjetas);
        spinnerMotivos = (Spinner) findViewById(R.id.spinnerMotivoBloqueo);
        botonBloquearTarjeta = findViewById(R.id.bloquearButton);

        ArrayList<String> arrayListMotivos = new ArrayList<>();
        arrayListMotivos.add("EXTRAVIO DE PLASTICO");
        arrayListMotivos.add("ROBO DE PLASTICO");
        arrayListMotivos.add("DETERIORO DE PLASTICO");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.color_spinner_layout, arrayListMotivos);
        arrayAdapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        spinnerMotivos.setAdapter(arrayAdapter);

        try {
            recibirDatos();
            buscarTarjetas(dpiUsusario);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        botonBloquearTarjeta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (spinnerTarjetas.getSelectedItem() == null){
                    Toast.makeText(getApplicationContext(), "Actualmente no tienes tarjetas activas asociadas.", Toast.LENGTH_SHORT).show();
                    Toast.makeText(getApplicationContext(), "Actualmente no tienes tarjetas activas asociadas.", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    confirmarBloqueo(spinnerTarjetas.getSelectedItem().toString().substring(0,16), spinnerMotivos.getSelectedItem().toString());
                }

            }
        });
    }

    private void buscarTarjetas(String dpiCliente) throws NoSuchAlgorithmException {
        String consultaSQL = ServidorSQL.SERVIDORSQL_CONRETORNO+"SELECT * FROM TARJETA WHERE dpi_cuenta_habiente ='"+dpiCliente+"'"+" AND estado='ACTIVA'";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(consultaSQL, new Response.Listener<JSONArray>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(JSONArray response) {
                //variables a utilizar
                String opcionFinal = "";
                String tipoTarjeta ="";
                String numTarjeta = "";

                JSONObject jsonObjectDatosUsuario = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObjectDatosUsuario = response.getJSONObject(i);
                        //datos de tarjeta credito
                        numTarjeta = jsonObjectDatosUsuario.getString("no_tarjeta");
                        tipoTarjeta = jsonObjectDatosUsuario.getString("tipo");
                        opcionFinal =numTarjeta+" - "+tipoTarjeta;

                        arrayListTarjetas.add(opcionFinal); //guarda solo los datos importantes de la tarjeta
                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), "Hay problemas de conexión al servidor.", Toast.LENGTH_SHORT).show();
                    }
                }

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.color_spinner_layout, arrayListTarjetas);
                arrayAdapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
                //arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerTarjetas.setAdapter(arrayAdapter);
                tamañoLista = arrayListTarjetas.size();
                Toast.makeText(getApplicationContext(), "Tamaño: "+arrayListTarjetas.size(), Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //En este punto no tendría que haber errores.
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

    public void confirmarBloqueo(final String numeroTarjetaABloquear, String motivoDeBloqueo){

        //preguntando confirmacion para ejecutar la solicitud
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("¿Está seguro de bloquear la tarjeta "+numeroTarjetaABloquear+" por: "+motivoDeBloqueo+"?");
        builder.setTitle("Confirmación");
        builder.setCancelable(false);
        builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                //Limpiar  informacion
                ejecutarBloqueo(numeroTarjetaABloquear);

            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getApplicationContext(), "Solicitud cancelada", Toast.LENGTH_LONG).show();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void ejecutarBloqueo(final String noTarjetaBloquear){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServidorSQL.SERVIDORSQL_SINRETORNO, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "La tarjeta se ha bloqueado correctamente", Toast.LENGTH_SHORT).show();
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Aqui entra si algo sale mal
                Toast.makeText(getApplicationContext(), "Hubo un error. La tarjeta no ha sido bloqueada", Toast.LENGTH_SHORT).show();
            }
        }) {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                String consultaSQLUpdate = null;
                consultaSQLUpdate = "UPDATE TARJETA SET estado = 'DESACTIVADA' WHERE no_tarjeta = '" + noTarjetaBloquear + "'";
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("consultaSQL", consultaSQLUpdate);
                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void recibirDatos() throws NoSuchAlgorithmException {
        Bundle datos = getIntent().getExtras();
        if (datos != null) {
            dpiUsusario = (String) datos.getString("dpiusuario");
        }
    }
}