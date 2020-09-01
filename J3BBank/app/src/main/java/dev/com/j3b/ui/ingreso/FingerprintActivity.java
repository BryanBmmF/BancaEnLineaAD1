package dev.com.j3b.ui.ingreso;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaCodec;
import android.os.Bundle;
import android.Manifest;
import android.annotation.TargetApi;
import android.app.KeyguardManager;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyPermanentlyInvalidatedException;
import android.security.keystore.KeyProperties;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import dev.com.j3b.MainActivity;
import dev.com.j3b.R;
import dev.com.j3b.manejadorLogIn.FingerprintHandler;

public class FingerprintActivity extends AppCompatActivity {


    private KeyStore keyStore;
    // Variable used for storing the key in the Android Keystore container
    private static final String KEY_NAME = "pruebaHuella";
    private Cipher cipher;
    private TextView textView;
    private TextView textoIntroHuella;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fingerprint);
        Button buttonSi = (Button) findViewById(R.id.btnSi);
        buttonSi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                crearArchivo("1");
            }
        });
        Button buttonNo = (Button) findViewById(R.id.btnNo);
        buttonNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                crearArchivo("0");
            }
        });

        // Initializing both Android Keyguard Manager and Fingerprint Manager
        KeyguardManager keyguardManager = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
        FingerprintManager fingerprintManager = (FingerprintManager) getSystemService(FINGERPRINT_SERVICE);


        this.textView = (TextView) findViewById(R.id.errorText);
        this.textoIntroHuella = (TextView) findViewById(R.id.textoIntroHuella);


        // Check whether the device has a Fingerprint sensor.
        if(!fingerprintManager.isHardwareDetected()){
            /**
             * An error message will be displayed if the device does not contain the fingerprint hardware.
             * However if you plan to implement a default authentication method,
             * you can redirect the user to a default authentication activity from here.
             * Example:
             * Intent intent = new Intent(this, DefaultAuthenticationActivity.class);
             * startActivity(intent);
             */
            Toast.makeText(getApplicationContext(), "Tu dispositivo no tiene un lector de huella digital, No se podria aplicar", Toast.LENGTH_SHORT).show();

            //*************************
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);

        }else {
            // Checks whether fingerprint permission is set on manifest
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getApplicationContext(), "La autenticacion por huella digital no esta habilitada", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);

            }else{
                // Check whether at least one fingerprint is registered
                if (!fingerprintManager.hasEnrolledFingerprints()) {
                    Toast.makeText(getApplicationContext(), "Para la autenticacion por huella digital debes de tener minimo una huella registrada en el dispositivo", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);


                }else{
                    // Checks whether lock screen security is enabled or not
                    if (!keyguardManager.isKeyguardSecure()) {
                        Toast.makeText(getApplicationContext(), "La seguridad de la pantalla de bloqueo no está habilitada en Configuración", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(this, MainActivity.class);
                        startActivity(intent);

                    }else{
                        generateKey();
                        try {
                            if (cipherInit()) {
                                leerArchivoHuella(fingerprintManager);
                            }
                        }catch (RuntimeException e){
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }catch (Exception e){
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        }
    }

    public void leerArchivoHuella(FingerprintManager fingerprintManager){
        try{//data/data/MiProyecto/files/archivo.txt
            File file = new File("/data/data/dev.com.j3b/files/config.j3b");
            boolean a = file.exists();
            if(file.exists()){
                BufferedReader aux = new BufferedReader(new InputStreamReader(openFileInput("config.j3b")));
                String texto = aux.readLine();
                if(texto.equals("1")){//Si quiere tener el paso de la huella
                    activarBotonesInicioNormal();

                    FingerprintManager.CryptoObject cryptoObject = new FingerprintManager.CryptoObject(cipher);
                    FingerprintHandler helper = new FingerprintHandler(this);
                    helper.startAuth(fingerprintManager, cryptoObject);
                }else{//texto = 0, no quiere tener el paso de la huella
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                }
            }else{
                 //  /config.j3b
                textView.setText("");
                textoIntroHuella.setVisibility(View.INVISIBLE);
                activarBotonesInicioAplicacion();
            }

        }catch (RuntimeException e){
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void activarBotonesInicioAplicacion(){
        textView.setText("");
        findViewById(R.id.textoIntroHuella).setVisibility(View.INVISIBLE);
        findViewById(R.id.btnNo).setVisibility(View.VISIBLE);
        findViewById(R.id.btnSi).setVisibility(View.VISIBLE);
        findViewById(R.id.textoInicialApp).setVisibility(View.VISIBLE);
    }

    public void activarBotonesInicioNormal(){
        this.textoIntroHuella.setVisibility(View.VISIBLE);
        this.textoIntroHuella.setText("Ingresa tu huella por favor");
        findViewById(R.id.btnNo).setVisibility(View.INVISIBLE);
        findViewById(R.id.btnSi).setVisibility(View.INVISIBLE);
        findViewById(R.id.textoInicialApp).setVisibility(View.INVISIBLE);
    }

    public void crearArchivo(String resultado){
        try {
            OutputStreamWriter aux = new OutputStreamWriter(openFileOutput("config.j3b", Context.MODE_PRIVATE));
            aux.write(resultado);
            aux.close();
            Toast.makeText(getApplicationContext(), "Se guardaron los datos correctamente, se utilizaran en el proximo inicio de aplicacion", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);

        }catch(Exception e){
            Toast.makeText(getApplicationContext(), "Existieron problemas al crear el archivo de configuracion", Toast.LENGTH_SHORT).show();
            Log.e("Archivo config.j3b ", "Error al escribir el archivo de configuracion en la memoria interna");
        }
    }
















    @TargetApi(Build.VERSION_CODES.M)
    protected void generateKey() {
        try {
            keyStore = KeyStore.getInstance("AndroidKeyStore");
        } catch (Exception e) {
            e.printStackTrace();
        }


        KeyGenerator keyGenerator;
        try {
            keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");
        } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
            throw new RuntimeException("Fallo al obtener una instancia el KeyGenerator", e);
        }


        try {
            keyStore.load(null);
            keyGenerator.init(new
                    KeyGenParameterSpec.Builder(KEY_NAME,
                    KeyProperties.PURPOSE_ENCRYPT |
                            KeyProperties.PURPOSE_DECRYPT)
                    .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                    .setUserAuthenticationRequired(true)
                    .setEncryptionPaddings(
                            KeyProperties.ENCRYPTION_PADDING_PKCS7)
                    .build());
            keyGenerator.generateKey();
        } catch (NoSuchAlgorithmException |
                InvalidAlgorithmParameterException
                | CertificateException | IOException e) {
            throw new RuntimeException(e);
        }
    }


    @TargetApi(Build.VERSION_CODES.M)
    public boolean cipherInit() {
        try {
            cipher = Cipher.getInstance(KeyProperties.KEY_ALGORITHM_AES + "/" + KeyProperties.BLOCK_MODE_CBC + "/" + KeyProperties.ENCRYPTION_PADDING_PKCS7);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            throw new RuntimeException("Fallo al obtener el Cipher", e);
        }


        try {
            keyStore.load(null);
            SecretKey key = (SecretKey) keyStore.getKey(KEY_NAME,
                    null);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return true;
        } catch (KeyPermanentlyInvalidatedException e) {
            return false;
        } catch (KeyStoreException | CertificateException | UnrecoverableKeyException | IOException | NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException("Fallo al iniciar el Cipher", e);
        }
    }
}
