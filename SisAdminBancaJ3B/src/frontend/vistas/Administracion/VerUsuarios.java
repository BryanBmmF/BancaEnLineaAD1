/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frontend.vistas.Administracion;

import backend.controladores.ControladorUsuarios;
import backend.pojos.TablaModelo;
import backend.pojos.UsuarioAdministrador;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.WindowConstants;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author jpmazate
 */
public class VerUsuarios extends javax.swing.JFrame {

    private ControladorUsuarios controladorUsuarios;
    private TableRowSorter tablaSorteada;
    private TablaModelo modelo;
    private SimpleDateFormat formateador;
    private boolean mostrarCambio;
    private boolean mostrarContra;
    private boolean mostrarContra2;

    public VerUsuarios() {
        initComponents();
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.controladorUsuarios = new ControladorUsuarios();
        this.modelo = new TablaModelo();
        this.formateador = new SimpleDateFormat("yyyy-MM-dd");
        this.mostrarCambio = false;
        this.mostrarContra = false;
        this.mostrarContra2 = false;
        ocultarCambioContrasena();
        llenarDatos();

    }

    public void llenarDatos() {
        cargarModelo();
        controladorUsuarios.llenarUsuarios(modelo);

    }

    public void recargarTabla() {
        modelo = new TablaModelo();
        this.textoFiltros.setText("");
        this.tablaUsuarios.setRowSorter(null);
        controladorUsuarios = new ControladorUsuarios();
        llenarDatos();
    }

    public void cargarModelo() {
        modelo.addColumn("ID USUARIO");
        modelo.addColumn("DPI");
        modelo.addColumn("NOMBRES");
        modelo.addColumn("APELLIDOS");
        modelo.addColumn("FECHA NACIMIENTO");
        this.tablaUsuarios.setModel(modelo);
    }

    public void reiniciarEditado() {
        this.textoApellidos.setText("");
        this.textoConfirmacionContra.setText("");
        this.textoDpi.setText("");
        this.textoFechaNacimiento.setDate(new Date());
        this.textoFiltros.setText("");
        this.textoIdUsuario.setText("");
        this.textoNombres.setText("");
        this.textoNuevaContra.setText("");

    }

    public void guardarCambios() {
        String usuario = this.textoIdUsuario.getText();

        String dpi = this.textoDpi.getText();
        String nombres = this.textoNombres.getText();
        String apellidos = this.textoApellidos.getText();
        Date fechaNacimiento = this.textoFechaNacimiento.getDate();
        if (!usuario.equals("")) {
            if (!dpi.equals("")) {
                if (dpi.length() == 13) {
                    if (!nombres.equals("")) {
                        if (nombres.length() <= 35) {
                            if (!apellidos.equals("")) {
                                if (apellidos.length() <= 35) {
                                    if (fechaNacimiento.compareTo(new Date()) < 0) {
                                        UsuarioAdministrador usuarioAdmin = new UsuarioAdministrador(usuario, dpi, "", nombres, apellidos, fechaNacimiento);
                                        boolean resultado = controladorUsuarios.editarUsuario(usuarioAdmin);
                                        if (resultado) {
                                            JOptionPane.showMessageDialog(null, "SE HA EDITADO CON EXITO EL USUARIO: " + usuario);
                                            recargarTabla();
                                            reiniciarEditado();
                                        } else {
                                            JOptionPane.showMessageDialog(null, "ERRORES AL EDITAR EL USUARIO, LOS ERRORES PUEDEN SER:\n"
                                                    + "1.- EL DPI SE REPITE EN EL SISTEMA\n"
                                                    + "2.- EL NOMBRE ES IGUAL AL DE OTRO REGISTRO");
                                        }
                                    } else {
                                        JOptionPane.showMessageDialog(null, "DEBES DE ESCOGER UNA FECHA DISTINTA A LA ACTUAL");
                                    }
                                } else {
                                    JOptionPane.showMessageDialog(null, "EL CAMPO DE APELLIDOS NO PUEDE PASAR DE 35 CARACTERES, ACTUALES: " + apellidos.length());
                                }
                            } else {
                                JOptionPane.showMessageDialog(null, "EL CAMPO DE APELLIDOS NO PUEDE ESTAR VACIO");
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "EL CAMPO DE NOMBRES NO PUEDE PASAR DE 35 CARACTERES, ACTUALES: " + nombres.length());
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "EL CAMPO DE NOMBRES NO PUEDE ESTAR VACIO");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "EL CAMPO DE DPI DEBE DE TENER 13 CARACTERES EXACTOS, ACTUALES: " + dpi.length());
                }
            } else {
                JOptionPane.showMessageDialog(null, "EL CAMPO DE DPI NO PUEDE ESTAR VACIO");
            }

        } else {
            JOptionPane.showMessageDialog(null, "NO SE HA SELECCIONADO NINGUN USUARIO");
        }
    }

