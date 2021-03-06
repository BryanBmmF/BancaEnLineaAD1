package frontend.vistas.Administracion;

import backend.pojos.UsuarioAdministrador;
import frontend.vistas.Login.Login;
import frontend.vistas.Prestamos.SolicitudesPrestamosDialog;
import frontend.vistas.Transacciones.TransaccionesMonetarias;
import frontend.vistas.cuentaHabientes.CuentaHabienteFrame;
import frontend.vistas.cuentas.PanelGestionCuentas;
import frontend.vistas.tarjetas.SolicitudesTarjetasDialog;
import frontend.vistas.tarjetas.TarjetasFrame;

public class VistaAdministracion extends javax.swing.JFrame {

    private UsuarioAdministrador usuario;
    private PanelGestionCuentas gestionCuentas;

    public VistaAdministracion(UsuarioAdministrador usuario) {
        initComponents();
        this.usuario = usuario;
        this.textoUsuarioEnSesion.setText(usuario.getUsuario());

    }

    public void cerrarSesion() {
        Login ventana = new Login();
        ventana.setVisible(true);
        this.setVisible(false);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        desktopPane = new javax.swing.JDesktopPane();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        textoUsuarioEnSesion = new javax.swing.JLabel();
        menuBar = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        openMenuItem = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        cambioDeContrasenajMenuItem7 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenuItem7 = new javax.swing.JMenuItem();
        jMenu5 = new javax.swing.JMenu();
        jMenuItem6 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Ubuntu", 1, 24)); // NOI18N
        jLabel1.setText("Vista Administracion");
        desktopPane.add(jLabel1);
        jLabel1.setBounds(10, 10, 270, 28);

        jLabel2.setText("Usuario en Sesion:");
        desktopPane.add(jLabel2);
        jLabel2.setBounds(480, 10, 140, 17);

        textoUsuarioEnSesion.setText("Aqui va el usuario");
        desktopPane.add(textoUsuarioEnSesion);
        textoUsuarioEnSesion.setBounds(620, 10, 120, 17);

        fileMenu.setMnemonic('f');
        fileMenu.setText("Cerrar Sesion");

        openMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Z, java.awt.event.InputEvent.CTRL_MASK));
        openMenuItem.setMnemonic('o');
        openMenuItem.setText("Cerrar Sesion");
        openMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(openMenuItem);

        menuBar.add(fileMenu);

        jMenu1.setText("Usuarios");

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_D, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem1.setText("Ver Usuarios");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        menuBar.add(jMenu1);

        jMenu2.setText("Cuenta Habientes");

        jMenuItem2.setText("Administracion ");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem2);

        cambioDeContrasenajMenuItem7.setText("Cambio de contraseña");
        cambioDeContrasenajMenuItem7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cambioDeContrasenajMenuItem7ActionPerformed(evt);
            }
        });
        jMenu2.add(cambioDeContrasenajMenuItem7);

        menuBar.add(jMenu2);

        jMenu3.setText("Cuentas Bancarias");

        jMenuItem3.setText("Gestionar Cuentas");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem3);

        jMenuItem4.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_T, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem4.setText("Transacciones Monetarias");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem4);

        menuBar.add(jMenu3);

        jMenu4.setText("Tarjetas de credito");

        jMenuItem5.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_9, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem5.setText("Solicitudes");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem5);

        jMenuItem7.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_0, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem7.setText("Ver Tarjetas Bloqueadas");
        jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem7ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem7);

        menuBar.add(jMenu4);

        jMenu5.setText("Prestamos");

        jMenuItem6.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_L, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem6.setText("Solicitudes");
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem6);

        menuBar.add(jMenu5);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(desktopPane, javax.swing.GroupLayout.DEFAULT_SIZE, 745, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(desktopPane, javax.swing.GroupLayout.DEFAULT_SIZE, 454, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void openMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openMenuItemActionPerformed
        cerrarSesion();

    }//GEN-LAST:event_openMenuItemActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        VerUsuarios usuarios = new VerUsuarios();
        usuarios.setVisible(true);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        CuentaHabienteFrame cuentaHabiente = new CuentaHabienteFrame();
        cuentaHabiente.setVisible(true);
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        // TODO add your handling code here:
        this.gestionCuentas = new PanelGestionCuentas(true);
        this.gestionCuentas.setVisible(true);

    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        TransaccionesMonetarias menu = new TransaccionesMonetarias(this, true);
        menu.setVisible(true);
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        SolicitudesTarjetasDialog solicitud = new SolicitudesTarjetasDialog(this, true);
        solicitud.setVisible(true);
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
        SolicitudesPrestamosDialog solicitud = new SolicitudesPrestamosDialog(this, true);
        solicitud.setVisible(true);
    }//GEN-LAST:event_jMenuItem6ActionPerformed

    private void cambioDeContrasenajMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cambioDeContrasenajMenuItem7ActionPerformed
        CambiarContrasenaDialog cambioDeContrasena = new CambiarContrasenaDialog(this, true);
        cambioDeContrasena.setVisible(true);
    }//GEN-LAST:event_cambioDeContrasenajMenuItem7ActionPerformed

    private void jMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem7ActionPerformed
        TarjetasFrame tarjetas = new TarjetasFrame(this, true);
        tarjetas.setVisible(true);
    }//GEN-LAST:event_jMenuItem7ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem cambioDeContrasenajMenuItem7;
    private javax.swing.JDesktopPane desktopPane;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenuItem openMenuItem;
    private javax.swing.JLabel textoUsuarioEnSesion;
    // End of variables declaration//GEN-END:variables

}
