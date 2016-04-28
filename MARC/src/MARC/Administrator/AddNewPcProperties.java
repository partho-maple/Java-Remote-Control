package MARC.Administrator;


import java.awt.Toolkit;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Hashtable;
import java.util.Properties;
import MARC.myjavaproject.Server.RMI.ServerAdmin;
import MARC.myjavaproject.utilities.InetAdrUtility;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.Hashtable;
import javax.swing.JOptionPane;


public class AddNewPcProperties {

    public static String server_Ip ;
    public static int server_prt ;
    public static String user_name ;
    public static String user_password ;
    public static boolean ssl_condition ;



    public static Hashtable storeAddNewPcProperties() {
        Toolkit tk = Toolkit.getDefaultToolkit();
        Hashtable<String, Object> newPcProperties = new Hashtable<String, Object>();
        newPcProperties.put("server_Ip", InetAdrUtility.getLocalAdr().toString());
        newPcProperties.put("server_prt", System.getProperty("java.version"));
        newPcProperties.put("ser_name", System.getProperty("os.name"));
        newPcProperties.put("user_password", System.getProperty("os.arch"));
        newPcProperties.put("ssl_condition", System.getProperty("os.version"));
        




        return newPcProperties;
    }


    
    public static void getAddNewPcProperties(Hashtable prop) {

   
       prop.get("server_Ip");

            
    }



}
