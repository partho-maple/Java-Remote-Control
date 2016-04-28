package MARC.myjavaproject.Viewer.RMI;

import MARC.Administrator.AddNewPcGUI;
import MARC.Administrator.HostConfigForAddingPc;
import MARC.Administrator.ServerMainWindoeForInitiating;
//import RDP_Util.server.rmi.ServerInterface;
//import RDP_Util.viewer.Recorder;
//import RDP_Util.utilities.InetAdrUtility;
//import RDP_Util.HostProperties;
//import RDP_Util.utilities.FileUtility;
//import RDP_Util.utilities.ZipUtility;
import MARC.myjavaproject.ViewerHelper.Recorder;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import javax.rmi.ssl.SslRMIClientSocketFactory;
import javax.swing.JOptionPane;
import MARC.RemoteHost.HostProperties;
import MARC.myjavaproject.LogInfo.ForAdministrator.AppLogInfoForAdministrator;
import MARC.myjavaproject.Server.RMI.ServerAdmin;
import java.util.Hashtable;
import MARC.myjavaproject.utilities.InetAdrUtility;
import MARC.myjavaproject.Server.RMI.ServerInterface;
import MARC.myjavaproject.utilities.ZipUtility;
import MARC.myjavaproject.utilities.FileUtility;
import javax.swing.table.DefaultTableModel;



public class Viewer extends Thread {
    
    private int index = -1;
//    private Recorder recorder;
    public  Recorder recorder;
    
    private Registry registry; 
    private ServerInterface rmiServer;
       
    private String server = "127.0.0.1";
    private int port = 6666;
    private String username = "admin";
    private String password = "admin";
    private boolean ssl_enabled = false;
    
    private boolean connected = false;
    
    private ArrayList<Object> Objects;
//    public static Viewer viw = new Viewer();


//    public Hashtable<Integer,Recorder> abcd = new Hashtable<Integer,Recorder>;

        
    public Viewer () {
        HostConfigForAddingPc.loadConfiguration();
        server = HostConfigForAddingPc.server_address;
        port = HostConfigForAddingPc.server_port;
        username = HostConfigForAddingPc.username;
        password = HostConfigForAddingPc.password;
        ssl_enabled = HostConfigForAddingPc.ssl_enabled;
        
        if (ssl_enabled) {     
            FileUtility.checkFile(ServerMainWindoeForInitiating.KEY_STORE, "keystore");
            FileUtility.checkFile(ServerMainWindoeForInitiating.TRUST_STORE, "truststore");
            ServerMainWindoeForInitiating.setStoreProperties();
        }
        else
            ServerMainWindoeForInitiating.clearStoreProperties();
    }   
    
    public boolean isConnected() {
        return connected;
    }
    
    public void Start() { 
        connect();
        if (connected) {
            recorder = new Recorder(this);  //  probably here i hava to make a hash table
            recorder.viewerGUI.Start();


//            ServerMainWindoeForInitiating.HostID.put((String) AddNewPcGUI.jTextFieldIPAdr.getText(),recorder);
//            ServerMainWindoeForInitiating.Id ++;

//            ServerAdmin.displayViewerPropertiesOnTable(ServerMainWindoeForInitiating.tableDispIndex);
//        ServerMainWindoeForInitiating.saveOnDirectAccTable(ServerMainWindoeForInitiating.tableDispIndex);


        }        
        else Stop();
    }
    
    public void Stop() {
        if (recorder != null) {
            recorder.Stop();
            recorder.interrupt();
        }
        disconnect();    
        interrupt();
    }
    
    public int connect() {  
        connected = false;
        
        try {       
            if (ssl_enabled)
                registry = LocateRegistry.getRegistry(server, port, 
                        new SslRMIClientSocketFactory());
            else
                registry = LocateRegistry.getRegistry(server, port);        
          
            rmiServer = (ServerInterface) registry.lookup("ServerImpl");                          
         
            index = rmiServer.startViewer(InetAdrUtility.getLocalAdr(),
                    username, password);
            if (index == -1) {
                JOptionPane.showMessageDialog(null, 
                        "Wrong username or password !!", "Error ! !! !!!",
                    JOptionPane.ERROR_MESSAGE);   
                return -1;
            }                         


            displayStatusAndUpdConnTable();
            


            Objects = new ArrayList<Object>();
            Objects.add(HostProperties.getLocalProperties());
            connected = true;
            return index;
       } catch (Exception e) {    
           JOptionPane.showMessageDialog(null, e.getMessage(), "Error ! !! !!! !!!!",
                    JOptionPane.ERROR_MESSAGE);
           return -1;
       }     
    }
    
