package MARC.myjavaproject.ViewerHelper;

//import MARC.Administrator.ConnectionInfos;
//import MARC.RemoteHost.HostProperties;
import MARC.myjavaproject.Viewer.RMI.Viewer;
import MARC.myjavaproject.Viewer.FileMng.FileManager;
import MARC.myjavaproject.Viewer.FileMng.FileTransGUI;
import RDP_Util.ConnectionInfos;
import RDP_Util.HostProperties;
import java.awt.Rectangle;
import java.io.File;
import java.util.ArrayList;
import java.util.Hashtable;
import javax.swing.ImageIcon;

import MARC.myjavaproject.utilities.InetAdrUtility;
import MARC.myjavaproject.utilities.ZipUtility;
import MARC.myjavaproject.utilities.ClipbrdUtility;


public class Recorder extends Thread {
    
    private boolean recording = false;          // control recording
    private boolean viewOnly = false;
    private boolean pause = false;
    
    public Viewer viewer;    
    public ViewerGUI viewerGUI;
    public ScreenPlayer screenPlayer;
    public ClipbrdUtility clipbrdUtility;
    public ViewerData viewerData; 
    public FileManager fileManager;
    public ConnectionInfos connectionInfos;
    
    public Recorder(Viewer viewer) {
        this.viewer = viewer;      
        start(); 
        
        clipbrdUtility = new ClipbrdUtility();      
//        fileManager = new FileManager(new Administrator.viewer.Recorder(new Viewer())); //  probably here is a mistake
        fileManager = new FileManager(this);
        connectionInfos = new ConnectionInfos(false);
        viewerData = new ViewerData(InetAdrUtility.getLocalAdr());          
        screenPlayer = new ScreenPlayer(this);//  probably here is a mistake
        viewerGUI = new ViewerGUI(this);
    }

//    Recorder(Administrator.viewer.Viewer aThis) {
//        throw new UnsupportedOperationException("Not yet implemented");
//    }
    
    @Override
    public void  run()
    {
        while (true) {
            Wait();

            while (recording && !pause) {                            
                viewer.sendData();            
                viewer.recieveData();
            } 
        }
    }
   
    public void Wait() {
        try {
            synchronized(this) {    
                wait();
            }
        }
        catch (Exception e) {
            e.getStackTrace();
        }         
    }
    
    public void Notify() {
        try {
            synchronized(this){            
                notify();
            }    
        }
        catch (Exception e) {
            e.getStackTrace();
        }   
    }
    
    public void Stop() {
        recording = false;   
        pause = true;
        viewOnly = false;
        clipbrdUtility.removeFlavorListener();
        screenPlayer.removeAdapters();
        screenPlayer.clearScreen();         
        if (viewerGUI.isFullScreenMode())
            viewerGUI.changeFullScreenMode();     
        viewerData.setScreenRect(new Rectangle(0, 0, 0, 0));          
        viewer.disconnect();
    }
    
    public void Start() {
        if (!viewer.isConnected()) 
            if (viewer.connect() == -1) return; 
        viewer.updateOptions(); // send options to server
        connectionInfos.init();
        clipbrdUtility.addFlavorListener();
        screenPlayer.addAdapters();         
        recording = true;            
        pause = false;    
        viewOnly = false;     
        Notify();     
    }
                
    public boolean isRecording () { 
        return recording;
    }
    
    public boolean isPaused() {
        return pause;
    }
    
    public void setPause(boolean bool) {
        pause = bool;
        if (pause)
               screenPlayer.removeAdapters();
        else
        {            
            if (recording && !viewOnly)
               screenPlayer.addAdapters();
            if (recording) Notify();            
        }
    }
    
    public void setViewOnly(boolean bool){
        viewOnly = bool;
        if (viewOnly)
            screenPlayer.removeAdapters();
        else
        {
            if (recording && !pause)
                screenPlayer.addAdapters();
        }
    }
        
    public boolean isViewOnly() {
        return viewOnly;
    }
    
    public ArrayList<Object> getViewerData() {
        ArrayList<Object> data = new ArrayList<Object>();        
        data.add(viewerData.getScreenScale());           
        data.add(viewerData.getScreenRect());          
        data.add(viewerData.getCompressionLevel());            
        data.add(viewerData.isDataCompressionEnabled());        
        data.add(viewerData.getImageQuality());        
        data.add(viewerData.getColorQuality());            
        data.add(viewerData.isClipboardTransferEnabled());
        data.add(viewerData.getInetAddress());
        return data;
    }   
    
    public void updateAllData(ArrayList objects){
        screenPlayer.UpdateScreen((byte[]) objects.get(0));
        
        Object object = null;
        try {
            byte[] data = (byte[]) objects.get(1);
            
            if (viewerData.isDataCompressionEnabled())        
                object = ZipUtility.decompressObject(data);
            else
                object = ZipUtility.byteArraytoObject(data);           
            updataData((ArrayList) object);
        }
        catch (Exception e) {
            e.getStackTrace();
        }          
    }
    
    public void updataData(ArrayList objects) {        
        for (int i=0; i<objects.size(); i++) {
            Object obj = objects.get(i);              
                          
            if (obj instanceof Rectangle)
                viewerData.setScreenRect((Rectangle) obj);    
            else if (obj instanceof String)
                clipbrdUtility.setTextToClipboard((String) obj);
            else if (obj instanceof ImageIcon)
                clipbrdUtility.setImageToClipboard((ImageIcon) obj);  
            else if (obj instanceof File) {
                File[] files = fileManager.getFiles();
                if (files.length == 0) return;
                new FileTransGUI(new Recorder(viewer)).SendFiles(files);     //  probably here is a mistake
            }
            else if (obj instanceof ArrayList)
                new FileTransGUI(new Recorder(viewer)).ReceiveFiles((ArrayList) obj); //  probably here is a mistake
            else if (obj instanceof Hashtable)
                HostProperties.displayRemoteProperties((Hashtable) obj); 
        }
    }
}
