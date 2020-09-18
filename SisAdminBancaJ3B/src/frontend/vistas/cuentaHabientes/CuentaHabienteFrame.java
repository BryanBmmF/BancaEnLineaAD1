/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frontend.vistas.cuentaHabientes;

import backend.controladores.ControladorCuenta;
import backend.controladores.ControladorCuentaHabiente;
import backend.controladores.ControladorPeticionesHttp;
import backend.controladores.ControladorTransaccionesMonetarias;
import backend.controladores.ControladorUsuarioCliente;
import backend.enums.EstadoCuenta;
import backend.enums.PeriodoInteres;
import backend.enums.TipoCuenta;
import backend.pojos.Cuenta;
import backend.pojos.CuentaHabiente;
import backend.pojos.UsuarioCliente;
import java.sql.Date;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;
import org.jdesktop.observablecollections.ObservableCollections;
import org.jdesktop.observablecollections.ObservableList;

/**
 *
 * @author jesfrin
 */
public class CuentaHabienteFrame extends javax.swing.JFrame {

    private ControladorCuentaHabiente controladorCuentaHabiente;
    private ControladorCuenta controladorCuenta;
    private CuentaHabiente cuentaHabiente = null;
    public List<CuentaHabiente> listaCuentaHabientes = null;
    public ObservableList<CuentaHabiente> listaObservableCuentaHabientes = null;
    private ControladorUsuarioCliente controladorUsuarioCLiente;
    private ControladorTransaccionesMonetarias controladorTransacciones;

