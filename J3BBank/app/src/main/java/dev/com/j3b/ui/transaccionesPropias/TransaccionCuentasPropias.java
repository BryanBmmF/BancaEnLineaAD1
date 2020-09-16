package dev.com.j3b.ui.transaccionesPropias;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
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
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import dev.com.j3b.MainActivity;
import dev.com.j3b.R;
import dev.com.j3b.enums.EstadoDeCuenta;
import dev.com.j3b.modelos.Cuenta;
import dev.com.j3b.modelos.ServidorSQL;
import dev.com.j3b.ui.aplicacion.VentanaPrincipal;

public class TransaccionCuentasPropias extends AppCompatActivity {

    //Isercion de cuenta
    /**
     * INSERT INTO CUENTA VALUES ('1234567890','0000000000002','MONETARIA','0','ANUAL','activa','800')
    **/
    private Button btnRealizarTransferencia;
    private EditText cantidadDeTransferencia;
    private EditText editTextDescripcion;
    //Spinners contenedores de las cuentas
    private Spinner spinnerCuentaOrigen;
    private Spinner spinnerCuentaDestino;
    //Manejadores y elementos de configuracion
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
        btnRealizarTransferencia=(Button) findViewById(R.id.btnRealizarTransferencia);
        cantidadDeTransferencia = (EditText) findViewById(R.id.cantidadDeTransferencia);
        editTextDescripcion = (EditText) findViewById(R.id.editTextDescripcion);
        //Configuracion de listas y manejadores
        listaDeCuentas = new ArrayList<>();
        seRealizoRollback=false;
        //Consultando las cuentas del cliente y llenando espinner
        consultarCuentasDeUsuario(MainActivity.usuarioLogueado.getDpiCliente(),EstadoDeCuenta.ACTIVA);


        /*//Evento del boton retroceder
        retroceder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast toast = Toast.makeText(getApplicationContext(), "RETROCEDIENDO", Toast.LENGTH_SHORT);
                toast.show();
                volverAPaginaPrincipal();
            }
        });*/

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

   public void crearMovimientosYTransacciones(final String cuentaOrigen,final String cuentaDestino,final double monto,String descripcion){
        String consultaSQL= ServidorSQL.SERVIDORSQL_CONRETORNO+
                "SELECT transferir_cuenta_propia('"+cuentaOrigen+"','"+cuentaDestino+"',"+monto+",'"+descripcion+"')";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(consultaSQL,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

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
                        listaDeCuentas= new ArrayList<>();
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                Cuenta cuenta = new Cuenta();
                                jsonObjectDatosCuenta = response.getJSONObject(i);
                                cuenta.setNoCuentaBancaria(jsonObjectDatosCuenta.getString("no_cuenta_bancaria"));
                                cuenta.setSaldo(jsonObjectDatosCuenta.getDouble("saldo"));
                                listaDeCuentas.add(new Cuenta(jsonObjectDatosCuenta.getString("no_cuenta_bancaria"),jsonObjectDatosCuenta.getDouble("saldo")));
                                System.out.println("En lista:"+ listaDeCuentas.get(i).toString());
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
        System.out.println("---------->Tamano de Spinner:"+listaDeCuentas.size());
        String[] arrayCuentas = new String[listaDeCuentas.size()];
        for (int i = 0; i < listaDeCuentas.size(); i++) {
            arrayCuentas[i]="No.Cuenta:"+listaDeCuentas.get(i).getNoCuentaBancaria()+" Saldo:"+listaDeCuentas.get(i).getSaldo();
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
        final Cuenta cuentaOrigen =listaDeCuentas.get(posicionDeCuentaOrigen);
        final Cuenta cuentaDestino = listaDeCuentas.get(posicionDeCuentaDestino);
        final String cadenaMonto = cantidadDeTransferencia.getText().toString();
        final double monto;
        if(cadenaMonto.isEmpty()){
            Toast.makeText(getApplicationContext(), "El monto es obligatorio", Toast.LENGTH_LONG).show();
        }else{
            monto=Double.valueOf(cadenaMonto);
            System.out.println("MONTO:"+monto);
            //Validacion de que cuenta ORIGEN != DESTINO
            if(posicionDeCuentaOrigen==posicionDeCuentaDestino){
                Toast.makeText(getApplicationContext(), "Las cuentas deben ser distintas", Toast.LENGTH_LONG).show();
            //Validacion de que sea un monto valido
            }else if(monto==0){
                Toast.makeText(getApplicationContext(), "El monto debe ser Mayor a 0", Toast.LENGTH_LONG).show();
            }else if(monto>cuentaOrigen.getSaldo()){
                Toast.makeText(getApplicationContext(), "Monto rechazado.\n No posees el monto descrito", Toast.LENGTH_LONG).show();
            }else{

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Desea realizar la transaccion?");
                builder.setTitle("Confirmacion");
                builder.setCancelable(false);
                builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Preparacion de informacion
                        double montoNuevoCuentaOrigen = cuentaOrigen.getSaldo()-monto;
                        double montoNUevoCuentaDestino =cuentaDestino.getSaldo()+monto;
                        System.out.println("Monto:"+monto+" DineroOrigen:"+cuentaOrigen.getSaldo()+" MontoFinal:"+montoNuevoCuentaOrigen);
                        System.out.println("Monto:"+monto+" DineroDestino:"+cuentaDestino.getSaldo()+" MontoFinal:"+montoNUevoCuentaDestino);
                        //Limpiar Spinner y Text
                        crearMovimientosYTransacciones(cuentaOrigen.getNoCuentaBancaria(),cuentaDestino.getNoCuentaBancaria(),monto,editTextDescripcion.getText().toString());
                        editTextDescripcion.setText("");
                        cantidadDeTransferencia.setText("");
                        //Llenarlo de nuevo
                        try {
                            Thread.sleep(1500L);
                            consultarCuentasDeUsuario(MainActivity.usuarioLogueado.getDpiCliente(),EstadoDeCuenta.ACTIVA);
                            //consultarCuentasDeUsuario(MainActivity.usuarioLogueado.getDpiCliente(),EstadoDeCuenta.ACTIVA);
                        }catch(InterruptedException e){
                        }
                        Toast.makeText(getApplicationContext(), "Se realizo la transferencia", Toast.LENGTH_LONG).show();
                        //consultarCuentasDeUsuario(MainActivity.usuarioLogueado.getDpiCliente(),EstadoDeCuenta.ACTIVA);
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

            }
        }

    }




}