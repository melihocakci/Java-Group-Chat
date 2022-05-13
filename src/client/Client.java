package client;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String username;

    public Client(Socket socket, String username) {
        try {
            this.socket = socket;
            this.username = username;
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (Exception ex) {
            closeClient();
        }
    }

    public void sendMessage() {
        try {
            bufferedWriter.write(username);
            bufferedWriter.newLine();
            bufferedWriter.flush();
            Scanner scanner = new Scanner(System.in);
            while (socket.isConnected()) {
                String messageToSend = scanner.nextLine();
                bufferedWriter.write(username + ": " + messageToSend);
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            closeClient();
        }
    }

    public void listenForMessage() {
        Listener listener = new Listener(socket, bufferedReader);
        Thread thread = new Thread(listener);
        thread.start();
    }

    public void closeClient() {
        try {
            bufferedReader.close();
            bufferedWriter.close();
            socket.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter your username for the group chat: ");
        String username = scanner.nextLine();

        Socket socket = new Socket("127.0.0.1", 8080);

        Client client = new Client(socket, username);
        client.listenForMessage();
        client.sendMessage();
    }
}
