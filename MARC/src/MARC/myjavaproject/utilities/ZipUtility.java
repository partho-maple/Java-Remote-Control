package MARC.myjavaproject.utilities;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;


public class ZipUtility {

    public static byte[] compress(byte[] input, int level) {
        // Create the compressor with highest level of compression
        Deflater compressor = new Deflater();
        compressor.setLevel(level);

        // Give the compressor the data to compress
        compressor.setInput(input);
        compressor.finish();

        // Create an expandable byte array to hold the compressed data.
        ByteArrayOutputStream bos = new ByteArrayOutputStream(input.length);

        // Compress the data
        byte[] buf = new byte[1024];
        while (!compressor.finished()) {
            int count = compressor.deflate(buf);
            bos.write(buf, 0, count);
        }
        try {
            bos.flush();
            bos.close();
        } catch (IOException e) { 
            e.getStackTrace();
        }
        // Get the compressed data
        return bos.toByteArray();        
    }    
    
    public static byte[] decompress (byte[] compressedData) {
        // Create the decompressor and give it the data to compress
        Inflater decompressor = new Inflater();
        decompressor.setInput(compressedData);

        // Create an expandable byte array to hold the decompressed data
        ByteArrayOutputStream bos = new ByteArrayOutputStream(compressedData.length);

        // Decompress the data
        byte[] buf = new byte[1024];
        while (!decompressor.finished()) {
            try {
                int count = decompressor.inflate(buf);
                bos.write(buf, 0, count);
            } catch (DataFormatException e) {
                e.getStackTrace();
            }
        } 
        try {
            bos.flush();
            bos.close();       
        } catch (IOException e) {
            e.getStackTrace();
        }
        // Get the decompressed data
        return bos.toByteArray();         
    }       
    
    public static byte[] compressObject(Object object, int level) throws IOException {              
        return compress(objecttoByteArray(object), level);
     }
    
    public static Object decompressObject(byte[] data) throws Exception {       
        return byteArraytoObject(decompress(data));
     }
     
    public static Object byteArraytoObject(byte[] data) throws Exception {       
        ByteArrayInputStream bais = new ByteArrayInputStream(data);              
        ObjectInputStream ois = new ObjectInputStream(bais);
        ois.close();
        bais.close();        
        return ois.readObject();                
     }     
    
    public static byte[] objecttoByteArray(Object obj) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(obj);
        oos.flush();
        oos.close();
        bos.close();
        return bos.toByteArray();
    }            
}
