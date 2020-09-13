package dev.com.j3b.ui.transaccionesPropias;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

import dev.com.j3b.MainActivity;
import dev.com.j3b.R;
import dev.com.j3b.enums.EstadoDeCuenta;
import dev.com.j3b.manejadorLogIn.ManejadorCuentaPropia;
import dev.com.j3b.modelos.Cuenta;
import dev.com.j3b.ui.aplicacion.VentanaPrincipal;

public class TransaccionCuentasPropias extends AppCompatActivity {

    private Button retroceder,btnConsultaCuentas;
    private ManejadorCuentaPropia manejadorCuentaPropia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaccion_cuentas_propias);
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
                ArrayList<Cuenta> cuentas=manejadorCuentaPropia.consultarCuentasDeUsuario(MainActivity.usuarioLogueado.getDpiCliente(), EstadoDeCuenta.ACTIVA);
                if(cuentas.isEmpty()){
                    System.out.println("NO EXISTEN CUENTAS");
                }
                for(Cuenta cuenta: cuentas){
                    System.out.println("---------------------Cuenta-----------------");
                    System.out.println("No:"+cuenta.getNoCuentaBancaria());
                    System.out.println("Saldo"+cuenta.getSaldo());
                }
            }
        });
    }

    public void volverAPaginaPrincipal(){
        Intent intent = new Intent(this,VentanaPrincipal.class);
        startActivity(intent);
    }
}