package MARC.myjavaproject.LogInfo.ForAdministrator;

/**
 * @author Partho Biswas
 */


import MARC.Administrator.ServerMainWindoeForInitiating;
import java.util.logging.*;
import java.io.*;
import javax.swing.JTextArea;




public class AppLogInfoForAdministrator
{

    public static Logger logger;
    public Logger loggerNoSt;


    public static String logDisplayer;
    public String logDisplayerNoSt;


    public  JTextArea adminLogInfoTextArea;

    public static   WindowHandlerForAdministrator handler;


    public AppLogInfoForAdministrator()
    {
        adminLogInfoTextArea = ServerMainWindoeForInitiating.adminLogInfoTextArea;
    }



    static
    {
        try
        {
            boolean append = true;
            //      FileHandler fh = new FileHandler("TestLog03.log", append);

            //      this gives the rolling pattern
//            int limit = 1000000000; // 1 Gb
            int limit = 10000; // 1 Gb
            int numLogFiles = 100;
            FileHandler fh = new FileHandler("MARS-Administrator-LogFile-%g.log", limit, numLogFiles, append);
            fh.setFormatter(new SimpleFormatter());



/*
      fh.setFormatter(
        new Formatter()
        {
         public String format(LogRecord rec)
         {
            StringBuffer buf = new StringBuffer(1000);
            buf.append(new java.util.Date());
            buf.append(' ');
            buf.append(rec.getLevel());
            buf.append(' ');
            buf.append(formatMessage(rec));
            buf.append('\n');
            return buf.toString();
         }
        }
                     );
*/


            handler = WindowHandlerForAdministrator.getInstance();
            logger = Logger.getLogger("MARS");
            logger.addHandler(handler);




            logger = Logger.getLogger("MARS");
            logger.addHandler(fh);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void conversionToNoSt()
    {
       loggerNoSt = logger;
    }




//    for clearing current log files
    public static void clearFile(String fileLocation)
    {
       try{
           BufferedWriter bw = new BufferedWriter(new FileWriter(fileLocation));
           bw.write("");
           bw.flush();
           bw.close();
        }catch(IOException ioe){
            // You should really do something more appropriate here
           ioe.printStackTrace();
       }
    }


//    for clearing All log files
    public static int logFileCounter;
//    public static int logFileCounterHelper;
    public static void clearAllFile()
    {
        for( logFileCounter=1; ; )
        {
    		File file = new File("MARS-Administrator-LogFile-"+ logFileCounter +".log");

    		if(file.delete())
                {
    			System.out.println(file.getName() + " is deleted!");
                        logFileCounter++;
    		}
                else
                {
    			System.out.println("Delete operation is failed.");
//                        System.out.println(logFileCounter);
//                        logFileCounterHelper = logFileCounter;
                        logFileCounter = 1;
                        return;
    		}
        
        }
    }

    

}










