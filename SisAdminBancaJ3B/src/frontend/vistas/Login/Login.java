/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frontend.vistas.Login;

import backend.controladores.ControladorInicioSesion;
import backend.manejadores.Md5;
import backend.pojos.UsuarioAdministrador;
import frontend.vistas.Administracion.VistaAdministracion;
import javax.swing.JOptionPane;

/**
 *
 * @author jpmazate
 */
public class Login extends javax.swing.JFrame {

    private boolean mostrarContra;
    private ControladorInicioSesion controladorInicioSesion;
    

    public Login() {
        initComponents();
        mostrarContra = false;
        this.getRootPane().setDefaultButton(this.botonIngresar);
        controladorInicioSesion = new ControladorInicioSesion();
    }

    public void ingresar() {
        UsuarioAdministrador mandar = new UsuarioAdministrador(this.textoUsuario.getText(), Md5.getMD5(this.textoContrasena.getText()));
        boolean resultado = controladorInicioSesion.validarDatos(mandar);
        if (resultado) {
            mandarVistaAdministrador(mandar);
        } else {
            JOptionPane.showMessageDialog(null, "Datos Incorrectos, vuelve a intentar");
            this.textoContrasena.setText("");
        }
    }

    public void mandarVistaAdministrador(UsuarioAdministrador usuario) {
        VistaAdministracion menu = new VistaAdministracion(usuario);
        menu.setVisible(true);
        this.setVisible(false);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        textoUsuario = new javax.swing.JTextField();
        textoContrasena = new javax.swing.JPasswordField();
        botonIngresar = new javax.swing.JButton();
        botonVer = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("INICIO DE SESION");

        jLabel2.setText("Usuario:");

        jLabel3.setText("Contraseña:");

        botonIngresar.setText("Ingresar");
        botonIngresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonIngresarActionPerformed(evt);
            }
        });

        botonVer.setText("Ver");
        botonVer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonVerActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(67, 67, 67)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(botonIngresar)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addGap(8, 8, 8)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(textoUsuario)
                            .addComponent(textoContrasena, javax.swing.GroupLayout.DEFAULT_SIZE, 244, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(46, 46, 46)
                                .addComponent(jLabel1)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(botonVer, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(28, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel1)
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(textoUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(textoContrasena, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(botonVer)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(botonIngresar)
                .addContainerGap(52, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void botonVerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonVerActionPerformed
        if (this.mostrarContra == false) {
            this.textoContrasena.setEchoChar((char) 0);
            this.mostrarContra = true;
        } else {
            this.textoContrasena.setEchoChar('*');
            this.mostrarContra = false;
        }
    }//GEN-LAST:event_botonVerActionPerformed

    private void botonIngresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonIngresarActionPerformed
        ingresar();
        
    }//GEN-LAST:event_botonIngresarActionPerformed

/**
 * INSERT DEL USUARIO DE PRUEBA, USUARIO = USUARIO1, CONTRASENA = 123
 * INSERT INTO USUARIO_ADMINISTRADOR VALUES('USUARIO1','1234567891234','202cb962ac59075b964b07152d234b70','USUARIO','PRUEBA','2000-03-10');
 * 
 */    
    


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botonIngresar;
    private javax.swing.JButton botonVer;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPasswordField textoContrasena;
    private javax.swing.JTextField textoUsuario;
    // End of variables declaration//GEN-END:variables
}
