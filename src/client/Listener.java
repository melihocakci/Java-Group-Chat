package client;

import java.io.*;
import java.net.Socket;

public class Listener implements Runnable {

    private Socket socket;
    private BufferedReader bufferedReader;

    public Listener(Socket socket, BufferedReader bufferedReader) {
        this.socket = socket;
        this.bufferedReader = bufferedReader;
    }

    @Override
    public void run() {
        String incomingMessage;
        while (socket.isConnected()) {
            try {
                incomingMessage = bufferedReader.readLine();
                System.out.println(incomingMessage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

}
