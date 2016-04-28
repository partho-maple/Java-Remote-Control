package MARC.myjavaproject.ServerHelper;

import MARC.myjavaproject.Server.RMI.ServerAdmin;
import java.awt.AWTException;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Hashtable;
import javax.swing.ImageIcon;
import MARC.RemoteHost.HostProperties;
import MARC.myjavaproject.utilities.ImageUtility;
import MARC.myjavaproject.ViewerHelper.ViewerData;



public class robot {

    private Robot rt;
            
    private Rectangle defaultScreenRect = null;
    private Rectangle oldScreenRect = null;
    private Toolkit tk = null;
    
    public robot() {
        tk = Toolkit.getDefaultToolkit();
        defaultScreenRect = new Rectangle(tk.getScreenSize()); 
        
        try {               
            rt = new Robot();
        }
        catch (AWTException awte) {
            awte.getStackTrace();
        }     
    }

    public BufferedImage captureScreen(ViewerData viewerData) {    
        updateScreenRect(viewerData);
        Rectangle screenRect = new Rectangle(viewerData.getScreenRect()); 
        BufferedImage screen =  rt.createScreenCapture(screenRect); 
                
        float screenScale = viewerData.getScreenScale();
        screenRect.width = (int) (screenRect.width * screenScale);
        screenRect.height = (int) (screenRect.height * screenScale);
        
        BufferedImage bimage = new BufferedImage (
                screenRect.width, screenRect.height, 
                viewerData.getColorQuality());
       Graphics2D g2d = bimage.createGraphics ();
       g2d.drawImage(screen, 0, 0, 
            screenRect.width, screenRect.height, null);       
       g2d.dispose ();  
       
       return bimage;
    }

    public byte[] CaptureScreenByteArray(ViewerData viewerData) {  
        return ImageUtility.toByteArray(captureScreen(viewerData), 
                viewerData.getImageQuality());
    }        
    
    public Rectangle getCustomScreenRect(Rectangle rect) {
        defaultScreenRect = new Rectangle(tk.getScreenSize()); 

        if (rect.equals(new Rectangle(0 , 0, 0, 0))) 
            return defaultScreenRect;
        else
            return rect;   
    }   
    
    public void updateScreenRect(ViewerData viewerData) {
        oldScreenRect = defaultScreenRect;
        defaultScreenRect = new Rectangle(tk.getScreenSize()); 
 
        if (viewerData.getScreenRect().equals(oldScreenRect) ||
                !defaultScreenRect.equals(oldScreenRect))
            viewerData.setScreenRect(defaultScreenRect);   
    } 
    
    public void updateData(Object object, ViewerData viewerData) {            
        ArrayList Objects = (ArrayList) object;     
        for (int i=0; i<Objects.size(); i++) {            
            Object obj = Objects.get(i);

            if (obj instanceof MouseEvent)
                applyMouseEvent((MouseEvent)obj, viewerData);
            else if (obj instanceof KeyEvent)
                applyKeyEvent((KeyEvent)obj);              
            else if (obj instanceof String)
                setTextToClipboard((String) obj);
            else if (obj instanceof ImageIcon)
                setImageToClipboard((ImageIcon) obj);              
            else if (obj instanceof File)
                getFilesFromClipboard((File) obj);
            else if (obj instanceof Hashtable)
                setgetProperties((Hashtable) obj, viewerData);                
        }
    }
    
    public void applyMouseEvent(MouseEvent evt, ViewerData viewerData) {
        final int x = viewerData.getScreenRect().x + (int) (evt.getX() / 
                viewerData.getScreenScale());
        final int y = viewerData.getScreenRect().y + (int) (evt.getY() / 
                viewerData.getScreenScale());
        rt.mouseMove(x, y);
        int buttonMask = 0;
        int buttons = evt.getButton();
        if ((buttons == MouseEvent.BUTTON1)) buttonMask = InputEvent.BUTTON1_MASK;
        if ((buttons == MouseEvent.BUTTON2)) buttonMask |= InputEvent.BUTTON2_MASK;
        if ((buttons == MouseEvent.BUTTON3)) buttonMask |= InputEvent.BUTTON3_MASK;     
        switch(evt.getID()) {         
            case MouseEvent.MOUSE_PRESSED: rt.mousePress(buttonMask); break;
            case MouseEvent.MOUSE_RELEASED: rt.mouseRelease(buttonMask); break;
            case MouseEvent.MOUSE_WHEEL: rt.mouseWheel(
                    ((MouseWheelEvent) evt).getUnitsToScroll()); break;
        }          
    }
    
    public void applyKeyEvent(KeyEvent evt) {
        switch(evt.getID()) {
            case KeyEvent.KEY_PRESSED: rt.keyPress(evt.getKeyCode()); break;
            case KeyEvent.KEY_RELEASED: rt.keyRelease(evt.getKeyCode()); break; 
        }
    }       
   
    public void setTextToClipboard(String string) {   
        ServerAdmin.clipbrdUtility.setTextToClipboard(string);
    }
    
    public void setImageToClipboard(ImageIcon image) {
        ServerAdmin.clipbrdUtility.setImageToClipboard(image);
    }
    
    public void getFilesFromClipboard(File file) {
        if (file.equals(new File("."))) 
            // set uploading folder to start receving files from viewer  
            ServerAdmin.setUploadingFolder();
        else
            // send file names in clipboard to viewer to start downloding them
            ServerAdmin.SendClipboardFileList();
    }
    
    public void setgetProperties(Hashtable props, ViewerData viewerData) {
        if (props.isEmpty())
            ServerAdmin.AddObject(HostProperties.getLocalProperties());
        else
            ServerAdmin.setViewerProperties(viewerData.getInetAddress(), props);
    }
}
