/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frontend.vistas.tarjetas;

import backend.controladores.ControladorSolicitudDeTarjeta;
import backend.enums.EstadoSolicitudDeTarjeta;
import backend.enums.TipoDeTarjetaSolicitud;
import backend.pojos.SolicitudTarjeta;
import backend.pojos.Tarjeta;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import javax.swing.JOptionPane;
import org.jdesktop.observablecollections.ObservableCollections;
import org.jdesktop.observablecollections.ObservableList;

/**
 *
 * @author jes
 */
public class SolicitudesTarjetasDialog extends javax.swing.JDialog {

    private ControladorSolicitudDeTarjeta controladorSolicitudDeTarjeta;
    private List<SolicitudTarjeta> listaSolicitudTarjeta;
    private ObservableList<SolicitudTarjeta> listaObservableSolicitudTarjeta;
    private SolicitudTarjeta solicitudSeleccionada;

    /**
     * Creates new form SolicitudesTarjetasDialog
     */
    public SolicitudesTarjetasDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        this.solicitudSeleccionada = null;
        this.controladorSolicitudDeTarjeta = new ControladorSolicitudDeTarjeta();
        this.listaSolicitudTarjeta = new LinkedList<>();
        this.listaObservableSolicitudTarjeta = ObservableCollections.observableList(listaSolicitudTarjeta);
        actualizarLista(controladorSolicitudDeTarjeta.consultarSolicitudesDeTarjeta(EstadoSolicitudDeTarjeta.EN_ESPERA));
        initComponents();
        this.resumenCuentajEditorPane1.setContentType("text/html");
        enviarCorreoAceptacion();
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

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        busquedajTextField = new javax.swing.JTextField();
        filtradoCuentaHabientesjComboBox1 = new javax.swing.JComboBox<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        resumenCuentajEditorPane1 = new javax.swing.JEditorPane();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        denegarSolicitudjButtonn = new javax.swing.JButton();
        cambioTarjetajButton = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        tipoTarjetajComboBox = new javax.swing.JComboBox<>();
        cancelarSolicitudjButton = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        comentarioDecisionjTextArea = new javax.swing.JTextArea();
        jLabel5 = new javax.swing.JLabel();
        aprobarSolicitudjButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        org.jdesktop.beansbinding.ELProperty eLProperty = org.jdesktop.beansbinding.ELProperty.create("${listaObservableSolicitudTarjeta}");
        org.jdesktop.swingbinding.JTableBinding jTableBinding = org.jdesktop.swingbinding.SwingBindings.createJTableBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, eLProperty, jTable1);
        org.jdesktop.swingbinding.JTableBinding.ColumnBinding columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${dpi}"));
        columnBinding.setColumnName("Dpi");
        columnBinding.setColumnClass(String.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${tipoDeTarjeta}"));
        columnBinding.setColumnName("Tipo De Tarjeta");
        columnBinding.setColumnClass(backend.enums.TipoDeTarjetaSolicitud.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${salarioMensual}"));
        columnBinding.setColumnName("Salario Mensual");
        columnBinding.setColumnClass(Double.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${empresa}"));
        columnBinding.setColumnName("Empresa");
        columnBinding.setColumnClass(String.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${tipoDeTrabajo}"));
        columnBinding.setColumnName("Tipo De Trabajo");
        columnBinding.setColumnClass(backend.enums.TipoDeTrabajoDeCliente.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${fechaSolicitud}"));
        columnBinding.setColumnName("Fecha Solicitud");
        columnBinding.setColumnClass(java.sql.Timestamp.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${descripcion}"));
        columnBinding.setColumnName("Descripcion");
        columnBinding.setColumnClass(String.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${estadoSolicitud}"));
        columnBinding.setColumnName("Estado Solicitud");
        columnBinding.setColumnClass(backend.enums.EstadoSolicitudDeTarjeta.class);
        bindingGroup.addBinding(jTableBinding);
        jTableBinding.bind();org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${solicitudSeleccionada}"), jTable1, org.jdesktop.beansbinding.BeanProperty.create("selectedElement"));
        bindingGroup.addBinding(binding);

        jScrollPane1.setViewportView(jTable1);

        jLabel1.setText("Solicitudes de tarjeta de Credito");

        busquedajTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                busquedajTextFieldActionPerformed(evt);
            }
        });
        busquedajTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                busquedajTextFieldKeyReleased(evt);
            }
        });

        filtradoCuentaHabientesjComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "DPI" }));

        resumenCuentajEditorPane1.setEditable(false);
        jScrollPane2.setViewportView(resumenCuentajEditorPane1);

        jLabel2.setText("Resumen");

        jLabel3.setText("Opciones Administrador");

        denegarSolicitudjButtonn.setText("Denegar");
        denegarSolicitudjButtonn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                denegarSolicitudjButtonnActionPerformed(evt);
            }
        });

        cambioTarjetajButton.setText("Cambiar");
        cambioTarjetajButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cambioTarjetajButtonActionPerformed(evt);
            }
        });

        jLabel4.setText("Cambiar tipo de tarjeta");

        tipoTarjetajComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ORO", "PLATA", "BRONCE" }));

        cancelarSolicitudjButton.setText("Cancelar");
        cancelarSolicitudjButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelarSolicitudjButtonActionPerformed(evt);
            }
        });

        comentarioDecisionjTextArea.setColumns(20);
        comentarioDecisionjTextArea.setRows(5);
        comentarioDecisionjTextArea.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                comentarioDecisionjTextAreaKeyTyped(evt);
            }
        });
        jScrollPane3.setViewportView(comentarioDecisionjTextArea);

        jLabel5.setText("Comentario de decision:");

        aprobarSolicitudjButton1.setText("Aprobar");
        aprobarSolicitudjButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aprobarSolicitudjButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGap(303, 303, 303)
                        .addComponent(busquedajTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 316, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(filtradoCuentaHabientesjComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(aprobarSolicitudjButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(denegarSolicitudjButtonn)
                        .addGap(18, 18, 18)
                        .addComponent(cancelarSolicitudjButton))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(241, 241, 241)
                                .addComponent(jLabel2))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 539, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 66, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane3)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel5))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(18, 18, 18)
                                .addComponent(tipoTarjetajComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cambioTarjetajButton, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)))))
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(363, 363, 363)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(busquedajTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(filtradoCuentaHabientesjComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(tipoTarjetajComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cambioTarjetajButton))
                        .addGap(21, 21, 21)
                        .addComponent(jLabel5)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(denegarSolicitudjButtonn)
                    .addComponent(cancelarSolicitudjButton)
                    .addComponent(aprobarSolicitudjButton1))
                .addGap(22, 22, 22))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        bindingGroup.bind();

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void busquedajTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_busquedajTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_busquedajTextFieldActionPerformed

    private void cancelarSolicitudjButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelarSolicitudjButtonActionPerformed
        limpiarYDesactivarOpciones();
    }//GEN-LAST:event_cancelarSolicitudjButtonActionPerformed

    private void busquedajTextFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_busquedajTextFieldKeyReleased
        String dpi = busquedajTextField.getText();
        if (dpi.isEmpty()) {
            //Se busca todo
            actualizarLista(controladorSolicitudDeTarjeta.consultarSolicitudesDeTarjeta(EstadoSolicitudDeTarjeta.EN_ESPERA));
        } else {//Se busca por dpi
            actualizarLista(controladorSolicitudDeTarjeta.filtrarSolicitudesPorDpi(EstadoSolicitudDeTarjeta.EN_ESPERA, dpi));
        }
    }//GEN-LAST:event_busquedajTextFieldKeyReleased

    private void comentarioDecisionjTextAreaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_comentarioDecisionjTextAreaKeyTyped
        //System.out.println("TAMANO DE COMENTARIO:" + comentarioDecisionjTextArea.getText().length());
        if (comentarioDecisionjTextArea.getText().length() > 199) {
            evt.consume();
        }
    }//GEN-LAST:event_comentarioDecisionjTextAreaKeyTyped

    private void cambioTarjetajButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cambioTarjetajButtonActionPerformed
        String tipoDeTarjeta = tipoTarjetajComboBox.getSelectedItem().toString();
        if (tipoDeTarjeta.equalsIgnoreCase(TipoDeTarjetaSolicitud.ORO.toString())) {
            this.solicitudSeleccionada.setTipoDeTarjeta(TipoDeTarjetaSolicitud.ORO);
        } else if (tipoDeTarjeta.equalsIgnoreCase(TipoDeTarjetaSolicitud.PLATA.toString())) {
            this.solicitudSeleccionada.setTipoDeTarjeta(TipoDeTarjetaSolicitud.PLATA);
        } else {
            this.solicitudSeleccionada.setTipoDeTarjeta(TipoDeTarjetaSolicitud.BRONCE);
        }
        mostrarInformacionDeSolicitud(solicitudSeleccionada);
        JOptionPane.showMessageDialog(this, "Se cambio el tipo de tarjeta","Aviso",JOptionPane.WARNING_MESSAGE);
    }//GEN-LAST:event_cambioTarjetajButtonActionPerformed

    private void denegarSolicitudjButtonnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_denegarSolicitudjButtonnActionPerformed
        if (!comentarioDecisionjTextArea.getText().isEmpty()) {
            int opcion = JOptionPane.showConfirmDialog(this, "Esta seguro que desea rechazar la solicitud");
            if (opcion == 0) {
                boolean respuesta = this.controladorSolicitudDeTarjeta.rechazarSolicitud(EstadoSolicitudDeTarjeta.RECHAZADO, this.solicitudSeleccionada.getId());
                if (respuesta) {
                    JOptionPane.showMessageDialog(this, "Se rechazo la solicitud", "Rechazo", JOptionPane.INFORMATION_MESSAGE);
                    //ENVIAR MENSAJE POR CORREO DE POR QUE SE RECHAZO
                    /**
                     * **
                     *
                     *
                     *
                     *
                     *
                     */
                    enviarCorreoRechazo();
                    limpiarYDesactivarOpciones();
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Debe colocar una descripcion", "Atencion", JOptionPane.WARNING_MESSAGE);
        }


    }//GEN-LAST:event_denegarSolicitudjButtonnActionPerformed

    private void aprobarSolicitudjButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aprobarSolicitudjButton1ActionPerformed
        if (!comentarioDecisionjTextArea.getText().isEmpty()) {
            int opcion = JOptionPane.showConfirmDialog(this, "Esta seguro que desea ACEPTAR la solicitud");
            if (opcion == 0) {
                Tarjeta tarjeta = new Tarjeta(this.solicitudSeleccionada.getTipoDeTarjeta(), this.solicitudSeleccionada.getDpi());
                boolean respuesta = this.controladorSolicitudDeTarjeta.aprobarSolicitud(EstadoSolicitudDeTarjeta.APROBADO, this.solicitudSeleccionada.getId(), this.solicitudSeleccionada.getTipoDeTarjeta(),tarjeta);
                if (respuesta) {
                    JOptionPane.showMessageDialog(this, "Se Acepto la solicitud", "Aceptacion", JOptionPane.INFORMATION_MESSAGE);
                    //ENVIAR MENSAJE POR CORREO DE POR QUE SE RECHAZO
                    /**
                     * Generara datos de tarjeta de credito **
                     *
                     *
                     *
                     *
                     *
                     */
                    enviarCorreoAceptacion();
                    limpiarYDesactivarOpciones();
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Debe colocar una descripcion", "Atencion", JOptionPane.WARNING_MESSAGE);
        }

    }//GEN-LAST:event_aprobarSolicitudjButton1ActionPerformed

    public ObservableList<SolicitudTarjeta> getListaObservableSolicitudTarjeta() {
        return listaObservableSolicitudTarjeta;
    }

    public void setListaObservableSolicitudTarjeta(ObservableList<SolicitudTarjeta> listaObservableSolicitudTarjeta) {
        this.listaObservableSolicitudTarjeta = listaObservableSolicitudTarjeta;
    }

    public SolicitudTarjeta getSolicitudSeleccionada() {
        return solicitudSeleccionada;
    }

    public void setSolicitudSeleccionada(SolicitudTarjeta solicitudSeleccionada) {
        if (solicitudSeleccionada != null) {
            this.aprobarSolicitudjButton1.setEnabled(true);
            this.denegarSolicitudjButtonn.setEnabled(true);
            this.cancelarSolicitudjButton.setEnabled(true);
            this.cambioTarjetajButton.setEnabled(true);
            this.solicitudSeleccionada = solicitudSeleccionada;
            mostrarInformacionDeSolicitud(solicitudSeleccionada);
            //System.out.println("Cuenta habiente:" + this.cuentaHabiente.getDpiCliente());
        } else {
            this.aprobarSolicitudjButton1.setEnabled(false);
            this.denegarSolicitudjButtonn.setEnabled(false);
            this.cancelarSolicitudjButton.setEnabled(false);
            this.cambioTarjetajButton.setEnabled(false);
            this.solicitudSeleccionada = null;
        }
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton aprobarSolicitudjButton1;
    private javax.swing.JTextField busquedajTextField;
    private javax.swing.JButton cambioTarjetajButton;
    private javax.swing.JButton cancelarSolicitudjButton;
    private javax.swing.JTextArea comentarioDecisionjTextArea;
    private javax.swing.JButton denegarSolicitudjButtonn;
    private javax.swing.JComboBox<String> filtradoCuentaHabientesjComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable1;
    private javax.swing.JEditorPane resumenCuentajEditorPane1;
    private javax.swing.JComboBox<String> tipoTarjetajComboBox;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

    private void actualizarLista(List<SolicitudTarjeta> listado) {
        listaObservableSolicitudTarjeta.clear();
        listaObservableSolicitudTarjeta.addAll((List<SolicitudTarjeta>) (List<?>) listado);
    }

    private void mostrarInformacionDeSolicitud(SolicitudTarjeta solicitud) {
        actualizarTipoDeTarjeta(solicitud);
        this.resumenCuentajEditorPane1.setText(
                "<ul>\n"
                + "  <li>DPI: " + solicitud.getDpi() + "</li>\n"
                + "  <li>TIPO DE TARJETA: " + solicitud.getTipoDeTarjeta().toString().toUpperCase() + "</li>\n"
                + "  <li>SALARIO MENSUAL: " + solicitud.getSalarioMensual() + "</li>\n"
                + "  <li>EMPRESA: " + solicitud.getEmpresa() + "</li>\n"
                + "  <li>TIPO DE TRABAJO: " + solicitud.getTipoDeTrabajo() + "</li>\n"
                + "  <li>FECHA SOLICITUD: " + solicitud.getFechaSolicitud() + "</li>\n"
                + "  <li>DESCRIPCION: " + solicitud.getDescripcion() + "</li>\n"
                + "  <li>ESTADO: " + solicitud.getEstadoSolicitud()+ "</li>\n"
                + "  <li>CORREO: " + solicitud.getEmail() + "</li>\n"
                + "</ul>"
        );
    }

    private void actualizarTipoDeTarjeta(SolicitudTarjeta solicitud) {
        switch (solicitud.getTipoDeTarjeta()) {
            case ORO:
                this.tipoTarjetajComboBox.setSelectedIndex(0);
                break;
            case PLATA:
                this.tipoTarjetajComboBox.setSelectedIndex(1);
                break;
            default:
                this.tipoTarjetajComboBox.setSelectedIndex(2);
                break;
        }

    }

    private void limpiarYDesactivarOpciones() {
        this.resumenCuentajEditorPane1.setText("");
        this.comentarioDecisionjTextArea.setText("");
        this.solicitudSeleccionada = null;
        this.aprobarSolicitudjButton1.setEnabled(false);
        this.denegarSolicitudjButtonn.setEnabled(false);
        this.cancelarSolicitudjButton.setEnabled(false);
        this.cambioTarjetajButton.setEnabled(false);
        actualizarLista(controladorSolicitudDeTarjeta.consultarSolicitudesDeTarjeta(EstadoSolicitudDeTarjeta.EN_ESPERA));
    }

    private void enviarCorreoAceptacion() {

    }

    private void enviarCorreoRechazo() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
}
