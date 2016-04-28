package MARC.Administrator;



import MARC.RemoteHost.HostServerConfig;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import MARC.myjavaproject.MARS_AboutBoxGUI;
import MARC.myjavaproject.main;
import MARC.RemoteHost.ClientMainWindoeForInitiating;
import MARC.myjavaproject.Server.RMI.ServerAdmin;



public class TrayIconWithPopForAdmin {


    final static public int SERVER_STARTED = 1;
    final static public int SERVER_STOPPED = 2;
    final static public int CONNECTION_FAILED = 3;
    final static public int SERVER_RUNNING = 4;
    final static public int SERVER_NOT_RUNNING = 5;
//    private static MenuItem serverItem;
//    private static TrayIcon trayIcon;
    private static boolean enabled = false;



        static final PopupMenu popup = new PopupMenu();
        static Image image = Toolkit.getDefaultToolkit().getImage("resourse/kdm-config-icon16.png");
        static final TrayIcon trayIcon = new TrayIcon(image,"MARS-Admin Module");
        static final SystemTray tray = SystemTray.getSystemTray();



public static void createAndShowGUIadd() {
        //Check the SystemTray support
        if (!SystemTray.isSupported()) {
            System.out.println("SystemTray is not supported");
            return;
        }


        

        // Create a popup menu components
        MenuItem aboutItem = new MenuItem("About");
        MenuItem helpItem = new MenuItem("Help");
        MenuItem showAd = new MenuItem("Show Admin Module");
        showAd.setFont(new Font(null, Font.BOLD, 12));
        MenuItem startHos = new MenuItem("Start Host");
        MenuItem exitItem = new MenuItem("Exit");

        //Add components to popup menu
        popup.add(aboutItem);
        popup.add(helpItem);
        popup.addSeparator();

        popup.add(showAd);
        popup.addSeparator();

        popup.add(startHos);
        popup.add(exitItem);

        trayIcon.setPopupMenu(popup);

        try {
            tray.add(trayIcon);
        } catch (AWTException e) {
            System.out.println("TrayIcon could not be added.");
            return;
        }

        trayIcon.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null,
                        "MARC is running...");
            }
        });

        aboutItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                MARS_AboutBoxGUI.main(null);
               
            }
        });


        helpItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {


        JOptionPane.showMessageDialog(ServerMainWindoeForInitiating.SMWFI02, "This feature not yet been implemented. .. ...", "Information", JOptionPane.INFORMATION_MESSAGE);
            return;

                

            }
        });


        showAd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                  /* show the admin GUI if it is hidden, and it bring the GUI on focus if it is minimised.
                  *  here main(null) means 'ServerMainWindoeForInitiating' invockes main(null) and it is null
                   * because main is static
                  */
                  
                  ServerMainWindoeForInitiating.showServerMainWindoeForInitiating();
                  
            }
        });




        startHos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {


//                ClientMainWindoeForInitiating.main(null);
                ClientMainWindoeForInitiating.showClientMainWindoeForInitiating();
            }
        });
           
        exitItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (JOptionPane.showConfirmDialog(null,
                                "Exit application ?", "Confirm Dialog",
                                JOptionPane.OK_CANCEL_OPTION) ==
                                JOptionPane.OK_OPTION) {
                            tray.remove(trayIcon);
                            System.exit(0);
                        }
            }
        });
    }






    public static void updateServerStatus(int msgType) {
        if (!SystemTray.isSupported() || enabled == false) return;

        switch (msgType) {
            case SERVER_RUNNING:
//                serverItem.setLabel("Stop Server");
//                if (ServerAdmin.isRunning()) {
//                    if (ServerAdmin.getViewersCount() != 0)
//                        trayIcon.setImage(new ImageIcon(main.ALIVE_ICON).getImage());
//                    else
//                        trayIcon.setImage(new ImageIcon(main.WAIT_ICON).getImage());
//                }
                trayIcon.setToolTip("MARC [Server running]\n" +  HostServerConfig.server_address);
                break;
            case SERVER_NOT_RUNNING:
//                serverItem.setLabel("Start");
//                trayIcon.setImage(new ImageIcon(main.IDLE_ICON).getImage());
                trayIcon.setToolTip("MARC [Server stopped]\n" +
                        HostServerConfig.server_address);
                break;
            case SERVER_STARTED:
//                serverItem.setLabel("Stop");
                trayIcon.displayMessage("Connection status", "Server Started ! !! !!!",TrayIcon.MessageType.INFO);
//                trayIcon.setImage(new ImageIcon(main.WAIT_ICON).getImage());
                trayIcon.setToolTip("MARC [Server running]\n" + HostServerConfig.server_address);
                break;
            case CONNECTION_FAILED:
                trayIcon.displayMessage("Connection status", "Connection Failed ! !! !!!",TrayIcon.MessageType.ERROR);
                break;
            case SERVER_STOPPED:
//                serverItem.setLabel("Start");
                trayIcon.displayMessage("Connection status", "Server Stopped ! !! !!!",
                        TrayIcon.MessageType.INFO);
//                trayIcon.setImage(new ImageIcon(main.IDLE_ICON).getImage());
                trayIcon.setToolTip("MARC [Server stopped]\n" +
                        HostServerConfig.server_address);
                break;
        }
//        serverItem.setEnabled(true);
    }

    public static void displayViewer(String viewer, int size, boolean connected) {
        if (!SystemTray.isSupported() || enabled == false) return;

        if (connected) {
            trayIcon.displayMessage("Viewer details", viewer + " connected !!",
                    TrayIcon.MessageType.INFO);
            if (size == 0) {
                trayIcon.setImage(new ImageIcon(main.ALIVE_ICON).getImage());
            }
        } else {
            trayIcon.displayMessage("Viewer details", viewer + " disconnected !!",
                    TrayIcon.MessageType.INFO);
            if (size == 0) {
//                trayIcon.setImage(new ImageIcon(main.WAIT_ICON).getImage());
            }
        }
    }

    public static boolean isSupported() {
        return SystemTray.isSupported();
    }

    public static boolean isEnabled() {
        return enabled;
    }

    public static boolean isServerRunning() {
        boolean bool = ServerAdmin.isRunning();
        if (!bool) {
            JOptionPane.showMessageDialog(null,
                    "Server is not running !!",
                    "Information", JOptionPane.INFORMATION_MESSAGE);
        }
        return bool;
    }

    

}
