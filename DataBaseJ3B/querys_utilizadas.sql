
/*Esta consulta funcionaba antes de relacionar al cuenthabiente con las cuentas ajenas,
 ahora da el resultado de las cuentas de un cuenthabienete que se registraron como ajenas por otros*/
 SELECT c.no_cuenta_bancaria,c.tipo_cuenta,c.saldo,cc.fecha_registro
                FROM CUENTA AS c JOIN CUENTA_DE_CONFIANZA AS cc ON c.no_cuenta_bancaria=cc.numero_cuenta
                WHERE c.dpi_cliente="3333333333333"
                AND c.estado="activa";

/*ESTA CONSULTA ES VALIDA PERO SE DEBERIA VALIDAR DE QUE UN CUNTAHABIENTE NO PUEDA REGISTRAR UNA CUENTA PERSONAL COMO AJENA*/
SELECT  * FROM CUENTA WHERE no_cuenta_bancaria = "4568128734" AND dpi_cliente ="3333333333333";
/*consulta para desplegar las cuentas ajenas de un usuario y de las cuales es propietario digamolo asi, aunque en realidad el cuentahabiente es alguien mas*/
SELECT ch.dpi_cliente,cc.numero_cuenta,cc.fecha_registro
                FROM CUENTA_HABIENTE AS ch JOIN CUENTA_DE_CONFIANZA AS cc ON ch.dpi_cliente=cc.dpi_propietario
                JOIN CUENTA AS c ON c.no_cuenta_bancaria=cc.numero_cuenta
                WHERE ch.dpi_cliente="3333333333333" AND c.estado="activa"; -- falta validar que la cuenta ademas este activa
                
                
/*consultas para desplegar las cuentas personales de un cuenthabiente la segunda da el resultado correcto,
 la primera rectifica las asociaciones que ha tenido una cuenta personal a cuentas de confianza por parte de otro cuentahabientes */
SELECT c.no_cuenta_bancaria,c.tipo_cuenta,c.saldo,cc.fecha_registro
                FROM CUENTA AS c LEFT JOIN CUENTA_DE_CONFIANZA AS cc ON c.no_cuenta_bancaria=cc.numero_cuenta
                 WHERE c.dpi_cliente="3333333333333"  -- WHERE cc.fecha_registro IS (NOT) NULL, puede servir para incluir o no las cuentas de un cuenthabiente que otros an puesto como ajenas
                AND c.estado="activa";

SELECT no_cuenta_bancaria,tipo_cuenta,saldo
                FROM CUENTA WHERE dpi_cliente="3333333333333"
                AND estado="activa";
                
/*procedimiento almacenado para registrar una cuenta de confianza para hacer depositos
no se va necesitar un procedimiento ya que la cuenta sin o si debe existir en este banco es decir debe pertenecer a otro usuario
*/

/*PARA REGISTAR UNA CUENTA AJENA SE DEBE VALIDAR QUE ESTA EXISTA Y QUE ADEMAS ESTE ACTIVA*/
SELECT  * FROM CUENTA WHERE no_cuenta_bancaria = "4568128734";
/*se asocian dos cuentas de usuarios diferntesa un usuario*/
INSERT INTO CUENTA_DE_CONFIANZA VALUES(null,now(),"3731101880","3333333333333");
INSERT INTO CUENTA_DE_CONFIANZA VALUES(null,now(),"6046306966","3333333333333");

/*se asocia una cuenta de un usuario a otros dos usuarios*/
INSERT INTO CUENTA_DE_CONFIANZA VALUES(null,now(),"2544708083","2222222222222");
INSERT INTO CUENTA_DE_CONFIANZA VALUES(null,now(),"2544708083","1111111111111");

/*prueba de funcion de transferencia de cuenta personal a cuenta ajena*/
SELECT transferir_cuenta_ajena("4568128734","6046306966","0");




