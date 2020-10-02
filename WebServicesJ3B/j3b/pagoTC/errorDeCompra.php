<?php

$codigoError = $_GET['error'];

if ($codigoError == 1) {
  // code...
  echo "Los datos de tu tarjeta son inválidos.";
} else if ($codigoError == 2) {
  // code...
  echo "Ha habido un error con tu tarjeta, por favor consulta al banco.";
} else if ($codigoError == 3) {
  // code...
  echo "Hubo un problema con tu conexión. Intenta más tarde.";
}

 ?>
