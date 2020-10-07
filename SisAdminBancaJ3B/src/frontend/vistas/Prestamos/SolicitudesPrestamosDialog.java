/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frontend.vistas.Prestamos;

import frontend.vistas.tarjetas.*;
import backend.controladores.ControladorSolicitudDeTarjeta;
import backend.controladores.ControladorSolicitudPrestamo;
import backend.enums.EstadoSolicitudDeTarjeta;
import backend.enums.EstadoSolicitudPrestamo;
import backend.enums.TipoDeTarjetaSolicitud;
import backend.enums.TiposPrestamos;
import backend.pojos.Prestamo;
import backend.pojos.SolicitudPrestamo;
import backend.pojos.TablaModelo;
import backend.pojos.Tarjeta;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author jes
 */
public class SolicitudesPrestamosDialog extends javax.swing.JDialog {

    private ControladorSolicitudPrestamo controladorSolicitud;
    private List<SolicitudPrestamo> listaSolicitudes;
    private SolicitudPrestamo solicitudSeleccionada;
    private TableRowSorter tablaSorteada;
    private TablaModelo modelo;

    public SolicitudesPrestamosDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        this.modelo = new TablaModelo();
        this.solicitudSeleccionada = null;
        this.controladorSolicitud = new ControladorSolicitudPrestamo();
        this.listaSolicitudes = new LinkedList<>();
        initComponents();
        llenarDatos();
        this.textAreaResumen.setContentType("text/html");
    }

    public void llenarDatos() {
        cargarModelo();
        listaSolicitudes.clear();
        listaSolicitudes = controladorSolicitud.consultarSolicitudesDePrestamos(EstadoSolicitudPrestamo.EN_ESPERA);
        llenarModelo();
        limpiarYDesactivarOpciones();
    }

    public void llenarModelo() {
        for (int i = 0; i < listaSolicitudes.size(); i++) {
            Object objeto[] = new Object[12];
            objeto[0] = listaSolicitudes.get(i).getId();
            objeto[1] = listaSolicitudes.get(i).getTipoTrabajo();
            objeto[2] = listaSolicitudes.get(i).getEmpresa();
            objeto[3] = listaSolicitudes.get(i).getEstado();
            objeto[4] = listaSolicitudes.get(i).getSalarioMensual();
            objeto[5] = listaSolicitudes.get(i).getMontoSolicitud();
            objeto[6] = listaSolicitudes.get(i).getTipo();
            objeto[7] = listaSolicitudes.get(i).getDescripcion();
            objeto[8] = listaSolicitudes.get(i).getDireccionBienRaiz();
            objeto[9] = listaSolicitudes.get(i).getFechaSolicitud();
            objeto[10] = listaSolicitudes.get(i).getDpiCliente();
            objeto[11] = listaSolicitudes.get(i).getEmail();
            modelo.addRow(objeto);
        }
    }

    public void cargarModelo() {
        this.modelo = new TablaModelo();
        modelo.addColumn("ID");
        modelo.addColumn("TIPO TRABAJO");
        modelo.addColumn("EMPRESA");
        modelo.addColumn("ESTADO");
        modelo.addColumn("SALARIO");
        modelo.addColumn("MONTO");
        modelo.addColumn("TIPO");
        modelo.addColumn("DESCRIPCION");
        modelo.addColumn("DIRECCION_BIEN");
        modelo.addColumn("FECHA SOLICITIUD");
        modelo.addColumn("DPI");
        modelo.addColumn("EMAIL");
        this.tablaSolicitudes.setModel(modelo);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaSolicitudes = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        textoFiltro = new javax.swing.JTextField();
        filtros = new javax.swing.JComboBox<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        textAreaResumen = new javax.swing.JEditorPane();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        denegarSolicitudjButtonn = new javax.swing.JButton();
        cancelarSolicitudjButton = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        comentarioDecision = new javax.swing.JTextArea();
        jLabel5 = new javax.swing.JLabel();
        aprobarSolicitudjButton1 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        cantidadMeses = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        tablaSolicitudes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tablaSolicitudes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaSolicitudesMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tablaSolicitudes);

        jLabel1.setText("Solicitudes de Prestamos");

        textoFiltro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textoFiltroActionPerformed(evt);
            }
        });
        textoFiltro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                textoFiltroKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                textoFiltroKeyTyped(evt);
            }
        });

        filtros.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ID", "TIPO TRABAJO", "EMPRESA", "ESTADO", "SALARIO MENSUAL", "MONTO SOLICITUD", "TIPO", "DESCRIPCION", "DIRECCION BIEN RAIZ", "FECHA SOLICITUD", "DPI", "EMAIL" }));
        filtros.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                filtrosItemStateChanged(evt);
            }
        });

        textAreaResumen.setEditable(false);
        jScrollPane2.setViewportView(textAreaResumen);

        jLabel2.setText("Resumen");

        jLabel3.setText("Opciones Administrador");

        denegarSolicitudjButtonn.setText("Denegar");
        denegarSolicitudjButtonn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                denegarSolicitudjButtonnActionPerformed(evt);
            }
        });

        cancelarSolicitudjButton.setText("Cancelar");
        cancelarSolicitudjButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelarSolicitudjButtonActionPerformed(evt);
            }
        });

        comentarioDecision.setColumns(20);
        comentarioDecision.setRows(5);
        comentarioDecision.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                comentarioDecisionKeyTyped(evt);
            }
        });
        jScrollPane3.setViewportView(comentarioDecision);

        jLabel5.setText("Comentario de decision:");

        aprobarSolicitudjButton1.setText("Aprobar");
        aprobarSolicitudjButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aprobarSolicitudjButton1ActionPerformed(evt);
            }
        });

        jLabel6.setText("Filtrar por:");

        jLabel4.setText("Cantidad Meses:");

        cantidadMeses.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "6", "12", "18", "24" }));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(241, 241, 241)
                                .addComponent(jLabel2))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 539, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 190, Short.MAX_VALUE)
                                .addComponent(jLabel3)
                                .addGap(131, 131, 131))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(29, 29, 29)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane3)
                                    .addComponent(cantidadMeses, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(10, 10, 10))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane1)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addGap(189, 189, 189)
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(29, 29, 29)
                                .addComponent(textoFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, 316, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(filtros, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(aprobarSolicitudjButton1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(denegarSolicitudjButtonn)
                                .addGap(18, 18, 18)
                                .addComponent(cancelarSolicitudjButton)))))
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
                    .addComponent(textoFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(filtros, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(denegarSolicitudjButtonn)
                            .addComponent(cancelarSolicitudjButton)
                            .addComponent(aprobarSolicitudjButton1))
                        .addGap(22, 22, 22))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(cantidadMeses, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(112, 112, 112))))
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

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void textoFiltroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textoFiltroActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textoFiltroActionPerformed

    private void cancelarSolicitudjButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelarSolicitudjButtonActionPerformed
        limpiarYDesactivarOpciones();
    }//GEN-LAST:event_cancelarSolicitudjButtonActionPerformed

    private void textoFiltroKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textoFiltroKeyReleased

    }//GEN-LAST:event_textoFiltroKeyReleased

    private void comentarioDecisionKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_comentarioDecisionKeyTyped
        //System.out.println("TAMANO DE COMENTARIO:" + comentarioDecisionjTextArea.getText().length());
        if (comentarioDecision.getText().length() > 199) {
            evt.consume();
        }
    }//GEN-LAST:event_comentarioDecisionKeyTyped

    private void denegarSolicitudjButtonnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_denegarSolicitudjButtonnActionPerformed
        if (!comentarioDecision.getText().isEmpty()) {
            int opcion = JOptionPane.showConfirmDialog(this, "Esta seguro que desea rechazar la solicitud");
            if (opcion == 0) {
                boolean respuesta = this.controladorSolicitud.cambiarEstadoSolicitud(EstadoSolicitudPrestamo.RECHAZADO, this.solicitudSeleccionada.getId(), null);
                if (respuesta) {
                    try {
                        controladorSolicitud.notificarRechazoDePrestamo(this.comentarioDecision.getText(), this.solicitudSeleccionada, EstadoSolicitudPrestamo.RECHAZADO.toString());
                        JOptionPane.showMessageDialog(this, "Se rechazo la solicitud", "Rechazo", JOptionPane.INFORMATION_MESSAGE);
                        limpiarYDesactivarOpciones();
                        llenarDatos();
                    } catch (Exception ex) {
                        Logger.getLogger(SolicitudesPrestamosDialog.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Debe colocar una descripcion", "Atencion", JOptionPane.WARNING_MESSAGE);
        }


    }//GEN-LAST:event_denegarSolicitudjButtonnActionPerformed

    private void aprobarSolicitudjButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aprobarSolicitudjButton1ActionPerformed
        if (!this.comentarioDecision.getText().isEmpty()) {
            int opcion = JOptionPane.showConfirmDialog(this, "Esta seguro que desea ACEPTAR la solicitud");
            if (opcion == 0) {
                String descripcion = this.comentarioDecision.getText();
                if (this.solicitudSeleccionada.getTipo().equals(TiposPrestamos.HIPOTECARIO)) {
                    descripcion += "DIRECCION BIEN RAIZ: " + this.solicitudSeleccionada.getDireccionBienRaiz();
                }
                Prestamo prestamo = new Prestamo(this.solicitudSeleccionada.getDpiCliente(), this.solicitudSeleccionada.getMontoSolicitud(), Integer.parseInt(this.cantidadMeses.getSelectedItem().toString()), this.solicitudSeleccionada.getTipo(), descripcion);
                boolean respuesta = this.controladorSolicitud.cambiarEstadoSolicitud(EstadoSolicitudPrestamo.APROBADO, this.solicitudSeleccionada.getId(), prestamo);
                if (respuesta) {
                    try {
                        controladorSolicitud.notificarAprobacionDePrestamo(comentarioDecision.getText(), this.solicitudSeleccionada, prestamo, EstadoSolicitudPrestamo.APROBADO.toString());
                        JOptionPane.showMessageDialog(this, "Se Acepto la solicitud de prestamo", "Aceptacion", JOptionPane.INFORMATION_MESSAGE);
                        limpiarYDesactivarOpciones();
                        llenarDatos();
                    } catch (Exception ex) {
                        Logger.getLogger(SolicitudesPrestamosDialog.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Debe colocar una descripcion", "Atencion", JOptionPane.WARNING_MESSAGE);
        }

    }//GEN-LAST:event_aprobarSolicitudjButton1ActionPerformed

    private void tablaSolicitudesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaSolicitudesMouseClicked
        int seleccion = tablaSolicitudes.getSelectedRow();// recoge la selecion
        SolicitudPrestamo solicitud = obtenerSolicitudPorId((Integer) tablaSolicitudes.getValueAt(seleccion, 0));
        setSolicitudSeleccionada(solicitud);
        mostrarInformacionDeSolicitud(this.solicitudSeleccionada);

    }//GEN-LAST:event_tablaSolicitudesMouseClicked

    private void textoFiltroKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textoFiltroKeyTyped
        int valor = filtros.getSelectedIndex();
// sortea la tabla
        textoFiltro.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                tablaSorteada.setRowFilter(RowFilter.regexFilter("(?i)" + textoFiltro.getText(), valor));
            }
        });

        tablaSorteada = new TableRowSorter(modelo);
        tablaSolicitudes.setRowSorter(tablaSorteada);
    }//GEN-LAST:event_textoFiltroKeyTyped

    private void filtrosItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_filtrosItemStateChanged
        int valor = this.filtros.getSelectedIndex();
// sortea la tabla
        tablaSorteada = new TableRowSorter(modelo);
        tablaSorteada.setRowFilter(RowFilter.regexFilter("(?i)" + textoFiltro.getText(), valor));
        tablaSolicitudes.setRowSorter(tablaSorteada);
    }//GEN-LAST:event_filtrosItemStateChanged

    public SolicitudPrestamo getSolicitudSeleccionada() {
        return solicitudSeleccionada;
    }

    public void setSolicitudSeleccionada(SolicitudPrestamo solicitudSeleccionada) {
        if (solicitudSeleccionada != null) {
            this.aprobarSolicitudjButton1.setEnabled(true);
            this.denegarSolicitudjButtonn.setEnabled(true);
            this.cancelarSolicitudjButton.setEnabled(true);
            this.solicitudSeleccionada = solicitudSeleccionada;
            mostrarInformacionDeSolicitud(solicitudSeleccionada);
        } else {
            this.aprobarSolicitudjButton1.setEnabled(false);
            this.denegarSolicitudjButtonn.setEnabled(false);
            this.cancelarSolicitudjButton.setEnabled(false);
            this.solicitudSeleccionada = null;
        }
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton aprobarSolicitudjButton1;
    private javax.swing.JButton cancelarSolicitudjButton;
    private javax.swing.JComboBox<String> cantidadMeses;
    private javax.swing.JTextArea comentarioDecision;
    private javax.swing.JButton denegarSolicitudjButtonn;
    private javax.swing.JComboBox<String> filtros;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable tablaSolicitudes;
    private javax.swing.JEditorPane textAreaResumen;
    private javax.swing.JTextField textoFiltro;
    // End of variables declaration//GEN-END:variables

    private SolicitudPrestamo obtenerSolicitudPorId(int id) {
        SolicitudPrestamo solicitud = null;
        for (int i = 0; i < listaSolicitudes.size(); i++) {
            if (listaSolicitudes.get(i).getId() == id) {
                solicitud = listaSolicitudes.get(i);
            }
        }
        return solicitud;
    }

    private void mostrarInformacionDeSolicitud(SolicitudPrestamo solicitud) {
        this.textAreaResumen.setText(
                "<ul>\n"
                + "  <li>ID PRESTAMO: " + solicitud.getId() + "</li>\n"
                + "  <li>TIPO TRABAJO: " + solicitud.getTipoTrabajo() + "</li>\n"
                + "  <li>EMPRESA: " + solicitud.getEmpresa() + "</li>\n"
                + "  <li>ESTADO: " + solicitud.getEstado() + "</li>\n"
                + "  <li>SALARIO MENSUAL: " + solicitud.getSalarioMensual() + "</li>\n"
                + "  <li>MONTO SOLICITUD: " + solicitud.getMontoSolicitud() + "</li>\n"
                + "  <li>TIPO PRESTAMO: " + solicitud.getTipo() + "</li>\n"
                + "  <li>DESCRIPCION: " + solicitud.getDescripcion() + "</li>\n"
                + "  <li>DIRECCION_BIEN_RAIZ: " + solicitud.getDireccionBienRaiz() + "</li>\n"
                + "  <li>FECHA_SOLICITUD: " + solicitud.getFechaSolicitud() + "</li>\n"
                + "  <li>DPI: " + solicitud.getDpiCliente() + "</li>\n"
                + "  <li>CORREO: " + solicitud.getEmail() + "</li>\n"
                + "</ul>"
        );
    }

    private void limpiarYDesactivarOpciones() {
        this.textAreaResumen.setText("");
        this.comentarioDecision.setText("");
        this.solicitudSeleccionada = null;
        this.aprobarSolicitudjButton1.setEnabled(false);
        this.denegarSolicitudjButtonn.setEnabled(false);
        this.cancelarSolicitudjButton.setEnabled(false);
    }

   

}
//INSERT INTO SOLICITUD_PRESTAMO VALUES(null,1,'Empresa prueba',1,5000,100000,2,'porque quiero','LOS ALMENDROS',now(),now(),'9999900090909');