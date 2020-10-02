<?php
$hostname='localhost';
$database='db_j3bank';
$username='ad1sysdba';
$password='Ad1sysdba$';

$conexion=new mysqli($hostname,$username,$password,$database);
if($conexion->connect_errno){
    echo "El servidor de Base de Datos está experimentado problemas";
}
?>
