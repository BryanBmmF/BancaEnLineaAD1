package dev.com.j3b.ui.consultaCuentas;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import dev.com.j3b.R;
import dev.com.j3b.modelos.ServidorSQL;
import dev.com.j3b.modelos.Usuario;

public class ConsultaCuentas extends AppCompatActivity implements View.OnClickListener {

    private String dpiUsusario = "";
    private ArrayList<String> arrayListCuentas = new ArrayList<>();
    private Spinner spinner;
    private Button bDesde, bHasta,bBuscar;
    private EditText fechaFin, fechaIni;
    private int dia1, dia2, mes1, mes2, anio1, anio2;
    private String textoFinalSaldo ="";

    private ListView listView;
    private ArrayList<String> arrayListMovimientos =  new ArrayList<>();
    private ArrayAdapter<String> adapter;
    private EditText txtInput;
    private TextView textViewSaldo;

    private ArrayList<String> listDatos = new ArrayList<>();
    private RecyclerView recycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta_cuentas);
        recycler = findViewById(R.id.recyclerId);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        //listView = (ListView)  findViewById(R.id.listv);
        //listView.setAdapter(adapter);
        textViewSaldo = findViewById(R.id.textSaldo);
        spinner = findViewById(R.id.spinner);
        bDesde = findViewById(R.id.desdeBtn);
        bHasta = findViewById(R.id.hastaBtn);
        bBuscar = findViewById(R.id.buscarBtn);
        fechaFin = findViewById(R.id.fechaFinalTxt);
        fechaIni = findViewById(R.id.fechaInicialTxt);
        try {
            recibirDatos();
            buscarCuentas(dpiUsusario);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        bHasta.setOnClickListener(this);
        bDesde.setOnClickListener(this);
        bBuscar.setOnClickListener(this);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String noCuentaSeleccionada = spinner.getSelectedItem().toString().substring(0,10);
                //String noCuentaSeleccionada = parent.getItemAtPosition(position).toString();
                try {
                    buscarSaldoCuentaSeleccionada(noCuentaSeleccionada);
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
                //Toast.makeText(parent.getContext(), "Selected: " + tutorialsName,          Toast.LENGTH_LONG).show();
            }
            @Override
            public void onNothingSelected(AdapterView <?> parent) {
            }
        });
    }

    private void recibirDatos() throws NoSuchAlgorithmException {
        Bundle datos = getIntent().getExtras();
        if (datos != null) {
            dpiUsusario = (String) datos.getString("dpiusuario");
        }
    }

    private void buscarCuentas(String dpiCliente) throws NoSuchAlgorithmException {
        String consultaSQL = ServidorSQL.SERVIDORSQL_CONRETORNO+"SELECT * FROM CUENTA WHERE dpi_cliente ='"+dpiCliente+"'";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(consultaSQL, new Response.Listener<JSONArray>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObjectDatosUsuario = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObjectDatosUsuario = response.getJSONObject(i);
                        String numCuenta = jsonObjectDatosUsuario.getString("no_cuenta_bancaria");
                        String tipoCuenta = jsonObjectDatosUsuario.getString("tipo_cuenta");
                        String opcionFinal = numCuenta+" - "+tipoCuenta;
                        arrayListCuentas.add(opcionFinal);
                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), "Hay problemas de conexión al servidor.", Toast.LENGTH_SHORT).show();
                    }
                }

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.color_spinner_layout, arrayListCuentas);
                arrayAdapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
                //arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(arrayAdapter);

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

    private void buscarMovimientos(String noCuenta, String fInicial, String fFinal) throws NoSuchAlgorithmException {
        listDatos.clear();
        String consultaSQL = ServidorSQL.SERVIDORSQL_CONRETORNO+"SELECT * FROM MOVIMIENTO_MONETARIO WHERE no_cuenta ='"+noCuenta+"' AND fecha BETWEEN '"+fInicial+" 00:00:00' AND '"+fFinal+" 23:59:59' order by fecha desc;";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(consultaSQL, new Response.Listener<JSONArray>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObjectDatosMovimiento = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObjectDatosMovimiento = response.getJSONObject(i);
                        String monto = jsonObjectDatosMovimiento.getString("monto");
                        String tipoMovimiento = jsonObjectDatosMovimiento.getString("tipo");
                        String descripcion = jsonObjectDatosMovimiento.getString("descripcion");
                        String fechaMovimiento = jsonObjectDatosMovimiento.getString("fecha");
                        String nuevoRegistro = tipoMovimiento+" | "+descripcion+" - Q"+monto+"\nFecha: "+fechaMovimiento;
                        listDatos.add(nuevoRegistro);
                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), "Hay problemas de conexión al servidor.", Toast.LENGTH_SHORT).show();
                    }
                }

                AdapterDatos adapterDatos = new AdapterDatos(listDatos);
                recycler.setAdapter(adapterDatos);

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

    public void ejecutarValidacionesDeBusqueda() throws ParseException, NoSuchAlgorithmException {
        if (fechaFin.getText().toString().contains("-") && fechaIni.getText().toString().contains("-")){
            Date fechaInicial = new SimpleDateFormat("yyyy-MM-dd").parse(fechaIni.getText().toString());
            Date fechaFinal = new SimpleDateFormat("yyyy-MM-dd").parse(fechaFin.getText().toString());
            if (fechaInicial.before(fechaFinal) || fechaInicial.equals(fechaFinal)){
                String noCuentaSeleccionada = spinner.getSelectedItem().toString().substring(0,10);
                buscarMovimientos(noCuentaSeleccionada, fechaIni.getText().toString(), fechaFin.getText().toString());
            } else {
                Toast.makeText(getApplicationContext(), "¡Hubo una confusion! La fecha inicial debe ser anterior a la final.", Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(), "¡Hubo una confusion! La fecha inicial debe ser anterior a la final.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Por favor, ingresa una fecha de inicio y fin para realizar la búsqueda.", Toast.LENGTH_SHORT).show();
            Toast.makeText(getApplicationContext(), "Por favor, ingresa una fecha de inicio y fin para realizar la búsqueda.", Toast.LENGTH_SHORT).show();
        }
    }

    private void buscarSaldoCuentaSeleccionada(String noCuenta) throws NoSuchAlgorithmException {
        String consultaSQL = ServidorSQL.SERVIDORSQL_CONRETORNO+"SELECT saldo FROM CUENTA WHERE no_cuenta_bancaria ='"+noCuenta+"'";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(consultaSQL, new Response.Listener<JSONArray>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObjectDatosUsuario = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObjectDatosUsuario = response.getJSONObject(i);
                        String saldo = jsonObjectDatosUsuario.getString("saldo");
                        textoFinalSaldo = "  SALDO ACTUAL: Q "+saldo;
                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), "Hay problemas de conexión al servidor.", Toast.LENGTH_SHORT).show();
                    }
                }
            textViewSaldo.setText(textoFinalSaldo);
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

    @Override
    public void onClick(View view) {
        if (view==bDesde){
            final Calendar fecha1 = Calendar.getInstance();
            dia1 = fecha1.get(Calendar.DAY_OF_MONTH);
            mes1 = fecha1.get(Calendar.MONTH);
            anio1 = fecha1.get(Calendar.YEAR);

            DatePickerDialog datePickerDialog1 = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                    fechaIni.setText(i+"-"+(i1+1)+"-"+i2);
                }
            }, anio1,mes1,dia1);
            datePickerDialog1.getDatePicker().setMaxDate(System.currentTimeMillis());
            datePickerDialog1.show();
        }
        if (view==bHasta){
            final Calendar fecha2 = Calendar.getInstance();
            dia2 = fecha2.get(Calendar.DAY_OF_MONTH);
            mes2 = fecha2.get(Calendar.MONTH);
            anio2 = fecha2.get(Calendar.YEAR);

            DatePickerDialog datePickerDialog2 = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                    fechaFin.setText(i+"-"+(i1+1)+"-"+i2);
                }
            }, anio2,mes2,dia2);
            datePickerDialog2.getDatePicker().setMaxDate(System.currentTimeMillis());
            datePickerDialog2.show();
        }
        if (view==bBuscar){
            try {
                ejecutarValidacionesDeBusqueda();
            } catch (ParseException | NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
    }
}