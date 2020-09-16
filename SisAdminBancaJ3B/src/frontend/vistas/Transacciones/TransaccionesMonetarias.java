/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frontend.vistas.Transacciones;

import backend.controladores.ControladorTransaccionesMonetarias;
import backend.pojos.Cuenta;
import backend.pojos.CuentaTransaccionMonetaria;
import backend.pojos.MovimientoMonetario;
import java.sql.Date;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;

/**
 *
 * @author jpmazate
 */
public class TransaccionesMonetarias extends javax.swing.JDialog {

    private ControladorTransaccionesMonetarias controladorTransacciones;
    private final String DEBITO = "DEBITO";
    private final String ABONO = "ABONO";
    private final String RETIRO_EN_AGENCIA = "Retiro en Agencia";
    private final String DEPOSITO_EN_AGENCIA = "Deposito en Agencia";
    private SimpleDateFormat fechaTimestamp;
    private CuentaTransaccionMonetaria cuentaActual;

    public TransaccionesMonetarias(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.getRootPane().setDefaultButton(this.botonBuscar);
        this.panelInformacion.setVisible(false);
        this.controladorTransacciones = new ControladorTransaccionesMonetarias();
        fechaTimestamp = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        filtroNoCuenta = new javax.swing.JFormattedTextField();
        botonBuscar = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        panelInformacion = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        textoNombre = new javax.swing.JTextField();
        textoSaldoActual = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        textoTipoCuenta = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        textoNoCuenta = new javax.swing.JTextField();
        botonRetiroDinero = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        textoMonto = new javax.swing.JFormattedTextField();
        botonDepositoDinero = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        textoEstadoActual = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Ubuntu", 1, 18)); // NOI18N
        jLabel1.setText("Transacciones Monetarias");

        jLabel2.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        jLabel2.setText("No Cuenta:");

        try {
            filtroNoCuenta.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##########")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        botonBuscar.setText("Buscar");
        botonBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonBuscarActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Ubuntu", 0, 12)); // NOI18N
        jLabel3.setText("*10 caracteres de numeros");

        panelInformacion.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        jLabel4.setText("Cuenta a nombre de:");
        panelInformacion.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 200, 215, 49));