    public void cambiarContrasena() {
        String usuario = this.textoIdUsuario.getText();
        String contra = this.textoNuevaContra.getText();
        String confirmacion = this.textoConfirmacionContra.getText();

        if (!usuario.equals("")) {
            if (!contra.equals("")) {
                if (!confirmacion.equals("")) {
                    if (contra.equals(confirmacion)) {
                        UsuarioAdministrador nuevo = new UsuarioAdministrador(usuario, contra);
                        boolean resultado = controladorUsuarios.cambiarContrasenaUsuario(nuevo);
                        if (resultado) {
                            JOptionPane.showMessageDialog(null, "SE REALIZO EL CAMBIO DE CONTRASEÑA CON EXITO DEL USUARIO: "+usuario);
                            recargarTabla();
                            reiniciarEditado();
                            botonCambiarContrasena();

                        } else {
                            JOptionPane.showMessageDialog(null, "SURGIO UN PROBLEMA AL INTENTAR HACER EL CAMBIO DE CONTRASEÑA");

                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "LAS CONTRASEÑAS NO COINCIDEN");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "EL CAMPO DE CONFIRMAR CONTRASEÑA NO PUEDE ESTAR VACIO");
                }
            } else {
                JOptionPane.showMessageDialog(null, "EL CAMPO DE CONTRASEÑA NO PUEDE ESTAR VACIO");
            }

        } else {
            JOptionPane.showMessageDialog(null, "NO SE HA SELECCIONADO NINGUN USUARIO");
        }
    }

    public void botonCambiarContrasena() {

        if (this.mostrarCambio) {
            ocultarCambioContrasena();
            this.mostrarCambio = false;
        } else {
            if (!this.textoIdUsuario.getText().equals("")) {
                mostrarCambioContrasena();
                this.mostrarCambio = true;
            } else {
                JOptionPane.showMessageDialog(null, "No se ha seleccionado ningun usuario para realizarle cambio de contraseña");
            }
        }
    }

    public void ocultarCambioContrasena() {
        this.labelCambiarContra.setVisible(false);
        this.labelConfirmacionContra.setVisible(false);
        this.labelNuevaContra.setVisible(false);
        this.textoNuevaContra.setVisible(false);
        this.textoConfirmacionContra.setVisible(false);
        this.botonGuardarContrasena.setVisible(false);
        this.botonMostrarConfirmacion.setVisible(false);
        this.botonMostrarContra.setVisible(false);

    }

