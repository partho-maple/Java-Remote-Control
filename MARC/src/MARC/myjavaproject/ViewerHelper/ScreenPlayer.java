package MARC.myjavaproject.ViewerHelper;

import MARC.myjavaproject.Viewer.FileMng.FilesDropTargetListener;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.dnd.DropTarget;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import javax.swing.JLabel;
import MARC.myjavaproject.utilities.ImageUtility;

//import RDP_Util.utilities.ImageUtility;
//import RDP_Util.viewer.FileMng.FilesDropTargetListener;


public class ScreenPlayer extends JLabel {
      
    private Recorder recorder;
    
    private Image img;
    
    boolean PartialScreenMode = false;
    private Rectangle selectionRect = new Rectangle(0, 0, 0, 0);
    private Rectangle oldselectionRect = new Rectangle(-1, -1, -1, -1);
    private Rectangle screenRect = new Rectangle(0, 0, 0, 0);
    private Rectangle oldScreenRect = new Rectangle(-1, -1, -1, -1);    
    //private Rectangle screenRectScale = new Rectangle(0, 0, 0, 0);
    
    private KeyAdapter keyAdapter;
    private MouseAdapter mouseAdapter;    
    private MouseWheelListener mouseWheelListener;
    private MouseMotionAdapter mouseMotionAdapter;
    
    private boolean isSelecting = false;
    
    // mouse cordination for selection
    private int srcx, srcy, destx, desty;

    // Stroke-defined outline of selection rectangle.
    private BasicStroke bs;

    // used to create a distinctive-looking selection rectangle outline.
    private GradientPaint gp;
   
    public ScreenPlayer(Recorder recorder) { 
        this.recorder = recorder;
        
        keyAdapter = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e)
            {
                if (e.getKeyCode() == KeyEvent.VK_F11)
                    ScreenPlayer.this.recorder.viewerGUI.changeFullScreenMode();   
                
                ScreenPlayer.this.recorder.viewer.AddObject(e);
            }
            
