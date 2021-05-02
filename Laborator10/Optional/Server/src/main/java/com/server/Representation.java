package com.server;

import com.jcraft.jsch.*;
import com.mxgraph.layout.mxCircleLayout;
import com.mxgraph.layout.mxIGraphLayout;
import com.mxgraph.util.mxCellRenderer;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import org.jgrapht.ext.JGraphXAdapter;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Representation {
    private static String username;
    private static String password;
    private static final String remoteHost = "174.138.110.11";
    private static Session jschSession;

    private static ChannelSftp setupJsch() throws JSchException {
        JSch jsch = new JSch();
//        jsch.setKnownHosts("known_hosts");

        File file = new File("credentials.txt");

        BufferedReader bufferedReader;
        try {
            bufferedReader = new BufferedReader(new FileReader(file));
            username = bufferedReader.readLine();
            password = bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        jschSession = jsch.getSession(username, remoteHost);

        java.util.Properties config = new java.util.Properties();
        config.put("StrictHostKeyChecking", "no");
        jschSession.setConfig(config);

        jschSession.setPassword(password);
        jschSession.connect();
        return (ChannelSftp) jschSession.openChannel("sftp");
    }

    public static void uploadFile() throws JSchException, SftpException {
        ChannelSftp channelSftp = setupJsch();
        channelSftp.connect();
        String localFile = "output.html";
        String representationFile = "representation.png";
        String remoteDir = "/var/www/21martie.live/";

        channelSftp.put(localFile, remoteDir + "index.html");
        channelSftp.put(representationFile,remoteDir +"representation.png");

        channelSftp.exit();
        jschSession.disconnect();
    }

    public static void report() throws IOException, TemplateException, SQLException, ClassNotFoundException {
        Configuration configuration = new Configuration();

        configuration.setDirectoryForTemplateLoading(new File("C:\\Users\\cezar\\Desktop\\Sem2\\Programare-Avansata\\Laborator10\\Optional\\Server\\src\\resources"));

        configuration.setDefaultEncoding("UTF-8");
        configuration.setLocale(Locale.US);

        configuration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

        Map<String, Object> input = new HashMap<>();

        input.put("title", "Friends network");
        input.put("source","representation.png");

        Template template = configuration.getTemplate("report.ftl");

        try (Writer fileWriter = new FileWriter("output.html")) {
            template.process(input, fileWriter);

            File entryFile = new File("output.html");
            Desktop desktop = Desktop.getDesktop();

            desktop.open(entryFile);
        }

        try {
            Representation.uploadFile();
        } catch (JSchException | SftpException e) {
            e.printStackTrace();
        }
    }

    public static void createRepresentation(List<ClientInfo> information)
    {
        DefaultDirectedGraph<String, DefaultEdge> network = new DefaultDirectedGraph<>(DefaultEdge.class);
        for(ClientInfo user:information)
        {
            network.addVertex(user.getName());
        }

        for(ClientInfo user:information)
        {
            for(ClientInfo friend:user.getFriends())
            {
                network.addEdge(user.getName(),friend.getName());
            }
        }

        File representationImage = new File("representation.png");
        if(representationImage.exists())
        {
            representationImage.delete();
        }

        try {
            representationImage.createNewFile();
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        JGraphXAdapter<String, DefaultEdge> graphAdapter =
                new JGraphXAdapter<>(network);
        mxIGraphLayout layout = new mxCircleLayout(graphAdapter);
        layout.execute(graphAdapter.getDefaultParent());

        BufferedImage image =
                mxCellRenderer.createBufferedImage(graphAdapter, null, 2, Color.WHITE, true, null);
        try {
            ImageIO.write(image, "PNG", representationImage);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        try {
            Representation.report();
        } catch (IOException | TemplateException | SQLException | ClassNotFoundException exception) {
            exception.printStackTrace();
        }
    }
}
