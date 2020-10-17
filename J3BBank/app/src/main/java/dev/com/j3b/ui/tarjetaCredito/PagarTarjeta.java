package dev.com.j3b.ui.tarjetaCredito;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;

import dev.com.j3b.MainActivity;
import dev.com.j3b.R;
import dev.com.j3b.enums.EstadoDeCuenta;
import dev.com.j3b.modelos.Cuenta;
import dev.com.j3b.modelos.ServidorSQL;
import dev.com.j3b.modelos.Tarjeta;

public class PagarTarjeta extends AppCompatActivity {

    //Spinners
    private Spinner spinnerTarjetasConDeuda;
    private Spinner spinnerCuentasDisponibles;
    //Boton
    private Button buttonPagarTarjeta;
    //EditText
    private EditText editTextMonto;
    //Listas
    private ArrayList<Tarjeta> listaTarjetasDeCredito;
    private ArrayList<Cuenta> listaCuentas;

    //Elementos seleccionados
    private int posicionCuentaSeleccionada;
    private int posicionTarjetaSeleccionada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagar_tarjeta);
        //Asignando el spinner
        spinnerTarjetasConDeuda = (Spinner)findViewById(R.id.spinnerTarjetasConDeuda);
        spinnerCuentasDisponibles = (Spinner)findViewById(R.id.spinnerCuentasDisponibles);
        //Asignando el resto de los elementos
        editTextMonto = (EditText)findViewById(R.id.editTextMonto);
        buttonPagarTarjeta = (Button)findViewById(R.id.buttonPagarTarjeta);

        //Llenando Spinners
        buscarTarjetasDeCredito();
        buscarCuentas();
        //Evento de spinner para seleccionar Tarjetas con deud
        spinnerTarjetasConDeuda.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> lista, View view, int position, long id) {
                if(lista!=null){
                    posicionTarjetaSeleccionada=position;
                    System.out.println("SE SELECCIONO UNA TARJETA"+position);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //Evento de spinner para seleccionar Cuentas disponibles
        spinnerCuentasDisponibles.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> lista, View view, int position, long id) {
                if(lista!=null){
                    posicionCuentaSeleccionada=position;
                    System.out.println("SE SELECCIONO UNA CUENTA"+position);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Evento del boton
        buttonPagarTarjeta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                evaluarDatos();
            }
        });


    }




    public void buscarTarjetasDeCredito(){
        String consultaSql= ServidorSQL.SERVIDORSQL_CONRETORNO+""+
        "SELECT no_tarjeta,tipo,limite,dpi_cuenta_habiente,estado,deuda_actual,fecha_vencimiento,codigoCVC,tasa_interes " +
                "FROM TARJETA WHERE dpi_cuenta_habiente='"+ MainActivity.usuarioLogueado.getDpiCliente() +"' AND estado='ACTIVA' AND deuda_actual>0 and tipo='CREDITO'";
        System.out.println("\n\nCONSULTA:"+consultaSql);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(consultaSql,
                new Response.Listener<JSONArray>() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onResponse(JSONArray response) {
                        JSONObject jsonObjectDatosTarjeta = null;
                        listaTarjetasDeCredito = new ArrayList<>();
                        Tarjeta tarjeta;
                        for (int i = 0; i < response.length(); i++) {
                            tarjeta = new Tarjeta();
                            try{
                                jsonObjectDatosTarjeta = response.getJSONObject(i);
                                tarjeta.setNoTarjeta(jsonObjectDatosTarjeta.getString("no_tarjeta"));
                                tarjeta.setDeudaActual(jsonObjectDatosTarjeta.getDouble("deuda_actual"));
                                listaTarjetasDeCredito.add(tarjeta);
                            }catch(JSONException e){
                                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                        escribirTarjetasEnSpinner();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

    public void buscarCuentas(){
        String consultaSql=ServidorSQL.SERVIDORSQL_CONRETORNO+"SELECT no_cuenta_bancaria,dpi_cliente,tipo_cuenta,tasa_interes,periodo_interes,estado,saldo " +
                "FROM CUENTA WHERE estado='activa' AND saldo>0 AND tipo_cuenta='MONETARIA' AND dpi_cliente="+MainActivity.usuarioLogueado.getDpiCliente();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(consultaSql,
                new Response.Listener<JSONArray>() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onResponse(JSONArray response) {
                        JSONObject jsonObjectDatosCuenta=null;
                        listaCuentas = new ArrayList<>();
                        Cuenta cuenta;
                        for (int i = 0; i < response.length(); i++) {
                              cuenta = new Cuenta();
                            try {
                                jsonObjectDatosCuenta = response.getJSONObject(i);
                                cuenta.setNoCuentaBancaria(jsonObjectDatosCuenta.getString("no_cuenta_bancaria"));
                                cuenta.setSaldo(jsonObjectDatosCuenta.getDouble("saldo"));
                                listaCuentas.add(cuenta);
                            }catch (JSONException e){
                                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                        }
                        escribirCuentasDisponibles();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);

    }

    public boolean evaluarDatos(){
        if(editTextMonto.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(), "El monto es obligatorio", Toast.LENGTH_LONG).show();
            return false;
        }
        DecimalFormat df = new DecimalFormat("#.00");
        final String montoPagoCadena=df.format(Double.parseDouble(editTextMonto.getText().toString()));
        final Double montoPago=Double.parseDouble(montoPagoCadena);
        //MONTO_PAGO <= SALDO_CUENTA
        //MONTO_PAGO <= DEUDA
        if(montoPago<=listaCuentas.get(posicionCuentaSeleccionada).getSaldo()){
            if(montoPago<=listaTarjetasDeCredito.get(posicionTarjetaSeleccionada).getDeudaActual()){
                final String numeroTarjeta = listaTarjetasDeCredito.get(posicionTarjetaSeleccionada).getNoTarjeta();
                final Timestamp fecha = new Timestamp(System.currentTimeMillis());
                final String numeroCuenta = listaCuentas.get(posicionCuentaSeleccionada).getNoCuentaBancaria();
                final Double saldoDeuda = listaTarjetasDeCredito.get(posicionTarjetaSeleccionada).getDeudaActual()-montoPago;
                final Double saldoCuenta = listaCuentas.get(posicionCuentaSeleccionada).getSaldo()-montoPago;
                 //Preguntar si esta seguro

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Desea cancelar la cantidad de:"+montoPago+"?");
                builder.setTitle("Confirmacion");
                builder.setCancelable(false);
                builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        efectuarPago(numeroTarjeta,montoPago,fecha,numeroCuenta,saldoDeuda,saldoCuenta);
                        Toast.makeText(getApplicationContext(), "SE PAGO LA TARJETA DE CREDITO", Toast.LENGTH_LONG).show();
                        //consultarCuentasDeUsuario(MainActivity.usuarioLogueado.getDpiCliente(),EstadoDeCuenta.ACTIVA);
                        editTextMonto.setText("");
                    }
                });
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getApplicationContext(), "Transferencia cancelada", Toast.LENGTH_LONG).show();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }else{
                Toast.makeText(getApplicationContext(), "El monto sobrepasa la deuda", Toast.LENGTH_LONG).show();
            }
        }else{
            Toast.makeText(getApplicationContext(), "No posees el monto en la cuenta", Toast.LENGTH_LONG).show();
        }

    return true;
    }

    public void efectuarPago(String numeroTarjeta,Double montoPago,Timestamp fecha,String numeroCuenta,Double saldoCuenta,Double saldoDeuda){
                String consultaSQL=ServidorSQL.SERVIDORSQL_CONRETORNO+"SELECT pago_de_tarjeta('"+numeroTarjeta+"',"+montoPago+",'"+fecha+"','"+numeroCuenta+"',"+saldoCuenta+","+saldoDeuda+")";
        System.out.println("-------------------------------********CONSULTA"+consultaSQL);
                JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(consultaSQL,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                buscarTarjetasDeCredito();
                                buscarCuentas();
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

                RequestQueue requestQueue = Volley.newRequestQueue(this);
                requestQueue.add(jsonArrayRequest);

    }


    public void escribirTarjetasEnSpinner(){
        System.out.println("---------->Tamano de Spinner:"+listaTarjetasDeCredito.size());
        String[] arrayCuentas = new String[listaTarjetasDeCredito.size()];
        for (int i = 0; i < listaTarjetasDeCredito.size(); i++) {
            arrayCuentas[i]="Tarjeta:"+listaTarjetasDeCredito.get(i).getNoTarjeta()+"   Deuda:"+listaTarjetasDeCredito.get(i).getDeudaActual();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arrayCuentas);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Llenando el spinner de Tarjetas
        spinnerTarjetasConDeuda.setAdapter(adapter);
    }

    public void escribirCuentasDisponibles(){
        System.out.println("---------->Tamano de Spinner:"+listaCuentas.size());
        String[] arrayCuentas = new String[listaCuentas.size()];
        for (int i = 0; i < listaCuentas.size(); i++) {
            arrayCuentas[i]="Cuenta:"+listaCuentas.get(i).getNoCuentaBancaria()+"   Saldo:"+listaCuentas.get(i).getSaldo();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arrayCuentas);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Llenando el spinner de Cuentas
        spinnerCuentasDisponibles.setAdapter(adapter);
    }



}


