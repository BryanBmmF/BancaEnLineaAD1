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

public class SolicitudPrestamo extends AppCompatActivity {
    private Spinner spinnerFormaTrabajo;
    private Spinner spinnerTipoPrestamo;
    private EditText textoIngresoMensual;
    private EditText textoNombreEmpresa;
    private EditText textoMontoPrestamo;
    private EditText textoMotivoSolicitudTarjeta;
    private EditText textoDireccionBienRaiz;
    private Usuario usuarioLogueado;
    private Button botonEnviarSolicitud;
    private int posicionTipoTrabajo;
    private int posicionTarjeta;
    private ArrayList<String> formasDeTrabajo;
    private ArrayList<String>  tiposPrestamo;
    private final Integer ID_INICIAL_SOLICITUD = -1;
    private final Integer NUMERO_BRONCE = 0;
    private final Integer NUMERO_PLATA = 1;
    private final Integer NUMERO_ORO = 2;

    private final Double MINIMO_INGRESO = 2500.0;
    private final Double LIMITE_PLATA = 20000.0;
    private final Double LIMITE_BRONCE = 10000.0;



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
        textoMotivoSolicitudTarjeta = findViewById(R.id.textMotivoSolicitudTarjeta);
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
                    String cuenta = (String) lista.getSelectedItem();
                    posicionTarjeta = posicion;
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
        String ingresoMensual = this.textoIngresoMensual.getText().toString();
        String empresa = this.textoNombreEmpresa.getText().toString();
        String formaTrabajo = this.spinnerFormaTrabajo.getSelectedItem().toString();
        Integer tipoTarjetaDeseada = this.spinnerTipoPrestamo.getSelectedItemPosition();
        String motivo = this.textoMotivoSolicitudTarjeta.getText().toString();
        if (!ingresoMensual.equals("")) {
            Double ingreso = Double.parseDouble(ingresoMensual);
            if (ingreso > 0) {
                if (ingreso >= 2500) {
                    if(!empresa.equals("")){
                        if(empresa.length()<=50){
                            if(motivo.length()<=50){
                                registrarSolicitud(ingreso,empresa,formaTrabajo,motivo,tipoTarjetaDeseada);
                            }else{
                                Toast.makeText(getApplicationContext(), "El campo de motivo no puede sobrepasar los 50 caracteres, actuales: "+motivo.length()+"\n", Toast.LENGTH_LONG).show();
                            }
                        }else{
                            Toast.makeText(getApplicationContext(), "El campo de empresa no puede sobrepasar los 50 caracteres, actuales: "+empresa.length()+"\n", Toast.LENGTH_LONG).show();
                        }
                    }else{
                        Toast.makeText(getApplicationContext(), "El campo de empresa no puede estar vacio \n", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Ingreso Mensual rechazado.\n No puedes realizar una solicitud si tus ingresos son menores a Q2500.00", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(getApplicationContext(), "Ingreso Mensual rechazado.\n No se pueden poner ingresos negativos", Toast.LENGTH_LONG).show();
            }
        }else{
            Toast.makeText(getApplicationContext(), "El campo de ingreso mensual no puede estar vacio \n", Toast.LENGTH_LONG).show();
        }
    }


    public void registrarSolicitud(Double ingresoMensual, String empresa, String formaTrabajo, String motivo, Integer tipoTarjetaDeseada){
        String tipoTarjeta = "";
        String deseada = "";
        boolean banderaSinErrores = true;
        if (ingresoMensual < MINIMO_INGRESO) {
            Toast.makeText(getApplicationContext(), "El ingreso minimo mensual debe de ser mayor a Q2500.00, no se puede generar una solicitud de tarjeta de credito con este ingreso mensual  \n", Toast.LENGTH_LONG).show();
            banderaSinErrores = false;
        } else if (ingresoMensual >= MINIMO_INGRESO && ingresoMensual <= LIMITE_BRONCE) {
            tipoTarjeta = TipoDeTarjeta.BRONCE.toString();
        } else if (ingresoMensual > LIMITE_BRONCE && ingresoMensual <= LIMITE_PLATA) {
            tipoTarjeta = TipoDeTarjeta.PLATA.toString();
        } else if (ingresoMensual > LIMITE_PLATA) {
            tipoTarjeta = TipoDeTarjeta.ORO.toString();
        }

        if(banderaSinErrores) {
            if (tipoTarjetaDeseada == NUMERO_BRONCE) {
                deseada = TipoDeTarjeta.BRONCE.toString();
            } else if (tipoTarjetaDeseada == NUMERO_PLATA) {
                deseada = TipoDeTarjeta.PLATA.toString();
            } else if (tipoTarjetaDeseada == NUMERO_ORO) {
                deseada = TipoDeTarjeta.ORO.toString();
            }
            //validando que coincidan la deseada con su tipo de tarjeta que le corresponderia
            if (deseada.equals(TipoDeTarjeta.BRONCE.toString()) && (tipoTarjeta.equals(TipoDeTarjeta.BRONCE.toString()) || tipoTarjeta.equals(TipoDeTarjeta.PLATA.toString()) || tipoTarjeta.equals(TipoDeTarjeta.ORO.toString()))) {
                // si pide una bronce y puede pedir bronce o superior esta bien y asi con el resto
            } else if (deseada.equals(TipoDeTarjeta.PLATA.toString()) && (tipoTarjeta.equals(TipoDeTarjeta.PLATA.toString()) || tipoTarjeta.equals(TipoDeTarjeta.ORO.toString()))) {

            } else if (deseada.equals(TipoDeTarjeta.ORO.toString()) && tipoTarjeta.equals(TipoDeTarjeta.ORO.toString())) {

            } else {//si solicita una mayor a la que puede solicitar muestra el error
                Toast.makeText(getApplicationContext(), "ERROR\n QUIERES APLICAR A: " + deseada + ", Y LA MAXIMA QUE PUEDES APLICAR ES: " + tipoTarjeta + " O INFERIOR\nMINIMO INGRESOS PARA APLICAR A TARJETAS:\n BRONCE: Q2500.00\n PLATA: Q10001.00\n ORO: Q20001.00", Toast.LENGTH_LONG).show();
                banderaSinErrores = false;
            }
            if (banderaSinErrores) {
                final SolicitudTarjeta solicitud = new SolicitudTarjeta(ID_INICIAL_SOLICITUD, formaTrabajo, this.usuarioLogueado.getDpiCliente(), empresa, EstadosSolicitudTarjeta.EN_ESPERA.toString(), ingresoMensual, deseada, motivo);
                //preguntando confirmacion para ejecutar la solicitud
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Desea realizar la solicitud de tarjeta de credito?\n Datos Ingresados:\n" +
                        "Ingreso Mensual: "+solicitud.getSalario()+"\n" +
                        "Empresa: "+solicitud.getEmpresa()+"\n" +
                        "Forma Trabajo: "+solicitud.getTipoTrabajo()+"\n" +
                        "Tipo Tarjeta Solicitada: "+solicitud.getTarjeta()+"\n" +
                        "Descripcion: "+solicitud.getDescripcion()+"\n");
                builder.setTitle("Confirmacion");
                builder.setCancelable(false);
                builder.setPositiveButton("Confirmar Solicitud", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        //Limpiar  informacion
                        textoIngresoMensual.setText("");
                        textoMotivoSolicitudTarjeta.setText("");
                        textoNombreEmpresa.setText("");
                        crearSolicitudTransaccion(solicitud);


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
        }
    }

    public void crearSolicitudTransaccion(final SolicitudTarjeta solicitud){
        String consultaSQL= ServidorSQL.SERVIDORSQL_CONRETORNO+
                "SELECT registrarSolicitudTarjetaCredito('"+solicitud.getTipoTrabajo()+"','"+solicitud.getDpi_cliente()+"','"+solicitud.getEmpresa()+"','"+solicitud.getEstado()+"',"+solicitud.getSalario()+",'"+solicitud.getTarjeta()+"','"+solicitud.getDescripcion()+"') AS solicitud;";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(consultaSQL,
                new Response.Listener<JSONArray>() {
                    JSONObject jsonObjectDatosUsuario = null;
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            jsonObjectDatosUsuario = response.getJSONObject(0);
                            Integer idSolicitud = jsonObjectDatosUsuario.getInt("solicitud");
                            solicitud.setId(idSolicitud);
                            Toast.makeText(getApplicationContext(), "Se realizo la solicitud correctamente, el siguiente id te servira para rastrear tu solicitud en los bancos. ID: " + solicitud.getId(), Toast.LENGTH_LONG).show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "ERROR, MENSAJE: "+error.getMessage(), Toast.LENGTH_LONG).show();
                solicitud.setId(null);
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }
}
