USE db_j3bank;

/*Procedimiento almacenado para cancelaciòn de cuentas bancarias*/
DELIMITER $$ 
CREATE PROCEDURE cancelar_cuenta (IN num_cuenta VARCHAR(10), IN motiv VARCHAR(60), IN fech TIMESTAMP)
BEGIN 

		-- hacemos el update de la Cuenta
        UPDATE CUENTA SET estado = "desactivada" WHERE no_cuenta_bancaria=num_cuenta; 
        
        -- insertamos el motivo de la cancelacion de la cuenta
        INSERT INTO CANCELACION_CUENTA VALUES(null,num_cuenta,motiv,fech,"cancelacion_activa");
 
END$$ 
DELIMITER ;

 DROP FUNCTION transferir_cuenta_ajena;

/*funcion para registrar dos movimientos monetarios en una transaccion*/
/*antes debe validarse que la cuenta emisora tenga fondos sino no se puede hacer la transferencia,
tambien se debe validar que el saldo actual de la cuenta sea mayor al monto a transferir
tambien se debe validar que el monto a transferir no sea 0 , esto se puede hacer desde backend
*/

/*funcion para registrar dos movimientos monetarios que se hacen en una transferencia con validacion de saldos*/
DELIMITER $$ 
CREATE FUNCTION transferir_cuenta_ajena(cuenta_origen VARCHAR(10), cuenta_destino VARCHAR(10), monto_mov DOUBLE, motivo VARCHAR(50)) RETURNS VARCHAR(15)

BEGIN 
		-- declarando variables de apoyo para registrar la transaccion
		DECLARE id_mov_origen INT;
        DECLARE id_mov_destino INT;
        DECLARE saldo_origen DOUBLE;
        DECLARE saldo_destino DOUBLE;
        
        -- 1. se verifica que la cuenta origen tenga fondos
		SET saldo_origen =(SELECT saldo FROM CUENTA WHERE no_cuenta_bancaria = cuenta_origen);
        SET saldo_destino =(SELECT saldo FROM CUENTA WHERE no_cuenta_bancaria = cuenta_destino);
        
        IF saldo_origen > 0 THEN
			-- 2. se verifica ahora que sea mayor al monto a transferir
			IF saldo_origen >= monto_mov THEN
				-- se hacen todas las operaciones e insertamos los movimientos
                SET saldo_origen = saldo_origen - monto_mov;
                SET saldo_destino = saldo_destino + monto_mov;
				-- movimiento debito en cuenta origen
				INSERT INTO MOVIMIENTO_MONETARIO (no_cuenta,monto,fecha,tipo,descripcion) VALUES(cuenta_origen,monto_mov,now(),"DEBITO",motivo);
				-- seteamos el id que se acaba de ingresar 
				SET id_mov_origen = LAST_INSERT_ID(); 
				-- hacemos el update de la Cuenta
				UPDATE CUENTA SET saldo = saldo_origen WHERE no_cuenta_bancaria=cuenta_origen; 
        
				-- movimiento abono en cuenta destino
				INSERT INTO MOVIMIENTO_MONETARIO (no_cuenta,monto,fecha,tipo,descripcion) VALUES(cuenta_destino,monto_mov,now(),"ABONO",motivo);
				-- seteamos el id que se acaba de ingresar 
				SET id_mov_destino = LAST_INSERT_ID(); 
				-- hacemos el update de la Cuenta
				UPDATE CUENTA SET saldo = saldo_destino WHERE no_cuenta_bancaria=cuenta_destino; 
                
                -- por ultimo registramos la transaccion realizada
                INSERT INTO TRANSACCION_CUENTA VALUES (null, cuenta_origen, monto_mov, cuenta_destino, now(), id_mov_origen, id_mov_destino);
                
                RETURN "TERMINADO";
			
			ELSE
				SIGNAL SQLSTATE '45000'
				SET MESSAGE_TEXT = ' El Saldo de su cuenta personal no alcanza para transferir el monto ingresado, porfavor realice un nuevo deposito. ';
				RETURN NULL;
			END IF;
        ELSE
			SIGNAL SQLSTATE '45000'
			SET MESSAGE_TEXT = ' No se pudo hacer la transferencia debido a que la cuenta personal seleccionada no tiene fondos, porfavor realice un nuevo deposito. ';
			RETURN NULL;
        END IF;
 
END$$ 
DELIMITER ;

/*Procedimiento almacenado para registrar dos movimientos monetarios que se hacen en una transferencia sin validar saldos*/
DELIMITER $$ 
CREATE PROCEDURE transferir_cuenta_ajena2(IN nuevo_monto_origen DOUBLE, IN nuevo_monto_destino DOUBLE, IN cuenta_origen VARCHAR(10), IN cuenta_destino VARCHAR(10), IN monto_mov DOUBLE, IN motivo VARCHAR(50))

BEGIN 
		-- declarando variables de apoyo para registrar la transaccion
		DECLARE id_mov_origen INT;
        DECLARE id_mov_destino INT;
        DECLARE saldo_origen DOUBLE;
        
        -- 1. se verifica que la cuenta origen tenga fondos
							SET total =(SELECT Cupo_Limite FROM ACTIVIDAD WHERE ID_Actividad=new.A_ID_Actividad);
        
        -- insertamos los movimientos
        -- movimiento debito en cuenta origen
        INSERT INTO MOVIMIENTO_MONETARIO (no_cuenta,monto,fecha,tipo,descripcion) VALUES(cuenta_origen,monto_mov,now(),"DEBITO",motivo);
        -- seteamos el id que se acaba de ingresar 
        SET id_mov_origen = LAST_INSERT_ID(); 
        -- hacemos el update de la Cuenta
        UPDATE CUENTA SET saldo = nuevo_monto_origen WHERE no_cuenta_bancaria=cuenta_origen; 
        
        -- movimiento abono en cuenta destino
        INSERT INTO MOVIMIENTO_MONETARIO (no_cuenta,monto,fecha,tipo,descripcion) VALUES(cuenta_destino,monto_mov,now(),"ABONO",motivo);
        -- seteamos el id que se acaba de ingresar 
        SET id_mov_destino = LAST_INSERT_ID(); 
        -- hacemos el update de la Cuenta
        UPDATE CUENTA SET saldo = nuevo_monto_destino WHERE no_cuenta_bancaria=cuenta_destino; 
 
END$$ 
DELIMITER ;