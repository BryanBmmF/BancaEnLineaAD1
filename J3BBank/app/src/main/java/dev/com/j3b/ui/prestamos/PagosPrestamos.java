package dev.com.j3b.ui.prestamos;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import dev.com.j3b.R;
import dev.com.j3b.modelos.ServidorSQL;
import dev.com.j3b.ui.consultaCuentas.AdapterDatos;

public class PagosPrestamos extends AppCompatActivity {

    private String idPrestamo = "";


    private ArrayList<String> listDatosRealizados = new ArrayList<>();
    private RecyclerView recyclerRealizados;

    private ArrayList<String> listDatosPendientes = new ArrayList<>();
    private RecyclerView recyclerPendientes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagos_prestamos);

        recyclerRealizados = findViewById(R.id.recyclerId1);
        recyclerRealizados.setLayoutManager(new LinearLayoutManager(this));

        recyclerPendientes = findViewById(R.id.recyclerId2);
        recyclerPendientes.setLayoutManager(new LinearLayoutManager(this));

        try {
            recibirDatos();

            buscarRealizados(idPrestamo);
            buscarPendientes(idPrestamo);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    private void buscarRealizados(String idPrestamo) throws NoSuchAlgorithmException {
        listDatosRealizados.clear();
        String consultaSQL = ServidorSQL.SERVIDORSQL_CONRETORNO+"SELECT * FROM PAGO_PRESTAMO WHERE id_prestamo ='"+idPrestamo+"' AND estado = 'PAGADO' order by fecha_pago desc;";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(consultaSQL, new Response.Listener<JSONArray>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObjectDatosMovimiento = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObjectDatosMovimiento = response.getJSONObject(i);
                        String idPagoPrestamo = jsonObjectDatosMovimiento.getString("id_pago_prestamo");
                        String montoCuota = jsonObjectDatosMovimiento.getString("monto");
                        String fechaDePago = jsonObjectDatosMovimiento.getString("fecha_pago");
                        String nuevoRegistro = "ID: "+idPagoPrestamo+" - Total: Q. "+montoCuota+"\nFecha de pago: "+fechaDePago;
                        listDatosRealizados.add(nuevoRegistro);
                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), "Hay problemas de conexión al servidor.", Toast.LENGTH_SHORT).show();
                    }
                }

                AdapterDatos adapterDatos = new AdapterDatos(listDatosRealizados);
                recyclerRealizados.setAdapter(adapterDatos);

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

    private void buscarPendientes(String idPrestamo) throws NoSuchAlgorithmException {
        listDatosPendientes.clear();
        String consultaSQL = ServidorSQL.SERVIDORSQL_CONRETORNO+"SELECT * FROM PAGO_PRESTAMO WHERE id_prestamo ='"+idPrestamo+"' AND estado = 'PENDIENTE';";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(consultaSQL, new Response.Listener<JSONArray>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObjectDatosMovimiento = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObjectDatosMovimiento = response.getJSONObject(i);
                        String idPagoPrestamo = jsonObjectDatosMovimiento.getString("id_pago_prestamo");
                        String montoCuota = jsonObjectDatosMovimiento.getString("monto");
                        String nuevoRegistro = "ID: "+idPagoPrestamo+" - Cuota: Q. "+montoCuota;
                        listDatosPendientes.add(nuevoRegistro);
                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), "Hay problemas de conexión al servidor.", Toast.LENGTH_SHORT).show();
                    }
                }

                AdapterDatos adapterDatos = new AdapterDatos(listDatosPendientes);
                recyclerPendientes.setAdapter(adapterDatos);

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

    private void recibirDatos() throws NoSuchAlgorithmException {
        Bundle datos = getIntent().getExtras();
        if (datos != null) {
            idPrestamo = (String) datos.getString("idprestamo");
        }
    }
}