        textoNombre.setEditable(false);
        panelInformacion.add(textoNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 200, 201, 48));

        textoSaldoActual.setEditable(false);
        panelInformacion.add(textoSaldoActual, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 270, 201, 48));

        jLabel8.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        jLabel8.setText("Tipo Cuenta:");
        panelInformacion.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, 215, 48));

        textoTipoCuenta.setEditable(false);
        panelInformacion.add(textoTipoCuenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 130, 201, 48));
        panelInformacion.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 12, 1027, 22));

        jLabel9.setFont(new java.awt.Font("Ubuntu", 2, 18)); // NOI18N
        jLabel9.setText("Informacion de la Cuenta");
        panelInformacion.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(475, 40, -1, -1));

        jLabel10.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        jLabel10.setText("No Cuenta:");
        panelInformacion.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 67, 215, 48));

        textoNoCuenta.setEditable(false);
        panelInformacion.add(textoNoCuenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(239, 67, 201, 48));

        botonRetiroDinero.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/retiroDinero_opt.png"))); // NOI18N
        botonRetiroDinero.setText("Retiro Dinero");
        botonRetiroDinero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonRetiroDineroActionPerformed(evt);
            }
        });
        panelInformacion.add(botonRetiroDinero, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 240, 320, -1));

        jLabel11.setText("Estado Actual:");
        panelInformacion.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 330, 215, 42));

        jLabel12.setText("Monto Transaccion:");
        panelInformacion.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 390, 215, 49));

        textoMonto.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("###0.###"))));
        panelInformacion.add(textoMonto, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 390, 204, 49));

        botonDepositoDinero.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/depositoDinero_opt.jpg"))); // NOI18N
        botonDepositoDinero.setText("Deposito Dinero");
        botonDepositoDinero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonDepositoDineroActionPerformed(evt);
            }
        });
        panelInformacion.add(botonDepositoDinero, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 70, 320, -1));

        jLabel13.setText("Saldo Actual:");
        panelInformacion.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 270, 215, 42));

        textoEstadoActual.setEditable(false);
        panelInformacion.add(textoEstadoActual, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 330, 201, 48));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(filtroNoCuenta))
                .addGap(18, 18, 18)
                .addComponent(botonBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(44, 44, 44))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelInformacion, javax.swing.GroupLayout.DEFAULT_SIZE, 1051, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(415, 415, 415))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(filtroNoCuenta, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botonBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(panelInformacion, javax.swing.GroupLayout.PREFERRED_SIZE, 508, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void botonBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonBuscarActionPerformed
        reiniciarDatos();
        cambiarPanelTransaccion(false);
        buscarCuenta();

    }//GEN-LAST:event_botonBuscarActionPerformed

    private void botonDepositoDineroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonDepositoDineroActionPerformed
        depositarDinero();
    }//GEN-LAST:event_botonDepositoDineroActionPerformed

    private void botonRetiroDineroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonRetiroDineroActionPerformed
        retirarDinero();
    }//GEN-LAST:event_botonRetiroDineroActionPerformed

    public void retirarDinero() {
        String monto = this.textoMonto.getText();
        String saldoActual = Double.toString(this.cuentaActual.getSaldo());
        if (!monto.equals("")) {
            double dinero = Double.parseDouble(monto);
            double saldo = Double.parseDouble(saldoActual);
            if (dinero <= saldo) {
                if (dinero > 0) {
                    MovimientoMonetario datos = new MovimientoMonetario(this.textoNoCuenta.getText(), Double.parseDouble(this.textoMonto.getText()), new java.util.Date(), this.DEBITO, this.RETIRO_EN_AGENCIA);
                    int respuesta = JOptionPane.showConfirmDialog(null, "Se realizara el retiro de dinero con los siguientes datos:\n"
                            + "No Cuenta: " + datos.getNoCuenta() + "\n"
                            + "Monto: " + datos.getMonto() + "\n"
                            + "Tipo Transaccion: " + datos.getTipo() + " \n\n¿Estas seguro de realizar la transaccion?");
                    if (respuesta == JOptionPane.YES_OPTION) {
                        MovimientoMonetario resultado = controladorTransacciones.realizarMovimientoMonetario(datos);
                        if (resultado != null) {
                            JOptionPane.showMessageDialog(null, "La transaccion se realizo correctamente, datos:\n"
                                    + "Id Movimiento Bancario: " + resultado.getId() + "\n"
                                    + "No Cuenta: " + resultado.getNoCuenta() + "\n"
                                    + "Monto: " + resultado.getMonto() + "\n"
                                    + "Fecha: " + fechaTimestamp.format(resultado.getFecha()) + "\n"
                                    + "Tipo Transaccion: " + resultado.getTipo() + "\n"
                                    + "", "TRANSACCION REALIZADA CORRECTAMENTE", JOptionPane.INFORMATION_MESSAGE);
                            controladorTransacciones.notificarCorreoCuentaHabiente(this.cuentaActual, resultado);
                            reiniciarDatos();
                            cambiarPanelTransaccion(false);

                        } else {
                            JOptionPane.showMessageDialog(null, "Ocurrio un error al realizar el movimiento monetario, por favor prueba volver a realizarlo", "ERROR EN LA TRANSACCION", JOptionPane.CANCEL_OPTION);
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "No se pueden realizar retiros de valores negativos, por favor introduzca un monto positivo", "ERROR DINERO NEGATIVO", JOptionPane.WARNING_MESSAGE);
                }

            } else {
                JOptionPane.showMessageDialog(null, "No se pueden realizar un retiro mayor al saldo actual, por favor introduzca un monto menor.\n\nSaldo Actual: " + saldo + "\nMonto que quiere retirar: " + monto + "", "ERROR CON SALDO", JOptionPane.WARNING_MESSAGE);
            }

        } else {
            JOptionPane.showMessageDialog(null, "Especifique el monto por favor", "ERROR DE MONTO", JOptionPane.WARNING_MESSAGE);
        }

    }

    public void depositarDinero() {
        String monto = this.textoMonto.getText();
        if (!monto.equals("")) {
            double dinero = Double.parseDouble(monto);
            if (dinero <= 50000) {
                if (dinero > 0) {
                    MovimientoMonetario datos = new MovimientoMonetario(this.textoNoCuenta.getText(), Double.parseDouble(this.textoMonto.getText()), new java.util.Date(), this.ABONO,this.DEPOSITO_EN_AGENCIA);
                    int respuesta = JOptionPane.showConfirmDialog(null, "Se realizara el deposito de dinero con los siguientes datos:\n"
                            + "No Cuenta: " + datos.getNoCuenta() + "\n"
                            + "Monto: " + datos.getMonto() + "\n"
                            + "Tipo Transaccion: " + datos.getTipo() + " \n\n¿Estas seguro de realizar la transaccion?");
                    if (respuesta == JOptionPane.YES_OPTION) {
                        MovimientoMonetario resultado = controladorTransacciones.realizarMovimientoMonetario(datos);
                        if (resultado != null) {
                            JOptionPane.showMessageDialog(null, "La transaccion se realizo correctamente, datos:\n"
                                    + "Id Movimiento Bancario: " + resultado.getId() + "\n"
                                    + "No Cuenta: " + resultado.getNoCuenta() + "\n"
                                    + "Monto: " + resultado.getMonto() + "\n"
                                    + "Fecha: " + fechaTimestamp.format(resultado.getFecha()) + "\n"
                                    + "Tipo Transaccion: " + resultado.getTipo() + "\n"
                                    + "", "TRANSACCION REALIZADA CORRECTAMENTE", JOptionPane.INFORMATION_MESSAGE);
                            controladorTransacciones.notificarCorreoCuentaHabiente(this.cuentaActual, resultado);
                            reiniciarDatos();
                            cambiarPanelTransaccion(false);

                        } else {
                            JOptionPane.showMessageDialog(null, "Ocurrio un error al realizar el movimiento monetario, por favor prueba volver a realizarlo", "ERROR EN LA TRANSACCION", JOptionPane.CANCEL_OPTION);
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "No se pueden realizar depositos de valores negativos, por favor introduzca un valor positivo", "ERROR DINERO NEGATIVO", JOptionPane.WARNING_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "No se pueden realizar depositos de mas de Q50,000, por favor introduzca un monto menor", "ERROR MAXIMO DINERO", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Especifique el monto por favor", "ERROR DE MONTO", JOptionPane.WARNING_MESSAGE);
        }
    }

    public void buscarCuenta() {
        String noCuenta = this.filtroNoCuenta.getText();
        if (!noCuenta.equals("          ")) {
            CuentaTransaccionMonetaria cuenta = new CuentaTransaccionMonetaria(noCuenta);
            CuentaTransaccionMonetaria aux = controladorTransacciones.validarCuenta(cuenta);
            if (aux != null) {
                if (aux.getEstado().equals("activa")) {
                    this.textoNoCuenta.setText(aux.getNumCuenta());
                    this.textoNombre.setText(aux.getNombre());
                    this.textoSaldoActual.setText(Double.toString(aux.getSaldo()));
                    this.textoSaldoActual.setText("?????????");
                    this.textoTipoCuenta.setText(aux.getTipo());
                    this.textoEstadoActual.setText(aux.getEstado());
                    this.cuentaActual = aux;
                    cambiarPanelTransaccion(true);
                } else if (aux.getEstado().equals("desactivada")) {
                    this.textoNoCuenta.setText(aux.getNumCuenta());
                    this.textoNombre.setText(aux.getNombre());
                    this.textoSaldoActual.setText(Double.toString(aux.getSaldo()));
                    this.textoSaldoActual.setText("?????????");
                    this.textoTipoCuenta.setText(aux.getTipo());
                    this.textoEstadoActual.setText(aux.getEstado());
                    this.cuentaActual = aux;
                    cambiarPanelTransaccion(true);
                    cambiarEstadoBotonesTransacciones(false);
                    JOptionPane.showMessageDialog(null, "La cuenta con numero: " + aux.getNumCuenta() + " esta desactivada, no se le pueden realizar operaciones", "Error Cuenta", JOptionPane.WARNING_MESSAGE);

                } else {
                    this.textoNoCuenta.setText(aux.getNumCuenta());
                    this.textoNombre.setText(aux.getNombre());
                    this.textoSaldoActual.setText(Double.toString(aux.getSaldo()));
                    this.textoSaldoActual.setText("?????????");
                    this.textoTipoCuenta.setText(aux.getTipo());
                    this.textoEstadoActual.setText(aux.getEstado());
                    this.cuentaActual = aux;
                    cambiarEstadoBotonesTransacciones(false);
                    JOptionPane.showMessageDialog(null, "La cuenta con numero: " + aux.getNumCuenta() + " no tiene un estado valido de cuenta", "Estado no valido", JOptionPane.CANCEL_OPTION);
                }
            } else {
                this.cuentaActual = null;
                cambiarPanelTransaccion(false);
                JOptionPane.showMessageDialog(null, "No existe ninguna cuenta asociada con el numero de cuenta: " + noCuenta);

            }
        } else {
            this.cuentaActual = null;
            cambiarPanelTransaccion(false);
            JOptionPane.showMessageDialog(null, "No se selecciono ninguna cuenta para buscar");
        }
    }

    public void cambiarPanelTransaccion(boolean valor) {
        this.panelInformacion.setVisible(valor);
    }

    public void reiniciarDatos() {
        this.textoNoCuenta.setText("");
        this.textoNombre.setText("");
        this.textoSaldoActual.setText("");
        this.textoTipoCuenta.setText("");
        this.textoMonto.setText("");
        this.textoEstadoActual.setText("");
        this.botonDepositoDinero.setEnabled(true);
        this.botonRetiroDinero.setEnabled(true);
        this.cuentaActual = null;
    }

    public void cambiarEstadoBotonesTransacciones(boolean valor) {
        this.botonDepositoDinero.setEnabled(valor);
        this.botonRetiroDinero.setEnabled(valor);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botonBuscar;
    private javax.swing.JButton botonDepositoDinero;
    private javax.swing.JButton botonRetiroDinero;
    private javax.swing.JFormattedTextField filtroNoCuenta;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JPanel panelInformacion;
    private javax.swing.JTextField textoEstadoActual;
    private javax.swing.JFormattedTextField textoMonto;
    private javax.swing.JTextField textoNoCuenta;
    private javax.swing.JTextField textoNombre;
    private javax.swing.JTextField textoSaldoActual;
    private javax.swing.JTextField textoTipoCuenta;
    // End of variables declaration//GEN-END:variables
}
