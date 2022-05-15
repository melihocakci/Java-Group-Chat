package client;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

import packet.Packet;

public class Client {

    private static Socket serverSocket;
    private static ObjectOutputStream outStream;
    private static ObjectInputStream inStream;
    private static Scanner scanner;
    private static String username;

    public static void main(String[] args) throws IOException {
        String serverAddress = args[0];
        int serverPort = Integer.parseInt(args[1]);

        serverSocket = new Socket(serverAddress, serverPort);

        outStream = new ObjectOutputStream(serverSocket.getOutputStream());
        inStream = new ObjectInputStream(serverSocket.getInputStream());

        scanner = new Scanner(System.in);

        System.out.print("Enter username: ");
        username = scanner.nextLine();
        
        Packet packet = new Packet(username, "aaa");
        outStream.writeObject(packet);
        outStream.flush();

        receivePacket();
        sendPacket();
    }

    private static void receivePacket() {
        PacketReceiver packetReceiver = new PacketReceiver(serverSocket, inStream);
        Thread thread = new Thread(packetReceiver);
        thread.start();
    }
    
    private static void sendPacket() {
    	try {
    		while (serverSocket.isConnected()) {
                String message = scanner.nextLine();
                Packet packet = new Packet(username, message);
                outStream.writeObject(packet);
                outStream.flush();
            } 
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}