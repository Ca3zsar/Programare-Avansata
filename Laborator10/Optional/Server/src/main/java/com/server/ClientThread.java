package com.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;

public class ClientThread extends Thread {
    private final Socket socket;
    private boolean isRunning = true;
    private boolean loggedIn = false;
    private int clientIndex;

    public ClientThread(Socket newSocket) {
        this.socket = newSocket;
    }

    @Override
    public void run() {
        try {
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter output = new PrintWriter(socket.getOutputStream(),true);
            while (isRunning) {
                if(loggedIn) {
                    this.socket.setSoTimeout(60 * 1000);
                }

                String request = input.readLine();
                if(request == null)
                {
                    break;
                }

                List<String> tokens = Arrays.asList(request.split(" "));
                System.out.println(tokens);
                switch (tokens.get(0)) {
                    case "exit" -> isRunning = false;
                    case "stop" -> {ServerManager.isRunning = false;isRunning=false;ServerManager.serverSocket.close();}
                    case "register" -> {
                        if(this.loggedIn)
                        {
                            output.println("You are already logged in!");
                            break;
                        }

                        if(tokens.size() != 2)
                        {
                            output.println("Invalid number of arguments");
                        }else{
                            String name = tokens.get(1);
                            clientIndex = ServerManager.addClient(new ClientInfo(name));

                            if(clientIndex == -1) {
                                output.println("There is another user with that name!");
                            }else{
                                loggedIn = true;
                                output.println("Account has been created!");
                            }
                        }
                    }
                    case "login" -> {
                        if(this.loggedIn)
                        {
                            output.println("You are already logged in!");
                            break;
                        }

                        if(tokens.size() != 2)
                        {
                            output.println("Invalid number of arguments");
                        }else{
                            clientIndex = ServerManager.checkClient(tokens.get(1));
                            if(clientIndex == -1)
                            {
                                output.println("The user does not exist!");
                            }else{
                                loggedIn = true;
                                output.println("You are now logged in");
                            }
                        }
                    }
                    case "friend" -> {
                        if(!this.loggedIn)
                        {
                            output.println("You are not logged in!");
                            break;
                        }

                        ServerManager.addFriend(clientIndex-1,tokens.subList(1,tokens.size()));
                        output.println("The users had been added as your friends");
                    }
                    case "send" -> {
                        if(!this.loggedIn)
                        {
                            output.println("You are not logged in!");
                            break;
                        }

                        ServerManager.addMessage(clientIndex-1, String.join(" ",tokens.subList(1,tokens.size())));
                        output.println("Message has been sent");
                    }

                    case "read" -> {
                        if(!this.loggedIn)
                        {
                            output.println("You are not logged in!");
                            break;
                        }

                        String response = String.join("\n",ServerManager.getMessages(clientIndex-1));
                        output.println(response);
                    }
                    default -> {
                        output.println("Invalid command!");

                    }
                }
            }
        } catch (IOException exception) {
            System.out.println("Client disconnected");
            try {
                new PrintWriter(socket.getOutputStream(),true).println("Timeout");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                System.err.println(e);
            }
        }
    }
}
