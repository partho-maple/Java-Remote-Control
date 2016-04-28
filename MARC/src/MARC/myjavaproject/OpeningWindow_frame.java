
package MARC.myjavaproject;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import MARC.RemoteHost.ClientMainWindoeForInitiating;
import MARC.Administrator.ServerMainWindoeForInitiating;

/**
 *
 * @author PARTHO BISWAS
 */
public class OpeningWindow_frame extends JFrame{


        ImageIcon icon;
//        private static OpeningWindow_frame frame = new OpeningWindow_frame();
        ServerMainWindoeForInitiating SMWFI = new ServerMainWindoeForInitiating();
        ClientMainWindoeForInitiating CMWFI = new ClientMainWindoeForInitiating();
//        private static OpeningWindow_frame OpWi_Fr = new OpeningWindow_frame();


	public OpeningWindow_frame()
	{
		setIconImage(Toolkit.getDefaultToolkit().getImage("resourse/Project-icon16.png"));

                icon = new ImageIcon("resourse/BlackWhite28.jpg");

		JPanel panel = new JPanel()
		{
			protected void paintComponent(Graphics g)
			{
				//  Dispaly image at at full size
//				g.drawImage(icon.getImage(), 0, 0, null);

				//  Scale image to size of component
				Dimension d = getSize();
				g.drawImage(icon.getImage(), 0, 0, d.width, d.height, null);

				//  Fix the image position in the scroll pane
//				Point p = scrollPane.getViewport().getViewPosition();
//				g.drawImage(icon.getImage(), p.x, p.y, null);

				super.paintComponent(g);
			}
		};
		panel.setOpaque( false );
//		panel.setPreferredSize( new Dimension(800, 800) );



                OpeningWindow_Panel OpWi_Pa = new OpeningWindow_Panel();
                OpWi_Pa.setOpaque( false );
                panel.add(OpWi_Pa);
                getContentPane().add( panel );
                setLocationRelativeTo(null);

	}

	public static void main(String [] args)
	{

            java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
		new OpeningWindow_frame().setVisible(true);
//                OpWi_Fr.setVisible(false);
            }
        });
        }
}
