package Upload;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.Date;
import java.util.Calendar;
import javacon.javaconnect;




import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import sqldosya.sqldosya;


/**
 *
 * @author Osi
 */
public class Upload {
    
   
    public static void uploadDirectory(FTPClient ftpClient,
			String remoteDirPath, String localParentDir, String remoteParentDir, String lastparent)
			throws IOException {
        
        
        System.out.println("LISTING local directory: " + localParentDir);
	File localDir = new File(localParentDir);
        File[] subFiles = localDir.listFiles();
        String[] kaynaklar = localDir.list();
	Set<String> kaynakisimleri = new HashSet<String>(Arrays.asList(kaynaklar));
	FTPFile[] hedefler = ftpClient.listFiles(remoteDirPath+"/"+remoteParentDir+"/");
        String[] hedefi = new String[30];
        int indis = 0;
        
        /**** String olarak atama işlemi ****/
        for (FTPFile k :hedefler){
            hedefi[indis] = k.getName();
            indis = indis +1;
            //System.out.println(k.getName());
        }
        
        /****Dosya zaman kontrolü ve dosyaların yazılması ****/
        for ( FTPFile dosya : hedefler ) {
            String dosyaismi = dosya.getName();
            if ( !kaynakisimleri.contains( dosyaismi ) ) {
                /**** Eleman dosya ise direk sil ****/
                if (dosya.getType() == FTPFile.FILE_TYPE) {
                    String yol = remoteDirPath+"/"+remoteParentDir+"/"+dosyaismi;
                    ftpClient.deleteFile(yol);
                    sqldosya.sil(dosya, "");
                    System.out.println(dosyaismi+" dosyası silindi.");
                }
                
                /**** Eleman klasör ise sil ****/
                else if(dosya.getType() == FTPFile.DIRECTORY_TYPE){
                    String yol1 = remoteDirPath+"/"+remoteParentDir+"/"+dosyaismi;
                    boolean deleted = ftpClient.removeDirectory(yol1);
                    /**** Klasör direk siliniyor ise sil yoksa RemoveDir fonksiyonunu çağır ****/
                    if (deleted) {
                        System.out.println("Klasor basari ile silindi.");
                        sqldosya.sil(dosya, "");
                    } 
                    else {
                        //System.out.println(yol1+dosya.getName());
                        Remove.RemoveDir.removeDirectory(ftpClient, yol1, "",dosyaismi);
                    }
                }
            }
        }
        
        
        //System.out.println(subFiles.length);
        if (subFiles != null && subFiles.length > 0) {
            for (File item : subFiles) {
                String remoteFilePath = remoteDirPath + "/" + remoteParentDir+ "/" + item.getName();
                if (remoteParentDir.equals("")) {
                    remoteFilePath = remoteDirPath + "/" + item.getName();
                }
                if (item.isFile()) {
                    long tar = item.lastModified();
                    Date date = new Date(tar);
                    if(sqldosya.dosya_varmi(item, lastparent)==0 ||(sqldosya.dosya_varmi(item, lastparent)!=0 && sqldosya.tarih_karsilastir(date, sqldosya.tarih_remote(item.getName()))== 1 )){
                        
                        // dosyayı upload et
                        String localFilePath = item.getAbsolutePath();
                        boolean uploaded = uploadSingleFile(ftpClient,localFilePath, remoteFilePath);
                        if (uploaded) {
                            //System.out.println("UPLOADED a file to: " + remoteFilePath);
                            sqldosya.ekle(item, lastparent);
                        }
                        else {
                            System.out.println("COULD NOT upload the file: " + localFilePath);
                        } 
                    }
                } 
                else {
                    // server üzerinde klasör oluştur.
                    boolean created = ftpClient.makeDirectory(remoteFilePath);
                    if (created) {
                        //System.out.println("CREATED the directory: " + remoteFilePath);
                        sqldosya.klasor_ekle(item, lastparent);
                        
                    } 
                    else {
                        //System.out.println("COULD NOT create the directory: " + remoteFilePath);
                    }
                    // geri kalanları upload et
                    String sonparent = item.getName();
                    String parent = remoteParentDir + "/" + item.getName();
                    if (remoteParentDir.equals("")) {
                        parent = item.getName();
                    }
                    localParentDir = item.getAbsolutePath();
                    //System.out.println("**********"+parent);
                    uploadDirectory(ftpClient, remoteDirPath, localParentDir, parent, sonparent);
                }
            }
        }
    }
    
    
    public static boolean uploadSingleFile(FTPClient ftpClient, 
            String localFilePath, String remoteFilePath) throws IOException {
        File localFile = new File(localFilePath);
        InputStream inputStream = new FileInputStream(localFile);
        try {
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            return ftpClient.storeFile(remoteFilePath, inputStream);
        } 
        
        finally {
            inputStream.close();
            
        }
   
    }
}
