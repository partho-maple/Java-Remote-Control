package MARC.myjavaproject.utilities;

import MARC.RemoteHost.ClientMainWindoeForInitiating;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;



public class FileUtility {

    public static void checkFile(String pathname, String filename) {
        File file = new File(pathname);
        
        if (!file.canRead()) 
            try {    
                file.createNewFile();
//                InputStream is =  main.class.getResourceAsStream(filename);
                InputStream is =  ClientMainWindoeForInitiating.class.getResourceAsStream(filename);
                BufferedInputStream bis = new BufferedInputStream(is);            
                FileOutputStream fos = new FileOutputStream(file);
            
                int ch;
                while ((ch = bis.read()) != -1) fos.write(ch);
                
                is.close();
                bis.close();
                fos.close();
            } catch(Exception e) {
                e.getStackTrace();
                return;
            }    
    }    
    
    public static ArrayList<File> getFiles(File[] files){
        ArrayList<File> _files = new ArrayList<File>();    
        for (int i=0; i<files.length; i++)
            if (files[i].isDirectory())
                _files.addAll(getFiles(new File(files[i].toString()).listFiles()));
            else 
                _files.add(files[i]);
        return _files;
    } 
    
    public static File[] getAllFiles(File[] files) {
        ArrayList<File> fs = getFiles(files);
        return (File[]) fs.toArray(new File[fs.size()]); 
    }
    
    public static String getCurrentDirectory () {
        String currentDirectory = null;
        try {
            currentDirectory = new File(".").getCanonicalPath() + File.separatorChar;            
        } catch (IOException e) {
            e.getStackTrace();
        }
        return currentDirectory;
    }     
}
