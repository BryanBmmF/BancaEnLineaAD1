package dev.com.j3b.modelos;

public class ServidorSQL {

    public static final String ipServer = "192.168.0.200";

    //utilizado por todos
    public static final String SERVIDORSQL_CONRETORNO = "http://"+ipServer+"/j3b/servicios/consultaConRetorno.php?consultaSQL=";
    public static final String SERVIDORSQL_SINRETORNO = "http://"+ipServer+"/j3b/servicios/consultaSinRetorno.php";
    public static final String SERVIDORSQL_TRANSACCION = "http://"+ipServer+"/j3b/servicios/transaccion.php?consultaSQL=";
    public static final String SERVIDORSQL_INSERCION_CON_RETORNO= "http://"+ipServer+"/j3b/servicios/ingresoConRetorno.php?consultaSQL=";
    public static final String SERVIDORSQL_INSERCION_TRANSFERENCIA_TERCEROS= "http://"+ipServer+"/j3b/servicios/ingresoTransferenciaTerceros.php?consultaSQL=";
    public static final String SERVIDORSQL_NOTIFICACION_TRANSFERENCIA_TERCEROS= "http://"+ipServer+"/j3b/servicios/switfMailer/envioNotificacionTransferenciaTerceros.php";
    public static final String SERVIDORSQL_NOTIFICACION_CODIGO_TRANSFERENCIA= "http://"+ipServer+"/j3b/servicios/switfMailer/envioCodigoTransferencia.php";
    public static final String SERVIDORSQL_NOTIFICACION_REGISTRO_CUENTA_TERCEROS= "http://"+ipServer+"/j3b/servicios/switfMailer/envioNotificacionRegistroTerceros.php";
    public static final String SERVIDORSQL_NOTIFICACION_CODIGO_REGISTRO_TERCEROS= "http://"+ipServer+"/j3b/servicios/switfMailer/envioCodigoRegistroCuenta.php";


    //utilizado por bryan con ip propia
    //public static final String SERVIDORSQL_SINRETORNO = "http://192.168.20.2/j3b/servicios/consultaSinRetorno.php";
    //public static final String SERVIDORSQL_CONRETORNO = "http://192.168.20.2/j3b/servicios/consultaConRetorno.php?consultaSQL=";
    //public static final String SERVIDORSQL_INSERCION_TRANSFERENCIA_TERCEROS= "http://192.168.20.2/j3b/servicios/ingresoTransferenciaTerceros.php?consultaSQL=";
    //public static final String SERVIDORSQL_NOTIFICACION_TRANSFERENCIA_TERCEROS= "http://192.168.20.2/j3b/servicios/switfMailer/envioNotificacionTransferenciaTerceros.php";
    //public static final String SERVIDORSQL_NOTIFICACION_CODIGO_TRANSFERENCIA= "http://192.168.20.2/j3b/servicios/switfMailer/envioCodigoTransferencia.php";
    //public static final String SERVIDORSQL_NOTIFICACION_REGISTRO_CUENTA_TERCEROS= "http://192.168.20.2/j3b/servicios/switfMailer/envioNotificacionRegistroTerceros.php";
    //public static final String SERVIDORSQL_NOTIFICACION_CODIGO_REGISTRO_TERCEROS= "http://192.168.20.2/j3b/servicios/switfMailer/envioCodigoRegistroCuenta.php";



    //localhost -> j3b -> servicios -> Archivos PHP

    // IP: 192.168.0.200

    // IP Router 192.168.0.1

}
