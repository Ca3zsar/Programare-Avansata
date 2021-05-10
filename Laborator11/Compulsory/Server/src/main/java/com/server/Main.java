package com.server;

public class Main {

    public static void main(String[] args) {
//        try {
//            ScriptRunner scriptRunner = new ScriptRunner(JDBCConnection.getInstance().getConnection());
//            Reader reader = new BufferedReader(new FileReader("C:\\Users\\cezar\\Desktop\\Sem2\\Programare-Avansata\\Laborator11\\script.sql"));
//
//            scriptRunner.runScript(reader);
//        }

	    ServerManager server = new ServerManager(8081);
	    server.runServer();
    }
}
