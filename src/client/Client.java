package client;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    private static Socket serverSocket;
    private static BufferedReader socketReader;
    private static BufferedWriter socketWriter;
    private static Scanner scanner;
    private static String username;

    public static void main(String[] args) throws IOException {
        String serverAddress = args[0];
        int serverPort = Integer.parseInt(args[1]);

        serverSocket = new Socket(serverAddress, serverPort);

        socketReader = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
        socketWriter = new BufferedWriter(new OutputStreamWriter(serverSocket.getOutputStream()));

        scanner = new Scanner(System.in);

        System.out.print("Enter username: ");
        username = scanner.nextLine();

        socketWriter.write(username);
        socketWriter.newLine();
        socketWriter.flush();

        receiveMessage();
        sendMessage();
    }

    private static void sendMessage() {
        while (serverSocket.isConnected()) {
            try {
                String messageToSend = scanner.nextLine();
                socketWriter.write(username + ": " + messageToSend);
                socketWriter.newLine();
                socketWriter.flush();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    private static void receiveMessage() {
        Receiver receiver = new Receiver(serverSocket, socketReader);
        Thread thread = new Thread(receiver);
        thread.start();
    }

}