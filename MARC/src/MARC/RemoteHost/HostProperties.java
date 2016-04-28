package MARC.RemoteHost;

import MARC.myjavaproject.Server.RMI.ServerAdmin;
import MARC.myjavaproject.utilities.InetAdrUtility;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.Hashtable;
import javax.swing.JOptionPane;


public class HostProperties {

    public static String host_os_name;
    public static String host_computer_name;
    public static String host_user_name;
 
    

    public static Hashtable getLocalProperties() {
        Toolkit tk = Toolkit.getDefaultToolkit();        
        Hashtable<String, Object> localProperties = new Hashtable<String, Object>();        
        localProperties.put("host-address", InetAdrUtility.getLocalAdr().toString());
        localProperties.put("java.version", System.getProperty("java.version"));
        localProperties.put("os.name", System.getProperty("os.name"));
        localProperties.put("os.arch", System.getProperty("os.arch"));
        localProperties.put("os.version", System.getProperty("os.version"));
        localProperties.put("user.name", System.getProperty("user.name"));
        localProperties.put("user.dir", System.getProperty("user.dir"));
        localProperties.put("screen.size", tk.getScreenSize());
        localProperties.put("screen.resolution", tk.getScreenResolution());




        return localProperties;
    }  


    public static Dimension size;
    public static void displayRemoteProperties(Hashtable prop) {



        size = (Dimension) prop.get("screen.size");
        
        JOptionPane.showMessageDialog(null,
            "Host: \t" + prop.get("host-address") + "\n\n" +        
            
            "Java version: \t" + prop.get("java.version") + "\n\n" +
            
            "OS: \t" + prop.get("os.name") + ", " +
            prop.get("os.arch") + ", " + prop.get("os.version") + "\n\n" +
            
            "User's name: \t" + prop.get("user.name") + "\n" +
            "User's current directory: \t" + prop.get("user.dir") + "\n\n" +
            
            "Screen resolution: \t" + 
            String.valueOf(size.width) + "x" + String.valueOf(size.height) + "\n" +               
            "Screen size: \t" + prop.get("screen.resolution").toString() + 
            " PPI (Pixels Per Inch)",
            "Remote host properties", JOptionPane.INFORMATION_MESSAGE);


//        host_os_name = (String) localProp.get("os.name");
//        Hashtable prop02 = ServerAdmin.prop01;
//        Object host_os_name2 =  prop.get("os.version");
//        host_os_name = String.valueOf(host_os_name2);
    }


//    public static Hashtable prop02 = ServerAdmin.prop01;
    public static void displayRemotePropertiesOnTable(Hashtable prop)
    {
//        Object host_os_name2 =  prop02.get("os.version");
//        host_os_name = String.valueOf(host_os_name2);
//        host_os_name = (String) prop.get("os.name");
        host_os_name = (String) getLocalProperties().get("os.name");
        host_computer_name = (String) getLocalProperties().get("host-address");
        host_user_name = (String) getLocalProperties().get("user.name");
        
    }
}
