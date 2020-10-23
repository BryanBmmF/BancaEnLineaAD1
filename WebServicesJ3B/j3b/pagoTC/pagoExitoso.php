<?php
include("conexion.php");
require_once '../servicios/switfMailer/vendor/autoload.php';
$dpi = $_GET['dpiCliente'];
$monto = $_GET['monto'];
$tarjeta = $_GET['tarjeta'];

echo "Tu pago ha sido realizado";

$mysqlQuery = "SELECT * FROM cuenta_habiente WHERE dpi_cliente='$dpi'";
$result = $conexion->query($mysqlQuery);
$num_rows = mysqli_num_rows($result);

$consultaDatosCliente = "SELECT * FROM cuenta_habiente WHERE dpi_cliente='$dpi'";
$resultado = mysqli_query($conexion, $consultaDatosCliente);

if (!$resultado) {
    echo "error al obtener los datos para la notificacion por correo";
    exit;
}

$row = mysqli_fetch_array($resultado);
$Correo = $row['email'];



//envio de correo de notificacion de la compra
try {
    // Create the SMTP Transport
    $transport = (new Swift_SmtpTransport('smtp.gmail.com',465,'ssl'))
        ->setUsername('j3b.bank@gmail.com')//Correo propio
        ->setPassword('J3Bbank@2020');//Contrasena de correo Propio
    // Create the Mailer using your created Transport
    $mailer = new Swift_Mailer($transport);
    // Create a message
    $message = new Swift_Message();
    // Set a "subject"
    $message->setSubject('J3Bank-Notifications.');
    // Set the "From address"
    $message->setFrom(['j3b.bank@gmail.com' => 'J3Bank-Admin']);
    // Set the "To address" [Use setTo method for multiple recipients, argument should be array]
    $message->addTo($Correo,'texto ');
    // Add "CC" address [Use setCc method for multiple recipients, argument should be array]
    $message->addCc($Correo, 'texto');
    // Add "BCC" address [Use setBcc method for multiple recipients, argument should be array]
    $message->addBcc($Correo, 'texto');
 
    // Set the plain-text "Body"
    $message->setBody("This is the plain text body of the message.\nGracias por tu Preferencia,\nAdmin");
    // Set a "Body"
    $message->addPart('Se ha registrado un nuevo cargo a tu tarjeta de credito:<br>
         <br>  Tarjeta utilizada: '.$tarjeta.'
         <br>  Descripcion del cargo: Pago de compra en linea
         <br>  Monto cargado: Q.'.$monto.'<br>
         <br>Gracias por tu Preferencia,<br>Admin', 'text/html');
 
    // Send the message
    $result = $mailer->send($message);
 } catch (Exception $e) {
  echo $e->getMessage();
 }


 ?>
