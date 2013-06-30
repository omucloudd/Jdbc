package main;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.math.*;
import java.security.*;
/**
 *
 * @author Osi
 */
public class MD5 {
    public static String getHash(String passWord){
		String hash = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(passWord.getBytes(), 0, passWord.length());
			hash = new BigInteger(1, md.digest()).toString(16);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return hash;
	}
    
}
