package dev.com.j3b.ui.aplicacion;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
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

import java.security.NoSuchAlgorithmException;

import dev.com.j3b.MainActivity;
import dev.com.j3b.R;
import dev.com.j3b.modelos.CuentaHabiente;
import dev.com.j3b.modelos.ServidorSQL;
import dev.com.j3b.modelos.Usuario;

import dev.com.j3b.ui.cambios.ConsultaCambioMoneda;
import dev.com.j3b.ui.consultaCuentas.ConsultaCuentas;

import dev.com.j3b.ui.prestamos.SolicitudPrestamo;
import dev.com.j3b.ui.tarjetaCredito.ConsultaTarjeta;
import dev.com.j3b.ui.tarjetaCredito.PagarTarjeta;
import dev.com.j3b.ui.tarjetaCredito.SolicitudTarjetaCreditoActivity;
import dev.com.j3b.ui.transaccionesAjenas.TransaccionCuentasAjenas;
import dev.com.j3b.ui.transaccionesPropias.TransaccionCuentasPropias;


public class VentanaPrincipal extends AppCompatActivity implements View.OnClickListener {

    private Usuario usuarioRecivido = new Usuario();
    private TextView displayNombre, displayEmail;
    public static CuentaHabiente cuentaHabienteLogueado = new CuentaHabiente();
    private CardView salirCardview, monetariasCardview, ahorrosCardview, tarjetasCardview, transaccionesCardview, segurosCardview, gestionesCardview, creditosCardview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ventana_principal);

        displayEmail = (TextView) findViewById(R.id.textoEmailUsuario);
        displayNombre = (TextView) findViewById(R.id.textoNombreUsuario);
        monetariasCardview = (CardView) findViewById(R.id.cuentasMonetariasButton);
        ahorrosCardview = (CardView) findViewById(R.id.cuentasAhorroButton);
        tarjetasCardview = (CardView) findViewById(R.id.tarjetasCreditoButton);
        transaccionesCardview = (CardView) findViewById(R.id.transaccionesButton);
        segurosCardview = (CardView) findViewById(R.id.segurosButton);
        gestionesCardview = (CardView) findViewById(R.id.gestionesButton);
        creditosCardview = (CardView) findViewById(R.id.creditosButton);
        salirCardview = (CardView) findViewById(R.id.salirButton);

        monetariasCardview.setOnClickListener(this);
        ahorrosCardview.setOnClickListener(this);
        tarjetasCardview.setOnClickListener(this);
        transaccionesCardview.setOnClickListener(this);
        segurosCardview.setOnClickListener(this);
        gestionesCardview.setOnClickListener(this);
        creditosCardview.setOnClickListener(this);
        salirCardview.setOnClickListener(this);
        try { recibirDatos(); } catch (NoSuchAlgorithmException e) { e.printStackTrace(); }
    }


        private void buscarUsuario(String dpiCliente) throws NoSuchAlgorithmException {
        String consultaSQL = ServidorSQL.SERVIDORSQL_CONRETORNO+"SELECT * FROM CUENTA_HABIENTE WHERE dpi_cliente ='"+dpiCliente+"'";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(consultaSQL, new Response.Listener<JSONArray>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(JSONArray response) {
                //cuentaHabienteLogueado = new CuentaHabiente();
                JSONObject jsonObjectDatosUsuario = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObjectDatosUsuario = response.getJSONObject(i);
                        cuentaHabienteLogueado.setDpiCliente(jsonObjectDatosUsuario.getString("dpi_cliente"));
                        cuentaHabienteLogueado.setNombres(jsonObjectDatosUsuario.getString("nombres"));
                        cuentaHabienteLogueado.setApellidos(jsonObjectDatosUsuario.getString("apellidos"));
                        cuentaHabienteLogueado.setFechaNacimiento(jsonObjectDatosUsuario.getString("fecha_nacimiento"));
                        cuentaHabienteLogueado.setDireccion(jsonObjectDatosUsuario.getString("direccion"));
                        cuentaHabienteLogueado.setTelefono(jsonObjectDatosUsuario.getString("telefono"));
                        cuentaHabienteLogueado.setCelular(jsonObjectDatosUsuario.getString("celular"));
                        cuentaHabienteLogueado.setEmail(jsonObjectDatosUsuario.getString("email"));
                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), "Hay problemas de conexión al servidor.", Toast.LENGTH_SHORT).show();
                    }
                }
                //Si el usuario ingresado es valido, podemos ejecutar las instrucciones con dicha informacion a continuacion de este comentario:
                displayNombre.setText(cuentaHabienteLogueado.getNombres()+" "+cuentaHabienteLogueado.getApellidos());
                displayEmail.setText(cuentaHabienteLogueado.getEmail());
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



    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            cerrarSesionBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void cerrarSesionBack() {
        new AlertDialog.Builder(this)
                .setIcon(R.drawable.padlock)
                .setTitle("¿Deseas cerrar sesión?")
                .setCancelable(false)
                .setNegativeButton(android.R.string.cancel, null)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {//Un listener que al pulsar, cierre la aplicacion
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent redireccionarLogin = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(redireccionarLogin);
                        //android.os.Process.killProcess(android.os.Process.myPid()); //Su funcion es algo similar a lo que se llama cuando se presiona el botón "Forzar Detención" o "Administrar aplicaciones", lo cuál mata la aplicación
                        finish();
                        //Si solo quiere mandar la aplicación a segundo plano
                    }
                }).show();
    }

    @Override
    public void onClick(View view) {
        Intent newIntent;
        switch (view.getId()){
            case R.id.cuentasMonetariasButton :
                Toast toastCCM = Toast.makeText(getApplicationContext(), "Consultar Cambio de Momeda", Toast.LENGTH_SHORT);
                toastCCM.show();
                Intent intentCCM = new Intent(this, ConsultaCambioMoneda.class);
                startActivity(intentCCM);
                break;
            case R.id.cuentasAhorroButton :
                Intent consultarTarjetas = new Intent(getApplicationContext(), ConsultaTarjeta.class);
                Bundle nuevoBundleConsultaTarjetas = new Bundle();
                nuevoBundleConsultaTarjetas.putString("dpiusuario", usuarioRecivido.getDpiCliente());
                consultarTarjetas.putExtras(nuevoBundleConsultaTarjetas);
                startActivity(consultarTarjetas);
                break;
             /*Por el momento este boton funciona como transferencias a cuentas ajenas*/
            case R.id.tarjetasCreditoButton :
                Toast toastTCC = Toast.makeText(getApplicationContext(), "Transferencias a cuentas de confianza", Toast.LENGTH_SHORT);
                toastTCC.show();
                Intent intentTCC = new Intent(this, TransaccionCuentasAjenas.class);
                startActivity(intentTCC);
                break;
            case R.id.transaccionesButton :
/*                Toast toast = Toast.makeText(getApplicationContext(), "TRANSACCIONES", Toast.LENGTH_SHORT);
                toast.show();
                Intent intent = new Intent(this,TransaccionCuentasPropias.class);
                startActivity(intent);
*/
             Intent intent = new Intent(this,PagarTarjeta.class);
                startActivity(intent);
                break;
            case R.id.segurosButton :
                Intent solicitudTarjeta = new Intent(getApplicationContext(), SolicitudTarjetaCreditoActivity.class);
                startActivity(solicitudTarjeta);
                break;
            case R.id.gestionesButton :
                Intent consultarCuentas = new Intent(getApplicationContext(), ConsultaCuentas.class);
                Bundle nuevoBundleConsultaCuentas = new Bundle();
                nuevoBundleConsultaCuentas.putString("dpiusuario", usuarioRecivido.getDpiCliente());
                consultarCuentas.putExtras(nuevoBundleConsultaCuentas);
                startActivity(consultarCuentas);
                break;
            case R.id.creditosButton :
                /*temporal solo para prueba*/
                Intent solicitudPrestamo = new Intent(getApplicationContext(), SolicitudPrestamo.class);
                startActivity(solicitudPrestamo);
                break;
            case R.id.salirButton3:
                Toast toast5 = Toast.makeText(getApplicationContext(), "PAGAR TARJETA", Toast.LENGTH_SHORT);
                toast5.show();
                break;
            case R.id.salirButton : cerrarSesionBack(); break;
        }
    }

    private void recibirDatos() throws NoSuchAlgorithmException {
        Bundle datos = getIntent().getExtras();
        if (datos != null) {
            usuarioRecivido = (Usuario) datos.getSerializable("usuario");
        }
        buscarUsuario(usuarioRecivido.getDpiCliente());
    }


}
