<?php
include("conexion.php");

$varNumTarjeta = $_POST['numerotarjeta'];
$varMes = $_POST['month'];
$varYear = $_POST['year'];
$varCodigoCVC = $_POST['codigocvc'];
$varNombrePropietario = $_POST['nombre'];
$varMontoPago = $_POST['montoPago'];
$varFechaVencimiento = $varYear."-".$varMes."-31 00:00:00";

$errorID = 0;
//select no_tarjeta, tipo, estado, limite, deuda_actual, fecha_vencimiento, codigoCVC, tasa_interes, numero_cuenta, concat_ws(' ', nombres, apellidos) as nombre_completo from tarjeta a INNER JOIN cuenta_habiente b on a.dpi_cuenta_habiente = b.dpi_cliente where fecha_vencimiento > '2020-9-1' and estado='ACTIVA';


$mysqlQuery = "SELECT no_tarjeta, tipo, estado, limite, deuda_actual, fecha_vencimiento, codigoCVC, tasa_interes, numero_cuenta, concat_ws(' ', nombres, apellidos) as nombre_completo from tarjeta a INNER JOIN cuenta_habiente b on a.dpi_cuenta_habiente = b.dpi_cliente where concat_ws(' ', nombres, apellidos)='$varNombrePropietario' and no_tarjeta='$varNumTarjeta' and codigoCVC='$varCodigoCVC' and fecha_vencimiento = '$varFechaVencimiento'  and estado='ACTIVA' and now() < fecha_vencimiento";
$result = $conexion->query($mysqlQuery);
$num_rows = mysqli_num_rows($result);

if ($num_rows > 0) {
  // do something
  echo "Se encontro algo";

  $consultaDatosTarjeta = "SELECT * FROM tarjeta where no_tarjeta='$varNumTarjeta' LIMIT 1";
  $resultadoDatosTarjeta = mysqli_query($conexion, $consultaDatosTarjeta);
  while ($datoTablaTarjeta = mysqli_fetch_array($resultadoDatosTarjeta)) {
    $datoNumeroCuenta = $datoTablaTarjeta['numero_cuenta'];
    $datoTipo = $datoTablaTarjeta['tipo'];
    $datoLimite = $datoTablaTarjeta['limite'];
    $datoDpi = $datoTablaTarjeta['dpi_cuenta_habiente'];
    $datoDeudaActual = $datoTablaTarjeta['deuda_actual'];
    $datoTasaInteres = $datoTablaTarjeta['tasa_interes'];
  }

  if ($datoTipo == "CREDITO") {
    // Si la tarjeta es de credito realizamos las siguientes validaciones...
    $disponibilidadDeGasto = $datoLimite - $datoDeudaActual;
    if ($disponibilidadDeGasto>$varMontoPago) {
      // Verificamos que no tenga la tarjeta sobregirada...
      $varInteresesPago = $varMontoPago*$datoTasaInteres;
      $varPagoTotal = $varInteresesPago+$varMontoPago;
      echo $varMontoPago;
      echo $varInteresesPago;
      echo $varPagoTotal;

      //Se realiza el insert de los nuevos datos de la tarjeta y se registra una transaccion de la misma
      $insertTransaccionTarjeta = "INSERT INTO transaccion_tarjeta (id_transaccion_tarjeta, no_tarjeta, monto_tienda, monto_interes, monto_total) VALUES (NULL, '$varNumTarjeta', '$varMontoPago', '$varInteresesPago', '$varPagoTotal');";
      if ($conexion->query($insertTransaccionTarjeta) === true) {
        //header("location: ./asignacionExitosa.php");
        $nuevaDeudaActual = $datoDeudaActual+$varPagoTotal;
        //Se realiza el insert de los nuevos datos de la tarjeta y se registra una transaccion de la misma
        $insertTransaccionTarjeta = "UPDATE tarjeta SET deuda_actual='$nuevaDeudaActual' WHERE no_tarjeta = '$varNumTarjeta';";
        if ($conexion->query($insertTransaccionTarjeta) === true) {
          //header("location: ./asignacionExitosa.php");
          header("location: ./pagoExitoso.php?dpiCliente=$datoDpi&monto=$varMontoPago&tarjeta=$varNumTarjeta");

        } else {
          header("location: ./errorDeCompra.php?error=3");
        }

      } else {
        header("location: ./errorDeCompra.php?error=3");
      }

    } else {
      // Si no posee disponibilidad de gasto se rechaza la compra...
      header("location: ./errorDeCompra.php?error=2");
    }

  } else if ($datoTipo == "DEBITO") {
    // Si la tarjeta es de debito realizamos las siguientes validaciones...
  }

} else {
  // Si no se encuentra una tarjeta valida con los datos ingresados por el usuario entonces se rechaza la compra
  header("location: ./errorDeCompra.php?error=1");
}

//$date=date_create("2013-03-15");
//echo date_format($date,"Y/m/d H:i:s");
?>
