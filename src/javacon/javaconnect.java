package javacon;


import java.sql.*;
import javax.swing.*;

public class javaconnect {
    Connection conn = null;
    public static Connection ConnectDB(){
    try{
    Class.forName("com.mysql.jdbc.Driver");
    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/omucloud_vt", "root", "");
    return conn;
    }
    catch(Exception e)
    {
        
        JOptionPane.showMessageDialog(null, "veritabanı hatası"+e);
        return null;
			
    }
    }
    
}
