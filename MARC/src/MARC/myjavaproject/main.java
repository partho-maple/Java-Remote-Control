
package MARC.myjavaproject;

import java.awt.Toolkit;
import javax.swing.UIManager;
import java.net.URL;
import MARC.Administrator.ServerMainWindoeForInitiating;
import MARC.myjavaproject.LogInfo.ForAdministrator.AppLogInfoForAdministrator;
import RDP_Util.SecurityMng;
import MARC.myjavaproject.Server.RMI.ServerAdmin;
//import others.SecurityMng;
import MARC.myjavaproject.utilities.FileUtility;




/**
 *
 * @author PARTHO BISWAS
 */
public class main {


    private static  int ySize;
    private static  int xSize;

    public static int iti = 1;

    public static String SERVER_CONFIG_FILE;
    public static String PASS_CONFIG_FILE;




    public static final URL IDLE_ICON = main.class.getResource("/MARC/myjavaproject/resources/idle.png");
    public static final URL ALIVE_ICON = main.class.getResource("/MARC/myjavaproject/resources/background.png");
    public static final URL WAIT_ICON = main.class.getResource("/MARC/myjavaproject/resources/display.png");
    public static final URL START_ICON = main.class.getResource("/MARC/myjavaproject/resources/player_play.png");
    public static final URL STOP_ICON = main.class.getResource("/MARC/myjavaproject/resources/player_stop.png");
    public static final URL PAUSE_ICON = main.class.getResource("/MARC/myjavaproject/resources/player_pause.png");
    public static final URL INPUTS_ICON = main.class.getResource("/MARC/myjavaproject/resources/input_devices.png");
    public static final URL LOCKED_INPUTS_ICON = main.class.getResource("/MARC/myjavaproject/resources/locked_inputs.png");
    public static final URL FULL_SCREEN_ICON = main.class.getResource("/MARC/myjavaproject/resources/view_fullscreen.png");
    public static final URL NORMAL_SCREEN_ICON = main.class.getResource("/MARC/myjavaproject/resources/view_nofullscreen.png");
    public static final URL DEFAULT_SCREEN_ICON = main.class.getResource("/MARC/myjavaproject/resources/default_screen.png");
    public static final URL CUSTOM_SCREEN_ICON = main.class.getResource("/MARC/myjavaproject/resources/custom_screen.png");


    

    

    /**
     * @param args the command line arguments
     */    
    public static void main(String[] args)
    {
        try
        {

            UIManager.setLookAndFeel("com.jtattoo.plaf.hifi.HiFiLookAndFeel");

        } catch (Exception ex)
        {
            ex.printStackTrace();
        }


        


         //Sets the System security.
        if (System.getSecurityManager() == null)
	    System.setSecurityManager(new SecurityMng());






//            displayHelp();


        SERVER_CONFIG_FILE = FileUtility.getCurrentDirectory() + "server.config";
        PASS_CONFIG_FILE = FileUtility.getCurrentDirectory() + "UserInfoPassword";


        OpeningWindow_frame OpWi_Fr = new OpeningWindow_frame();
        OpWi_Fr.setTitle("Mandala Remote Control (MARC)");
        OpWi_Fr.setDefaultCloseOperation( OpeningWindow_frame.EXIT_ON_CLOSE );

        Toolkit tk = Toolkit.getDefaultToolkit();
        ySize = ( (int)  tk.getScreenSize().getHeight());
        xSize = ( (int)  tk.getScreenSize().getWidth());
        OpWi_Fr.setBounds((xSize/2) - 250, (ySize/2) - 200, 500, 400);

        OpWi_Fr.setResizable(false);
        OpWi_Fr.setVisible( true ); // display frame



//          this line shows log info on consone and adminLogInfoTextArea
            if(ServerMainWindoeForInitiating.isLogEnable == Boolean.TRUE)
            {
                AppLogInfoForAdministrator.logger.info("Application has been started");
            }



//        UserAccInfoPassword.loadPassword();
    }

    
    public static void exit() {
        if (ServerAdmin.isRunning())
            ServerAdmin.Stop();
        ServerMainWindoeForInitiating.clearStoreProperties();
        System.setSecurityManager(null);
        System.exit(0);
    }

}