    public void mostrarCambioContrasena() {
        this.labelCambiarContra.setVisible(true);
        this.labelConfirmacionContra.setVisible(true);
        this.labelNuevaContra.setVisible(true);
        this.textoNuevaContra.setVisible(true);
        this.textoConfirmacionContra.setVisible(true);
        this.botonGuardarContrasena.setVisible(true);
        this.botonMostrarConfirmacion.setVisible(true);
        this.botonMostrarContra.setVisible(true);

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        filtros = new javax.swing.JComboBox<>();
        textoFiltros = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaUsuarios = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        textoFechaNacimiento = new com.toedter.calendar.JDateChooser();
        jLabel7 = new javax.swing.JLabel();
        textoIdUsuario = new javax.swing.JTextField();
        textoApellidos = new javax.swing.JTextField();
        textoNombres = new javax.swing.JTextField();
        textoDpi = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        labelCambiarContra = new javax.swing.JLabel();
        labelNuevaContra = new javax.swing.JLabel();
        labelConfirmacionContra = new javax.swing.JLabel();
        textoConfirmacionContra = new javax.swing.JPasswordField();
        textoNuevaContra = new javax.swing.JPasswordField();
        botonMostrarConfirmacion = new javax.swing.JButton();
        botonMostrarContra = new javax.swing.JButton();
        botonGuardarContrasena = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Ubuntu", 1, 24)); // NOI18N
        jLabel1.setText("USUARIOS ADMINISTRADORES");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 30, -1, -1));

        jLabel2.setText("FILTRAR POR:");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 95, -1, -1));

        filtros.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Usuario", "Dpi", "Nombre", "Apellido", "Fecha Nacimiento" }));
        filtros.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                filtrosItemStateChanged(evt);
            }
        });
        getContentPane().add(filtros, new org.netbeans.lib.awtextra.AbsoluteConstraints(141, 87, 170, -1));

        textoFiltros.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                textoFiltrosKeyTyped(evt);
            }
        });
        getContentPane().add(textoFiltros, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 90, 880, -1));

        tablaUsuarios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tablaUsuarios.setEditingColumn(-5);
        tablaUsuarios.setEditingRow(-5);
        tablaUsuarios.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaUsuariosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tablaUsuarios);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 150, 1180, 250));

        jLabel3.setText("Id Usuario:");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 460, -1, -1));

        jLabel4.setText("Dpi:");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 500, -1, -1));

        jLabel5.setText("Nombres:");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 540, -1, -1));

        jLabel6.setText("Apellidos:");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 580, -1, -1));
        getContentPane().add(textoFechaNacimiento, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 610, 290, -1));

        jLabel7.setText("Fecha Nacimiento:");
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 620, -1, -1));

        textoIdUsuario.setEditable(false);
        getContentPane().add(textoIdUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 450, 290, -1));
        getContentPane().add(textoApellidos, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 570, 290, -1));
        getContentPane().add(textoNombres, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 530, 290, -1));
        getContentPane().add(textoDpi, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 490, 290, -1));

        jButton1.setText("Guardar Cambios");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 450, 190, -1));

        jButton2.setText("Crear Usuario");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 530, 190, -1));

        jButton3.setText("Cambiar Contraseña");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 490, 190, -1));

        labelCambiarContra.setFont(new java.awt.Font("Ubuntu", 2, 18)); // NOI18N
        labelCambiarContra.setText("Cambiar Contrasena");
        getContentPane().add(labelCambiarContra, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 430, -1, -1));

        labelNuevaContra.setText("Nueva Contrasena:");
        getContentPane().add(labelNuevaContra, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 490, -1, -1));

        labelConfirmacionContra.setText("Confirmacion Contrasena:");
        getContentPane().add(labelConfirmacionContra, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 530, -1, -1));
        getContentPane().add(textoConfirmacionContra, new org.netbeans.lib.awtextra.AbsoluteConstraints(940, 530, 210, -1));
        getContentPane().add(textoNuevaContra, new org.netbeans.lib.awtextra.AbsoluteConstraints(940, 480, 210, -1));

        botonMostrarConfirmacion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/MostrarContrasena.png"))); // NOI18N
        botonMostrarConfirmacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonMostrarConfirmacionActionPerformed(evt);
            }
        });
        getContentPane().add(botonMostrarConfirmacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(1160, 520, 40, 30));

        botonMostrarContra.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/MostrarContrasena.png"))); // NOI18N
        botonMostrarContra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonMostrarContraActionPerformed(evt);
            }
        });
        getContentPane().add(botonMostrarContra, new org.netbeans.lib.awtextra.AbsoluteConstraints(1160, 480, 40, 30));

        botonGuardarContrasena.setText("Guardar Contraseña");
        botonGuardarContrasena.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonGuardarContrasenaActionPerformed(evt);
            }
        });
        getContentPane().add(botonGuardarContrasena, new org.netbeans.lib.awtextra.AbsoluteConstraints(940, 580, 210, -1));

        jButton4.setText("Eliminar Filtros");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 570, 190, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void botonMostrarConfirmacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonMostrarConfirmacionActionPerformed
        if (this.mostrarContra2 == false) {
            this.textoConfirmacionContra.setEchoChar((char) 0);
            this.mostrarContra2 = true;
        } else {
            this.textoConfirmacionContra.setEchoChar('*');
            this.mostrarContra2 = false;
        }
    }//GEN-LAST:event_botonMostrarConfirmacionActionPerformed

    private void botonMostrarContraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonMostrarContraActionPerformed
        if (this.mostrarContra == false) {
            this.textoNuevaContra.setEchoChar((char) 0);
            this.mostrarContra = true;
        } else {
            this.textoNuevaContra.setEchoChar('*');
            this.mostrarContra = false;
        }
    }//GEN-LAST:event_botonMostrarContraActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        guardarCambios();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        botonCambiarContrasena();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        CrearUsuario usuario = new CrearUsuario(this, true);
        usuario.setVisible(true);
        recargarTabla();
        reiniciarEditado();
        

    }//GEN-LAST:event_jButton2ActionPerformed

    private void textoFiltrosKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textoFiltrosKeyTyped
        int valor = filtros.getSelectedIndex();
