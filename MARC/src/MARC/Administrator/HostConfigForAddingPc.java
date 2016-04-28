package MARC.Administrator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;



public class HostConfigForAddingPc {

    public static String server_address = "127.0.0.1";
    public static int server_port = 6666;    
    public static String username = "admin";
    public static String password = "admin";    
    public static boolean ssl_enabled = false;
    
    public static void loadConfiguration() {
        if (new File(ServerMainWindoeForInitiating.VIEWER_CONFIG_FILE).canRead())
            try {
                Properties properties = new Properties();            
                properties.load(new FileInputStream(ServerMainWindoeForInitiating.VIEWER_CONFIG_FILE));
                
                server_address = properties.get("server-address").toString(); 
                server_port = Integer.valueOf(properties.get("server-port").toString());
                
                username = properties.get("username").toString();
                password = properties.get("password").toString();                
                
                ssl_enabled = Boolean.valueOf(properties.getProperty("ssl-enabled"));                               
            }
            catch (Exception e) {
                e.getStackTrace();
            }
       else
            storeConfiguration();    
    }
    
    public static void storeConfiguration () {
        try {
            new File(ServerMainWindoeForInitiating.VIEWER_CONFIG_FILE).createNewFile();
            Properties properties = new Properties();
            properties.put("server-address", server_address);
            properties.put("server-port", String.valueOf(server_port));
            properties.put("username", username);
            properties.put("password", password);                
            properties.put("ssl-enabled", String.valueOf(ssl_enabled));
        
            properties.store(new FileOutputStream(ServerMainWindoeForInitiating.VIEWER_CONFIG_FILE),
                "MARSE-Remote Control viewer configuration file");
        } catch (Exception e) {
            e.getStackTrace();
        }            
    }    
    
    public static void SetConfiguration(String Address, int Port, 
            String Username, String Password, boolean Ssl_enabled) { 
        server_address = Address; 
        server_port = Port; 
        username = Username;
        password = Password;                                
        ssl_enabled = Ssl_enabled;
        
        storeConfiguration();       
    }    
}