            @Override
            public void keyReleased(KeyEvent e)
            {
                ScreenPlayer.this.recorder.viewer.AddObject(e);
            }          
        };
       
        mouseWheelListener = new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                ScreenPlayer.this.recorder.viewer.AddObject(e);
            }
        };
        
        mouseMotionAdapter = new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                ScreenPlayer.this.recorder.viewer.AddObject(e);
            } 
            
            @Override
            public void mouseDragged(MouseEvent e) {
                 if (isSelecting) {
                    destx = e.getX ();
                    desty = e.getY ();                 
                 }
                 else
                    ScreenPlayer.this.recorder.viewer.AddObject(e);                     
            }             
        };  
        
        mouseAdapter = new MouseAdapter()
        {
            @Override
            public void mousePressed(MouseEvent e) {
                if (isSelecting) {
                    destx = srcx = e.getX ();
                    desty = srcy = e.getY ();
                }
                else
                    ScreenPlayer.this.recorder.viewer.AddObject(e);
            }
            
            @Override
            public void mouseReleased(MouseEvent e) {
                 DoneSelecting();                 
                 ScreenPlayer.this.recorder.viewer.AddObject(e);          
            }        
        };    
        
        setFocusable(true);
        InitialSelectionRect();
        new DropTarget(this, new FilesDropTargetListener(this, recorder));           
    };

    public void addAdapters() {
        addKeyListener(keyAdapter); 
        addMouseWheelListener(mouseWheelListener);
        addMouseMotionListener(mouseMotionAdapter);
        addMouseListener(mouseAdapter);        
    }
     
    public void removeAdapters() {
        removeKeyListener(keyAdapter);
        removeMouseWheelListener(mouseWheelListener);
        removeMouseMotionListener(mouseMotionAdapter);
        removeMouseListener(mouseAdapter);
    }  
    
    @Override
    public void paint(Graphics g) {
        g.drawImage(img, 0, 0, screenRect.width, screenRect.height, this);            
        DrawSelectingRect(g);
    }

    public void UpdateScreen(byte[] data) { 
        screenRect = new Rectangle(recorder.viewerData.getScreenRect());  
        
        float screenScale = recorder.viewerData.getScreenScale();              
        screenRect.width = (int) (screenRect.width * screenScale);
        screenRect.height = (int) (screenRect.height * screenScale);  
        
         if (!PartialScreenMode) {
           if (!screenRect.equals(oldScreenRect)) {
               oldScreenRect = screenRect;
               setSize(screenRect.getSize());
               setPreferredSize(screenRect.getSize());   
           }
         }
       else {
             if(!isSelecting)       
                if (!selectionRect.equals(oldselectionRect)) {
                    oldselectionRect = selectionRect;
                    setSize(selectionRect.getSize());
                    setPreferredSize(selectionRect.getSize());       
                }         
       }
        
        img = ImageUtility.read(data);  
        repaint();  
    }
    
    public void clearScreen() {
        setSize(new Dimension(1, 1));
        setPreferredSize(new Dimension(1, 1));
        img = createImage(getWidth(), getHeight());
        repaint();
        oldScreenRect = new Rectangle(-1, -1, -1, -1); 
    }
    
    public void InitialSelectionRect() {
        // Define the stroke for drawing selection rectangle outline.
        bs = new BasicStroke (5, BasicStroke.CAP_ROUND, 
                               BasicStroke.JOIN_ROUND,
                               0, new float [] { 12, 12 }, 0);

        // Define the gradient paint for coloring selection rectangle outline.
        gp = new GradientPaint (0.0f, 0.0f, Color.red, 1.0f, 1.0f, Color.white, true);    
    }  
    
    public void DrawSelectingRect(Graphics g) {
        if (isSelecting)
            if (srcx != destx || srcy != desty)
            {
                // Compute upper-left and lower-right coordinates for selection
                // rectangle corners.

                int x1 = (srcx < destx) ? srcx : destx;
                int y1 = (srcy < desty) ? srcy : desty;

                int x2 = (srcx > destx) ? srcx : destx;
                int y2 = (srcy > desty) ? srcy : desty;

                // Establish selection rectangle origin.
                selectionRect.x = x1;
                selectionRect.y = y1;

                // Establish selection rectangle extents.
                selectionRect.width = (x2-x1)+1;
                selectionRect.height = (y2-y1)+1;

                // Draw selection rectangle.
                Graphics2D g2d = (Graphics2D) g;
                g2d.setStroke (bs);
                g2d.setPaint (gp);
                g2d.draw (selectionRect);

                PartialScreenMode = true;
      }         
    }
    
    public boolean isPartialScreenMode() {
        return PartialScreenMode;
    } 
    
    public Rectangle getSelectionRect () {
        return selectionRect;
    }
    
    public boolean isSelectionRectChanged () {
        boolean bool = (!selectionRect.equals(oldselectionRect));     
        oldselectionRect = selectionRect;
        return bool;    
    }
    
    public boolean isScreenRectChanged () {
        boolean bool = (!screenRect.equals(oldScreenRect));     
        oldScreenRect = screenRect;
        return bool;
    }
    
    public void startSelectingMode() {
        isSelecting = true;
        Cursor cursor = new Cursor(Cursor.CROSSHAIR_CURSOR);
        setCursor(cursor);
    }
    
    public void stopSelectingMode() {
        PartialScreenMode = false;
        selectionRect = new Rectangle(0, 0, 0, 0);      
        recorder.viewerData.setScreenRect(new Rectangle(0, 0, 0, 0));       
        recorder.viewer.updateOptions();
    }
    
    public void DoneSelecting ()
    {        
        if (isSelecting) {
            isSelecting = false;
            oldselectionRect = new Rectangle();
            
            if (PartialScreenMode) {
                float screenScale = 1.0f / recorder.viewerData.getScreenScale();
                Rectangle rect = new Rectangle(selectionRect);
                rect.x = (int) (rect.x * screenScale);
                rect.y = (int) (rect.y * screenScale);
                rect.height = (int) (rect.height * screenScale);
                rect.width = (int) (rect.width * screenScale);
                recorder.viewerData.setScreenRect(rect);
                recorder.viewer.updateOptions();
//                recorder.viewerGUI.jBtnPartialComplete.setIcon(
//                        new ImageIcon(main.DEFAULT_SCREEN_ICON));
            }
            
            srcx = destx;
            srcy = desty;     
        
            Cursor cursor = new Cursor(Cursor.DEFAULT_CURSOR);
            setCursor(cursor);   
        }
    }
}