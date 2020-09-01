package dev.com.j3b.manejadorLogIn;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.CancellationSignal;
import android.os.Handler;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.UiThread;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.Date;

import dev.com.j3b.MainActivity;
import dev.com.j3b.R;
import dev.com.j3b.ui.ingreso.FingerprintActivity;

public class FingerprintHandler extends FingerprintManager.AuthenticationCallback {


    private Context context;
    private int value;

    // Constructor
    public FingerprintHandler(Context mContext) {
        context = mContext;
        value = 0;

    }


    public void startAuth(FingerprintManager manager, FingerprintManager.CryptoObject cryptoObject) {
        
        CancellationSignal cancellationSignal = new CancellationSignal();
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        manager.authenticate(cryptoObject, cancellationSignal, 0, this, null);
    }


    @Override
    public void onAuthenticationError(int errMsgId, CharSequence errString) {
        this.update("Error de Autenticación de huellas dactilares,\n" +"Espera 30 segundos para poder volver a poner tu huella", false);
        Toast.makeText(context, "HOLLL", Toast.LENGTH_SHORT).show();
        try {
            Thread.sleep(30000);
            Intent i = context.getPackageManager()
                    .getLaunchIntentForPackage( context.getPackageName() );
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(i);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }



    @Override
    public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {
        this.update("Ayuda de Autenticación de huellas dactilares\n" + helpString, false);
    }


    @Override
    public void onAuthenticationFailed() {
        if(value<4){
            this.update("Fallo al autenticar con la huella dactilar.", false);
            value++;
        }else{
            this.update("Error de Autenticación de huellas dactilares,\n En 30 segundos podras volver a intentar", false);

        }

    }


    @Override
    public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
        this.update("Éxito al autenticar con la huella dactilar.", true);
    }


    public void update(String e, Boolean success) {
        TextView textView = (TextView) ((Activity) context).findViewById(R.id.errorText);
        textView.setText(e);
        if (success) {
            //Redirigir hacia la vista de login
            Toast.makeText(context, e, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(context, MainActivity.class);
            context.startActivity(intent);
            ((Activity) context).finish();
            //textView.setTextColor(ContextCompat.getColor(context, R.color.successText));
        }
    }




}