package MARC.RemoteHost;



import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import MARC.myjavaproject.MARS_AboutBoxGUI;
import MARC.myjavaproject.main;
import MARC.RemoteHost.ClientMainWindoeForInitiating;
import MARC.Administrator.ServerMainWindoeForInitiating;




public class TrayIconWithPopForHost {


public static void createAndShowGUIadd() {
        //Check the SystemTray support
        if (!SystemTray.isSupported()) {
            System.out.println("SystemTray is not supported");
            return;
        }


        final PopupMenu popup = new PopupMenu();
        Image image = Toolkit.getDefaultToolkit().getImage("resourse/krfb-icon16.png");
        final TrayIcon trayIcon = new TrayIcon(image,"MARS-Host Module");
        final SystemTray tray = SystemTray.getSystemTray();

        // Create a popup menu components
        MenuItem aboutItem = new MenuItem("About");
        MenuItem helpItem = new MenuItem("Help");
        MenuItem showHos = new MenuItem("Show Host Module");
        showHos.setFont(new Font(null, Font.BOLD, 12));
        MenuItem curCon = new MenuItem("Current Connections...");
        MenuItem startAdm = new MenuItem("Start Admin");
        MenuItem exitItem = new MenuItem("Exit");

        //Add components to popup menu
        popup.add(aboutItem);
        popup.add(helpItem);
        popup.addSeparator();

        popup.add(showHos);
        popup.add(curCon);
        popup.addSeparator();

        popup.add(startAdm);
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


        JOptionPane.showMessageDialog(ClientMainWindoeForInitiating.CMWFI02, "This feature not yet been implemented. .. ...", "Information", JOptionPane.INFORMATION_MESSAGE);
            return;

               

            }
        });


        showHos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

            ClientMainWindoeForInitiating.showClientMainWindoeForInitiating();
            }
        });


        curCon.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {


        JOptionPane.showMessageDialog(ClientMainWindoeForInitiating.CMWFI02, "This feature not yet been implemented. .. ...", "Information", JOptionPane.INFORMATION_MESSAGE);
            return;


                
            }
        });




        startAdm.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

//                ServerMainWindoeForInitiating.main(null);
                ServerMainWindoeForInitiating.showServerMainWindoeForInitiating();
            }
        });

        exitItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                
                if (JOptionPane.showConfirmDialog(null,
                                "Exit application ?", "Confirm Dialog",
                                JOptionPane.OK_CANCEL_OPTION) ==
                                JOptionPane.OK_OPTION) {
                            tray.remove(trayIcon);
                            main.exit();
                        }
            }
        });
    }





}
