package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private static ServerSocket serverSocket;

    public static void main(String[] args) throws IOException {
        int port = Integer.parseInt(args[0]);
        serverSocket = new ServerSocket(port);
        System.out.println("Server is listening on port " + port);
        startServer();
    }

    private static void startServer() throws IOException {
        while (!serverSocket.isClosed()) {
            try {
                Socket clientSocket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                Thread thread = new Thread(clientHandler);
                thread.start();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

}
