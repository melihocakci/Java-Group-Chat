package client;

import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.concurrent.locks.ReentrantLock;

import packet.Packet;

public class PacketReceiver implements Runnable {

    private static ReentrantLock lock = new ReentrantLock();
    private Socket serverSocket;
    private ObjectInputStream inStream;

    public PacketReceiver(Socket serverSocket, ObjectInputStream inStream) {
        this.serverSocket = serverSocket;
        this.inStream = inStream;
    }

    @Override
    public void run() {
    	try {
    		while (serverSocket.isConnected()) {
            	Packet packet = (Packet) inStream.readObject();
                lock.lock();
                System.out.println(packet.username + ": " + packet.message);
                lock.unlock();
            } 
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
