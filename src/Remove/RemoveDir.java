/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Remove;

import java.io.File;
import java.io.IOException;
 
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import sqldosya.sqldosya;

/**
 *
 * @author Osi
 */
public class RemoveDir {
    
    public static void removeDirectory(FTPClient ftpClient, String parentDir,
            String currentDir,String lastparent) throws IOException {
        System.out.println(parentDir+"  "+currentDir+"  "+lastparent);
        String dirToList = parentDir;
        if (!currentDir.equals("")) {
            dirToList += "/" + currentDir;
        }
 
        FTPFile[] subFiles = ftpClient.listFiles(dirToList);
 
        if (subFiles != null && subFiles.length > 0) {
            for (FTPFile aFile : subFiles) {
                String currentFileName = aFile.getName();
                if (currentFileName.equals(".") || currentFileName.equals("..")) {
                    // skip parent directory and the directory itself
                    continue;
                }
                
                String filePath = parentDir + "/" + currentDir + "/" + currentFileName;
                //System.out.println("if e girmeden lastparent"+lastparent);
                //System.out.println("if e girmeden parentDir"+parentDir);
                //System.out.println("if e girmeden current"+currentDir);
                //System.out.println("if e girmeden currentfilename"+currentFileName);
                
                if (currentDir.equals("")) {
                    filePath = parentDir + "/" + currentFileName;
                }
 
                if (aFile.isDirectory()) {
                    String sonparent = currentFileName;
                    //System.out.println("if dongusunde sonparent"+sonparent);
                    // remove the sub directory
                    //System.out.println("yeni fonksiyon çağırıldı");
                    removeDirectory(ftpClient, dirToList, currentFileName, sonparent);
                }
                else {
                    // delete the file
                    boolean deleted = ftpClient.deleteFile(filePath);
                    if (deleted) {
                        //System.out.println("silmeden önceeeeee"+aFile.getName()+lastparent);
                        //sqldosya.sil(aFile, lastparent);
                        //System.out.println("****************"+ sqldosya.parent_id_al(aFile.getName()));
                        sqldosya.sil(aFile, lastparent);
                        //
                        System.out.println("DELETED the file: " + filePath);
                    } else {
                        System.out.println("CANNOT delete the file: "
                                + filePath);
                    }
                }
                //sqldosya.klasor_sil(lastparent);
            }
 
            // finally, remove the directory itself
            boolean removed = ftpClient.removeDirectory(dirToList);
            if (removed) {
                sqldosya.klasor_sil(lastparent);
                //System.out.println("son klasor : "+aFile+sonparent);
                //sqldosya.sil(aFile, sonparent);
                System.out.println("REMOVED the directory: " + dirToList);
            } else {
                System.out.println("CANNOT remove the directory: " + dirToList);
            }
        }
    }
    
    
    public static boolean deleteDirectory(File directory) {
    if(directory.exists()){
        File[] files = directory.listFiles();
        if(null!=files){
            for(int i=0; i<files.length; i++) {
                if(files[i].isDirectory()) {
                    deleteDirectory(files[i]);
                }
                else {
                    files[i].delete();
                }
            }
        }
    }
    return(directory.delete());
    }
}
    
