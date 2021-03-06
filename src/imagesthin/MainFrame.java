/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imagesthin;

import java.awt.BorderLayout;
import static java.awt.BorderLayout.CENTER;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Borys
 */
public class MainFrame extends javax.swing.JFrame {
    private static MainFrame main;
    private BufferedImage original;
    private BufferedImage filtered;
    private BufferedImage initial;
    private File f;
    /**
     * Creates new form MainFrame
     */
    public MainFrame() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        menuBar = new javax.swing.JMenuBar();
        processing = new javax.swing.JMenu();
        thinningOne = new javax.swing.JMenuItem();
        thinningTwo = new javax.swing.JMenuItem();
        openImage = new javax.swing.JMenuItem();
        refreshImage = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Images thinning");
        setLocation(new java.awt.Point(300, 300));
        setMinimumSize(new java.awt.Dimension(675, 517));

        mainPanel.setAutoscrolls(true);
        mainPanel.setLayout(new java.awt.BorderLayout());

        menuBar.setAlignmentX(100.0F);
        menuBar.setAlignmentY(100.0F);

        processing.setText("Images processing");

        thinningOne.setText("Thinning 1");
        processing.add(thinningOne);

        thinningTwo.setText("Thninning 2");
        thinningTwo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                thinningTwoActionPerformed(evt);
            }
        });
        processing.add(thinningTwo);

        openImage.setText("Open image");
        openImage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openImageActionPerformed(evt);
            }
        });
        processing.add(openImage);

        refreshImage.setText("Refresh image");
        refreshImage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshImageActionPerformed(evt);
            }
        });
        processing.add(refreshImage);

        menuBar.add(processing);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void thinningTwoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_thinningTwoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_thinningTwoActionPerformed

    private void openImageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openImageActionPerformed
        JFileChooser choice = new JFileChooser();
        final int opt = choice.showDialog(choice, null);
        if (opt != JFileChooser.APPROVE_OPTION) {
                return;
            }
        mainPanel.removeAll();
        f = choice.getSelectedFile();
        System.out.println("Opening image: "+f.getName());
        try {
            original = ImageIO.read(f);
            filtered = ImageIO.read(f);
            initial = original;
            changePic(mainPanel,original);
        } catch (IOException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_openImageActionPerformed

    private void refreshImageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshImageActionPerformed
        mainPanel.removeAll();
        changePic(mainPanel,initial);
        original = takeNew();
        filtered = original;
        System.out.println("Refreshed");
    }//GEN-LAST:event_refreshImageActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                main = new MainFrame();
                main.setVisible(true);
            }
        });
    }
    private void changePic(JPanel panel, BufferedImage image){
        Image oimg= image.getScaledInstance(500, -1 , Image.SCALE_SMOOTH);
        ImageIcon oicon = new ImageIcon(oimg);
        JLabel originalLab;
        originalLab = new JLabel(oicon);
        originalLab.setSize(oimg.getWidth(originalLab),oimg.getHeight(originalLab));
        panel.setSize(originalLab.getSize());
        panel.add(originalLab,BorderLayout.CENTER);
        panel.revalidate();
        panel.repaint();
    }
    private BufferedImage takeNew(){
        BufferedImage image = null;
        try {
            image = ImageIO.read(f);
        } catch (IOException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        return image;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel mainPanel;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenuItem openImage;
    private javax.swing.JMenu processing;
    private javax.swing.JMenuItem refreshImage;
    private javax.swing.JMenuItem thinningOne;
    private javax.swing.JMenuItem thinningTwo;
    // End of variables declaration//GEN-END:variables
}
