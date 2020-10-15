package dev.com.j3b.ui.prestamos;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
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
import dev.com.j3b.enums.EstadosSolicitudTarjeta;
import dev.com.j3b.enums.FormaTrabajo;
import dev.com.j3b.enums.TipoDeTarjeta;
import dev.com.j3b.modelos.ServidorSQL;
import dev.com.j3b.modelos.SolicitudTarjeta;
import dev.com.j3b.modelos.Usuario;
import dev.com.j3b.ui.aplicacion.VentanaPrincipal;

public class SolicitudPrestamo extends AppCompatActivity {
    private Spinner spinnerFormaTrabajo;
    private Spinner spinnerTipoPrestamo;
    private EditText textoIngresoMensual;
    private EditText textoNombreEmpresa;
    private EditText textoMontoPrestamo;
    private EditText textoMotivoSolicitudPrestamo;
    private EditText textoDireccionBienRaiz;
    private Usuario usuarioLogueado;
    private Button botonEnviarSolicitud;
    private int posicionTipoTrabajo;
    private int posicionPrestamo;
    private ArrayList<String> formasDeTrabajo;
    private ArrayList<String>  tiposPrestamo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solicitud_prestamo);
        usuarioLogueado = MainActivity.usuarioLogueado;
        spinnerFormaTrabajo = findViewById(R.id.spinnerFormaTrabajo);
        spinnerTipoPrestamo = findViewById(R.id.spinnerTipoPrestamo);
        textoIngresoMensual = findViewById(R.id.textIngresoMensual);
        textoNombreEmpresa = findViewById(R.id.textNombreEmpresa);
        textoMontoPrestamo = findViewById(R.id.textMontoSolicitud);
        textoMotivoSolicitudPrestamo = findViewById(R.id.textMotivo);
        textoDireccionBienRaiz = findViewById(R.id.textDireccionBienRaiz);
        botonEnviarSolicitud = findViewById(R.id.buttonMandarSolicitud);
        formasDeTrabajo = new ArrayList<>();
        tiposPrestamo = new ArrayList<>();

        spinnerFormaTrabajo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> lista, View view, int posicion, long l) {
                if (lista != null) {
                    String cuenta = (String) lista.getSelectedItem();
                    posicionTipoTrabajo = posicion;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinnerTipoPrestamo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> lista, View view, int posicion, long l) {
                if (lista != null) {
                    String tipo = (String) lista.getSelectedItem();
                    posicionPrestamo = posicion;
                    if(tipo.equalsIgnoreCase("HIPOTECARIO")){
                        textoDireccionBienRaiz.setText("");
                        textoDireccionBienRaiz.setEnabled(true);
                    } else {
                        textoDireccionBienRaiz.setText("");
                        textoDireccionBienRaiz.setEnabled(false);
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //Evento para realizar transferencia
        botonEnviarSolicitud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validarSolicitud();
            }
        });

        //llena el spinner de las formas de trabajo que existen
        llenarSpinnerFormaTrabajo();
        llenarSpinnerTiposPrestamo();

    }

    public void llenarSpinnerFormaTrabajo() {
        llenarListaFormaTrabajo();
        escribirCuentasEnSpinnerFormaTrabajo();
    }
    public void llenarSpinnerTiposPrestamo() {
        llenarListaTiposPrestamo();
        escribirTiposPrestamoEnSpinner();
    }

    public void llenarListaFormaTrabajo() {
        this.formasDeTrabajo.add(FormaTrabajo.INDEPENDIENTE.toString());
        this.formasDeTrabajo.add(FormaTrabajo.DEPENDIENTE.toString());
    }

    public void escribirCuentasEnSpinnerFormaTrabajo() {
        String[] arrayCuentas = new String[formasDeTrabajo.size()];
        for (int i = 0; i < formasDeTrabajo.size(); i++) {
            arrayCuentas[i] = formasDeTrabajo.get(i);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arrayCuentas);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Llenando el spinner de Cuentas ORIGEN
        spinnerFormaTrabajo.setAdapter(adapter);
    }

    public void llenarListaTiposPrestamo() {
        this.tiposPrestamo.add("PERSONAL");
        this.tiposPrestamo.add("HIPOTECARIO");

    }

    public void escribirTiposPrestamoEnSpinner() {
        String[] arrayCuentas = new String[tiposPrestamo.size()];
        for (int i = 0; i < tiposPrestamo.size(); i++) {
            arrayCuentas[i] = tiposPrestamo.get(i);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arrayCuentas);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Llenando el spinner de Cuentas ORIGEN
        spinnerTipoPrestamo.setAdapter(adapter);
    }


    public void validarSolicitud() {
        double ingresoMensual;
        String empresa = this.textoNombreEmpresa.getText().toString();
        String formaTrabajo = this.spinnerFormaTrabajo.getSelectedItem().toString();
        String tipoPrestamoDeseado = this.spinnerTipoPrestamo.getSelectedItem().toString();
        String motivo = this.textoMotivoSolicitudPrestamo.getText().toString();
        double montoPrestamo;
        String direccionBienRaiz = this.textoDireccionBienRaiz.getText().toString();

        try {
            if(this.textoIngresoMensual.getText().toString().isEmpty() || this.textoNombreEmpresa.getText().toString().isEmpty() || this.textoMontoPrestamo.getText().toString().isEmpty()){
                Toast.makeText(getApplicationContext(), "Todos los campos marcados con (*) son obligatorios porfavor revise. "+"\n", Toast.LENGTH_LONG).show();
            } else {
                ingresoMensual = Double.parseDouble(this.textoIngresoMensual.getText().toString());
                montoPrestamo = Double.parseDouble(this.textoMontoPrestamo.getText().toString());
                if (ingresoMensual == 0 || montoPrestamo ==0){
                    Toast.makeText(getApplicationContext(), "Las cantidades solicitadas no pueden tener valor de 0, porfavor revise. "+"\n", Toast.LENGTH_LONG).show();
                } else {
                    if (empresa.length()>50 || direccionBienRaiz.length()>50 || motivo.length()>200){
                        Toast.makeText(getApplicationContext(), "El nombre de la empresa o dirección de bien raiz son muy grandes, porfavor revise. "+"\n", Toast.LENGTH_LONG).show();
                    } else {
                        if (tipoPrestamoDeseado.equalsIgnoreCase("HIPOTECARIO") && direccionBienRaiz.isEmpty()){
                            Toast.makeText(getApplicationContext(), "Si el prestamo es hipotecario se debe especificar la dirección del bien a hipotecar. "+"\n", Toast.LENGTH_LONG).show();
                        } else {
                            //realizar solicitud
                            registrarSolicitud(ingresoMensual,empresa,formaTrabajo,motivo,tipoPrestamoDeseado,montoPrestamo,direccionBienRaiz);
                        }

                    }
                }

            }

        }
        catch (NumberFormatException e){
            Toast.makeText(getApplicationContext(), "Alguna de las cantidades ingresadas no es valida por favor revise.: "+motivo.length()+"\n", Toast.LENGTH_LONG).show();
        }

    }


    public void registrarSolicitud(final Double ingresoMensual, final String empresa, final String formaTrabajo, final String motivo, final String tipoPrestamoDeseado, final Double montoPrestamo, final String bienraiz){

        //preguntando confirmacion para ejecutar la solicitud
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Desea completar la solicitud de prestamo?\nDatos Ingresados:\n" +
                "   Ingreso Mensual: "+ingresoMensual+"\n" +
                "   Empresa: "+empresa+"\n" +
                "   Forma Trabajo: "+formaTrabajo+"\n" +
                "   Tipo Prestamo Solicitada: "+tipoPrestamoDeseado+"\n" +
                "   Monto de Prestamo Solicitado: "+montoPrestamo+"\n" +
                "   Motivo: "+motivo+"\n");
        builder.setTitle("Confirmacion");
        builder.setCancelable(false);
        builder.setPositiveButton("Confirmar Solicitud", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                //Limpiar  informacion
                textoIngresoMensual.setText("");
                textoMotivoSolicitudPrestamo.setText("");
                textoNombreEmpresa.setText("");
                crearSolicitudTransaccion(ingresoMensual,empresa,formaTrabajo,motivo,tipoPrestamoDeseado,montoPrestamo,bienraiz);

            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getApplicationContext(), "Solicitud cancelada", Toast.LENGTH_LONG).show();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void crearSolicitudTransaccion(Double ingresoMensual, String empresa, String formaTrabajo, String motivo, String tipoPrestamoDeseado, Double montoPrestamo, String bienraiz){
        String consultaSQL= ServidorSQL.SERVIDORSQL_CONRETORNO+
                "SELECT registrarSolicitudPrestamo('"+formaTrabajo+"','"+ VentanaPrincipal.cuentaHabienteLogueado.getDpiCliente()
                +"','"+empresa+"','"+"EN_ESPERA"+"','"+ingresoMensual+"','"+montoPrestamo+"','"+tipoPrestamoDeseado+"','"+bienraiz+"','"+motivo+"') AS solicitud;";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(consultaSQL,
                new Response.Listener<JSONArray>() {
                    JSONObject jsonObjectDatosUsuario = null;
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            jsonObjectDatosUsuario = response.getJSONObject(0);
                            Integer idSolicitud = jsonObjectDatosUsuario.getInt("solicitud");

                            Toast.makeText(getApplicationContext(), "Se realizo la solicitud correctamente, el siguiente id te servira para rastrear tu solicitud en los bancos. ID: " + idSolicitud, Toast.LENGTH_LONG).show();
                            textoDireccionBienRaiz.setText("");
                            textoMontoPrestamo.setText("");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "ERROR, MENSAJE: "+error.getMessage(), Toast.LENGTH_LONG).show();

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }
}
