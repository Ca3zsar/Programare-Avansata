package com.mainPackage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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

    public List<String> getCommand() throws InvalidCommandException
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
            return new ArrayList<>(Collections.singletonList("exit"));
        }

        if(command.equals(""))
        {
            return new ArrayList<>(Collections.singletonList("pass"));
        }

        List<String>arguments = new ArrayList<>(Arrays.asList(command.split("\\s+")));

        if(!(new ArrayList<>(Arrays.asList("add","list","play","load","save")).contains(arguments.get(0))))
        {
            throw new InvalidCommandException("Invalid command!");
        }

        return arguments;
    }

}
