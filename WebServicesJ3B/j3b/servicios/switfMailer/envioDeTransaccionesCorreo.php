<?php

require_once './vendor/autoload.php';

/* Recibiendo parametros con Get */
//$Usuario = $_GET["Usuario"];
//$Pass = $_GET["Pass"];
//$FechaCaducidad = $_GET["FechaCaducidad"];
//$NumCuenta = $_GET["NumCuenta"];
//$Tipo = $_GET["Tipo"];
//$Saldo = $_GET["Saldo"];


$idMovimiento= $_GET["idMovimiento"];
$noCuenta= $_GET["noCuenta"];
$monto= $_GET["monto"];
$fecha= $_GET["fecha"];
$tipo= $_GET["tipo"];
$tipoCuenta= $_GET["tipoCuenta"];

$Correo = $_GET["Email"];

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
   $message->addPart('Se ha realizado una transaccion sobre una cuenta:<br>
        <br>  Id Movimiento Monetario: '.$idMovimiento.'
        <br>  Tipo de Cuenta: '.$tipoCuenta.'
        <br>  Numero de Cuenta: '.$noCuenta.'
        <br>  Monto: '.$monto.'
        <br>  Fecha de Transaccion: '.$fecha.'
        <br>  Tipo de Transaccion: '.$tipo.'
        
        <br><br>  Si no has sido tu por favor dirigete a tu agencia mas cercana para informar el problema '.'
        <br>Gracias por tu Preferencia,<br>Admin J3Bank', 'text/html');

   // Send the message
   $result = $mailer->send($message);
} catch (Exception $e) {
 echo $e->getMessage();
}

?>
