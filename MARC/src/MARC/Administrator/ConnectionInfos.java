package MARC.Administrator;

import javax.swing.JOptionPane;


public class ConnectionInfos {

    // measurement units for size formatting, from bytes to yottabytes.
    public static final String[] BYTES = {" B", " kB", " MB", " GB", " TB", 
        " PB", " EB", " ZB", " YB"};

    // measurement units for speed formatting, from "bytes per second" to yottabytes.
    public static final String[] BYTES_PER_SECOND = {" B/s", " kB/s", " MB/s", 
        " GB/s", " TB/s", " PB/s", " EB/s", " ZB/s", " YB/s"};
    
    private long previous = 0;
    private long startedAt = 0;
    private long duration = 0;
    private long dataSize = 0;
    private long sentData = 0;
    private long receivedData = 0;
    private long transferSpeed = 0;

    public ConnectionInfos(boolean start) {
        if (start) init();
    }
    
    public void init() {
        startedAt = System.currentTimeMillis();
        duration = 0;
        dataSize = 0;
        sentData = 0;
        receivedData = 0;
        transferSpeed = 0;
    }

    public void display() {
        refresh();
        JOptionPane.showMessageDialog(null, 
                "Duration: \t" + getDuration() + "\n\n" +
                "Sent data: \t" + getSize(sentData) + "\n" +
                "Received data: \t" + getSize(receivedData) + "\n\n" +
                "Total data size: \t" + getSize(dataSize) + "\n\n" +
                "Transfer speed: \t" + getSpeed(),
                "Connection infos", JOptionPane.INFORMATION_MESSAGE);
    }   
    
    public void incSentData(long size) {
        sentData += size;
    }

    public void incReceivedData(long size) {
        receivedData += size;
    }
    
    public void refresh() {
        duration = previous + System.currentTimeMillis() - startedAt;
        dataSize = sentData + receivedData;
        transferSpeed = dataSize * 1000 / duration;
    }

    public String getDuration() {
        long h = duration / 3600000;
        long m = (duration % 3600000) / 60000;
        long s = (duration % 60000) / 1000;
        return h + ":" + m + ":" + s;
    }

    public String getSize(long size) {
        return getHumanFormat(size, BYTES);
    }

    public String getSpeed() {
        return getHumanFormat(transferSpeed, BYTES_PER_SECOND);
    }

    public void resetStartTime() {
        previous = System.currentTimeMillis() - startedAt;
        startedAt = 0;
    }
    
    public String getHumanFormat(long size, String[] measureUnits) {
        int measureQuantity = 1024;

        if (size <= 0)
            return null;

        if (size < measureQuantity)
            return size + measureUnits[0];

        // incrementing "letter" while value >1023
        int i = 1;
        double d = size;
        while ((d = d / measureQuantity) > (measureQuantity - 1)) i++;

        // remove symbols after coma, left only 2:
        long l = (long) (d * 100);
        d = (double) l / 100;

        if (i < measureUnits.length)
            return d + measureUnits[i];

        // if we still here - value is tooo big for us.
        return String.valueOf(size);
    }
}
