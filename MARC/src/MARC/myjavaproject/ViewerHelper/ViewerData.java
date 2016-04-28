package MARC.myjavaproject.ViewerHelper;

import MARC.RemoteHost.HostProperties;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.net.InetAddress;
import java.util.Hashtable;
import java.util.zip.Deflater;

//import RDP_Util.HostProperties;


public class ViewerData {

    private InetAddress inetAddress;
    
    private boolean dataCompression = true; 
    
    private float ImageQuality = -1.0f;
    
    private int compressionLevel = Deflater.DEFAULT_COMPRESSION;
    
    private float screenScale = 1.0f;

    private int colorQuality = BufferedImage.TYPE_INT_ARGB;
    
    private boolean clipboardTransfer = true;
    
    private Rectangle ScreenRect = new Rectangle(0 , 0, 0, 0);

    private Hashtable properties = new Hashtable();
            
    public ViewerData () {}
    
    public ViewerData (InetAddress inetAddress) {
        this.inetAddress = inetAddress;
        properties = HostProperties.getLocalProperties();
    }

    // todo check what happen when local ip adr changed
    // solution add isInetAddressChanged

    public InetAddress getInetAddress() {
        return inetAddress;
    }
    
    public void setInetAddress(InetAddress inetAddress) {
        this.inetAddress = inetAddress;
    }
    
    public void setDataCompression(boolean bool) {
        dataCompression = bool;
    }
    
    public boolean isDataCompressionEnabled() {
        return dataCompression;
    }    
    
    public void setCompressionLevel(int level) {
        compressionLevel = level;
    }
            
    public int getCompressionLevel() {
        return compressionLevel;
    }
    
    public void setImageQuality(float cq) {
        ImageQuality = cq;
    }
    
    public float getImageQuality() {
        return ImageQuality;
    }    
    
    public void setColorQuality(int clQuality) {
        colorQuality = clQuality;
    }
    
    public int getColorQuality() {
        return colorQuality;
    }
    
    public void setScreenScale(float screenScale) {
        this.screenScale = screenScale;
    }

    public float getScreenScale() {
        return screenScale;
    }
    
    public void setScreenRect(Rectangle rect) {       
        ScreenRect = rect;  
    }  
    
    public Rectangle getScreenRect() {     
        return ScreenRect;
    } 
    
    public void setClipboardTransfer(boolean clipboardTransfer) {
        this.clipboardTransfer = clipboardTransfer;
    }
   
    public boolean isClipboardTransferEnabled() {
        return clipboardTransfer;
    }   
    
    public Hashtable getProperties() {
        return properties;
    }
    
    public void setProperties (Hashtable props) {
        properties = props;
    }      
}
    
    
