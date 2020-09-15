package dev.com.j3b.manejadorLogIn;

import android.os.Build;
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
import dev.com.j3b.enums.EstadoDeCuenta;
import dev.com.j3b.modelos.Cuenta;
import dev.com.j3b.modelos.CuentaHabiente;
import dev.com.j3b.modelos.ServidorSQL;

public class ManejadorCuentaPropia extends AppCompatActivity {



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
                                Cuenta cuenta = new Cuenta();
                                JSONObject jsonObjectDatosCuenta=null;
                                System.out.println("ConsultaTamano:"+response.length());
                                for (int i = 0; i < response.length(); i++) {
                                        try {
                                                jsonObjectDatosCuenta = response.getJSONObject(i);
                                                cuenta.setNoCuentaBancaria(jsonObjectDatosCuenta.getString("no_cuenta_bancaria"));
                                                cuenta.setSaldo(jsonObjectDatosCuenta.getDouble("saldo"));
                                                cuentasDeUsuario.add(cuenta);
                                                System.out.println(cuenta);
                                        }catch (JSONException e){
                                                Toast.makeText(null, e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                }
                        }
                }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                                //En caso de que la consulta SQL no encuentre ningún dato, el codigo ingresa en esta sección.
                                System.out.println("ERROR AL CONSULTAR CUENTAS");
                                Toast.makeText(null, "Sin cuentas", Toast.LENGTH_SHORT).show();
                        }
                });
                RequestQueue requestQueue = Volley.newRequestQueue(ManejadorCuentaPropia.this);
                requestQueue.add(jsonArrayRequest);
                return cuentasDeUsuario;

        }

}