    public void disconnect() {
        connected = false;
        try {
            if (rmiServer != null) {
                    rmiServer.stopViewer(index);
                    UnicastRemoteObject.unexportObject(rmiServer, true);
            }
        }
        catch (Exception e) {
            e.getStackTrace(); 
        } 
      rmiServer = null;
      registry = null;
    }
    
    public void updateData(Object object) {
        byte[] data;
        try {
            if (recorder.viewerData.isDataCompressionEnabled())
                data = ZipUtility.compressObject(object, 
                        recorder.viewerData.getCompressionLevel());        
            else 
                data = ZipUtility.objecttoByteArray(object);
            
            recorder.connectionInfos.incSentData(data.length);
            updateData(data);  
        }
        catch (IOException e) {
            e.getStackTrace();
        }
    }
    
    public void updateData(byte[] data) {
        try {rmiServer.updateData(data, index);} 
        catch (Exception re) {
            re.getStackTrace();
        }        
    }
    
    public void AddObject(Object object) {
        Objects.add(object);
    }   
   
    public void updateOptions () {     
        try {rmiServer.updateOptions(
                recorder.getViewerData(), index);
        } 
        catch (Exception re) {
            re.getStackTrace();
        }          
    }
    
    public void sendData() {
        ArrayList SendObjects;       
        synchronized(Objects){              
            if (recorder.viewerData.isClipboardTransferEnabled())
                if (!recorder.clipbrdUtility.isEmpty())
                Objects.add(recorder.clipbrdUtility.getContent());              
            
            SendObjects = Objects; 
            Objects = new ArrayList<Object>();
        }
        updateData(SendObjects);
    }     
    
    public void recieveData() {
        Object object = null;
        try {
            byte[] data = rmiServer.updateData(index);
            object = ZipUtility.byteArraytoObject(data);              
                         
            recorder.connectionInfos.incReceivedData(data.length);
            recorder.updateAllData((ArrayList) object);    
        }
        catch (Exception e) {
            e.getStackTrace();
        }           
    }   
    
    public byte[] ReceiveFile(String filename) {
        try {            
            byte[] data = rmiServer.ReceiveFile(filename, index);
            recorder.connectionInfos.incReceivedData(data.length);
            return data;
        } catch(RemoteException re) {
            re.getStackTrace();
            return null;
        }
    }
    
    public void SendFile (byte[] buffer, String filename) {
        try {        
            rmiServer.SendFile(buffer, filename, index);
            recorder.connectionInfos.incSentData(buffer.length);
       } catch(RemoteException re) {
            re.getStackTrace();
       }        
    } 


