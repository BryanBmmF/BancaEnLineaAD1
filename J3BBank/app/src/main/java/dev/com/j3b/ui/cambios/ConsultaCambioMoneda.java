package dev.com.j3b.ui.cambios;

import android.annotation.SuppressLint;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import dev.com.j3b.MainActivity;
import dev.com.j3b.R;
import dev.com.j3b.modelos.Cuenta;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ConsultaCambioMoneda extends AppCompatActivity {
    //private Button btnRealizarConsulta;
    //private EditText cantidad;
    //private EditText total;
    //Spinners contenedores de las cuentas
    //private Spinner spinnerMonedaOrigen;
    //private Spinner spinnerMonedaDestino;
    //Manejadores y elementos de configuracion
    //private ArrayList<Cuenta> listaDeCuentas;
    //private int posicionDeCuentaDestino;
    //private int posicionDeCuentaOrigen;

    // Crear variable con el tipo de moneda por Pais
    Currency[] currency ={
            //new Currency("Euro", "EUR"),
            //new Currency("Hong Kong Dollar", "HKD"),
            //new Currency("Honduran Lempira", "HNL"),
            new Currency("USD. United States Dollar", "USD"),
            //new Currency("Venezuelan Bolívar Fuerte", "VEF"),
            //new Currency("Mexican Peso", "MXN"),
            new Currency("GTQ. Quetzal Guatemalteco", "GTQ")
    };

    // Declaración de variables
    private String jsonResult;
    String divisa, divisa2, cantidad;
    Spinner spinnerMonedaOrigen, spinnerMonedaDestino;
    Button btnConvert;
    EditText edtCantidad;
    TextView txtResultado;
    private static String urls = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta_cambio_moneda);
// Obtener la referencia del objeto spinner
        spinnerMonedaOrigen = (Spinner)findViewById(R.id.spinnerMonedaOrigen);
// Construir los datos en el spinner
        MySpinnerAdapter adapter = new MySpinnerAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, currency);
        spinnerMonedaOrigen.setAdapter(adapter);
// Evento selección del spinner
        spinnerMonedaOrigen.setOnItemSelectedListener(onItemSelectedListener1);
// Obtener la referencia del objeto spinner
        spinnerMonedaDestino = (Spinner)findViewById(R.id.spinnerMonedaDestino);
// Construir los datos en el spinner
        MySpinnerAdapter adapter2 = new MySpinnerAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, currency);
        spinnerMonedaDestino.setAdapter(adapter2);
// Evento selección del spinner
        spinnerMonedaDestino.setOnItemSelectedListener(onItemSelectedListener2);
// Obtener la refencia del Button
        btnConvert = (Button) findViewById(R.id.btnRealizarConversion);
        btnConvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
// Obtener referencia del objeto editText
                edtCantidad = (EditText)findViewById(R.id.cantidad);
