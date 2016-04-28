/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package MARC.myjavaproject.LogInfo.ForAdministrator;

/**
 *
 * @author Partho Biswas
 */

import MARC.Administrator.ServerMainWindoeForInitiating;
import java.util.logging.*;
import java.io.*;
import javax.swing.JTextArea;





class WindowHandlerForAdministrator extends Handler
{
  public  JTextArea adminLogInfoTextArea;
  public  ServerMainWindoeForInitiating SMWFI3;

//  private Formatter formater = null;

//  private Level level = null;

  private static WindowHandlerForAdministrator handler = null;

  private WindowHandlerForAdministrator()
  {
      adminLogInfoTextArea = ServerMainWindoeForInitiating.adminLogInfoTextArea;
      SMWFI3 = ServerMainWindoeForInitiating.SMWFI02;




      LogManager manager = LogManager.getLogManager();
      String className = this.getClass().getName();
      String level = manager.getProperty(className + ".level");
  //    @Override
      setLevel(level != null ? Level.parse(level) : Level.INFO);
  //    if (window == null)
  //      window = new LogWindow();
  }

  public static synchronized WindowHandlerForAdministrator getInstance()
  {
    if (handler == null) {
      handler = new WindowHandlerForAdministrator();
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
    SMWFI3.showInfoOnAdminTextArea(message);
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


