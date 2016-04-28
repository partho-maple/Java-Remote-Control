package MARC.RemoteHost;

import MARC.RemoteHost.ClientMainWindoeForInitiating;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;
import MARC.myjavaproject.main;
import MARC.myjavaproject.utilities.InetAdrUtility;

//import RDP_Util.utilities.InetAdrUtility;


public class HostServerConfig {

    public static String server_address = "127.0.0.1";
    public static int server_port = 6666;    
    public static String username = "admin";
    public static String password = "admin";    
    public static boolean ssl_enabled = false;
    public static boolean multihomed_enabled = false;
    public static boolean default_address = false;
    
    public static void loadConfiguration() {
        if (new File(main.SERVER_CONFIG_FILE).canRead())
            try {
                Properties properties = new Properties();            
                properties.load(new FileInputStream(main.SERVER_CONFIG_FILE));
                
                server_address = properties.get("server-address").toString(); 
                server_port = Integer.valueOf(properties.get("server-port").toString());
                
                username = properties.get("username").toString();
                password = properties.get("password").toString();                
                
                ssl_enabled = Boolean.valueOf(properties.getProperty("ssl-enabled"));
                multihomed_enabled = Boolean.valueOf(properties.getProperty("multihomed-enabled"));                
                
                default_address = Boolean.valueOf(properties.getProperty("default-address"));
            }
            catch (Exception e) {
                e.getStackTrace();
            }
       else
            storeConfiguration();  
    }
    
    public static void storeConfiguration () {
        try {
            new File(main.SERVER_CONFIG_FILE).createNewFile();
            Properties properties = new Properties();
            properties.put("server-address", server_address);
            properties.put("server-port", String.valueOf(server_port));
            properties.put("username", username);
            properties.put("password", password);                
            properties.put("ssl-enabled", String.valueOf(ssl_enabled));
            properties.put("multihomed-enabled", String.valueOf(multihomed_enabled));
            properties.put("default-address", String.valueOf(default_address));
        
            properties.store(new FileOutputStream(main.SERVER_CONFIG_FILE),
                "MARC server configuration file");
        } catch (Exception e) {
            e.getStackTrace();
        }            
    }    
    
    public static void SetConfiguration(int Port, 
            String Username, String Password,
            boolean Ssl_enabled, boolean Multihomed_enabled) { 
        server_address = InetAdrUtility.getLocalAdr().getHostAddress(); 
        server_port = Port; 
        username = Username;
        password = Password;                                
        ssl_enabled = Ssl_enabled;
        multihomed_enabled = Multihomed_enabled; 
        
        storeConfiguration();       
    }    
}
