package main;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Osi
 */
import javacon.javaconnect;
import Upload.Upload;
import download.download;
import java.awt.*;
import javax.swing.*;
import java.sql.*;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;



public class Login_jframe extends javax.swing.JFrame {
public static String kul_adi="";
public static String yol2 = "";
Connection conn = null;
ResultSet rs = null;
PreparedStatement pst =null;
PreparedStatement degisken = null;
ResultSet kullanici = null;
String klasor = null;


    /**
     * Creates new form Login_jframe
     */
    public Login_jframe() {
        initComponents();
        conn = javaconnect.ConnectDB();
        jLabel3.setVisible(false);
        jButton1.setVisible(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txt_password = new javax.swing.JPasswordField();
        txt_username = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        cmd_login = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("OmuCloud");
        setBackground(new java.awt.Color(51, 153, 255));
        setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Kullanıcı Girişi", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 18), new java.awt.Color(51, 153, 255))); // NOI18N
        jPanel1.setPreferredSize(new java.awt.Dimension(308, 207));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 153, 255));
        jLabel1.setText("Kullanıcı Adı:");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(51, 153, 255));
        jLabel2.setText("Şifre:");

        cmd_login.setIcon(new javax.swing.ImageIcon("D:\\Belgelerim\\NetBeansProjects\\Jdbc\\src\\main\\Adsız.png")); // NOI18N
        cmd_login.setText("Giriş Yap");
        cmd_login.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmd_loginActionPerformed(evt);
            }
        });

        jLabel3.setText("jLabel3");

        jButton1.setIcon(new javax.swing.ImageIcon("D:\\Belgelerim\\NetBeansProjects\\Jdbc\\src\\main\\sync.png")); // NOI18N
        jButton1.setText("Senkronize");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(cmd_login)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(txt_password)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_username)))
                .addGap(4, 4, 4))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txt_username, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txt_password, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmd_login)
                    .addComponent(jButton1))
                .addContainerGap(58, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 341, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void cmd_loginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmd_loginActionPerformed
        // TODO add your handling code here:
        String sql = "select * from users where email=? and password=?";
        
        try{
        conn = javaconnect.ConnectDB();
        pst = conn.prepareStatement(sql);
        pst.setString(1, txt_username.getText());
        pst.setString(2, MD5.getHash(txt_password.getText()));
        rs = pst.executeQuery();
        if(rs.next()){
        JOptionPane.showMessageDialog(null, "Kullanıcı Adı ve Şifre Doğru");       
        jLabel3.setText(txt_username.getText());
        kul_adi = jLabel3.getText();
        jLabel3.setVisible(true);
        jButton1.setVisible(true);
        //jLabel1.setVisible(false);
        jLabel2.setVisible(false);
        txt_username.setVisible(false);
        txt_password.setVisible(false);
        cmd_login.setVisible(false);
        //Employee_info s = new Employee_info();
        //s.setVisible(true);
        conn.close();
        }
        else{
        JOptionPane.showMessageDialog(null, "Kullanıcı Adı ve Parola Yanlış!!!");
        
        } 
        
        }
        catch(Exception e){
        JOptionPane.showMessageDialog(null, "burada hata" + e);
        }
    }//GEN-LAST:event_cmd_loginActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        String server = "localhost";
        int port = 800;
        String user = "senkron";
        String pass = "senkron";
        int durum = sqldosya.sqldosya.durum_dondur();
        
 
        FTPClient ftpClient = new FTPClient();
 
        try {
            conn = javaconnect.ConnectDB();
            // servera baglan ve giris yap
            //ftpClient.connect(server);
            ftpClient.connect(server, port);
            ftpClient.login(user, pass);
            ftpClient.changeWorkingDirectory("/omucloud_vt/");
            ftpClient.enterLocalPassiveMode();
            String sql2 = "select user_id from users where email = ?";
            degisken = conn.prepareStatement(sql2);
            degisken.setString(1, txt_username.getText());
            kullanici = degisken.executeQuery();
            while(kullanici.next()){
                klasor = MD5.getHash(Integer.toString(kullanici.getInt("user_id")));
            //System.out.println(klasor);
            }
            
            System.out.println("Connected");
 
            yol2 = MD5.getHash(Integer.toString(sqldosya.sqldosya.kullanici_id_al()));
            String remoteDirPath = "/httpdocs/user_uploads/" + klasor;
            String localDirPath = "C:/Users/Osi/Desktop/httpdocs/user_uploads/"+klasor;
            System.out.println(localDirPath);
            if(durum==0){
                Upload.uploadDirectory(ftpClient, remoteDirPath, localDirPath, "", "");
                
            }
            else{
                localDirPath = "C:/Users/Osi/Desktop";
                download.downloadDirStructure(ftpClient, remoteDirPath, "", localDirPath);
		download.downloadDirectory(ftpClient, remoteDirPath, "", localDirPath);
                sqldosya.sqldosya.durum_duzenle();
            }
            conn.close();
 
            // log out and disconnect from the server
            ftpClient.logout();
            ftpClient.disconnect();
 
            System.out.println("Disconnected");
        } catch (Exception ex) {
            //ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "buradaaaaaaaaaaaa"+ex);
        }
        
    }//GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(Login_jframe.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Login_jframe.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Login_jframe.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Login_jframe.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Login_jframe().setVisible(true);
                //Login_jframe.setIconImage(loadImageIcon("logo.png").getImage());
             
                //Image im = Toolkit.getDefaultToolkit().getImage("C:/Users/Osi/Desktop/logo.png");
                //Login_jframe.
              
                //Login_jframe.setIconImage(im);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cmd_login;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPasswordField txt_password;
    private javax.swing.JTextField txt_username;
    // End of variables declaration//GEN-END:variables
}