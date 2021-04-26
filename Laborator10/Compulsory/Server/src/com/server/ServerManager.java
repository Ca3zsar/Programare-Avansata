package com.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerManager {
    private final int port;
    public static boolean isRunning = true;
    private static List<ClientInfo> information;
    private static List<String> messages;

    ServerSocket serverSocket = null;
    public ServerManager(int port)
    {
        this.port = port;
        try {
            serverSocket = new ServerSocket(this.port);
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        information = new ArrayList<>();

        readInfo();
    }

    public static void readInfo()
    {
        try {
            FileInputStream file = new FileInputStream("C:\\Users\\cezar\\Desktop\\Sem2\\Programare-Avansata\\Laborator10\\Compulsory\\users.ser");
            ObjectInputStream input;

            if(file.available() > 0) {
                input = new ObjectInputStream(file);
                information = (List<ClientInfo>) input.readObject();
            }


            file.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static int addClient(ClientInfo newClient)
    {
        for(ClientInfo client : information)
        {
            if(client.getName().equals(newClient.getName()))
            {
                return -1;
            }
        }
        information.add(newClient);
        return information.size();
    }

    public static void addMessage(String message)
    {
        if(messages == null){
            messages = new ArrayList<>();
        }
        messages.add(message);
    }

    public static void addFriend(int index, List<String>friends)
    {
        List<String>toLookFor = information.get(index).getFriends();
        for(String friend:friends)
        {
            if(toLookFor.indexOf(friend) < 0) {
                information.get(index).addFriend(friend);
            }
        }
    }

    public static int checkClient(String name)
    {
        for(int i =0 ;i<information.size();i++)
        {
            if(information.get(i).getName().equals(name))
            {
                return i;
            }
        }
        return -1;
    }

    public static void writeInfo()
    {
        try {
            FileOutputStream file = new FileOutputStream("C:\\Users\\cezar\\Desktop\\Sem2\\Programare-Avansata\\Laborator10\\Compulsory\\users.ser");
            ObjectOutputStream output = new ObjectOutputStream(file);

            output.writeObject(information);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static List<String> getMessages()
    {
        return messages;
    }

    public void runServer()
    {
        while(isRunning)
        {
            System.out.println("Waiting for a client to connect");

            try {
                Socket socket = serverSocket.accept();
                new ClientThread(socket).start();
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        try {
            serverSocket.close();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        writeInfo();
    }
}
