<?php
include('conexion.php');

$consultaSQL=$_GET['consultaSQL'];

//$consulta = "select * from usuario_cliente where usuario_cliente='$consultaSQL'";
$consulta = $consultaSQL;

$resultado = $conexion -> query($consulta);

while ($fila=$resultado -> fetch_array()) {
	$producto[] = array_map('utf8_encode', $fila);
}

echo json_encode($producto);
$resultado -> close();

?>
