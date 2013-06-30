/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sqldosya;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javacon.javaconnect;
import main.Login_jframe;
import org.apache.commons.net.ftp.FTPFile;

/**
 *
 * @author Osi
 */
public class sqldosya extends Login_jframe{
    
    public static void ekle(File dosya, String parent){
        try
        {
            Connection conn = javacon.javaconnect.ConnectDB();
 
            long deg = dosya.lastModified();
            String isim = dosya.getName();
            //Date local = new Date(deg);
            if (sqldosya.dosya_varmi(dosya, parent)==0){
                // the mysql insert statement
                String query = " insert into files (owner, name, description, size, modified, owner_folder, folder)" + " values (?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement pst = conn.prepareStatement(query);
                pst.setInt(1, kullanici_id_al() );
                pst.setString (2, dosya.getName());
                pst.setString(3, "aciklama");
                pst.setLong(4, dosya.length());
                //pst.setTimestamp(5, new Timestamp(local.getTime()));
                pst.setLong(5, deg);
                pst.setInt(6, sqldosya.parent_id_al(parent));
                pst.setInt(7, 0);
                // execute the preparedstatement
                pst.execute();
                
            }
            else{
                String query = "update files set modified = ? where name = ? and owner_folder= ?";
                PreparedStatement pst = conn.prepareStatement(query);
                pst.setLong(1, deg);
                pst.setString(2, isim);
                pst.setInt(3, sqldosya.parent_id_al(parent));
                pst.execute();
            }
            conn.close();
    }
    catch (Exception e)
    {
      System.err.println("Got an exception!");
      System.err.println(e.getMessage());
    }
  }    
    public static Date tarih_remote(String dosya_ismi){
        Date date = new Date();
        long tar = 0;
        try {
            Connection conn = javacon.javaconnect.ConnectDB();
            String query = " select modified from files where name=? and owner=(select user_id from users where email=?) ";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, dosya_ismi);
            pst.setString(2, Login_jframe.kul_adi);
            ResultSet rs = pst.executeQuery();
            while (rs.next()){
                //date = rs.getTimestamp("modified");
                tar = rs.getLong("modified");
            }
            date = new Date(tar);
            conn.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return date;
        
        
    }
    
    /*** Bilgisayardaki dosya tarihi sunucudaki dosya tarihini karşılaştırır
    uzak sunucu daha güncelse -1, tarihler eşitse 0, bilgisayardakiler güncelse 1 döndürür.  ***/
    public static int tarih_karsilastir(Date local, Date remote){
        int sonuc = 5;
        if(local.before(remote)){
            sonuc = -1;
        }
        else if (local.equals(remote)){
            sonuc = 0;
        }
        else if (local.after(remote)){
            sonuc = 1;
        }
        return sonuc;
    }
    
    
    public static int kullanici_id_al(){
        int dondur=0;
        try {
            Connection conn = javacon.javaconnect.ConnectDB();
            String query = " select user_id from users where email=? ";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, Login_jframe.kul_adi);
            ResultSet rs = pst.executeQuery();
            while (rs.next()){
                dondur = rs.getInt("user_id");
            }
            conn.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return dondur;
        
    }
    
    
    public static int  dosya_varmi (File dosya, String alt_klasor) {
        int rowCount = 0;
        try {
            
            Connection conn = javacon.javaconnect.ConnectDB();
            String query = " select * from files where name=? and owner=(select user_id from users where email=? ) and owner_folder=? and folder=?";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, dosya.getName());
            pst.setString(2, Login_jframe.kul_adi);
            pst.setInt(3, sqldosya.parent_id_al(alt_klasor));
            if(dosya.isFile()){
                pst.setInt(4, 0);
            }
            else{
                pst.setInt(4, 1);
            }
            
            ResultSet rs = pst.executeQuery();
            while (rs.next()){
                rowCount = rowCount+1;
            }
            conn.close();

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return rowCount;
    }
    
    
    public static void  klasor_ekle (File dosya, String parent){
        try {
            Connection conn = javacon.javaconnect.ConnectDB();
 
            long deg = dosya.lastModified();
            //String isim = dosya.getName();
            String query = " insert into files (owner, name, modified, owner_folder, folder)" + " values (?, ?, ?, ?, ?)";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setInt(1, kullanici_id_al() );
            pst.setString (2, dosya.getName());
            pst.setLong(3, deg);
            pst.setInt(4, sqldosya.parent_id_al(parent));
            pst.setInt(5, 1);
            // execute the preparedstatement
            pst.execute();
                
            conn.close();
            
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
    
    /*** Argüman olarak verilen parentin file_id' sini alır ***/
    public static int parent_id_al(String alt_klasor){
        int id = 0;
        Connection conn = javacon.javaconnect.ConnectDB();
        try {
            if (alt_klasor.compareTo("")!=0){
                
                String query = " select file_id from files where name=?";
                PreparedStatement pst = conn.prepareStatement(query);
                pst.setString (1, alt_klasor);
                ResultSet rs = pst.executeQuery();
                while (rs.next()){
                    id = rs.getInt("file_id");
                }
            }
            conn.close();
            
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        
        
        return id;
    }
    
    
    public static void sil(FTPFile dosya, String parent){
        int parent_id = parent_id_al(parent);
        System.out.println(parent_id);
        Connection conn = javacon.javaconnect.ConnectDB();
        try
        {
            
            String query = "delete from files where name=? and owner_folder=? and folder=?";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, dosya.getName());
            pst.setInt(2, parent_id);
            if(dosya.isFile()){
                pst.setInt(3, 0);
            }
            else{
                pst.setInt(3, 1);
            }
            pst.execute();
            conn.close();
    }
    catch (Exception e)
    {
      System.err.println("Got an exception!");
      System.err.println(e.getMessage());
    }
  }
    
    
    public static void klasor_sil(String parent_adi){   
        Connection conn = javacon.javaconnect.ConnectDB();
        try
        {
            
            String query = "delete from files where file_id=?";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setInt(1, parent_id_al(parent_adi));
            pst.execute();
            conn.close();
        }
        catch (Exception e)
        {
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
        }
    }
    
    public static int durum_dondur(){
        int dondur=0;
        try {
            Connection conn = javacon.javaconnect.ConnectDB();
            String query = " select web from users where email=? ";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, Login_jframe.kul_adi);
            ResultSet rs = pst.executeQuery();
            while (rs.next()){
                dondur = rs.getInt("web");
            }
            conn.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return dondur;
        
    }
    public static void durum_duzenle(){
        try {
            Connection conn = javacon.javaconnect.ConnectDB();
            String query = "UPDATE users SET web = 0 WHERE user_id = ?";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setInt(1, sqldosya.kullanici_id_al());
            pst.execute();
            conn.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
    
    
    /*public static void sil_nop(FTPFile dosya){
        Connection conn = javacon.javaconnect.ConnectDB();
        try
        {
            
            String query = "delete from files where name=?";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, dosya.getName());
            pst.setInt(2, parent_id);
            if(dosya.isFile()){
                pst.setInt(3, 0);
            }
            else{
                pst.setInt(3, 1);
            }
            pst.execute();
            conn.close();
    }
    catch (Exception e)
    {
      System.err.println("Got an exception!");
      System.err.println(e.getMessage());
    }
  }*/
    
}
