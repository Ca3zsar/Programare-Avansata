package com.server;

import Database.*;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

public class ServerManager {
    public static boolean isRunning = true;
    public static ServerSocket serverSocket = null;
    private final int port;

    public ServerManager(int port) {
        this.port = port;
        try {
            serverSocket = new ServerSocket(this.port);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public static String addClient(String newClient) {
        if (checkClient(newClient) == null) {
            UserRepository userRepository = new UserRepository(Factory.getInstance().getEntityManagerFactory());
            UserEntity user = new UserEntity();
            user.setUsername(newClient);
            userRepository.create(user);
            return newClient;
        }
        return null;
    }

    public static void addMessage(String sender, String message) {
        FriendshipRepository friendshipRepository = new FriendshipRepository(Factory.getInstance().getEntityManagerFactory());
        List<FriendshipEntity> friendshipEntities = friendshipRepository.findAllFriendships(sender);

        for (FriendshipEntity friend : friendshipEntities) {
            MessageRepository messageRepository = new MessageRepository(Factory.getInstance().getEntityManagerFactory());

            MessageEntity messageEntity = new MessageEntity();
            messageEntity.setSender(sender);
            messageEntity.setReceiver(friend.getSecondUser());
            messageEntity.setContent(message);

            messageRepository.create(messageEntity);
        }
    }

    public static List<String> getMessages(String receiver) {
        MessageRepository messageRepository = new MessageRepository(Factory.getInstance().getEntityManagerFactory());
        return messageRepository.findAllMessages(receiver).stream().map(MessageEntity::getContent).collect(Collectors.toList());
    }

    public static void addFriend(String firstUser, List<String> friendsNames) {
        FriendshipRepository friendshipRepository = new FriendshipRepository(Factory.getInstance().getEntityManagerFactory());
        List<String> existent = friendshipRepository.findAllFriendships(firstUser).stream().map(FriendshipEntity::getSecondUser).collect(Collectors.toList());

        List<String> toLookFor = new ArrayList<>(friendsNames);
        toLookFor.removeAll(existent);
        for (String friend : toLookFor) {
            String found = ServerManager.checkClient(friend);
            if (found != null) {
                FriendshipEntity friendshipEntity = new FriendshipEntity();
                friendshipEntity.setFirstUser(firstUser);
                friendshipEntity.setSecondUser(friend);
                friendshipRepository.create(friendshipEntity);
            }

        }
    }

    public static String checkClient(String name) {
        UserRepository userRepository = new UserRepository(Factory.getInstance().getEntityManagerFactory());
        UserEntity found = userRepository.findByName(name);
        if (found == null) {
            return null;
        } else {
            return found.getUsername();
        }
    }

    public void runServer() {
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(100);
        while (isRunning) {
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
        while (executor.getActiveCount() != 0) ;
        executor.shutdownNow();
    }
}
