package dev.com.j3b.ui.transaccionesPropias;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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

import java.sql.Timestamp;
import java.util.ArrayList;

import dev.com.j3b.MainActivity;
import dev.com.j3b.R;
import dev.com.j3b.enums.EstadoDeCuenta;
import dev.com.j3b.enums.TipoDeMovimientoMonetario;
import dev.com.j3b.manejadorLogIn.ManejadorCuentaPropia;
import dev.com.j3b.modelos.Cuenta;
import dev.com.j3b.modelos.ServidorSQL;
import dev.com.j3b.ui.aplicacion.VentanaPrincipal;

public class TransaccionCuentasPropias extends AppCompatActivity {

    //Isercion de cuenta
    /**
     * INSERT INTO CUENTA VALUES ('1234567890','0000000000002','MONETARIA','0','ANUAL','activa','800')
    **/
    private Button retroceder;
    private Button btnRealizarTransferencia;
    //Spinners contenedores de las cuentas
    private Spinner spinnerCuentaOrigen;
    private Spinner spinnerCuentaDestino;
    //Manejadores y elementos de configuracion
    private ManejadorCuentaPropia manejadorCuentaPropia;
    private ArrayList<Cuenta> listaDeCuentas;
    private int posicionDeCuentaDestino;
    private int posicionDeCuentaOrigen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaccion_cuentas_propias);
        //Asignando el spinner
        spinnerCuentaOrigen =(Spinner) findViewById(R.id.spinnerCuentaOrigen);
        spinnerCuentaDestino=(Spinner) findViewById(R.id.spinnerCuentaDestino);
        retroceder=(Button) findViewById(R.id.btnRetroceder);
        btnRealizarTransferencia=(Button) findViewById(R.id.btnRealizarTransferencia);
        //Configuracion de listas y manejadores
        listaDeCuentas = new ArrayList<>();
        manejadorCuentaPropia = new ManejadorCuentaPropia();
        //Consultando las cuentas del cliente y llenando espinner
        consultarCuentasDeUsuario(MainActivity.usuarioLogueado.getDpiCliente(),EstadoDeCuenta.ACTIVA);


        //Evento del boton retroceder
        retroceder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast toast = Toast.makeText(getApplicationContext(), "RETROCEDIENDO", Toast.LENGTH_SHORT);
                toast.show();
                volverAPaginaPrincipal();
            }
        });

        //Evento de Spinner para seleccionar cuenta origen
        spinnerCuentaOrigen.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> lista, View view, int posicion, long l) {
                if(lista!=null){
                    String cuenta = (String) lista.getSelectedItem();
                    System.out.println("CUENTA SELECCIONADA:"+cuenta+" Posicion:"+posicion);
                    posicionDeCuentaOrigen=posicion;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //Evento de Spinner para seleccionar cuenta destino
        spinnerCuentaDestino.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> lista, View view, int posicion, long l) {
                if(lista!=null){
                    String cuenta = (String) lista.getSelectedItem();
                    System.out.println("CUENTA SELECCIONADA:"+cuenta+" Posicion:"+posicion);
                    posicionDeCuentaDestino=posicion;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //Evento para realizar transferencia
        btnRealizarTransferencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrarCuentasEscogidas();
            }
        });

    }

    public void volverAPaginaPrincipal(){
        Intent intent = new Intent(this,VentanaPrincipal.class);
        startActivity(intent);
    }

    public void insertarMovimientoMonetario(String numeroDeCuenta, double monto, TipoDeMovimientoMonetario tipo){
        Timestamp fecha = new Timestamp(System.currentTimeMillis());
        String consultaSQL = ServidorSQL.SERVIDORSQL_INSERCION_CON_RETORNO+
                " INSERT INTO MOVIMIENTO_MONETARIO(no_cuenta,monto,fecha,tipo)" +
                " VALUES('"+numeroDeCuenta+"',"+monto+",'"+fecha+"','"+tipo+"')";
        System.out.println("\n\nConsulta MOVIMIENTO_MONETARIO:"+consultaSQL);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(consultaSQL,
                new Response.Listener<JSONArray>() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onResponse(JSONArray response) {
                        //System.out.println("Response:"+response.length());
                        JSONObject jsonObjectDatosCuenta=null;
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                int idIngreso = jsonObjectDatosCuenta.getInt("id_mov_monetario");
                                System.out.println("ID DE INGRESO:"+idIngreso);
                            }catch(JSONException e){
                                System.out.println("HOLAAAAAAAAAAAAAAAAAAAA");
                                e.printStackTrace();
                            }
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error);
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

    /**
     * Se consultan las cuentas Activas del usuario
     * @param dpiCliente
     * @param estadoDeCuenta
     */
    public void consultarCuentasDeUsuario(String dpiCliente, EstadoDeCuenta estadoDeCuenta){
        final ArrayList<Cuenta> cuentasDeUsuario = new ArrayList<>();
        String consultaSQL = ServidorSQL.SERVIDORSQL_CONRETORNO+"" +
                "SELECT no_cuenta_bancaria,tipo_cuenta,saldo" +
                " FROM CUENTA WHERE dpi_cliente='"+dpiCliente+"'" +
                " AND estado='"+estadoDeCuenta.toString().toLowerCase()+"'";
        System.out.println("\n\nCONSULTA:"+consultaSQL);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(consultaSQL,
                new Response.Listener<JSONArray>() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onResponse(JSONArray response) {

                        JSONObject jsonObjectDatosCuenta=null;
                        System.out.println("ConsultaTamano:"+response.length());
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                Cuenta cuenta = new Cuenta();
                                jsonObjectDatosCuenta = response.getJSONObject(i);
                                cuenta.setNoCuentaBancaria(jsonObjectDatosCuenta.getString("no_cuenta_bancaria"));
                                cuenta.setSaldo(jsonObjectDatosCuenta.getDouble("saldo"));
                                listaDeCuentas.add(new Cuenta(jsonObjectDatosCuenta.getString("no_cuenta_bancaria"),jsonObjectDatosCuenta.getDouble("saldo")));
                                System.out.println("Tamano de lista:"+ listaDeCuentas.size());
                            }catch (JSONException e){
                                Toast.makeText(null, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                        System.out.println("Saliendo del for:"+ listaDeCuentas.size());
                        escribirCuentasEnSpinner();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //En caso de que la consulta SQL no encuentre ningún dato, el codigo ingresa en esta sección.
                System.out.println("ERROR AL CONSULTAR CUENTAS");
                Toast.makeText(null, "Sin cuentas", Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

    public void escribirCuentasEnSpinner(){
        String[] arrayCuentas = new String[listaDeCuentas.size()];
        for (int i = 0; i < listaDeCuentas.size(); i++) {
            arrayCuentas[i]="No.Cuenta:"+listaDeCuentas.get(i).getNoCuentaBancaria();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arrayCuentas);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Llenando el spinner de Cuentas ORIGEN
        spinnerCuentaOrigen.setAdapter(adapter);
        //Llenando el spinner de Cuentas DESTINO
        spinnerCuentaDestino.setAdapter(adapter);

    }

    public void mostrarCuentasEscogidas(){
        System.out.println("CUENTA ORIGEN("+posicionDeCuentaOrigen+"):"+listaDeCuentas.get(posicionDeCuentaOrigen).toString());
        System.out.println("CUENTA DESTINO("+posicionDeCuentaDestino+"):"+listaDeCuentas.get(posicionDeCuentaDestino).toString());
        insertarMovimientoMonetario(listaDeCuentas.get(posicionDeCuentaOrigen).getNoCuentaBancaria(),124.23,TipoDeMovimientoMonetario.DEBITO);
    }
}