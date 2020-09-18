<?php
include('conexion.php');

//se recibe como parametro la query de la transferencia
$consultaSQL=$_GET['consultaSQL'];

//$consulta = "SELECT transferir_cuenta_ajena('4568128734','6046306966','100','Transferencia a cuenta de juanito 2');";
$consulta = $consultaSQL;

$resultado = $conexion -> query($consulta);

if (!$resultado) {
    # si ocurrio algun error se debe enviar en formato json y se muestra en la app
    // Declare an array  
    $value = array( 
        "respuesta"=>"invalida", 
        "error"=>$conexion->error); 
    //exit;
    $producto[] = array_map('utf8_encode', $value);
} else {
    # sin errores
    while ($fila=$resultado -> fetch_array()) {
         // Declare an array  
        $value = array( 
        "respuesta"=>"valida", 
        "solicitud"=>$fila[0]); 
        $producto[] = array_map('utf8_encode', $value);  
    }

}

echo json_encode($producto);
//$resultado -> close();



?>
