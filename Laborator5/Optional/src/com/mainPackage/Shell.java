package com.mainPackage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Shell {

    public Shell()
    {

    }

    public void showHelp()
    {
        String helpString = """
                The commands are:\s
                add type name path <options>
                list
                play name
                save path\s
                load path
                -type from the add command can be of type : Song, Movie or Book
                -<options> has to be replaced by Artist, Album and Release Year for a Song,
                \t\t by Author and ISBN for a Book,
                \t\t by Genre and Release Year for a Movie
                Use ? instead of spaces in the names of the files or in the paths
                """;
        System.out.println(helpString);
    }

    public String[] getCommand()
    {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("shell>> ");

        String command = null;

        try {
            command = reader.readLine();
        } catch (IOException exception) {
            System.out.println("Can't read the command from the shell!");
            exception.printStackTrace();
        }

        if(command == null)
        {
            return new String[]{"exit"};
        }

        if(command.equals(""))
        {
            return new String[]{"pass"};
        }

        return command.split(" ");
    }

}
