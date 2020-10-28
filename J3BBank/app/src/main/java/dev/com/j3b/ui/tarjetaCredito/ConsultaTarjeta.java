package dev.com.j3b.ui.tarjetaCredito;

import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import java.util.Calendar;
import java.util.Date;

import dev.com.j3b.R;
import dev.com.j3b.modelos.ServidorSQL;
import dev.com.j3b.ui.consultaCuentas.AdapterDatos;

public class ConsultaTarjeta extends AppCompatActivity implements View.OnClickListener {

    private String dpiUsusario = "";
    private ArrayList<String> arrayListTarjetas = new ArrayList<>();
    private ArrayList<String> arrayListDatosTarjetas = new ArrayList<>();
    private Spinner spinner;
    private Button bDesde, bHasta,bBuscar,bBuscar2;
    private EditText fechaFin, fechaIni;
    private int dia1, dia2, mes1, mes2, anio1, anio2;

    private ListView listView;
    private ArrayList<String> arrayListMovimientos =  new ArrayList<>();
    private ArrayAdapter<String> adapter;
    private EditText txtInput;

    private ArrayList<String> listDatos = new ArrayList<>();
    private RecyclerView recycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta_tarjetas);
        recycler = findViewById(R.id.recyclerId);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        //listView = (ListView)  findViewById(R.id.listv);
        //listView.setAdapter(adapter);
        spinner = findViewById(R.id.spinner);
        bDesde = findViewById(R.id.desdeBtn);
        bHasta = findViewById(R.id.hastaBtn);
        bBuscar = findViewById(R.id.buscarBtn);
        bBuscar2 = findViewById(R.id.buscar2Btn);
        fechaFin = findViewById(R.id.fechaFinalTxt);
        fechaIni = findViewById(R.id.fechaInicialTxt);
        try {
            recibirDatos();
            buscarTarjetas(dpiUsusario);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        bHasta.setOnClickListener(this);
        bDesde.setOnClickListener(this);
        bBuscar.setOnClickListener(this);
        bBuscar2.setOnClickListener(this);
    }

    private void recibirDatos() throws NoSuchAlgorithmException {
        Bundle datos = getIntent().getExtras();
        if (datos != null) {
            dpiUsusario = (String) datos.getString("dpiusuario");
        }
    }

    private void buscarTarjetas(String dpiCliente) throws NoSuchAlgorithmException {
        String consultaSQL = ServidorSQL.SERVIDORSQL_CONRETORNO+"SELECT * FROM TARJETA WHERE dpi_cuenta_habiente ='"+dpiCliente+"'"+" AND tipo='CREDITO'";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(consultaSQL, new Response.Listener<JSONArray>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(JSONArray response) {
                //variables a utilizar
                String opcionFinal = "";
                String tipo ="";
                String numTarjeta = "";
                String limite = "";
                String estado = "";
                String deudaActual = "";
                String fechaVencimiento = "";
                String codigoCVC = "";
                String tasaInteres = "";
                double calInteres = 0;
                int lim = 0;
                JSONObject jsonObjectDatosUsuario = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObjectDatosUsuario = response.getJSONObject(i);
                        //recuperando tipo de tarjeta
                        tipo = jsonObjectDatosUsuario.getString("tipo");
                        //datos de tarjeta credito
                        numTarjeta = jsonObjectDatosUsuario.getString("no_tarjeta");
                        limite = jsonObjectDatosUsuario.getString("limite");
                        estado = jsonObjectDatosUsuario.getString("estado");
                        fechaVencimiento = jsonObjectDatosUsuario.getString("fecha_vencimiento");
                        codigoCVC = jsonObjectDatosUsuario.getString("codigoCVC");
                        deudaActual = jsonObjectDatosUsuario.getString("deuda_actual");
                        tasaInteres = jsonObjectDatosUsuario.getString("tasa_interes");
                        calInteres = Double.parseDouble(tasaInteres)*100;
                        opcionFinal =numTarjeta+"\n  Limite: Q."+limite+"\n  Estado: "+estado+"\n  Adeudo: "+deudaActual+"\n  Interes: "+calInteres+"%\n  Vence: "+fechaVencimiento+ "\n  CVC: "+codigoCVC;

                        arrayListTarjetas.add(opcionFinal); //guarda solo los datos importantes de la tarjeta
                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), "Hay problemas de conexión al servidor.", Toast.LENGTH_SHORT).show();
                    }
                }

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.color_spinner_layout, arrayListTarjetas);
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

    private void buscarPagos(String noTarjeta, String fInicial, String fFinal) throws NoSuchAlgorithmException {
        listDatos.clear();
        String consultaSQL = ServidorSQL.SERVIDORSQL_CONRETORNO+"SELECT * FROM PAGO_TARJETA WHERE no_tarjeta ='"+noTarjeta+"' AND fecha_pago BETWEEN '"+fInicial+" 00:00:00' AND '"+fFinal+" 23:59:59' order by fecha_pago desc;";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(consultaSQL, new Response.Listener<JSONArray>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObjectDatosMovimiento = null;
                String montoTotal = "";
                String fechaTransaccion = "";
                String nuevoRegistro ="";
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObjectDatosMovimiento = response.getJSONObject(i);
                        //datos de la transaccion
                        montoTotal = jsonObjectDatosMovimiento.getString("monto");
                        fechaTransaccion = jsonObjectDatosMovimiento.getString("fecha_pago");
                        nuevoRegistro ="Monto Pagado: Q."+montoTotal+"  Fecha: "+fechaTransaccion;
                        listDatos.add(nuevoRegistro);
                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), "Hay problemas de conexión al servidor.", Toast.LENGTH_SHORT).show();
                    }
                }
                //llenar list box
                AdapterDatos adapterDatos = new AdapterDatos(listDatos);
                recycler.setAdapter(adapterDatos);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //En este punto no tendría que haber errores.
                Toast.makeText(getApplicationContext(), "No se encontraron registros correspondientes a las fechas establecidas.", Toast.LENGTH_SHORT).show();
                //llenar list box
                AdapterDatos adapterDatos = new AdapterDatos(listDatos);
                recycler.setAdapter(adapterDatos);
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

    private void buscarConsumos(String noTarjeta, String fInicial, String fFinal) throws NoSuchAlgorithmException {
        listDatos.clear();
        String consultaSQL = ServidorSQL.SERVIDORSQL_CONRETORNO+"SELECT * FROM TRANSACCION_TARJETA WHERE no_tarjeta ='"+noTarjeta+"' AND fecha_transaccion BETWEEN '"+fInicial+" 00:00:00' AND '"+fFinal+" 23:59:59' order by fecha_transaccion desc;";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(consultaSQL, new Response.Listener<JSONArray>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObjectDatosMovimiento = null;
                String montoTienda = "";
                String montoInteres = "";
                String montoTotal = "";
                String fechaTransaccion = "";
                String nuevoRegistro ="";
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObjectDatosMovimiento = response.getJSONObject(i);
                        //datos de la transaccion
                        montoTienda = jsonObjectDatosMovimiento.getString("monto_tienda");
                        montoInteres = jsonObjectDatosMovimiento.getString("monto_interes");
                        montoTotal = jsonObjectDatosMovimiento.getString("monto_total");
                        fechaTransaccion = jsonObjectDatosMovimiento.getString("fecha_transaccion");
                        nuevoRegistro ="Monto Gastado: Q."+ montoTienda+"  Interes: Q. "+montoInteres+"\nMonto Cobrado: Q."+montoTotal+"  Fecha: "+fechaTransaccion;

                        listDatos.add(nuevoRegistro);
                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), "Hay problemas de conexión al servidor.", Toast.LENGTH_SHORT).show();
                    }
                }
                //llenar list box
                AdapterDatos adapterDatos = new AdapterDatos(listDatos);
                recycler.setAdapter(adapterDatos);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //En este punto no tendría que haber errores.
                Toast.makeText(getApplicationContext(), "No se encontraron registros correspondientes a las fechas establecidas.", Toast.LENGTH_SHORT).show();
                //llenar list box
                AdapterDatos adapterDatos = new AdapterDatos(listDatos);
                recycler.setAdapter(adapterDatos);
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

    public void ejecutarValidacionesDeBusqueda(String consulta) throws ParseException, NoSuchAlgorithmException {
        if (fechaFin.getText().toString().contains("-") && fechaIni.getText().toString().contains("-")){
            Date fechaInicial = new SimpleDateFormat("yyyy-MM-dd").parse(fechaIni.getText().toString());
            Date fechaFinal = new SimpleDateFormat("yyyy-MM-dd").parse(fechaFin.getText().toString());
            if (fechaInicial.before(fechaFinal) || fechaInicial.equals(fechaFinal)){
                if (spinner.getSelectedItem() == null){
                    Toast.makeText(getApplicationContext(), "Actualmente no tienes tarjetas activas asociadas.", Toast.LENGTH_SHORT).show();
                    Toast.makeText(getApplicationContext(), "Actualmente no tienes tarjetas activas asociadas.", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    String noTarjetaSeleccionada = spinner.getSelectedItem().toString().substring(0,16);
                    if (consulta.equalsIgnoreCase("consumos")) {
                        buscarConsumos(noTarjetaSeleccionada, fechaIni.getText().toString(), fechaFin.getText().toString());
                    } else {
                        buscarPagos(noTarjetaSeleccionada, fechaIni.getText().toString(), fechaFin.getText().toString());
                    }
                }
            } else {
                Toast.makeText(getApplicationContext(), "¡Hubo una confusion! La fecha inicial debe ser anterior a la final.", Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(), "¡Hubo una confusion! La fecha inicial debe ser anterior a la final.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Por favor, ingresa una fecha de inicio y fin para realizar la búsqueda.", Toast.LENGTH_SHORT).show();
            Toast.makeText(getApplicationContext(), "Por favor, ingresa una fecha de inicio y fin para realizar la búsqueda.", Toast.LENGTH_SHORT).show();
        }
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
                ejecutarValidacionesDeBusqueda("consumos");
            } catch (ParseException | NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
        if (view==bBuscar2){
            try {
                ejecutarValidacionesDeBusqueda("pagos");
            } catch (ParseException | NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
    }
}