package com.cezartodirisca;

import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.jdbc.ScriptRunner;

public class Main {

    public static void main(String[] args) {
        try {
            ScriptRunner scriptRunner = new ScriptRunner(JDBCConnection.getInstance().getConnection());
            Reader reader = new BufferedReader(new FileReader("C:\\Users\\cezar\\Desktop\\Sem2\\Programare-Avansata\\Laborator8\\movies.sql"));

            long start;
            long stop;
            scriptRunner.runScript(reader);

            //Approach with JDBC singleton
//            start = System.currentTimeMillis();
//            InformationTool importer = new InformationTool();
//            importer.AddInformation(1,1,0,0,JDBCConnection.getInstance().getConnection());
//            stop = System.currentTimeMillis();
//            System.out.println("Time : " + (stop-start) + " ms");

            //ThreadPoolExecutor + HikariCP approach
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl("jdbc:oracle:thin:@localhost:1521:XE");
            config.setUsername("movie");
            config.setPassword("movie");
            config.addDataSourceProperty("cachePrepStmts", "true");
            config.addDataSourceProperty("prepStmtCacheSize", "250");
            config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
            config.setMaximumPoolSize(100);
            HikariDataSource ds = new HikariDataSource(config);
            Connection connection = ds.getConnection();

            int threads = 50;
            ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(threads);
            List<InformationAdder> adders = new ArrayList<>();

            executor.setMaximumPoolSize(threads);
            executor.setCorePoolSize(threads);
            for(int i=0;i<threads;i++)
            {
                adders.add(new InformationAdder(i*threads,i*threads, ds.getConnection(),threads));
            }
            start = System.currentTimeMillis();
            for (InformationAdder adder : adders) {
                executor.execute(adder);

            }

            while (executor.getActiveCount() != 0) ;
            executor.shutdownNow();

            stop = System.currentTimeMillis();
            System.out.println("Time : " + (stop-start) + " ms");

        } catch (SQLException | ClassNotFoundException | IOException exception) {
            exception.printStackTrace();
        }
    }
}
