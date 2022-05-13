package server;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable {

    public static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
    private Socket clientSocket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String clientUsername;

    public ClientHandler(Socket clientSocket) {
        try {
            this.clientSocket = clientSocket;
            bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            clientUsername = bufferedReader.readLine();
            clientHandlers.add(this);
        } catch (Exception ex) {
            closeSocket();
        }
    }

    @Override
    public void run() {
        String messageFromClient;
        while (clientSocket.isConnected()) {
            try {
                messageFromClient = bufferedReader.readLine();
                broadcast(messageFromClient);
            } catch (Exception ex) {
                closeSocket();
                return;
            }
        }
    }

    public void broadcast(String message) {
        for (ClientHandler clientHandler : clientHandlers) {
            try {
                if (!clientHandler.clientUsername.equals(clientUsername)) {
                    clientHandler.bufferedWriter.write(message);
                    clientHandler.bufferedWriter.newLine();
                    clientHandler.bufferedWriter.flush();
                }
            } catch (Exception ex) {
                closeSocket();
            }
        }
    }

    public void closeSocket() {
        clientHandlers.remove(this);
        try {
            bufferedReader.close();
            bufferedWriter.close();
            clientSocket.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
