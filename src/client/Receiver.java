package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.locks.ReentrantLock;

public class Receiver implements Runnable {

    private static ReentrantLock lock = new ReentrantLock();
    private Socket serverSocket;
    private BufferedReader socketReader;

    public Receiver(Socket serverSocket, BufferedReader socketReader) {
        this.serverSocket = serverSocket;
        this.socketReader = socketReader;
    }

    @Override
    public void run() {
        while (serverSocket.isConnected()) {
            try {
                String message = socketReader.readLine();
                lock.lock();
                System.out.println(message);
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            } finally {
                lock.unlock();
            }
        }
    }

}
