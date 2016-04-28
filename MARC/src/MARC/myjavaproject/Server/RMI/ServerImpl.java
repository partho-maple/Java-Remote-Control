package MARC.myjavaproject.Server.RMI;

import MARC.myjavaproject.Server.RMI.ServerAdmin;
import java.net.InetAddress;
import java.rmi.RemoteException;
import java.rmi.server.RMIClientSocketFactory;
import java.rmi.server.RMIServerSocketFactory;
import java.rmi.server.UnicastRemoteObject;


public class ServerImpl extends UnicastRemoteObject implements ServerInterface {        
     
    public ServerImpl () throws RemoteException {} 
    
    public ServerImpl (
            RMIClientSocketFactory csf,
            RMIServerSocketFactory ssf) throws RemoteException {
        super(0, csf, ssf);
    }   
    
    @Override
    public int startViewer(InetAddress inetAddress,
            String username, String password) throws RemoteException {
        return ServerAdmin.addViewer(inetAddress, username, password);
    }
    
    @Override
    public void stopViewer(int index) throws RemoteException {
        ServerAdmin.removeViewer(index);
    }
    
    @Override
    public void updateOptions(Object data, int index) {
        ServerAdmin.updateOptions(data, index);
    }
    
    @Override
    public void updateData(byte[] data, int index) {
       ServerAdmin.updateData(data, index);
    }
    
    @Override
    public byte[] updateData(int index) {
        return ServerAdmin.updateData(index);
    }
    
    @Override
    public byte[] ReceiveFile(String fileName, int index) {
        return ServerAdmin.ReceiveFile(fileName, index);
    }
    
    @Override
    public void SendFile(byte[] filedata, String fileName, int index) {
        ServerAdmin.SendFile(filedata, fileName, index);
    }    
}
