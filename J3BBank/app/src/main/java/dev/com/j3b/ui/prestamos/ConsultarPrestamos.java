package dev.com.j3b.ui.prestamos;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
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
import java.util.ArrayList;

import dev.com.j3b.R;
import dev.com.j3b.modelos.ServidorSQL;
import dev.com.j3b.ui.aplicacion.VentanaPrincipal;

public class ConsultarPrestamos extends AppCompatActivity {

    private String dpiUsusario = "";
    private Spinner spinnerPrestamos;
    private ArrayList<String> arrayListPrestamos = new ArrayList<>();
    private Button consultarPrestamoButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_prestamos);
        spinnerPrestamos = findViewById(R.id.spinnerPrestamos);
        consultarPrestamoButton = findViewById(R.id.verPrestamoButton);

        try {
            recibirDatos();
            buscarPrestamos(dpiUsusario);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        //Evento para realizar transferencia
        consultarPrestamoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                consultarEstadoPrestamo(spinnerPrestamos.getSelectedItem().toString().substring(0,4));
            }
        });
    }

    private void consultarEstadoPrestamo(String identificadorPrestamo){
        Intent nuevaVentanaPrincipal = new Intent(this, PagosPrestamos.class);
        Bundle nuevoBundlePrincipal = new Bundle();
        nuevoBundlePrincipal.putSerializable("idprestamo", identificadorPrestamo);
        nuevaVentanaPrincipal.putExtras(nuevoBundlePrincipal);
        startActivity(nuevaVentanaPrincipal);
    }

    private void buscarPrestamos(String dpiCliente) throws NoSuchAlgorithmException {
        String consultaSQL = ServidorSQL.SERVIDORSQL_CONRETORNO+"SELECT * FROM PRESTAMO WHERE dpi_cuenta_habiente ='"+dpiCliente+"'"+" AND estado='activo'";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(consultaSQL, new Response.Listener<JSONArray>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(JSONArray response) {
                //variables a utilizar
                String id_prestamo = "";
                String monto_total ="";
                String deuda_restante = "";
                String cantidad_meses = "";
                String tipo = "";
                String fecha_vencimiento = "";
                String tasaInteres = "";
                double calInteres = 0;
                String opcionFinal = "";

                JSONObject jsonObjectDatosUsuario = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObjectDatosUsuario = response.getJSONObject(i);
                        //datos de prestamo
                        id_prestamo = jsonObjectDatosUsuario.getString("id_prestamo");
                        monto_total = jsonObjectDatosUsuario.getString("montoTotal");
                        deuda_restante = jsonObjectDatosUsuario.getString("deuda_restante");
                        cantidad_meses = jsonObjectDatosUsuario.getString("cantidad_meses");
                        tipo = jsonObjectDatosUsuario.getString("tipo");
                        fecha_vencimiento = jsonObjectDatosUsuario.getString("fecha_vencimiento");
                        tasaInteres = jsonObjectDatosUsuario.getString("tasa_interes");
                        calInteres = Double.parseDouble(tasaInteres)*100;
                        opcionFinal =id_prestamo+"\n  Monto: Q. "+monto_total+ "\n  Deuda: Q. "+deuda_restante+"\n  Plazo: "+cantidad_meses+" meses\n  Tipo: "+tipo+"\n  Interes: "+calInteres+"%\n  Vence: "+fecha_vencimiento;

                        arrayListPrestamos.add(opcionFinal); //guarda solo los datos importantes de la tarjeta
                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), "Hay problemas de conexión al servidor.", Toast.LENGTH_SHORT).show();
                    }
                }

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.color_spinner_layout, arrayListPrestamos);
                arrayAdapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
                //arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerPrestamos.setAdapter(arrayAdapter);

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
            dpiUsusario = (String) datos.getString("dpiusuario");
        }
    }
}