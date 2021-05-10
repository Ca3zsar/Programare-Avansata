package com.server;

import com.mxgraph.layout.mxCircleLayout;
import com.mxgraph.layout.mxIGraphLayout;
import com.mxgraph.util.mxCellRenderer;
import org.jgrapht.ext.JGraphXAdapter;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

public class ServerManager {
    private final int port;
    public static boolean isRunning = true;
    private static List<ClientInfo> information;
    private static List<String> messages;

    public static ServerSocket serverSocket = null;
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
            FileInputStream file = new FileInputStream("C:\\Users\\cezar\\Desktop\\Sem2\\Programare-Avansata\\Laborator10\\Optional\\users.ser");
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

    public static void addMessage(int index, String message)
    {
        for(ClientInfo friend:information.get(index).getFriends())
        {
            friend.addMessage(message);
        }
    }

    public static List<String>getMessages(int index)
    {
        return information.get(index).getMessages();
    }

    public static void addFriend(int index, List<String>friendsNames)
    {
        List<ClientInfo>existentFriends = information.get(index).getFriends();
        List<String>toLookFor = existentFriends.stream().map(ClientInfo::getName).collect(Collectors.toList());
        for(String friend:friendsNames)
        {
            if(!toLookFor.contains(friend)) {
                int indexFriend = ServerManager.checkClient(friend);
                if(indexFriend != -1) {
                    information.get(index).addFriend(information.get(indexFriend-1));
                }
            }
        }
    }




    public static int checkClient(String name)
    {
        for(int i =0 ;i<information.size();i++)
        {
            if(information.get(i).getName().equals(name))
            {
                return i+1;
            }
        }
        return -1;
    }

    public static void writeInfo()
    {
        try {
            FileOutputStream file = new FileOutputStream("C:\\Users\\cezar\\Desktop\\Sem2\\Programare-Avansata\\Laborator10\\Optional\\users.ser");
            ObjectOutputStream output = new ObjectOutputStream(file);

            output.writeObject(information);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void runServer()
    {
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(100);
        while(isRunning)
        {
            System.out.println("Waiting for a client to connect");

            try {
                Socket socket = serverSocket.accept();
                executor.execute(new ClientThread(socket));

//                new ClientThread(socket).start();
            } catch (IOException exception) {
                System.out.println("Server is now closing");
            }
        }
        try {
            serverSocket.close();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        while (executor.getActiveCount() != 0);
        executor.shutdownNow();
        writeInfo();
        Representation.createRepresentation(information);
    }
}
