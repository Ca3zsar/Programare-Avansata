package com.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    private final int PORT = 8081;
    private final String serverAddress = "127.0.0.1";
    private Socket socket = null;

    public Client()
    {
        try {
            this.socket = new Socket(serverAddress,PORT);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public void execute()
    {
        try {
            PrintWriter output = new PrintWriter(socket.getOutputStream(),true);
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            while(true) {
                String request = reader.readLine();
                output.println(request);

                String response = input.readLine();
                if(response == null)
                {
                    System.out.println("The connection has been stopped.");
                    break;
                }
                System.out.println(response);

                if(request.equals("exit"))
                {
                    break;
                }
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        try {
            this.socket.close();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
