<?php

require_once './vendor/autoload.php';

/* Recibiendo parametros con Get */
$Usuario = $_POST["Usuario"];
$CuentaPersonal = $_POST["CuentaPersonal"];
$CuentaDestino = $_POST["CuentaDestino"];
$Monto = $_POST["Monto"];
$Fecha = $_POST["Fecha"]; # date("d-m-Y H:i:s"); por alguna razon esta fecha y la que envia java estan adelantadas 6 horas 
$CorreoEmisor = $_POST["EmailEmisor"];
$CorreoReceptor = $_POST["EmailReceptor"];


//envio de correo al usuario emisor
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
   $message->addTo($CorreoEmisor,'texto ');
   // Add "CC" address [Use setCc method for multiple recipients, argument should be array]
   $message->addCc($CorreoEmisor, 'texto');
   // Add "BCC" address [Use setBcc method for multiple recipients, argument should be array]
   $message->addBcc($CorreoEmisor, 'texto');

   // Set the plain-text "Body"
   $message->setBody("This is the plain text body of the message.\nGracias por tu Preferencia,\nAdmin");
   // Set a "Body"
   $message->addPart('Se a registrado una nueva transferencia a una cuenta de confianza, los detalles son los siguientes:<br>
        <br>  Usuario: '.$Usuario.'
        <br>  Cuenta Personal: '.$CuentaPersonal.'
        <br>  Cuenta Destino: '.$CuentaDestino.'
        <br>  Monto Transferido: '.$Monto.'
        <br>  Fecha: '.$Fecha.'
        <br>Gracias por tu Preferencia,<br>Admin', 'text/html');

   // Send the message
   $result = $mailer->send($message);
} catch (Exception $e) {
 echo $e->getMessage();
}


//envio de correo al usuario receptor
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
    $message->addTo($CorreoReceptor,'texto ');
    // Add "CC" address [Use setCc method for multiple recipients, argument should be array]
    $message->addCc($CorreoReceptor, 'texto');
    // Add "BCC" address [Use setBcc method for multiple recipients, argument should be array]
    $message->addBcc($CorreoReceptor, 'texto');
 
    // Set the plain-text "Body"
    $message->setBody("This is the plain text body of the message.\nGracias por tu Preferencia,\nAdmin");
    // Set a "Body"
    $message->addPart('Se te ha acreditado una nueva transferencia de efectivo proveniente de la siguiente cuenta:<br>
         <br>  Cuenta Acreditadora: '.$CuentaPersonal.'
         <br>  Usuario que emite: '.$Usuario.'
         <br>  Cuenta Receptora: '.$CuentaDestino.'
         <br>  Monto Transferido: '.$Monto.'
         <br>  Fecha: '.$Fecha.'
         <br>Gracias por tu Preferencia,<br>Admin', 'text/html');
 
    // Send the message
    $result = $mailer->send($message);
 } catch (Exception $e) {
  echo $e->getMessage();
 }

?>