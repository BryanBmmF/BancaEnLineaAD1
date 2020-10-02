<?php

require_once './vendor/autoload.php';

/* Recibiendo parametros con Get */
$Correo = $_POST["Email"];
$descripcion = $_POST["descripcion"];

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
   $message->addPart('Lamentamos informarte que tu solicitud de tarjeta de credito ha sido rechazada.
        <br> Comentario Administrador: '.$descripcion.'
        <br>Gracias por tu Preferencia,<br>Admin', 'text/html');

   // Send the message
   $result = $mailer->send($message);
} catch (Exception $e) {
 echo $e->getMessage();
}

?>
