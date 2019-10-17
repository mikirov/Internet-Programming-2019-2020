package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient {
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public void startConnection(String address, int port) throws IOException {
        clientSocket = new Socket(address, port);
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

    public void sendMessage(String msg) throws IOException {
        out.println(msg);

    }

    public String readLine() throws IOException {
        return in.readLine();
    }

    public void stopConnection() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        ChatClient socketClient = new ChatClient();
        socketClient.startConnection("localhost", 8888);

        Thread readThread = new Thread(() -> {
            try {
                String receivedLine;
                do {
                    receivedLine = socketClient.readLine();
                    if(receivedLine != null){
                        System.out.println(receivedLine);
                    }
                } while (receivedLine != null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        readThread.start();

        Scanner scanner = new Scanner(System.in);
        String line;
        do {
            line = scanner.nextLine();
            socketClient.sendMessage(line);

        } while (!line.equals("exit"));
        readThread.interrupt();
        readThread.join();
        socketClient.stopConnection();
    }
}
