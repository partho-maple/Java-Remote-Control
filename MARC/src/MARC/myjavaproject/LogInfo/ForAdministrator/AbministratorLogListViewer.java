/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * AbministratorLogListViewer.java
 *
 * Created on Feb 5, 2011, 4:49:38 AM
 */

package MARC.myjavaproject.LogInfo.ForAdministrator;

import MARC.Administrator.ServerMainWindoeForInitiating;
import java.awt.Toolkit;
import java.io.File;
import javax.swing.DefaultListModel;
import javax.swing.JRootPane;

/**
 *
 * @author Partho Biswas
 */
public class AbministratorLogListViewer extends javax.swing.JFrame {

    public int counter;


    /** Creates new form AbministratorLogListViewer */
    public AbministratorLogListViewer() {

        setIconImage(Toolkit.getDefaultToolkit().getImage("resourse/kdm-config-icon.png"));
        
        initComponents();
        DefaultListModel listModel = new DefaultListModel();
        LogViewerlist.setModel(listModel);




//        this loop shows the log files on the list viewer
        for( counter=0; ; )
        {
    		File file = new File("MARS-Administrator-LogFile-"+ counter +".log");

    		if(file.exists())
                {
    			listModel.addElement("MARS-Administrator-LogFile-"+ counter +".log");
                        counter++;
    		}
                
                else
                {
                    break;
    		}
                 

        }



//        this.accessibleContext(ServerMainWindoeForInitiating.SMWFI02);


    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        LogViewerScrollPane = new javax.swing.JScrollPane();
        LogViewerlist = new javax.swing.JList();
        ViweLogButton = new javax.swing.JButton();
        CancelLogWinButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Administrator Log List Viewer");
        setBackground(new java.awt.Color(51, 51, 51));
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(51, 51, 51));

        LogViewerScrollPane.setBackground(new java.awt.Color(51, 51, 51));
        LogViewerScrollPane.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED, java.awt.Color.gray, java.awt.Color.lightGray, java.awt.Color.black, java.awt.Color.darkGray));

        LogViewerlist.setBackground(new java.awt.Color(102, 102, 102));
        LogViewerlist.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED, java.awt.Color.gray, java.awt.Color.lightGray, java.awt.Color.black, java.awt.Color.darkGray));
        LogViewerlist.setFont(new java.awt.Font("Comic Sans MS", 0, 11));
        LogViewerlist.setForeground(new java.awt.Color(153, 0, 0));
        LogViewerlist.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        LogViewerScrollPane.setViewportView(LogViewerlist);

        ViweLogButton.setBackground(new java.awt.Color(51, 51, 51));
        ViweLogButton.setForeground(new java.awt.Color(153, 0, 0));
        ViweLogButton.setText("View");
        ViweLogButton.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.white, java.awt.Color.darkGray, java.awt.Color.gray));
        ViweLogButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ViweLogButtonActionPerformed(evt);
            }
        });

        CancelLogWinButton.setBackground(new java.awt.Color(51, 51, 51));
        CancelLogWinButton.setForeground(new java.awt.Color(153, 0, 0));
        CancelLogWinButton.setText("Cancel");
        CancelLogWinButton.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.white, java.awt.Color.darkGray, java.awt.Color.gray));
        CancelLogWinButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CancelLogWinButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(88, 88, 88)
                        .addComponent(ViweLogButton, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(CancelLogWinButton, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(LogViewerScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 320, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(LogViewerScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(CancelLogWinButton, javax.swing.GroupLayout.DEFAULT_SIZE, 51, Short.MAX_VALUE)
                    .addComponent(ViweLogButton, javax.swing.GroupLayout.DEFAULT_SIZE, 51, Short.MAX_VALUE))
                .addGap(20, 20, 20))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void CancelLogWinButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CancelLogWinButtonActionPerformed

        this.setVisible(false);

    }//GEN-LAST:event_CancelLogWinButtonActionPerformed


    public static int index;
    private void ViweLogButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ViweLogButtonActionPerformed

        index  = LogViewerlist.getSelectedIndex();
        if (index == -1) return;

        LogFileViewerForAdmin.main(null);
//        LogFileViewerForAdmin.openFile(index);

    }//GEN-LAST:event_ViweLogButtonActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {

                AbministratorLogListViewer ALLV = new AbministratorLogListViewer();

                ALLV.setVisible(true);
                ALLV.setLocationRelativeTo( null );
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton CancelLogWinButton;
    private javax.swing.JScrollPane LogViewerScrollPane;
    private javax.swing.JList LogViewerlist;
    private javax.swing.JButton ViweLogButton;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables

}
