<?php

include 'conexion.php';

//$consultaSQL=$_GET['consultaSQL'];
$consultaSQL = $_POST['consultaSQL'];

mysqli_query($conexion, $consultaSQL) or die (mysqli_error());
mysqli_close($conexion);

/*
$consulta = $consultaSQL;

$resultado = $conexion -> query($consulta);
$resultado -> close();
*/
 ?>
