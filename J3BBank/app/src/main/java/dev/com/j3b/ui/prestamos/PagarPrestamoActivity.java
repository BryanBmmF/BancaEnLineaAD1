package dev.com.j3b.ui.prestamos;

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
import dev.com.j3b.enums.EstadosPrestamo;
import dev.com.j3b.manejadorLogIn.CuentaSaldo;
import dev.com.j3b.manejadorLogIn.PrestamosActivos;
import dev.com.j3b.modelos.ServidorSQL;
import dev.com.j3b.modelos.Usuario;
import dev.com.j3b.ui.aplicacion.VentanaPrincipal;
import dev.com.j3b.ui.tarjetaCredito.SolicitudTarjetaCreditoActivity;

public class PagarPrestamoActivity extends AppCompatActivity {


    private ArrayList<PrestamosActivos> arrayListPrestamosActivos = new ArrayList<>();
    private ArrayList<CuentaSaldo> arrayListCuentas = new ArrayList<>();

    private Spinner spinnerPrestamosActivos;
    private Spinner spinnerCuentas;
    private Button btnRealizarPago;
    private EditText txtMonto;
    private EditText txtDescripcion;
    private Usuario usuarioLogueado;
    private Double ERROR = -1.0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagar_prestamo);
        usuarioLogueado = MainActivity.usuarioLogueado;
        spinnerPrestamosActivos = findViewById(R.id.spinnerPrestamosActivos);
        spinnerCuentas = findViewById(R.id.spinnerCuentaPrestamo);
        btnRealizarPago = findViewById(R.id.buttonPagarPrestamo);
        txtMonto = findViewById(R.id.editTextMontoCancelarPrestamo);
        txtDescripcion = findViewById(R.id.editTextDescripcionPagoPrestamo);

        btnRealizarPago.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                realizarPago();
            }
        });




        spinnerPrestamosActivos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                txtDescripcion.setText(arrayListPrestamosActivos.get(position).getDescripcion());
                txtMonto.setText(arrayListPrestamosActivos.get(position).getMontoCancelar());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                txtDescripcion.setText("");
                txtMonto.setText("");
            }

        });

        buscarPrestamosActivos();
        buscarCuentas();

    }

    public void realizarPago(){
        if(this.arrayListPrestamosActivos.size()>0) {

            String numeroCuenta = this.arrayListCuentas.get(this.spinnerCuentas.getSelectedItemPosition()).getNumeroCuenta();
            Double montoAPagar = Double.parseDouble(this.arrayListPrestamosActivos.get(this.spinnerPrestamosActivos.getSelectedItemPosition()).getMontoCancelar());
            String idPrestamo = this.arrayListPrestamosActivos.get(this.spinnerPrestamosActivos.getSelectedItemPosition()).getIdPrestamo();
            //buscarCuentas(); // actualizara las cuentas y el saldo en ellas para tener la ultima actualizacion del saldo

            if (this.arrayListPrestamosActivos.size() > 0) {
                if (this.arrayListCuentas.size() > 0) {
                    Double saldoActualCuenta = obtenerMontoDeCuenta(numeroCuenta);
                    if (saldoActualCuenta == ERROR) {
                        Toast.makeText(getApplicationContext(), "LA CUENTA QUE SE HA SELECCIONADO YA NO ESTA ACTIVA O HABILITADA, VERIFICA SU ESTADO POR FAVOR.", Toast.LENGTH_SHORT).show();
                    } else {
                        if (saldoActualCuenta >= montoAPagar) {
                            realizarTransaccion(numeroCuenta, montoAPagar, idPrestamo);
                        } else {
                            Toast.makeText(getApplicationContext(), "SALDO INSUFICIENTE EN LA CUENTA: " + numeroCuenta + ", POR FAVOR ELIGE OTRA CUETNTA", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "NO EXISTEN CUENTAS CON LAS QUE PAGAR UN PRESTAMO ACTIVO.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getApplicationContext(), "NO EXISTEN PRESTAMOS ACTIVOS QUE PAGAR.", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(getApplicationContext(), "NO EXISTEN PRESTAMOS ACTIVOS QUE PAGAR.", Toast.LENGTH_SHORT).show();
        }
    }

    public void realizarTransaccion(String numeroCuenta, Double monto, String idPrestamo){
        String consultaSQL = ServidorSQL.SERVIDORSQL_PAGO_PRESTAMO+"?monto="+monto+"&idPrestamo="+idPrestamo+"&numeroCuenta="+numeroCuenta;
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(consultaSQL, new Response.Listener<JSONArray>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObjectDatosUsuario = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObjectDatosUsuario = response.getJSONObject(i);
                        String mensaje = jsonObjectDatosUsuario.getString("mensaje");
                        String resultado = jsonObjectDatosUsuario.getString("resultado");
                        Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_LONG).show();
                        if (resultado.equals("true")) {
                            finish();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "Hay problemas de conexión al servidor.", Toast.LENGTH_SHORT).show();
                    }
                }
                /*Intent nuevaVentanaPrincipal = new Intent(getApplicationContext(), VentanaPrincipal.class);
                Bundle nuevoBundlePrincipal = new Bundle();
                nuevoBundlePrincipal.putSerializable("usuario", );
                nuevaVentanaPrincipal.putExtras(nuevoBundlePrincipal);
                startActivity(nuevaVentanaPrincipal);
                */

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Hay problemas de conexión al servidor. DETALLES: "+error, Toast.LENGTH_LONG).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

    public Double obtenerMontoDeCuenta(String numeroCuenta){
        for (int i = 0; i < this.arrayListCuentas.size(); i++) {
            if(arrayListCuentas.get(i).getNumeroCuenta().equals(numeroCuenta)){
                return arrayListCuentas.get(i).getSaldoActual();
            }
        }
        return ERROR;
    }


    public void buscarCuentas(){
        this.arrayListCuentas.clear();
        String consultaSQL = ServidorSQL.SERVIDORSQL_CONRETORNO+"SELECT NO_CUENTA_BANCARIA,SALDO FROM CUENTA WHERE dpi_cliente ='"+this.usuarioLogueado.getDpiCliente()+"' AND ESTADO= '"+ EstadoDeCuenta.ACTIVA+"'";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(consultaSQL, new Response.Listener<JSONArray>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObjectDatosUsuario = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObjectDatosUsuario = response.getJSONObject(i);
                        String numCuenta = jsonObjectDatosUsuario.getString("NO_CUENTA_BANCARIA");
                        String saldo = jsonObjectDatosUsuario.getString("SALDO");
                        String opcionFinal ="Cuenta:"+ numCuenta+" - Saldo: Q"+saldo;
                        arrayListCuentas.add(new CuentaSaldo(numCuenta,Double.parseDouble(saldo)));
                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), "Hay problemas de conexión al servidor.", Toast.LENGTH_SHORT).show();
                    }
                }

                ArrayAdapter<CuentaSaldo> arrayAdapter = new ArrayAdapter<CuentaSaldo>(getApplicationContext(), R.layout.color_spinner_layout, arrayListCuentas);
                arrayAdapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
                //arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerCuentas.setAdapter(arrayAdapter);

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

    public void buscarPrestamosActivos(){
        String consultaSQL = ServidorSQL.SERVIDORSQL_CONRETORNO+"SELECT * FROM PRESTAMO WHERE dpi_cuenta_habiente ='"+this.usuarioLogueado.getDpiCliente()+"'"+" AND ESTADO='"+ EstadosPrestamo.activo.toString() +"'";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(consultaSQL, new Response.Listener<JSONArray>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(JSONArray response) {
                // id_prestamo | dpi_cuenta_habiente | montoTotal | deuda_restante | tasa_interes | cantidad_meses | tipo     | descripcion             | fecha_vencimiento   | estado
                //variables a utilizar

                String montoTotal = "";
                String deudaRestante ="";
                String fechaVencimiento = "";
                String idPrestamo = "";
                String tipo = "";
                String tasaInteres = "";
                String descripcion ="";
                String meses= "";
                JSONObject jsonObjectDatosUsuario = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObjectDatosUsuario = response.getJSONObject(i);
                        //recuperando tipo de tarjeta
                        montoTotal = jsonObjectDatosUsuario.getString("montoTotal");
                        deudaRestante = jsonObjectDatosUsuario.getString("deuda_restante");
                        fechaVencimiento = jsonObjectDatosUsuario.getString("fecha_vencimiento");
                        idPrestamo = jsonObjectDatosUsuario.getString("id_prestamo");
                        tipo = jsonObjectDatosUsuario.getString("tipo");
                        tasaInteres = jsonObjectDatosUsuario.getString("tasa_interes");
                        meses = jsonObjectDatosUsuario.getString("cantidad_meses");
                        descripcion ="  Monto Total:"+montoTotal+"\n  Deuda Restante: "+deudaRestante+ "\n  Fecha Vencimiento: "+fechaVencimiento+"\n  Tipo: "+tipo+"\n  Tasa Interes: "+tasaInteres+"\n  Cantidad Meses: "+meses;
                        double total = Double.parseDouble(montoTotal)/Double.parseDouble(meses);
                        arrayListPrestamosActivos.add(new PrestamosActivos(descripcion,Double.toString(total),idPrestamo));
                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), "Hay problemas de conexión al servidor.", Toast.LENGTH_SHORT).show();
                    }
                }

                ArrayAdapter<PrestamosActivos> arrayAdapter = new ArrayAdapter<PrestamosActivos>(getApplicationContext(), R.layout.color_spinner_layout, arrayListPrestamosActivos);
                arrayAdapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
                //arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerPrestamosActivos.setAdapter(arrayAdapter);
                if(arrayListPrestamosActivos.size()>0){
                    txtDescripcion.setText(arrayListPrestamosActivos.get(0).getDescripcion());
                    txtMonto.setText(arrayListPrestamosActivos.get(0).getMontoCancelar());
                }else{
                    Toast.makeText(getApplicationContext(), "No existen prestamos activos para realizarles un pago.", Toast.LENGTH_LONG).show();
                }


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







}

