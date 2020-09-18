package dev.com.j3b.ui.transaccionesAjenas;

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

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

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
import dev.com.j3b.manejadorLogIn.ManejadorCuentaAjena;
import dev.com.j3b.modelos.Cuenta;
import dev.com.j3b.modelos.ServidorSQL;
import dev.com.j3b.ui.aplicacion.VentanaPrincipal;

public class TransaccionCuentasAjenas extends AppCompatActivity {

    private Button btnRetroceder;
    private Button btnRegistrarCuentaAjena;
    private Button btnRealizarTransferencia;
    private EditText txtMonto;
    private EditText txtMotivo;
    
    //Spinners contenedores de las cuentas
    private Spinner spinnerCuentaOrigen;
    private Spinner spinnerCuentaDestino;
    //Manejadores y elementos de configuracion
    private ManejadorCuentaAjena manejadorCuentaAjena;
    private ArrayList<Cuenta> listaDeCuentas;
    private ArrayList<Cuenta> listaDeCuentasAjenas;
    private int posicionDeCuentaDestino;
    private int posicionDeCuentaOrigen;

    public String cuentaOrigenSelec;
    public String cuentaDestinoSelec;
    private RequestQueue requestQueue ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaccion_cuentas_ajenas);
        cuentaOrigenSelec = "";
        cuentaDestinoSelec= "";
        //voley global
        requestQueue = Volley.newRequestQueue(this);
        //campos recibidos
        txtMonto = (EditText) findViewById(R.id.txtMonto);
        txtMotivo = (EditText) findViewById(R.id.txtMotivo);
        //Asignando el spinner
        spinnerCuentaOrigen =(Spinner) findViewById(R.id.spinnerCuentaOrigen);
        spinnerCuentaDestino=(Spinner) findViewById(R.id.spinnerCuentaDestino);
        btnRetroceder=(Button) findViewById(R.id.btnRetroceder);
        btnRealizarTransferencia=(Button) findViewById(R.id.btnRealizarTransferencia);
        btnRegistrarCuentaAjena=(Button) findViewById(R.id.btnRegistrarCuentaAjena);
        //Configuracion de listas y manejadores
        listaDeCuentas = new ArrayList<>();
        listaDeCuentasAjenas = new ArrayList<>();
        manejadorCuentaAjena = new ManejadorCuentaAjena();
        //Consultando las cuentas del cliente y llenando espinner
        consultarCuentasDeUsuario(MainActivity.usuarioLogueado.getDpiCliente(),EstadoDeCuenta.ACTIVA);
        consultarCuentasDeUsuarioAjenas(MainActivity.usuarioLogueado.getDpiCliente(),EstadoDeCuenta.ACTIVA);

        //Evento del boton retroceder
        btnRetroceder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast toast = Toast.makeText(getApplicationContext(), "RETROCEDIENDO", Toast.LENGTH_SHORT);
                //toast.show();
                volverAPaginaPrincipal();
            }
        });

        //Evento de Spinner para seleccionar cuenta origen
        spinnerCuentaOrigen.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> lista, View view, int posicion, long l) {
                if(lista!=null){
                    String cuenta = (String) lista.getSelectedItem();
                    cuentaOrigenSelec = cuenta;
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
                    cuentaDestinoSelec = cuenta;
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
                registrarTransferenciaTerceros();

            }
        });

        //Evento para registrar una nueva cuenta de confianza
        btnRegistrarCuentaAjena.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirigirRegistroCuenta();

            }
        });

    }

    public void volverAPaginaPrincipal(){
        Intent intent = new Intent(this,VentanaPrincipal.class);
        startActivity(intent);
    }

    public void redirigirEvaluadorCodigo(){
        Intent intent = new Intent(this,EvaluadorCodigoTransferencia.class);

        Bundle nuevoBundlePrincipal = new Bundle();

        nuevoBundlePrincipal.putString("CuentaOrigen",listaDeCuentas.get(posicionDeCuentaOrigen).getNoCuentaBancaria());
        nuevoBundlePrincipal.putString("CuentaDestino",listaDeCuentasAjenas.get(posicionDeCuentaDestino).getNoCuentaBancaria());
        nuevoBundlePrincipal.putString("Monto",txtMonto.getText().toString());
        nuevoBundlePrincipal.putString("Motivo",txtMotivo.getText().toString());
        intent.putExtras(nuevoBundlePrincipal);

        startActivity(intent);
        //ULEMWQ
    }
    public void redirigirRegistroCuenta(){
        Intent intent = new Intent(this,RegistroCuentaConfianza.class);
        startActivity(intent);
        //ULEMWQ
    }

    /**
     * Metodo para registrar una trenasferencia a cuentas de terceros
     * */
    public void registrarTransferenciaTerceros(){
        if (txtMonto.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(), "Se debe especificar un monto a transferir", Toast.LENGTH_SHORT).show();
        } else {
            try {
                double monto = Double.parseDouble(txtMonto.getText().toString());
                String motivo = txtMotivo.getText().toString();
                //validar motivo tambien
                if (manejadorCuentaAjena.validarMonto(monto)){
                    //si todo va bien
                    if (motivo.length()<50){
                        Toast toast = Toast.makeText(getApplicationContext(), "Evaluar Codigo de transferencia", Toast.LENGTH_SHORT);
                        toast.show();
                        redirigirEvaluadorCodigo();
                    } else {
                        Toast.makeText(getApplicationContext(), "El motivo de tu transferencia es mauy grande, porfavor reducelo", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "El monto a transferir no puede ser 0", Toast.LENGTH_SHORT).show();
                }
            } catch (NumberFormatException ex){
                Toast.makeText(getApplicationContext(), "El monto a transferir no es valido", Toast.LENGTH_SHORT).show();
            }
        }

    }

    /**
     * Se consultan las cuentas personales Activas del usuario
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
                        escribirCuentasUsuarioEnSpinner();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //En caso de que la consulta SQL no encuentre ningún dato, el codigo ingresa en esta sección.
                System.out.println("ERROR AL CONSULTAR CUENTAS");
                Toast.makeText(null, "Sin cuentas", Toast.LENGTH_SHORT).show();
            }
        });
        //RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

    /**
     * Se consultan las cuentas Activas del usuario las cuales registrò como de confianza
     * @param dpiCliente
     * @param estadoDeCuenta
     */
    public void consultarCuentasDeUsuarioAjenas(String dpiCliente, EstadoDeCuenta estadoDeCuenta){
        String consultaSQL = ServidorSQL.SERVIDORSQL_CONRETORNO+"" +
                "SELECT c.no_cuenta_bancaria, cc.fecha_registro, c.saldo, ch.dpi_cliente" +
                " FROM CUENTA_HABIENTE AS ch JOIN CUENTA_DE_CONFIANZA AS cc ON ch.dpi_cliente=cc.dpi_propietario"+
                " JOIN CUENTA AS c ON c.no_cuenta_bancaria=cc.numero_cuenta"+
                " WHERE ch.dpi_cliente='"+dpiCliente+"'" +
                " AND c.estado='"+estadoDeCuenta.toString().toLowerCase()+"'";
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
                                listaDeCuentasAjenas.add(new Cuenta(jsonObjectDatosCuenta.getString("no_cuenta_bancaria"),jsonObjectDatosCuenta.getDouble("saldo")));
                                System.out.println("Tamano de lista:"+ listaDeCuentasAjenas.size());
                            }catch (JSONException e){
                                Toast.makeText(null, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                        System.out.println("Saliendo del for:"+ listaDeCuentasAjenas.size());
                        escribirCuentasUsuarioAjenasEnSpinner();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //En caso de que la consulta SQL no encuentre ningún dato, el codigo ingresa en esta sección.
                System.out.println("ERROR AL CONSULTAR CUENTAS");
                Toast.makeText(null, "Sin cuentas", Toast.LENGTH_SHORT).show();
            }
        });
        //RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

    public void escribirCuentasUsuarioEnSpinner(){
        String[] arrayCuentas = new String[listaDeCuentas.size()];
        for (int i = 0; i < listaDeCuentas.size(); i++) {
            arrayCuentas[i]="No.Cuenta:"+listaDeCuentas.get(i).getNoCuentaBancaria();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arrayCuentas);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Llenando el spinner de Cuentas ORIGEN
        spinnerCuentaOrigen.setAdapter(adapter);

    }
    public void escribirCuentasUsuarioAjenasEnSpinner(){
        String[] arrayCuentas = new String[listaDeCuentasAjenas.size()];
        for (int i = 0; i < listaDeCuentasAjenas.size(); i++) {
            arrayCuentas[i]="No.Cuenta:"+listaDeCuentasAjenas.get(i).getNoCuentaBancaria();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arrayCuentas);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Llenando el spinner de Cuentas DESTINO
        spinnerCuentaDestino.setAdapter(adapter);

    }

}
