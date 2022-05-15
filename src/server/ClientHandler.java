package server;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

import packet.Packet;

public class ClientHandler implements Runnable {

    public static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
    private Socket clientSocket;
    private ObjectInputStream inStream;
    private ObjectOutputStream outStream;
    private String clientUsername;

    public ClientHandler(Socket clientSocket) {
        try {
            this.clientSocket = clientSocket;
            inStream = new ObjectInputStream(clientSocket.getInputStream());
            outStream = new ObjectOutputStream(clientSocket.getOutputStream());
            Packet packet = (Packet) inStream.readObject();
            clientUsername = packet.username;
            clientHandlers.add(this);
        } catch (Exception ex) {
            closeHandler();
        }
    }

    @Override
    public void run() {
    	try {
    		while (clientSocket.isConnected()) { 
                Packet packet = (Packet) inStream.readObject();
                broadcast(packet);
            } 
        } catch (Exception ex) {
            closeHandler();
        }
    }

    public void broadcast(Packet packet) {
    	try {
    		for (ClientHandler clientHandler : clientHandlers) {
                if (!clientHandler.clientUsername.equals(clientUsername)) {
                	clientHandler.outStream.writeObject(packet);
                	clientHandler.outStream.flush();
                }
            } 
        } catch (Exception ex) {
            closeHandler();
        }
    }

    public void closeHandler() {
        clientHandlers.remove(this);
        try {
            inStream.close();
            outStream.close();
            clientSocket.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
