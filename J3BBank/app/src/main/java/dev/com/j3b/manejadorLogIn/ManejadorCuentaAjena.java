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
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import dev.com.j3b.enums.EstadoDeCuenta;
import dev.com.j3b.modelos.Cuenta;
import dev.com.j3b.modelos.ServidorSQL;

public class ManejadorCuentaAjena extends AppCompatActivity {

    public ArrayList<Cuenta> consultarCuentasDeUsuarioAjenas(String dpiCliente, EstadoDeCuenta estadoDeCuenta){
        final ArrayList<Cuenta> cuentasDeUsuario = new ArrayList<>();
        String consultaSQL = ServidorSQL.SERVIDORSQL_CONRETORNO+"" +
                "SELECT ch.dpi_cliente,cc.numero_cuenta,cc.fecha_registro" +
                "FROM CUENTA_HABIENTE AS ch JOIN CUENTA_DE_CONFIANZA AS cc ON ch.dpi_cliente=cc.dpi_propietario"+
                " JOIN CUENTA AS c ON c.no_cuenta_bancaria=cc.numero_cuenta"+
                " WHERE ch.dpi_cliente='"+dpiCliente+"'" +
                " AND c.estado'"+estadoDeCuenta.toString().toLowerCase()+"'";
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
        RequestQueue requestQueue = Volley.newRequestQueue(ManejadorCuentaAjena.this);
        requestQueue.add(jsonArrayRequest);
        return cuentasDeUsuario;

    }
    /**
     * Funcion para validar que un monto a transferir no sea 0
     * */
    public boolean validarMonto(double monto){
        if (monto > 0){
            return  true;
        }
        return false;

    }


    /**
     * Función para generar un codigo numerico de 6 digitos
     * */
    public String generarCodigo() {
        String numeroDev = "";
        // Conjunto de números ya usados
        Set<Integer> alreadyUsedNumbers = new HashSet<>();
        Random random = new Random();
        // Vamos a generar 6 números aleatorios sin repetición
        while (alreadyUsedNumbers.size()<3) {
            // Número aleatorio entre 0 y 40, excluido el 40.
            int randomNumber = (int)Math.floor(Math.random()*(90-10+1)+10);
            // Si no lo hemos usado ya, lo usamos y lo metemos en el conjunto de usados.
            if (!alreadyUsedNumbers.contains(randomNumber)){
                alreadyUsedNumbers.add(randomNumber);
                numeroDev +=""+randomNumber;
            }
        }
        return numeroDev;

    }


}
