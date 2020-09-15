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
import android.widget.EditText;
import android.widget.Spinner;
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

import java.math.BigInteger;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
    private EditText cantidadDeTransferencia;
    //Spinners contenedores de las cuentas
    private Spinner spinnerCuentaOrigen;
    private Spinner spinnerCuentaDestino;
    //Manejadores y elementos de configuracion
    private ManejadorCuentaPropia manejadorCuentaPropia;
    private ArrayList<Cuenta> listaDeCuentas;
    private int posicionDeCuentaDestino;
    private int posicionDeCuentaOrigen;
    private boolean seRealizoRollback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaccion_cuentas_propias);
        //Asignando el spinner
        spinnerCuentaOrigen =(Spinner) findViewById(R.id.spinnerCuentaOrigen);
        spinnerCuentaDestino=(Spinner) findViewById(R.id.spinnerCuentaDestino);
        retroceder=(Button) findViewById(R.id.btnRetroceder);
        btnRealizarTransferencia=(Button) findViewById(R.id.btnRealizarTransferencia);
        cantidadDeTransferencia = (EditText) findViewById(R.id.cantidadDeTransferencia);
        //Configuracion de listas y manejadores
        listaDeCuentas = new ArrayList<>();
        manejadorCuentaPropia = new ManejadorCuentaPropia();
        seRealizoRollback=false;
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
                generarTransaccion();
            }
        });

    }

    public void volverAPaginaPrincipal(){
        Intent intent = new Intent(this,VentanaPrincipal.class);
        startActivity(intent);
    }

    /**
     * Permite inicar una transaccion con el comando START TRANSACTION;
     */
    public void iniciarTransaccion(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServidorSQL.SERVIDORSQL_SINRETORNO, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){@Override
        protected Map<String, String> getParams() throws AuthFailureError {
            final Timestamp fecha = new Timestamp(System.currentTimeMillis());
            String insertSQL = "START TRANSACTION";
            Map<String, String> parametros = new HashMap<String, String>();
            parametros.put("consultaSQL", insertSQL);
            return parametros;
        }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    /**
     * Permite validar las operaciones realizadas en la transaccion con un COMMIT;
     */
    public void ejecutarCommit(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServidorSQL.SERVIDORSQL_SINRETORNO, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){@Override
        protected Map<String, String> getParams() throws AuthFailureError {
            final Timestamp fecha = new Timestamp(System.currentTimeMillis());
            String insertSQL = "COMMIT";
            Map<String, String> parametros = new HashMap<String, String>();
            parametros.put("consultaSQL", insertSQL);
            return parametros;
        }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    /**
     * Permite ejecutar un ROLLBACK en caso de que la transaccion falle
     */
    public void ejecutarRollback(){
        seRealizoRollback =true;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServidorSQL.SERVIDORSQL_SINRETORNO, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){@Override
        protected Map<String, String> getParams() throws AuthFailureError {
            String insertSQL = "ROLLBACK";
            System.out.println("SE EJECUTO UN ROLLBACK");
            Map<String, String> parametros = new HashMap<String, String>();
            parametros.put("consultaSQL", insertSQL);
            return parametros;
        }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    /**
     * Permite insertar una Transaccion_Cuenta luego de haber realizado los movimientos monetarios
     * @param cuentaEmisora
     * @param monto
     * @param cuentaReceptora
     * @param claveMovimientoOrigen
     * @param claveMovimientoDestino
     */
    public void insertarTransaccionCuenta(final String cuentaEmisora,final double monto,final String cuentaReceptora,final String claveMovimientoOrigen,final String claveMovimientoDestino){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServidorSQL.SERVIDORSQL_SINRETORNO, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                /*if(response.isEmpty()){//No se ejecuto la insercion en la base de datos
                    ejecutarRollback();
                }*/
                System.out.println("RESPONSE TRANSACCION_CUENTA:"+response);
                if(!response.equalsIgnoreCase("1")){
                    ejecutarRollback();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {@Override
            protected Map<String, String> getParams() throws AuthFailureError {
            final Timestamp fecha = new Timestamp(System.currentTimeMillis());
            String insertSQL = "INSERT INTO TRANSACCION_CUENTA(no_cuenta_emisora,monto,no_cuenta_receptora,fecha,movimiento_origen,movimiento_destino)" +
                    " VALUES ('"+cuentaEmisora+"',"+monto+",'"+cuentaReceptora+"','"+fecha+"','"+claveMovimientoOrigen+"','"+claveMovimientoDestino+"')";
            Map<String, String> parametros = new HashMap<String, String>();
            parametros.put("consultaSQL", insertSQL);
            return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    /***
     * Permite la insercion de un movimiento monetario
     * @param numeroDeCuenta
     * @param monto
     * @param tipo
     */
    public boolean insertarMovimientoMonetario(final String idMovMonetario,final String numeroDeCuenta,final double monto,final TipoDeMovimientoMonetario tipo){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServidorSQL.SERVIDORSQL_SINRETORNO, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                /*if(response.isEmpty()){//No se ejecuto la insercion en la base de datos
                    ejecutarRollback();
                }*/
                System.out.println("RESPONSE Movimiento_Monetario:"+response);
                if(!response.equalsIgnoreCase("1")){
                    ejecutarRollback();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){@Override
        protected Map<String, String> getParams() throws AuthFailureError {
            final Timestamp fecha = new Timestamp(System.currentTimeMillis());
            String insertSQL = "INSERT INTO MOVIMIENTO_MONETARIO(id_mov_monetario,no_cuenta,monto,fecha,tipo)" +
                    " VALUES('"+idMovMonetario+"','"+numeroDeCuenta+"',"+monto+",'"+fecha+"','"+tipo+"')";
            Map<String, String> parametros = new HashMap<String, String>();
            parametros.put("consultaSQL", insertSQL);
            return parametros;
        }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
        return true;

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

    public void generarTransaccion(){
//        System.out.println("CUENTA ORIGEN("+posicionDeCuentaOrigen+"):"+listaDeCuentas.get(posicionDeCuentaOrigen).toString());
//        System.out.println("CUENTA DESTINO("+posicionDeCuentaDestino+"):"+listaDeCuentas.get(posicionDeCuentaDestino).toString());
        Cuenta cuentaOrigen =listaDeCuentas.get(posicionDeCuentaOrigen);
        Cuenta cuentaDestino = listaDeCuentas.get(posicionDeCuentaDestino);
        String cadenaMonto = cantidadDeTransferencia.getText().toString();
        double monto;
        if(cadenaMonto.isEmpty()){
            Toast.makeText(getApplicationContext(), "El monto es obligatorio", Toast.LENGTH_LONG).show();
        }else{
            monto=Double.valueOf(cadenaMonto);
            System.out.println("MONTO:"+monto);
            //Validacion de que cuenta ORIGEN != DESTINO
            if(posicionDeCuentaOrigen==posicionDeCuentaDestino){
                Toast.makeText(getApplicationContext(), "Las cuentas deben ser distintas", Toast.LENGTH_LONG).show();
            //Validacion de que sea un monto valido
            }else if(monto>cuentaOrigen.getSaldo()){
                Toast.makeText(getApplicationContext(), "Monto rechazado.\n No posees el monto descrito", Toast.LENGTH_LONG).show();
            }else{
                //Preparacion de informacion
                String claveMovOrigen= generarCodigoMovimientoMonetario();
                String claveMovDestino = generarCodigoMovimientoMonetario();
                //Inicia transaccion
                iniciarTransaccion();
                insertarMovimientoMonetario(claveMovOrigen,cuentaOrigen.getNoCuentaBancaria(),monto,TipoDeMovimientoMonetario.DEBITO);
                System.out.println("->1.SE REALIZO ROLBBACK:"+seRealizoRollback);
                if(!seRealizoRollback) insertarMovimientoMonetario(claveMovDestino,cuentaDestino.getNoCuentaBancaria(),monto,TipoDeMovimientoMonetario.ABONO);
                System.out.println("->2.SE REALIZO ROLBBACK:"+seRealizoRollback);
                if(!seRealizoRollback) insertarTransaccionCuenta(cuentaOrigen.getNoCuentaBancaria(),monto,cuentaDestino.getNoCuentaBancaria(),claveMovOrigen,claveMovDestino);
                System.out.println("->3.SE REALIZO ROLBBACK:"+seRealizoRollback);
                if(!seRealizoRollback) ejecutarCommit();
                seRealizoRollback=false;
                cantidadDeTransferencia.setText("");
                Toast.makeText(getApplicationContext(), "Se realizo la transferencia", Toast.LENGTH_LONG).show();

            }

        }

        //System.out.println("CLAVE DE MOVIMIENTO:"+generarCodigoMovimientoMonetario());
        //Validacion de que cuenta ORIGEN != DESTINO  Y El monto sea correcto
        //Inicia transaccion
        //Inserta el movimiento monetario ORIGEN(Se ejecuta el trigger que disminuira el valor de cuenta ORIGEN)(Colocar Rollback)
        //Inserta el movimiento monetario DESTINO(Seejecuta el trigger que aumentara el valor de la cuenta DESTINO)(Colocar Rollback)
        //Insertar transaccion Cuenta(Colcar Rollback)
        //Se ejecuta el commit
    }


    private String generarCodigoMovimientoMonetario() {
        SecureRandom random = new SecureRandom();
        String text = new BigInteger(130, random).toString(32);
        return  text.substring(1, 9);

    }

}