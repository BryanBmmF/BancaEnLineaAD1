package dev.com.j3b.ui.transaccionesPropias;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
import dev.com.j3b.manejadorLogIn.ManejadorCuentaPropia;
import dev.com.j3b.modelos.Cuenta;
import dev.com.j3b.modelos.ServidorSQL;
import dev.com.j3b.ui.aplicacion.VentanaPrincipal;

public class TransaccionCuentasPropias extends AppCompatActivity {

    private Button retroceder,btnConsultaCuentas;
    private ManejadorCuentaPropia manejadorCuentaPropia;
    private ArrayList<Cuenta> listaDeCuentasOrigen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaccion_cuentas_propias);
        listaDeCuentasOrigen = new ArrayList<>();
        manejadorCuentaPropia = new ManejadorCuentaPropia();
        retroceder=(Button) findViewById(R.id.btnRetroceder);
        btnConsultaCuentas =(Button) findViewById(R.id.btnConsultaCuentas);
        retroceder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast toast = Toast.makeText(getApplicationContext(), "RETROCEDIENDO", Toast.LENGTH_SHORT);
                toast.show();
                volverAPaginaPrincipal();
            }
        });

        btnConsultaCuentas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                consultarCuentasDeUsuario(MainActivity.usuarioLogueado.getDpiCliente(), EstadoDeCuenta.ACTIVA).size();

                System.out.println("TAMANO EN CLICK:"+listaDeCuentasOrigen.size());
                /*for(Cuenta cuenta: cuentas){
                    System.out.println("---------------------Cuenta-----------------");
                    System.out.println("No:"+cuenta.getNoCuentaBancaria());
                    System.out.println("Saldo"+cuenta.getSaldo());
                }*/
            }
        });
    }

    public void volverAPaginaPrincipal(){
        Intent intent = new Intent(this,VentanaPrincipal.class);
        startActivity(intent);
    }

    public ArrayList<Cuenta> consultarCuentasDeUsuario(String dpiCliente, EstadoDeCuenta estadoDeCuenta){
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
                                listaDeCuentasOrigen.add(new Cuenta(jsonObjectDatosCuenta.getString("no_cuenta_bancaria"),jsonObjectDatosCuenta.getDouble("saldo")));
                                System.out.println("Tamano de lista:"+listaDeCuentasOrigen.size());
                            }catch (JSONException e){
                                Toast.makeText(null, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                        System.out.println("Saliendo del for:"+listaDeCuentasOrigen.size());
                        escribirCuentas();

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
        return cuentasDeUsuario;

    }

    public void escribirCuentas(){
        for(Cuenta cuenta: listaDeCuentasOrigen){
                    System.out.println("---------------------Cuenta-----------------");
                    System.out.println("No:"+cuenta.getNoCuentaBancaria());
                    System.out.println("Saldo"+cuenta.getSaldo());
                }
    }
}