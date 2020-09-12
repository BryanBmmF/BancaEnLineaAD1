/*
 * Panel de consulta y registrop de Cuentas
 */
package frontend.vistas.cuentas;

import backend.controladores.ControladorCuenta;
import backend.controladores.ControladorCuentaHabiente;
import backend.enums.EstadoCuenta;
import backend.enums.PeriodoInteres;
import backend.enums.TipoCuenta;
import backend.pojos.Cuenta;
import backend.pojos.CuentaHabiente;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JOptionPane;
import org.jdesktop.observablecollections.ObservableCollections;
import org.jdesktop.observablecollections.ObservableList;

/**
 *
 * @author bryan
 */
public class PanelGestionCuentas extends javax.swing.JDialog {
    
    private ControladorCuentaHabiente controladorCuentaHabiente;
    private ControladorCuenta controladorCuenta;
    private CuentaHabiente cuentaHabienteSeleccionado = null;
    private Cuenta cuentaSeleccionada = null;
    public List<CuentaHabiente> listaCuentaHabientes = null;
    public ObservableList<CuentaHabiente> listaObservableCuentaHabientes = null;
    public List<Cuenta> listaCuentas = null;
    public ObservableList<Cuenta> listaObservableCuentas = null;
    private String tempDPI;
    private String tempNumCuenta;
    /**
     * Creates new form PanelGestionCuentas
     */
    public PanelGestionCuentas(boolean modal) {
        this.controladorCuentaHabiente = new ControladorCuentaHabiente();
        this.controladorCuenta = new ControladorCuenta();
        this.listaCuentaHabientes = new LinkedList<>();
        this.listaObservableCuentaHabientes = ObservableCollections.observableList(listaCuentaHabientes);
        this.listaCuentas = new LinkedList<>();
        this.listaObservableCuentas = ObservableCollections.observableList(listaCuentas);
        this.cuentaHabienteSeleccionado = new CuentaHabiente();
        this.cuentaSeleccionada = new Cuenta();
        
        /*actualizando lista al mostrar vista*/
        actualizarListaClientes(controladorCuentaHabiente.busquedaDeCunetaHabientes());
        initComponents();
        this.setLocationRelativeTo(null);
        this.setModal(modal);
        btnGenerarCuenta.setEnabled(false);
        btnCancelarCuenta.setEnabled(false);
        txtMotivo.setEnabled(false);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtDpiCliente = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        comboTipoCuenta = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        txtDepositoInicial = new javax.swing.JTextField();
        btnGenerarCuenta = new javax.swing.JButton();
        btnCancelarCuenta = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtNumCuenta = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtMotivo = new javax.swing.JTextArea();
        jLabel10 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Gestión de Cuentas Bancarias");

        jPanel1.setBackground(new java.awt.Color(153, 204, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("Cuentas Bancarias");

        jLabel2.setText("DPI Cliente * :");

        txtDpiCliente.setEditable(false);

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${cuentaHabienteSeleccionado.dpiCliente}"), txtDpiCliente, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        org.jdesktop.beansbinding.ELProperty eLProperty = org.jdesktop.beansbinding.ELProperty.create("${listaObservableCuentas}");
        org.jdesktop.swingbinding.JTableBinding jTableBinding = org.jdesktop.swingbinding.SwingBindings.createJTableBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, eLProperty, jTable1);
        org.jdesktop.swingbinding.JTableBinding.ColumnBinding columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${numCuenta}"));
        columnBinding.setColumnName("No. Cuenta");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${dpiCliente}"));
        columnBinding.setColumnName("DPI-Cliente");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${tipo}"));
        columnBinding.setColumnName("Tipo");
        columnBinding.setColumnClass(backend.enums.TipoCuenta.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${saldo}"));
        columnBinding.setColumnName("Saldo");
        columnBinding.setColumnClass(Double.class);
        columnBinding.setEditable(false);
        bindingGroup.addBinding(jTableBinding);
        jTableBinding.bind();binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${cuentaSeleccionada}"), jTable1, org.jdesktop.beansbinding.BeanProperty.create("selectedElement"));
        bindingGroup.addBinding(binding);

        jScrollPane1.setViewportView(jTable1);

        jLabel3.setText("Tipo de Cuenta :");

        comboTipoCuenta.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "MONETARIA", "AHORRO" }));

        jLabel6.setText("Deposito Inicial * :");