// sortea la tabla
        textoFiltros.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                tablaSorteada.setRowFilter(RowFilter.regexFilter("(?i)" + textoFiltros.getText(), valor));
            }
        });

        tablaSorteada = new TableRowSorter(modelo);
        tablaUsuarios.setRowSorter(tablaSorteada);
    }//GEN-LAST:event_textoFiltrosKeyTyped

    private void filtrosItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_filtrosItemStateChanged
        int valor = this.filtros.getSelectedIndex();
// sortea la tabla
        tablaSorteada = new TableRowSorter(modelo);
        tablaSorteada.setRowFilter(RowFilter.regexFilter("(?i)" + textoFiltros.getText(), valor));
        tablaUsuarios.setRowSorter(tablaSorteada);
    }//GEN-LAST:event_filtrosItemStateChanged

    private void tablaUsuariosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaUsuariosMouseClicked

        int seleccion = tablaUsuarios.getSelectedRow();// recoge la selecion

        this.textoIdUsuario.setText((String) tablaUsuarios.getValueAt(seleccion, 0));
        this.textoDpi.setText((String) tablaUsuarios.getValueAt(seleccion, 1));
        this.textoNombres.setText((String) tablaUsuarios.getValueAt(seleccion, 2));
        this.textoApellidos.setText((String) tablaUsuarios.getValueAt(seleccion, 3));

        try {
            this.textoFechaNacimiento.enableInputMethods(true);
            this.textoFechaNacimiento.setDate(formateador.parse((String) tablaUsuarios.getValueAt(seleccion, 4)));

        } catch (ParseException ex) {
            ex.printStackTrace();
        }

    }//GEN-LAST:event_tablaUsuariosMouseClicked

    private void botonGuardarContrasenaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonGuardarContrasenaActionPerformed
        cambiarContrasena();
    }//GEN-LAST:event_botonGuardarContrasenaActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
       reiniciarEditado();
       this.mostrarCambio = true;
       botonCambiarContrasena();
       recargarTabla();
    }//GEN-LAST:event_jButton4ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botonGuardarContrasena;
    private javax.swing.JButton botonMostrarConfirmacion;
    private javax.swing.JButton botonMostrarContra;
    private javax.swing.JComboBox<String> filtros;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel labelCambiarContra;
    private javax.swing.JLabel labelConfirmacionContra;
    private javax.swing.JLabel labelNuevaContra;
    private javax.swing.JTable tablaUsuarios;
    private javax.swing.JTextField textoApellidos;
    private javax.swing.JPasswordField textoConfirmacionContra;
    private javax.swing.JTextField textoDpi;
    private com.toedter.calendar.JDateChooser textoFechaNacimiento;
    private javax.swing.JTextField textoFiltros;
    private javax.swing.JTextField textoIdUsuario;
    private javax.swing.JTextField textoNombres;
    private javax.swing.JPasswordField textoNuevaContra;
    // End of variables declaration//GEN-END:variables
}
