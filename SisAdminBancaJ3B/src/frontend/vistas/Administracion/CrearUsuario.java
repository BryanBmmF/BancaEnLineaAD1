/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frontend.vistas.Administracion;

import backend.controladores.ControladorUsuarios;
import backend.pojos.UsuarioAdministrador;
import java.util.Date;
import javax.swing.JOptionPane;

/**
 *
 * @author jpmazate
 */
public class CrearUsuario extends javax.swing.JDialog {

    private boolean mostrarContra;
    private boolean mostrarContra2;
    private ControladorUsuarios controladorUsuarios;

    public CrearUsuario(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        mostrarContra = false;
        mostrarContra2 = false;
        controladorUsuarios = new ControladorUsuarios();
    }

    public void guardarUsuario() {
        String usuario = this.textoUsuario.getText();
        String contrasena = this.textoContrasena.getText();
        String confirmarContra = this.textoConfirmar.getText();
        String dpi = this.textoDpi.getText();
        String nombres = this.textoNombre.getText();
        String apellidos = this.textoApellidos.getText();
        Date fechaNacimiento = this.textoFecha.getDate();
        if (!usuario.equals("")) {
            if (usuario.startsWith("A")) {
                if (usuario.length() <= 8) {
                    if (!contrasena.equals("")) {
                        if (contrasena.length() <= 35) {
                            if (!confirmarContra.equals("")) {
                                if (confirmarContra.length() <= 35) {
                                    if (contrasena.equals(confirmarContra)) {
                                        if (!dpi.equals("")) {
                                            if (dpi.length() == 13) {
                                                if (!nombres.equals("")) {
                                                    if (nombres.length() <= 35) {
                                                        if (!apellidos.equals("")) {
                                                            if (apellidos.length() <= 35) {
                                                                if (fechaNacimiento.compareTo(new Date()) < 0) {
                                                                    UsuarioAdministrador usuarioAdmin = new UsuarioAdministrador(usuario, dpi, contrasena, nombres, apellidos, fechaNacimiento);
                                                                    boolean resultado = controladorUsuarios.crearUsuario(usuarioAdmin);
                                                                    if (resultado) {
                                                                        JOptionPane.showMessageDialog(null, "SE HA CREADO CON EXITO EL USUARIO: " + usuario);
                                                                        this.setVisible(false);
                                                                    } else {
                                                                        JOptionPane.showMessageDialog(null, "ERRORES AL CREAR EL USUARIO, LOS ERRORES PUEDEN SER:\n"
                                                                                + "1.- EL USUARIO " + usuario + " YA EXISTE\n"
                                                                                + "2.- EL DPI SE REPITE EN EL SISTEMA\n"
                                                                                + "3.- EL NOMBRE ES IGUAL AL DE OTRO REGISTRO");
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
                                        JOptionPane.showMessageDialog(null, "LAS CONTRASEÑAS NO COINCIDEN");
                                    }
                                } else {
                                    JOptionPane.showMessageDialog(null, "EL CAMPO DE CONFIRMAR CONTRASEÑA NO PUEDE PASAR DE 35 CARACTERES, ACTUALES: " + confirmarContra.length());
                                }
                            } else {
                                JOptionPane.showMessageDialog(null, "EL CAMPO DE CONFIRMAR CONTRASEÑA NO PUEDE ESTAR VACIO");
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "EL CAMPO DE CONTRASEÑA NO PUEDE PASAR DE 35 CARACTERES, ACTUALES: " + contrasena.length());
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "EL CAMPO DE CONTRASEÑA NO PUEDE ESTAR VACIO");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "EL CAMPO DE USUARIO NO PUEDE PASAR DE 8 CARACTERES, ACTUALES: " + usuario.length());
                }
            } else {
                JOptionPane.showMessageDialog(null, "EL CAMPO DE USUARIO DEBE DE EMPEZAR CON UNA A");
            }
        } else {
            JOptionPane.showMessageDialog(null, "EL CAMPO DE USUARIO NO PUEDE ESTAR VACIO");
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        textoApellidos = new javax.swing.JTextField();
        textoConfirmar = new javax.swing.JPasswordField();
        textoContrasena = new javax.swing.JPasswordField();
        textoUsuario = new javax.swing.JTextField();
        textoNombre = new javax.swing.JTextField();
        textoFecha = new com.toedter.calendar.JDateChooser();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        textoDpi = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Ubuntu", 1, 24)); // NOI18N
        jLabel1.setText("CREAR USUARIO ADMINISTRADOR");

        jLabel2.setText("*Id Usuario:");

        jLabel3.setText("*Contraseña:");

        jLabel4.setText("*Confirmar Contraseña:");

        jLabel5.setText("*Dpi:");

        jLabel6.setText("*Nombres:");

        jLabel7.setText("*Apellidos:");

        jLabel8.setText("*Fecha Nacimiento:");

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/MostrarContrasena.png"))); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/MostrarContrasena.png"))); // NOI18N
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Ubuntu", 0, 10)); // NOI18N
        jLabel9.setText("Debe Empezar Con A");

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/guardar_opt.png"))); // NOI18N
        jButton1.setText("Guardar Usuario");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addComponent(jLabel7)
                    .addComponent(jLabel6)
                    .addComponent(jLabel5)
                    .addComponent(jLabel4)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 85, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(textoApellidos)
                    .addComponent(textoConfirmar)
                    .addComponent(textoContrasena)
                    .addComponent(textoNombre, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(textoFecha, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(textoUsuario)
                    .addComponent(textoDpi))
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jButton2)
                        .addComponent(jButton3))
                    .addComponent(jLabel9))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addComponent(jLabel1)
                .addGap(31, 31, 31)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(textoUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3)
                        .addComponent(textoContrasena, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(textoConfirmar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(textoDpi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(textoNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(textoApellidos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(textoFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(jLabel8)))
                .addGap(28, 28, 28)
                .addComponent(jButton1)
                .addContainerGap(41, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        guardarUsuario();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        if (this.mostrarContra == false) {
            this.textoContrasena.setEchoChar((char) 0);
            this.mostrarContra = true;
        } else {
            this.textoContrasena.setEchoChar('*');
            this.mostrarContra = false;
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        if (this.mostrarContra2 == false) {
            this.textoConfirmar.setEchoChar((char) 0);
            this.mostrarContra2 = true;
        } else {
            this.textoConfirmar.setEchoChar('*');
            this.mostrarContra2 = false;
        }
    }//GEN-LAST:event_jButton2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JTextField textoApellidos;
    private javax.swing.JPasswordField textoConfirmar;
    private javax.swing.JPasswordField textoContrasena;
    private javax.swing.JTextField textoDpi;
    private com.toedter.calendar.JDateChooser textoFecha;
    private javax.swing.JTextField textoNombre;
    private javax.swing.JTextField textoUsuario;
    // End of variables declaration//GEN-END:variables
}
