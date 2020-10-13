package dev.com.j3b.ui.tarjetaCredito;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import dev.com.j3b.MainActivity;
import dev.com.j3b.R;

public class PagarTarjeta extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagar_tarjeta);
    }

    public void mostrarTarjetas(){
        String consultaSql="SELECT no_tarjeta,tipo,limite,dpi_cuenta_habiente,estado,deuda_actual,fecha_vencimiento,codigoCVC,tasa_interes\n" +
                "FROM TARJETA WHERE dpi_cuenta_habiente="+ MainActivity.usuarioLogueado.getDpiCliente() +" AND estado='ACTIVA' AND deuda_actual>0 and tipo='CREDITO'";
    }

    public void mostrarCuentas(){
        String consultaSql="SELECT no_cuenta_bancaria,dpi_cliente,tipo_cuenta,tasa_interes,periodo_interes,estado,saldo\n" +
                "FROM CUENTA WHERE estado='activa' AND saldo>0 AND tipo_cuenta='MONETARIA' AND dpi_cliente="+MainActivity.usuarioLogueado.getDpiCliente();
    }

    public void efectuarPago(){
        String consultaInsertarPago="INSERT INTO PAGO_TARJETA(no_tarjeta,monto,fecha_pago) VALUES(?,?,?);\n";
        String consultaFuncion="SELECT pago_de_tarjeta()";
    }
}


