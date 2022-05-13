package server;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable {

    public static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
    private Socket clientSocket;
    private BufferedReader socketReader;
    private BufferedWriter socketWriter;
    private String clientUsername;

    public ClientHandler(Socket clientSocket) {
        try {
            this.clientSocket = clientSocket;
            socketReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            socketWriter = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            clientUsername = socketReader.readLine();
            clientHandlers.add(this);
        } catch (IOException ex) {
            closeSocket();
        }
    }

    @Override
    public void run() {
        String messageFromClient;
        while (clientSocket.isConnected()) {
            try {
                messageFromClient = socketReader.readLine();
                broadcast(messageFromClient);
            } catch (IOException ex) {
                closeSocket();
                return;
            }
        }
    }

    public void broadcast(String message) {
        for (ClientHandler clientHandler : clientHandlers) {
            try {
                if (!clientHandler.clientUsername.equals(clientUsername)) {
                    clientHandler.socketWriter.write(message);
                    clientHandler.socketWriter.newLine();
                    clientHandler.socketWriter.flush();
                }
            } catch (IOException ex) {
                closeSocket();
            }
        }
    }

    public void closeSocket() {
        clientHandlers.remove(this);
        try {
            socketReader.close();
            socketWriter.close();
            clientSocket.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
