<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8" />
  <title>Pago Online</title>
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />

  <link rel="stylesheet" type="text/css" href="bootstrapPagoTarjeta/css/bootstrap.min.css" />
  <link rel="stylesheet" type="text/css" href="font-PagoTarjeta/css/font-awesome.min.css" />

  <script type="text/javascript" src="jsPagoTarjeta/jquery-1.10.2.min.js"></script>
  <script type="text/javascript" src="bootstrapPagoTarjeta/js/bootstrap.min.js"></script>
</head>
<body>

  <div class="container">

    <div class="page-header">
      <h1>El total a pagar es de: Q <?php
      $d=mt_rand(100, 2000);
      echo $d ;
      ?></h1>
    </div>

    <!-- Credit Card Payment Form - START -->

    <div class="container">
      <div class="row">
        <div class="col-xs-12 col-md-4 col-md-offset-4">
          <div class="panel panel-default">
            <div class="panel-heading">
              <div class="row">
                <h3 class="text-center">Detalles del medio de pago</h3>
                <img class="img-responsive cc-img" src="http://prepbootstrap.com/Content/images/shared/misc/creditcardicons.png">
              </div>
            </div>
            <div class="panel-body">
              <form role="form" method="post" action="verificacionPago.php">
                <div class="row">
                  <div class="col-xs-12">
                    <div class="form-group">
                      <label>Número de Tarjeta</label>
                      <div class="input-group">
                        <input type="tel" class="form-control" autocomplete="off" name="numerotarjeta" placeholder="Ingresa la numeración de tu tarjeta" />
                        <span class="input-group-addon"><span class="fa fa-credit-card"></span></span>
                      </div>
                    </div>
                  </div>
                </div>
                <div class="row">
                  <div class="col-xs-7 col-md-7">
                    <div class="form-group">
                      <label><span class="hidden-xs">Fecha de </span><span class="visible-xs-inline">Fecha </span>Vencimiento</label>
                      <select name="month" id="month" class="form-control">
                        <option value="" disabled selected>Mes</option>
                        <option value = "1">01</option>
                        <option value = "2">02</option>
                        <option value = "3">03</option>
                        <option value = "4">04</option>
                        <option value = "5">05</option>
                        <option value = "6">06</option>
                        <option value = "7">07</option>
                        <option value = "8">08</option>
                        <option value = "9">09</option>
                        <option value = "10">10</option>
                        <option value = "11">11</option>
                        <option value = "12">12</option>
                      </select>
                      <br>
                      <select name="year" id="month" class="form-control">
                        <option value="" disabled selected>Año</option>
                        <option value = "2020">2020</option>
                        <option value = "2021">2021</option>
                        <option value = "2022">2022</option>
                        <option value = "2023">2023</option>
                        <option value = "2024">2024</option>
                        <option value = "2025">2025</option>
                      </select>
                    </div>
                  </div>
                  <div class="col-xs-5 col-md-5 pull-right">
                    <div class="form-group">
                      <label>Código CVC</label>
                      <input type="tel" class="form-control" autocomplete="off" name="codigocvc" placeholder="CVC" />
                    </div>
                  </div>
                </div>
                <div class="row">
                  <div class="col-xs-12">
                    <div class="form-group">
                      <label>Nombre del Propietario</label>
                      <input type="text" class="form-control"autocomplete="off"  name="nombre" placeholder="Nombre completo" />
                    </div>
                  </div>
                </div>
                <input type="hidden" name="montoPago" value="<?php echo $d ;?>">
                <div class="panel-footer">
                  <div class="row">
                    <div class="col-xs-12">
                      <button class="btn btn-warning btn-lg btn-block">Process payment</button>
                    </div>
                  </div>
                </div>
              </form>
            </div>
          </div>
        </div>
      </div>
    </div>

    <style>
    .cc-img {
      margin: 0 auto;
    }
  </style>
  <!-- Credit Card Payment Form - END -->

</div>

</body>
</html>
