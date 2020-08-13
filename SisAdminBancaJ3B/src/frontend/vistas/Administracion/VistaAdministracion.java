
package frontend.vistas.Administracion;

import backend.pojos.UsuarioAdministrador;
import frontend.vistas.Login.Login;

public class VistaAdministracion extends javax.swing.JFrame {

    private UsuarioAdministrador usuario;

    public VistaAdministracion(UsuarioAdministrador usuario) {
        initComponents();
        this.usuario = usuario;
        this.textoUsuarioEnSesion.setText(usuario.getUsuario());
    }
    
    public void cerrarSesion(){
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

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Vista Administracion");
        desktopPane.add(jLabel1);
        jLabel1.setBounds(180, 20, 160, 18);

        jLabel2.setText("Usuario en Sesion:");
        desktopPane.add(jLabel2);
        jLabel2.setBounds(10, 70, 140, 18);

        textoUsuarioEnSesion.setText("Aqui va el usuario");
        desktopPane.add(textoUsuarioEnSesion);
        textoUsuarioEnSesion.setBounds(150, 70, 120, 18);

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

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(desktopPane, javax.swing.GroupLayout.DEFAULT_SIZE, 577, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(desktopPane, javax.swing.GroupLayout.DEFAULT_SIZE, 399, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void openMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openMenuItemActionPerformed
     cerrarSesion();
     
    }//GEN-LAST:event_openMenuItemActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDesktopPane desktopPane;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenuItem openMenuItem;
    private javax.swing.JLabel textoUsuarioEnSesion;
    // End of variables declaration//GEN-END:variables

}
