package dev.com.j3b.ui.transaccionesAjenas;

import android.content.Intent;

import android.os.Build;
import android.os.Bundle;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;

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

import dev.com.j3b.MainActivity;
import dev.com.j3b.R;
import dev.com.j3b.modelos.ServidorSQL;
import dev.com.j3b.ui.aplicacion.VentanaPrincipal;


public class RegistroCuentaConfianza extends AppCompatActivity {
    private Button btnRetroceder;
    private Button btnRegistrarCuentaAjena;
    private EditText txtCuenta;
    private RequestQueue requestQueue ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_cuenta_confianza);
        //voley global
        requestQueue = Volley.newRequestQueue(this);
        //campos recibidos
        txtCuenta = (EditText) findViewById(R.id.txtCuenta);
        //Asignando el spinner
        btnRetroceder=(Button) findViewById(R.id.btnRetroceder);
        btnRegistrarCuentaAjena=(Button) findViewById(R.id.btnRegistrarCuentaAjena);

        //Evento del boton retroceder
        btnRetroceder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast toast = Toast.makeText(getApplicationContext(), "RETROCEDIENDO", Toast.LENGTH_SHORT);
                //toast.show();
                volverAPaginaPrincipal();
            }
        });

        //Evento para registrar una nueva cuenta de confianza
        btnRegistrarCuentaAjena.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registrarCuentaTerceros();

            }
        });

    }

    public void volverAPaginaPrincipal(){
        Intent intent = new Intent(this, TransaccionCuentasAjenas.class);
        startActivity(intent);
        finish();
    }

    public void redirigirEvaluadorCodigo(){
        Intent intent = new Intent(this,EvaluadorCodigoRegistroCuenta.class);

        Bundle nuevoBundlePrincipal = new Bundle();
        nuevoBundlePrincipal.putString("Cuenta",txtCuenta.getText().toString());
        intent.putExtras(nuevoBundlePrincipal);
        startActivity(intent);
        finish();
    }
    /**
     * Metodo para registrar una trenasferencia a cuentas de terceros
     * */
    public void registrarCuentaTerceros(){
        if (txtCuenta.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(), "Ingrese un número de Cuenta porfavor", Toast.LENGTH_SHORT).show();
        } else {
            validarCuentaIngresada();
        }

    }

    /*validaciones para la cuenta ingresada*/
    public void validarCuentaIngresada(){
        String consultaSQL = ServidorSQL.SERVIDORSQL_CONRETORNO+
                "SELECT  * FROM CUENTA WHERE no_cuenta_bancaria='"+txtCuenta.getText().toString()
                +"' AND estado='activa'";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(consultaSQL,
                new Response.Listener<JSONArray>() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onResponse(JSONArray response) {
                        JSONObject jsonObjectDatosTrans=null;
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                jsonObjectDatosTrans = response.getJSONObject(i);
                                //hacer la otra validacion
                                validarSiEsCuentaCliente();
                            }catch (JSONException e){
                                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //en caso de no recuperar nada
                System.out.println("ERROR AL GENERAR JSON");
                Toast.makeText(getApplicationContext(), "La cuenta ingresada no existe o se encuentra inactiva, porfavor revise.", Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(jsonArrayRequest);

    }

    /*validaciones para la cuenta ingresada*/
    public void validarSiEsCuentaCliente(){
        String consultaSQL = ServidorSQL.SERVIDORSQL_CONRETORNO+
                "SELECT  * FROM CUENTA WHERE no_cuenta_bancaria='"+txtCuenta.getText().toString()
                +"' AND dpi_cliente='"+ VentanaPrincipal.cuentaHabienteLogueado.getDpiCliente()+"'";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(consultaSQL,
                new Response.Listener<JSONArray>() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onResponse(JSONArray response) {
                        JSONObject jsonObjectDatosTrans=null;
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                jsonObjectDatosTrans = response.getJSONObject(i);
                                //la cuenta pertenece al mismo cliente
                                Toast.makeText(getApplicationContext(), "La cuenta ingresada es una cuenta Personal, esta no puede ser registrada como cuenta de Terceros", Toast.LENGTH_SHORT).show();
                            }catch (JSONException e){
                                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //en caso de no recuperar nada
                validarSiEsCuentaAjenaCliente();
            }
        });

        requestQueue.add(jsonArrayRequest);

    }

    /*validaciones para la cuenta ingresada*/
    public void validarSiEsCuentaAjenaCliente(){
        String consultaSQL = ServidorSQL.SERVIDORSQL_CONRETORNO+
                "SELECT  * FROM CUENTA_DE_CONFIANZA WHERE numero_cuenta='"+txtCuenta.getText().toString()
                +"' AND dpi_propietario='"+ VentanaPrincipal.cuentaHabienteLogueado.getDpiCliente()+"'";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(consultaSQL,
                new Response.Listener<JSONArray>() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onResponse(JSONArray response) {
                        JSONObject jsonObjectDatosTrans=null;
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                jsonObjectDatosTrans = response.getJSONObject(i);
                                //la cuenta pertenece al mismo cliente
                                Toast.makeText(getApplicationContext(), "La cuenta ingresada ya existe como cuenta de Terceros", Toast.LENGTH_SHORT).show();
                            }catch (JSONException e){
                                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //en caso de no recuperar nada
                redirigirEvaluadorCodigo();
            }
        });

        requestQueue.add(jsonArrayRequest);

    }



}
