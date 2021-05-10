package com.server;

public class Main {

    public static void main(String[] args) {
	    ServerManager server = new ServerManager(8081);
	    server.runServer();
    }
}