// Asignar el valor a la variable string
                cantidad = edtCantidad.getText().toString();
                /*Esta url funcionaba antes cuando era gratis*/
                //urls = "https://api.fixer.io/latest?base="+divisa+"&symbols="+divisa2+"&amount="+cantidad+"";

                /*Ahora es necesario registrarse en https://fixer.io/
                * Con una cuenta gratuita de un mes: unicamente se puede consultar los cambios con respecto a una base determinada en este caso el EUR
                *   urls = "http://data.fixer.io/api/latest?access_key=c780a58bac4ba80bd2b79294f9ca7e2d&symbols=GTQ,USD,EUR";
                * Conuna Cuenta pagada: ya se habilitan todas las opciones
                *   urls = "http://data.fixer.io/api/convert?access_key=c780a58bac4ba80bd2b79294f9ca7e2d&from="+divisa+"&to="+divisa2+"&amount="+cantidad+"";
                * Clave de acceso, en mi caso es gratuita: c780a58bac4ba80bd2b79294f9ca7e2d
                *  */

                urls = "http://data.fixer.io/api/latest?access_key=c780a58bac4ba80bd2b79294f9ca7e2d&symbols=GTQ,USD,EUR";

                //Toast.makeText(getApplicationContext(),urls,Toast.LENGTH_LONG).show();
                if(cantidad.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Porfavor ingresa una cantidad a consultar", Toast.LENGTH_LONG).show();
                } else {
                    // Ejecutar la clase asincrona
                    webService task = new webService();
                    task.execute();
                }

            }
        });
    }

    // Clase asincrona del servicio
    //@SuppressLint("StaticFieldLeak")
    private class webService extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            String forecastJsonStr = null;
            try {
// Asignar la URL de la conexión
                URL url = new URL(urls);
// Establece el tipo de conexiòn
                urlConnection = (HttpURLConnection) url.openConnection();
// El tipo de conexiòn Get
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }
                if (buffer.length() == 0) {
                    return null;
                }

                forecastJsonStr = buffer.toString();
                jsonResult = buffer.toString();
                return forecastJsonStr;
            } catch (IOException e) {
                Log.e("PlaceholderFragment", "Error ", e);
                return null;
            } finally{
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("PlaceholderFragment", "Error closing stream", e);
                    }
                }
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            getResultado();
        }
    }

    // Obtener el resultado
    public void getResultado(){
        try{
            String resDivisa, resDivisa1, resDivisa2;
            double calc = 0;
// Obtener el valor del arreglo de la devoluciòn
            JSONObject reader = new JSONObject(jsonResult);
// Especificar el valor que se necesita
            JSONObject rates = reader.getJSONObject("rates");
// Obtener el valor pero en nodo
            // valor actual de divisa 1
            resDivisa1 = rates.getString(divisa);
            // valor actual de divisa 2
            resDivisa2 = rates.getString(divisa2);

            //casos
            if(divisa.equalsIgnoreCase("USD") && divisa2.equalsIgnoreCase("GTQ")){
                calc= (Double.parseDouble(cantidad)*Double.parseDouble(resDivisa2))/Double.parseDouble(resDivisa1);
                calc = Math.round(calc * 100) / 100d; // redondeando el calculo
                resDivisa = String.valueOf(calc);
            } else if (divisa.equalsIgnoreCase("GTQ") && divisa2.equalsIgnoreCase("USD")){
                calc= (Double.parseDouble(cantidad)*Double.parseDouble(resDivisa2))/Double.parseDouble(resDivisa1);
                resDivisa = String.valueOf(calc);
            } else {
                resDivisa= cantidad;
            }
            Toast.makeText(getApplicationContext(), "Consulta realizada correctamente", Toast.LENGTH_LONG).show();
// Asignar el valor al TextView
            txtResultado = (TextView) findViewById(R.id.total);
            txtResultado.setText(resDivisa);
            System.out.println("******************* Total: "+resDivisa);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    AdapterView.OnItemSelectedListener onItemSelectedListener1 =
            new AdapterView.OnItemSelectedListener(){

                @Override
                public void onItemSelected(AdapterView<?> parent, View view,
                                           int position, long id) {
                    Currency obj = (Currency)(parent.getItemAtPosition(position));
                    divisa = String.valueOf(obj.getValue());
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {}

            };
    AdapterView.OnItemSelectedListener onItemSelectedListener2 =
            new AdapterView.OnItemSelectedListener(){

                @Override
                public void onItemSelected(AdapterView<?> parent, View view,
                                           int position, long id) {
                    Currency obj = (Currency)(parent.getItemAtPosition(position));
                    divisa2 = String.valueOf(obj.getValue());
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {}

            };

    // Definicion de la clase Currency
    public class Currency{

        private String text;
        private String value;

        public Currency(String text, String value){
            this.text = text;
            this.value = value;
        }

        public void setText(String text){
            this.text = text;
        }

        public String getText(){
            return this.text;
        }

        public void setValue(String value){
            this.value = value;
        }

        public String getValue(){
            return this.value;
        }
    }

    //custom adapter
    public class MySpinnerAdapter extends ArrayAdapter<Currency> {

        private Context context;
        private Currency[] myObjs;

        public MySpinnerAdapter(Context context, int textViewResourceId,
                                Currency[] myObjs) {
            super(context, textViewResourceId, myObjs);
            this.context = context;
            this.myObjs = myObjs;
        }

        public int getCount(){
            return myObjs.length;
        }

        public Currency getItem(int position){
            return myObjs[position];
        }

        public long getItemId(int position){
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView label = new TextView(context);
            label.setText(myObjs[position].getText());
            return label;
        }

        @Override
        public View getDropDownView(int position, View convertView,
                                    ViewGroup parent) {
            TextView label = new TextView(context);
            label.setText(myObjs[position].getText());
            return label;
        }
    }
}