    /**
     * Creates new form CuentaHabienteFrame
     */
    public CuentaHabienteFrame() {
        controladorCuentaHabiente = new ControladorCuentaHabiente();
        this.controladorCuenta = new ControladorCuenta();
        this.controladorTransacciones = new ControladorTransaccionesMonetarias();
        this.listaCuentaHabientes = new LinkedList<>();
        this.listaObservableCuentaHabientes = ObservableCollections.observableList(listaCuentaHabientes);
        actualizarLista(controladorCuentaHabiente.busquedaDeCunetaHabientes());
        controladorUsuarioCLiente = new ControladorUsuarioCliente();
        initComponents();
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        nombresjTextField2 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        apellidosjTextField3 = new javax.swing.JTextField();
        fechajDateChooser1 = new com.toedter.calendar.JDateChooser();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        direccionjTextField4 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        emailjTextField7 = new javax.swing.JTextField();
        crearCuentaHabientejButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        logUusuariosjTextArea = new javax.swing.JTextArea();
        jLabel10 = new javax.swing.JLabel();
        comboTipoCuenta = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        txtDepositoInicial = new javax.swing.JTextField();
        dpijTextField1 = new javax.swing.JFormattedTextField();
        telefonojTextField5 = new javax.swing.JFormattedTextField();
        celularjTextField6 = new javax.swing.JFormattedTextField();
        jPanel1 = new javax.swing.JPanel();
        modificarjButton2 = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("DPI*");

        jLabel2.setText("Nombres*");

        jLabel3.setText("Apellidos*");

        jLabel4.setText("Fecha  de nacimiento*");

        jLabel5.setText("Direccion*");

        jLabel6.setText("Telefono*");

        jLabel7.setText("Celular*");

        jLabel8.setText("Email*");

        crearCuentaHabientejButton1.setText("Crear Cuenta Habiente");
        crearCuentaHabientejButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                crearCuentaHabientejButton1ActionPerformed(evt);
            }
        });

        logUusuariosjTextArea.setColumns(20);
        logUusuariosjTextArea.setRows(5);
        jScrollPane1.setViewportView(logUusuariosjTextArea);

        jLabel10.setText("Tipo de Cuenta*");

        comboTipoCuenta.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "MONETARIA", "AHORRO" }));

        jLabel11.setText("Deposito Inicial*");

        try {
            dpijTextField1.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("#############")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        try {
            telefonojTextField5.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("########")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel7)
                                        .addComponent(jLabel6)
                                        .addComponent(jLabel8)
                                        .addComponent(jLabel10))
                                    .addGap(131, 131, 131)
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(celularjTextField6)
                                        .addComponent(telefonojTextField5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 359, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(emailjTextField7, javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(comboTipoCuenta, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addComponent(jLabel11)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtDepositoInicial, javax.swing.GroupLayout.PREFERRED_SIZE, 359, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jLabel1)
                                                .addComponent(jLabel2))
                                            .addGap(68, 68, 68))
                                        .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING))
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                            .addGap(89, 89, 89)
                                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(nombresjTextField2)
                                                .addComponent(dpijTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, 360, Short.MAX_VALUE)))
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                            .addGap(90, 90, 90)
                                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(apellidosjTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 359, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(fechajDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 359, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                            .addComponent(direccionjTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 359, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(73, 73, 73)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 401, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(342, 342, 342)
                        .addComponent(crearCuentaHabientejButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(64, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(dpijTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(nombresjTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(7, 7, 7)
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel4))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(apellidosjTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(9, 9, 9)
                                .addComponent(fechajDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(direccionjTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel5))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel6)
                                    .addComponent(telefonojTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(celularjTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7))
                        .addGap(20, 20, 20)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(emailjTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(comboTipoCuenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtDepositoInicial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 336, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(crearCuentaHabientejButton1)
                .addContainerGap(123, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Creacion de cuenta Habientes", jPanel2);

        modificarjButton2.setText("Modificar");
        modificarjButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                modificarjButton2ActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel9.setText("Cuenta Habientes");

        org.jdesktop.beansbinding.ELProperty eLProperty = org.jdesktop.beansbinding.ELProperty.create("${listaObservableCuentaHabientes}");
        org.jdesktop.swingbinding.JTableBinding jTableBinding = org.jdesktop.swingbinding.SwingBindings.createJTableBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, eLProperty, jTable1);
        org.jdesktop.swingbinding.JTableBinding.ColumnBinding columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${dpiCliente}"));
        columnBinding.setColumnName("Dpi Cliente");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${nombres}"));
        columnBinding.setColumnName("Nombres");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${apellidos}"));
        columnBinding.setColumnName("Apellidos");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${celular}"));
        columnBinding.setColumnName("Celular");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${direccion}"));
        columnBinding.setColumnName("Direccion");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${email}"));
        columnBinding.setColumnName("Email");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${fecha_nacimiento}"));
        columnBinding.setColumnName("Fecha_nacimiento");
        columnBinding.setColumnClass(java.sql.Date.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${telefono}"));
        columnBinding.setColumnName("Telefono");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        bindingGroup.addBinding(jTableBinding);
        jTableBinding.bind();org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${cuentaHabiente}"), jTable1, org.jdesktop.beansbinding.BeanProperty.create("selectedElement"));
        bindingGroup.addBinding(binding);

        jScrollPane2.setViewportView(jTable1);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1153, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(modificarjButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 335, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 459, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(modificarjButton2)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Listado de cuenta Habientes", jPanel1);

        jMenu1.setText("Opciones");

        jMenuItem1.setText("Vista principal");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );

        bindingGroup.bind();

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void crearCuentaHabientejButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_crearCuentaHabientejButton1ActionPerformed
        CuentaHabiente cuentaHabiente = controladorCuentaHabiente.verificarDatosDeCuentaHabiente(this.dpijTextField1.getText(), this.nombresjTextField2.getText(), this.apellidosjTextField3.getText(), new Date(this.fechajDateChooser1.getDate().getTime()), this.direccionjTextField4.getText(), this.telefonojTextField5.getText(), this.celularjTextField6.getText(), this.emailjTextField7.getText());
        if (cuentaHabiente != null) {
            if (controladorCuentaHabiente.registroCuentaHabiente(cuentaHabiente)) {
                UsuarioCliente user = new UsuarioCliente(this.dpijTextField1.getText());
                String numCuenta = controladorCuenta.generarCuenta(); //Generando Cuenta automaticamente
                Cuenta cuenta = new Cuenta(numCuenta, user.getDpiCliente(),
                            Enum.valueOf(TipoCuenta.class, comboTipoCuenta.getSelectedItem().toString()), 0, PeriodoInteres.ANUAL, EstadoCuenta.activa, 0);
                if (controladorUsuarioCLiente.insertarUsuarioCliente(user) && controladorCuenta.verificarRegistroCuenta(cuenta, txtDepositoInicial.getText())) {
                    JOptionPane.showMessageDialog(this, "Se ha creado la cuenta Habiente. Usuario:\n"
                            + user.toString()+cuenta.toString());
                    //Se agrega el movimiento monetario inicial de la cuenta
                        boolean resultado = this.controladorTransacciones.crearRegistroSaldoInicial(cuenta);
                        if(!resultado){
                            JOptionPane.showMessageDialog(null, "No se pudo crear eL registro del movimiento monetario del pago inicial de la cuenta", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    //
                    this.logUusuariosjTextArea.setText(this.logUusuariosjTextArea.getText() + "-----------------------------\n"
                            + user.toString() +cuenta.toString() + "\n-----------------------------\n");
                    //notificar correo
                    controladorCuentaHabiente.notificarCorreoCuentaHabiente(cuenta, user, cuentaHabiente.getEmail());
                } else {
                    controladorCuentaHabiente.eliminarCuentaHabiente(this.dpijTextField1.getText());
                    JOptionPane.showMessageDialog(null, "No se pudo crear el usuario. Se elimino CuentaHabiente", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            limpiarCambios();
        }
    }//GEN-LAST:event_crearCuentaHabientejButton1ActionPerformed
    
    private void modificarjButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_modificarjButton2ActionPerformed
        ModificacionJDialog modificacion = new ModificacionJDialog(this, true, cuentaHabiente);
        modificacion.setVisible(true);
        actualizarLista(controladorCuentaHabiente.busquedaDeCunetaHabientes());
    }//GEN-LAST:event_modificarjButton2ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        this.dispose();
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    public ObservableList<CuentaHabiente> getListaObservableCuentaHabientes() {
        return listaObservableCuentaHabientes;
    }

    public void setListaObservableCuentaHabientes(ObservableList<CuentaHabiente> listaObservableCuentaHabientes) {
        this.listaObservableCuentaHabientes = listaObservableCuentaHabientes;
    }

    public CuentaHabiente getCuentaHabiente() {
        return cuentaHabiente;
    }

    public void setCuentaHabiente(CuentaHabiente cuentaHabiente) {
        if (cuentaHabiente != null) {
            this.modificarjButton2.setEnabled(true);
            this.cuentaHabiente = cuentaHabiente;
            System.out.println("Cuenta habiente:" + this.cuentaHabiente.getDpiCliente());
        } else {
            this.modificarjButton2.setEnabled(false);
            this.cuentaHabiente = null;
        }
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField apellidosjTextField3;
    private javax.swing.JFormattedTextField celularjTextField6;
    private javax.swing.JComboBox<String> comboTipoCuenta;
    private javax.swing.JButton crearCuentaHabientejButton1;
    private javax.swing.JTextField direccionjTextField4;
    private javax.swing.JFormattedTextField dpijTextField1;
    private javax.swing.JTextField emailjTextField7;
    private com.toedter.calendar.JDateChooser fechajDateChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextArea logUusuariosjTextArea;
    private javax.swing.JButton modificarjButton2;
    private javax.swing.JTextField nombresjTextField2;
    private javax.swing.JFormattedTextField telefonojTextField5;
    private javax.swing.JTextField txtDepositoInicial;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

    private void limpiarCambios() {
        this.dpijTextField1.setText("");
        this.nombresjTextField2.setText("");
        this.apellidosjTextField3.setText("");
        this.direccionjTextField4.setText("");
        this.telefonojTextField5.setText("");
        this.celularjTextField6.setText("");
        this.emailjTextField7.setText("");
    }

    private void actualizarLista(List<CuentaHabiente> listado) {
        listaObservableCuentaHabientes.clear();
        listaObservableCuentaHabientes.addAll((List<CuentaHabiente>) (List<?>) listado);
    }

}
