/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package download;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import main.Login_jframe;

import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

/**
 *
 * @author Osi
 */
public class download {
    public static void downloadDirStructure(FTPClient ftpClient, String parentDir,
			String currentDir, String saveDir) throws IOException {
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

				if (aFile.isDirectory()) {
					String newDirPath = saveDir + parentDir + File.separator
						+ currentDir + File.separator + currentFileName;
					if (currentDir.equals("")) {
						newDirPath = saveDir + parentDir + File.separator
						+ currentFileName;
					}

					// create the directory in saveDir
					File newDir = new File(newDirPath);
					boolean created = newDir.mkdirs();
					if (created) {
						System.out.println("CREATED the directory: " + newDirPath);
					} else {
						System.out.println("COULD NOT create the directory: " + newDirPath);
					}

					// download the sub directory
					downloadDirStructure(ftpClient, dirToList, currentFileName,
							saveDir);
				}
			}
		}
	}
    
    public static void downloadDirectory(FTPClient ftpClient, String parentDir,
			String currentDir, String saveDir) throws IOException {
		String dirToList = parentDir;
                
                File kayit = new File(saveDir+"/httpdocs/user_uploads/"+Login_jframe.yol2);
                System.out.println(saveDir+"/httpdocs/user_uploads/"+Login_jframe.yol2);
                String[] hedefler = kayit.list();
                Set<String> hedefisimleri = new HashSet<String>(Arrays.asList(hedefler));
                FTPFile[] kaynaklar = ftpClient.listFiles(parentDir+"/"+currentDir+"/");
                String[] kaynaki = new String[30];
                File[] dosyalar = kayit.listFiles();
                int indis=0;
                
                for (FTPFile k :kaynaklar){
                    kaynaki[indis] = k.getName();
                    indis = indis +1;
                    //System.out.println("kaynaklar"+k.getName());
                }
                Set<String> kaynakisimleri = new HashSet<String>(Arrays.asList(kaynaki));
                
                for(File b_dosya:dosyalar){
                    //System.out.println("hedefler"+b_dosya.getName());
                    String dosyaismi = b_dosya.getName();
                    if(!kaynakisimleri.contains(b_dosya.getName())){
                        System.out.println("bu dosya kaynakta yok");
                        Remove.RemoveDir.deleteDirectory(b_dosya);
                    }
                    else{
                        System.out.println("bu dosya kaynakta var");
                    }
                    
                }
                
            
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
				String filePath = parentDir + "/" + currentDir + "/"
						+ currentFileName;
				if (currentDir.equals("")) {
					filePath = parentDir + "/" + currentFileName;
				}

				String newDirPath = saveDir + parentDir + File.separator
						+ currentDir + File.separator + currentFileName;
				if (currentDir.equals("")) {
					newDirPath = saveDir + parentDir + File.separator
							  + currentFileName;
				}

				if (aFile.isDirectory()) {
					// create the directory in saveDir
					File newDir = new File(newDirPath);
					boolean created = newDir.mkdirs();
					if (created) {
						System.out.println("CREATED the directory: " + newDirPath);
					} else {
						System.out.println("COULD NOT create the directory: " + newDirPath);
					}
                                        Login_jframe.yol2 = Login_jframe.yol2+"\\"+ currentFileName;

					// download the sub directory
					downloadDirectory(ftpClient, dirToList, currentFileName,
							saveDir);
				} else {
					// download the file
					boolean success = downloadSingleFile(ftpClient, filePath,
							newDirPath);
					if (success) {
						System.out.println("DOWNLOADED the file: " + filePath);
					} else {
						System.out.println("COULD NOT download the file: "
								+ filePath);
					}
				}
			}
		}
	}


	public static boolean downloadSingleFile(FTPClient ftpClient,
			String remoteFilePath, String savePath) throws IOException {
		File downloadFile = new File(savePath);
		OutputStream outputStream = new BufferedOutputStream(
				new FileOutputStream(downloadFile));
		try {
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			return ftpClient.retrieveFile(remoteFilePath, outputStream);
		} catch (IOException ex) {
			throw ex;
		} finally {
			if (outputStream != null) {
				outputStream.close();
			}
		}
	}
    
}
