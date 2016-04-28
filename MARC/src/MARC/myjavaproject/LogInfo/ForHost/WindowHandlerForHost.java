/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package MARC.myjavaproject.LogInfo.ForHost;

/**
 *
 * @author Partho Biswas
 */

//import MARC.Administrator.ServerMainWindoeForInitiating;
import MARC.RemoteHost.ClientMainWindoeForInitiating;
import java.util.logging.*;
import javax.swing.JTextArea;





class WindowHandlerForHost extends Handler
{
  public  JTextArea hostLogInfoTextArea;
  public  ClientMainWindoeForInitiating CMWFI3;

//  private Formatter formater = null;

//  private Level level = null;

  private static WindowHandlerForHost handler = null;

  private WindowHandlerForHost()
  {
      hostLogInfoTextArea = ClientMainWindoeForInitiating.hostLogInfoTextArea;
      CMWFI3 = ClientMainWindoeForInitiating.CMWFI02;




      LogManager manager = LogManager.getLogManager();
      String className = this.getClass().getName();
      String level = manager.getProperty(className + ".level");
  //    @Override
      setLevel(level != null ? Level.parse(level) : Level.INFO);
  //    if (window == null)
  //      window = new LogWindow();
  }

  public static synchronized WindowHandlerForHost getInstance()
  {
    if (handler == null) {
      handler = new WindowHandlerForHost();
    }
    return handler;
  }




 public SimpleFormatter formatter = new SimpleFormatter();



  public  synchronized void publish(LogRecord record)
  {
    String message = null;
    if (!isLoggable(record))
      return;
    message = formatter.format(record);
    CMWFI3.showInfoOnHostTextArea(message);
  }






    @Override
    public void flush()
    {

    }

    @Override
    public void close() throws SecurityException
    {

    }

}


