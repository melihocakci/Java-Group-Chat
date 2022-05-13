package server;

import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private final ServerSocket serverSocket;

    public Server(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(8080);
            Server server = new Server(serverSocket);
            System.out.println("Server is online");
            server.startServer();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void startServer() {
        try {
            while (!serverSocket.isClosed()) {
                Socket clientSocket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                Thread thread = new Thread(clientHandler);
                thread.start();
            }
        } catch (Exception ex) {
            closeServer();
        }
    }

    public void closeServer() {
        try {
            serverSocket.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
