package dev.com.j3b.ui.aplicacion;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import java.security.NoSuchAlgorithmException;

import dev.com.j3b.MainActivity;
import dev.com.j3b.R;

public class VentanaPrincipal extends AppCompatActivity {

    private Button cerrarSesionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ventana_principal);
        cerrarSesionButton = findViewById(R.id.cerrarSesionButton);

        cerrarSesionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cerrarSesion();
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            cerrarSesion();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void cerrarSesion() {
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
}