    public static  Boolean flag = false ;
    public void displayStatusAndUpdConnTable() {


        // Updates the tables connection status
//        ServerMainWindoeForInitiating.connection_sts =  ServerMainWindoeForInitiating.connected;
//        ServerMainWindoeForInitiating.directAccTable1.setValueAt(ServerMainWindoeForInitiating.connection_sts, ServerMainWindoeForInitiating.i, 1);
//        ServerMainWindoeForInitiating.directAccTable1.setValueAt(ServerMainWindoeForInitiating.connected, ServerMainWindoeForInitiating.i, 1);

        // this makes the connection_sts true that means Connected
//        ServerMainWindoeForInitiating.connection_sts = Boolean.parseBoolean("True");
//        ServerMainWindoeForInitiating.directAccTable1.setEnabled(Boolean.TRUE);
//        ServerMainWindoeForInitiating.directAccTable1.setColumnSelectionAllowed(true);
//        ServerMainWindoeForInitiating.directAccTable1.setRowSelectionAllowed(true);
//        ServerMainWindoeForInitiating.directAccTable1.editCellAt(ServerMainWindoeForInitiating.i, 5);
//        ServerMainWindoeForInitiating.directAccTable1.setValueAt(ServerMainWindoeForInitiating.directAccTable1.getValueAt(ServerMainWindoeForInitiating.i, 6), ServerMainWindoeForInitiating.i, 5);

//        this line changes the connection status
//        ServerMainWindoeForInitiating.directAccTable1.setValueAt(ServerMainWindoeForInitiating.connection_sts, ServerMainWindoeForInitiating.i, 5);

//////        ServerMainWindoeForInitiating.i = ServerMainWindoeForInitiating.i + 1;
////////        ServerMainWindoeForInitiating.tableDispIndex = ServerMainWindoeForInitiating.tableDispIndex + 1 ;


        //  this line store the information on the account info table , which need passward to view
////////        storeAccInfoOnInfoTable();
//        saveOnDirectAccTable(int p)


////////        ServerAdmin.displayViewerPropertiesOnTable(ServerMainWindoeForInitiating.tableDispIndex);
////////        ServerMainWindoeForInitiating.saveOnDirectAccTable(ServerMainWindoeForInitiating.tableDispIndex);





        boolean auth = (username.length() != 0) || (password.length() != 0);
        System.out.println("Viewer connected to " + rmiServer +                                   
            "\n\tauthentication: " + (auth == true ? "enabled" : "desabled") +
            "\n\tencryption: " + (ssl_enabled == true ? "enabled" : "desabled"));


//        ServerMainWindoeForInitiating.saveOnDirectAccTable(ServerMainWindoeForInitiating.tableDispIndex);

//        ServerAdmin.displayViewerPropertiesOnTable(ServerMainWindoeForInitiating.tableDispIndex);
//        ServerMainWindoeForInitiating.saveOnDirectAccTable(ServerMainWindoeForInitiating.tableDispIndex);

        flag = true;

        if(ServerMainWindoeForInitiating.fromConntctPCButt == Boolean.FALSE)
        {
            if(Viewer.flag)
            {

//                ServerMainWindoeForInitiating.tableRowCount = ServerMainWindoeForInitiating.tableRowCount + 1;
                ServerMainWindoeForInitiating.tableDispIndex = ServerMainWindoeForInitiating.tableDispIndex + 1 ;


             //   these lines add a row dynamically on Direct IP Account Info Table and account info table
                Object[] rowData = new Object[0];
                DefaultTableModel DTM = (DefaultTableModel) ServerMainWindoeForInitiating.directAccTable1.getModel();
                DTM.insertRow(ServerMainWindoeForInitiating.tableDispIndex, rowData);
                DefaultTableModel DTM2 = (DefaultTableModel) ServerMainWindoeForInitiating.userAccInfoTable.getModel();
                DTM2.insertRow(ServerMainWindoeForInitiating.tableDispIndex, rowData);



             //  this line store the information on the account info table , which need passward to view
                Viewer.storeAccInfoOnInfoTable();



            //        this line store the information on the Direct IP Account Info Table
                ServerMainWindoeForInitiating.saveOnDirectAccTable(ServerMainWindoeForInitiating.tableDispIndex);
            }
        }



//          this line shows log info on consone and adminLogInfoTextArea
            if(ServerMainWindoeForInitiating.isLogEnable == Boolean.TRUE)
            {
                AppLogInfoForAdministrator.logger.info("Viewer connected to " + rmiServer +
            "\n\tauthentication: " + (auth == true ? "enabled" : "desabled") +
            "\n\tencryption: " + (ssl_enabled == true ? "enabled" : "desabled"));
            }




    }


    private static Boolean ssl_con ;
    public static void storeAccInfoOnInfoTable()
    {
       ServerMainWindoeForInitiating.userAccInfoTable.setValueAt(AddNewPcGUI.jTextFieldIPAdr.getText(), ServerMainWindoeForInitiating.tableDispIndex, 0);
       ServerMainWindoeForInitiating.userAccInfoTable.setValueAt(AddNewPcGUI.jTextFieldPort.getText(), ServerMainWindoeForInitiating.tableDispIndex, 1);
       ServerMainWindoeForInitiating.userAccInfoTable.setValueAt(AddNewPcGUI.jTextFieldUsername.getText(), ServerMainWindoeForInitiating.tableDispIndex, 2);
       ServerMainWindoeForInitiating.userAccInfoTable.setValueAt(String.copyValueOf(AddNewPcGUI.jPasswordField.getPassword()), ServerMainWindoeForInitiating.tableDispIndex, 3);
       
       if(AddNewPcGUI.jCheckBoxSSLEnabled.isSelected())
             ssl_con = Boolean.parseBoolean("True");
       else ssl_con = Boolean.parseBoolean("yes");
       ServerMainWindoeForInitiating.userAccInfoTable.setValueAt(ssl_con, ServerMainWindoeForInitiating.tableDispIndex, 4);

    }


}