        btnGenerarCuenta.setText("Generar Cuenta");
        btnGenerarCuenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGenerarCuentaActionPerformed(evt);
            }
        });

        btnCancelarCuenta.setText("Cancelar Cuenta");
        btnCancelarCuenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarCuentaActionPerformed(evt);
            }
        });

        eLProperty = org.jdesktop.beansbinding.ELProperty.create("${listaObservableCuentaHabientes}");
        jTableBinding = org.jdesktop.swingbinding.SwingBindings.createJTableBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, eLProperty, jTable2);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${dpiCliente}"));
        columnBinding.setColumnName("Dpi Cliente");
        columnBinding.setColumnClass(String.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${nombres}"));
        columnBinding.setColumnName("Nombres");
        columnBinding.setColumnClass(String.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${apellidos}"));
        columnBinding.setColumnName("Apellidos");
        columnBinding.setColumnClass(String.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${celular}"));
        columnBinding.setColumnName("Celular");
        columnBinding.setColumnClass(String.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${email}"));
        columnBinding.setColumnName("Email");
        columnBinding.setColumnClass(String.class);
        bindingGroup.addBinding(jTableBinding);
        jTableBinding.bind();binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${cuentaHabienteSeleccionado}"), jTable2, org.jdesktop.beansbinding.BeanProperty.create("selectedElement"));
        bindingGroup.addBinding(binding);

        jScrollPane2.setViewportView(jTable2);

        jLabel4.setText("Clientes Registrados: ");

        jLabel5.setText("Cuentas Asociadas:");

        jLabel7.setText("No. Cuenta:");

        txtNumCuenta.setEditable(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${cuentaSeleccionada.numCuenta}"), txtNumCuenta, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        jLabel9.setForeground(new java.awt.Color(255, 51, 51));
        jLabel9.setText("* Se debe seleccionar un Cliente e ingresar mínimo Q.200 de déposito.");

        txtMotivo.setColumns(20);
        txtMotivo.setRows(5);
        jScrollPane3.setViewportView(txtMotivo);

        jLabel10.setForeground(new java.awt.Color(255, 51, 51));
        jLabel10.setText("* Motivo de cancelación de la cuenta.");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtDpiCliente, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtDepositoInicial, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel2)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addComponent(txtNumCuenta))
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(10, 10, 10)
                                        .addComponent(jLabel3))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(comboTipoCuenta, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(btnGenerarCuenta, javax.swing.GroupLayout.DEFAULT_SIZE, 227, Short.MAX_VALUE)))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(btnCancelarCuenta, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(21, 21, 21))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane3)
                            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 516, Short.MAX_VALUE))
                        .addGap(0, 21, Short.MAX_VALUE)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 441, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(27, 27, 27)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtDpiCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(comboTipoCuenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtDepositoInicial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnGenerarCuenta))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtNumCuenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnCancelarCuenta))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(17, 17, 17)
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 417, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        bindingGroup.bind();

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnGenerarCuentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenerarCuentaActionPerformed
        // TODO add your handling code here:
        if (cuentaHabienteSeleccionado!=null) {
            String numCuenta = controladorCuenta.generarCuenta(); //Generando Cuenta automaticamente
            Cuenta cuenta = new Cuenta(numCuenta, cuentaHabienteSeleccionado.getDpiCliente(),
                    Enum.valueOf(TipoCuenta.class, comboTipoCuenta.getSelectedItem().toString()), 0, PeriodoInteres.ANUAL, EstadoCuenta.activa, 0);
            if (controladorCuenta.verificarRegistroCuenta(cuenta, txtDepositoInicial.getText())) {
                //notificar correo
                JOptionPane.showMessageDialog(this, "Cuenta No." + numCuenta + " creada satisfactoriamente", "Info", JOptionPane.INFORMATION_MESSAGE);
                controladorCuentaHabiente.notificarCorreoCuentaHabiente(cuenta, null, cuentaHabienteSeleccionado.getEmail());
                actualizarListaCuentas(controladorCuenta.busquedaDeCunetasCliente(this.cuentaHabienteSeleccionado.getDpiCliente(),"activa")); //se actualiza la lista de cuentas
            }
        }
        btnGenerarCuenta.setEnabled(false);
        limpiarCampos();
    }//GEN-LAST:event_btnGenerarCuentaActionPerformed

    private void btnCancelarCuentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarCuentaActionPerformed
        // TODO add your handling code here:
        if (cuentaSeleccionada!=null ) {
            if (controladorCuenta.cancelarCuenta(cuentaSeleccionada.getNumCuenta(), txtMotivo.getText())) {
                JOptionPane.showMessageDialog(this, "Cuenta No." + cuentaSeleccionada.getNumCuenta() + " cancelada satisfactoriamente", "Info", JOptionPane.INFORMATION_MESSAGE);
                            actualizarListaCuentas(controladorCuenta.busquedaDeCunetasCliente(this.cuentaHabienteSeleccionado.getDpiCliente(),"activa")); //se actualiza la lista de cuentas
            }
        }
        btnCancelarCuenta.setEnabled(false);
        txtMotivo.setEnabled(false);
        limpiarCampos();
    }//GEN-LAST:event_btnCancelarCuentaActionPerformed
    private void actualizarListaClientes(List<CuentaHabiente> listado) {
        listaObservableCuentaHabientes.clear();
        listaObservableCuentaHabientes.addAll((List<CuentaHabiente>) (List<?>) listado);
    }
    private void actualizarListaCuentas(List<Cuenta> listado) {
        listaObservableCuentas.clear();
        listaObservableCuentas.addAll((List<Cuenta>) (List<?>) listado);
    }

    public CuentaHabiente getCuentaHabienteSeleccionado() {
        return cuentaHabienteSeleccionado;
    }

    public void setCuentaHabienteSeleccionado(CuentaHabiente cuentaHabienteSeleccionado) {
        CuentaHabiente anterior = this.cuentaHabienteSeleccionado;
		if (cuentaHabienteSeleccionado != null) { 
			this.cuentaHabienteSeleccionado = cuentaHabienteSeleccionado.clone();
			tempDPI = this.cuentaHabienteSeleccionado.getDpiCliente();
			//todo lo demas como cargar la otra tabla con la observvable de cuentas asociadas
                        btnGenerarCuenta.setEnabled(true);
                        actualizarListaCuentas(controladorCuenta.busquedaDeCunetasCliente(this.cuentaHabienteSeleccionado.getDpiCliente(), "activa"));
                        
		}
		firePropertyChange("cuentaHabienteSeleccionado", anterior, this.cuentaHabienteSeleccionado);
    }

    public Cuenta getCuentaSeleccionada() {
        return cuentaSeleccionada;
    }

    public void setCuentaSeleccionada(Cuenta cuentaSeleccionada) {
        Cuenta anterior = this.cuentaSeleccionada;
		if (cuentaSeleccionada != null) { 
			this.cuentaSeleccionada = cuentaSeleccionada.clone();
			tempNumCuenta = this.cuentaSeleccionada.getNumCuenta();
			//todo lo demas
                        btnCancelarCuenta.setEnabled(true);
                        txtMotivo.setEnabled(true);
		}
		firePropertyChange("cuentaSeleccionada", anterior, this.cuentaSeleccionada);
    }

    public List<CuentaHabiente> getListaCuentaHabientes() {
        return listaCuentaHabientes;
    }

    public void setListaCuentaHabientes(List<CuentaHabiente> listaCuentaHabientes) {
        this.listaCuentaHabientes = listaCuentaHabientes;
    }

    public ObservableList<CuentaHabiente> getListaObservableCuentaHabientes() {
        return listaObservableCuentaHabientes;
    }

    public void setListaObservableCuentaHabientes(ObservableList<CuentaHabiente> listaObservableCuentaHabientes) {
        this.listaObservableCuentaHabientes = listaObservableCuentaHabientes;
    }

    public List<Cuenta> getListaCuentas() {
        return listaCuentas;
    }

    public void setListaCuentas(List<Cuenta> listaCuentas) {
        this.listaCuentas = listaCuentas;
    }

    public ObservableList<Cuenta> getListaObservableCuentas() {
        return listaObservableCuentas;
    }

    public void setListaObservableCuentas(ObservableList<Cuenta> listaObservableCuentas) {
        this.listaObservableCuentas = listaObservableCuentas;
    }
    
    public void limpiarCampos(){
        this.txtDpiCliente.setText("");
        this.txtNumCuenta.setText("");
        this.txtDepositoInicial.setText("");
        this.txtMotivo.setText("");
    }
    
    
    
   
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelarCuenta;
    private javax.swing.JButton btnGenerarCuenta;
    private javax.swing.JComboBox<String> comboTipoCuenta;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextField txtDepositoInicial;
    private javax.swing.JTextField txtDpiCliente;
    private javax.swing.JTextArea txtMotivo;
    private javax.swing.JTextField txtNumCuenta;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables
}
