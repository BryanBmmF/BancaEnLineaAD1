<?php

require_once './vendor/autoload.php';

/* Recibiendo parametros con Get */
$Correo = $_POST["Email"];
$idSolicitud = $_POST["idSolicitud"];
$estado = $_POST["estado"];
$salarioMensual = $_POST["salarioMensual"];
$montoSolicitud = $_POST["montoSolicitud"];
$tipo = $_POST["tipo"];
$descripcion = $_POST["descripcion"];

$meses = $_POST["meses"];
$deuda = $_POST["deuda"];
$fechaVencimiento = $_POST["fechaVencimiento"];
$idPrestamo = $_POST["idPrestamo"];
$montoTotal = $_POST["montoTotal"];
$tasaInteres = $_POST["tasaInteres"];
$tipoPrestamo = $_POST["tipoPrestamo"];


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
   $message->addPart('Se ha aprobado tu solicitud de Prestamo.Tus datos de la solicitud son los siguientes:<br>
        <br> Id Solicitud Prestamo: '.$idSolicitud.'
        <br> Salario Mensual: '.$salarioMensual.'
        <br> Monto Solicitud: '.$montoSolicitud.'
        <br> Tipo Prestamo: '.$tipo.'
        <br>  Comentario del Administrador: '.$descripcion.'<br>
        <br><br> Tus Datos del prestamo son los siguientes: <br>
        <br> Id Prestamo: '.$idPrestamo.'
        <br> Meses del prestamo: '.$meses.'
        <br> Tasa Interes: '.$tasaInteres.'
        <br> Monto Total: '.$montoTotal.'
        <br> Deuda Pendiente: '.$deuda.'
        <br> Fecha Vencimiento: '.$fechaVencimiento.'
        <br> Tipo de Prestamo: '.$tipoPrestamo.'
        <br>
        <br>Gracias por tu Preferencia,<br>Admin', 'text/html');

   // Send the message
   $result = $mailer->send($message);
} catch (Exception $e) {
 echo $e->getMessage();
}

?>
