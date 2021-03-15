package com.mainPackage;

import catalog.*;
import catalogEntries.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Shell {
    static public void showHelp() {
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

    /**
     * A method to run the shell, reading commands from the command line, parsing them, checking their validity and
     * executing them.
     *
     * @param mediaCatalog the catalog to execute commands on
     * @throws InvalidCommandException throw this exception in the case of an invalid command
     */
    static public void runShell(Catalog mediaCatalog) throws InvalidCommandException {
        Shell.showHelp();

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        String command = null;

        boolean shellRunning = true;

        while (shellRunning) {
            System.out.print("shell>> ");
            try {
                command = reader.readLine();
            } catch (IOException exception) {
                System.out.println("Can't read the command from the shell!");
                exception.printStackTrace();
            }

            if (command == null) {
                return;
            }

            if (command.equals("")) {
                continue;
            }

            // Split the command by the space delimiter
            List<String> arguments = new ArrayList<>(Arrays.asList(command.split("\\s+")));

            // If the command is not in the already defined ones, throw exception
            if (!(new ArrayList<>(Arrays.asList("add", "list", "play", "load", "save", "help")).contains(arguments.get(0)))) {
                throw new InvalidCommandException("Invalid command!");
            }

            arguments = arguments.stream().map(argument -> argument.replace("?", " ")).collect(Collectors.toList());

            // Check what command was read
            switch (arguments.get(0)) {
                case "pass":
                    continue;
                case "exit":
                    shellRunning = false;
                    break;
                case "help":
                    Shell.showHelp();
                    break;
                case "add":
                    CatalogEntry newEntry;
                    CatalogCommand toAdd;
                    if (arguments.size() < 2) {
                        throw new InvalidCommandException("Invalid number of arguments!");
                    }
                    switch (arguments.get(1)) {
                        case "Song" -> {
                            if (arguments.size() < 7) {
                                throw new InvalidCommandException("Invalid number of arguments!");
                            }
                            try {
                                newEntry = new Song(arguments.get(2), arguments.get(3), arguments.get(4), arguments.get(5), Integer.parseInt(arguments.get(6)));

                                toAdd = new AddCommand(mediaCatalog, newEntry);
                                toAdd.executeCommand();
                            } catch (InvalidYearException exception) {
                                System.err.println("Invalid year!");
                            }
                        }
                        case "Movie" -> {
                            if (arguments.size() < 6) {
                                throw new InvalidCommandException("Invalid number of arguments!");
                            }
                            try {
                                newEntry = new Movie(arguments.get(2), arguments.get(3), arguments.get(4), Integer.parseInt(arguments.get(5)));

                                toAdd = new AddCommand(mediaCatalog, newEntry);
                                toAdd.executeCommand();
                            } catch (InvalidYearException exception) {
                                System.err.println("Invalid year!");
                            }
                        }
                        case "Book" -> {
                            if (arguments.size() < 6) {
                                throw new InvalidCommandException("Invalid number of arguments!");
                            }
                            try {
                                newEntry = new Book(arguments.get(2), arguments.get(3), arguments.get(4), arguments.get(5));

                                toAdd = new AddCommand(mediaCatalog, newEntry);
                                toAdd.executeCommand();
                            } catch (InvalidISBNException exception) {
                                System.err.println("Invalid ISBN!");
                            }
                        }
                        default -> System.out.println("Invalid Type!");
                    }
                    break;
                case "list":
                    if (arguments.size() > 1) {
                        throw new InvalidCommandException("Invalid number of arguments!");
                    }
                    CatalogCommand toList = new ListCommand(mediaCatalog);
                    toList.executeCommand();
                    break;
                case "play":
                    if (arguments.size() < 2) {
                        throw new InvalidCommandException("Invalid number of arguments!");
                    }
                    CatalogCommand toPlay = new PlayCommand(mediaCatalog, arguments.get(1));
                    toPlay.executeCommand();
                    break;
                case "load":
                    if (arguments.size() < 2) {
                        throw new InvalidCommandException("Invalid number of arguments!");
                    }
                    CatalogCommand toLoad = new LoadCommand(arguments.get(1));
                    toLoad.executeCommand();
                    try {
                        mediaCatalog = ((LoadCommand) toLoad).getCatalog();
                    } catch (NoCatalogLoadedException noCatalog) {
                        System.err.println("No catalog was loaded!");
                    }
                    break;
                case "save":
                    if (arguments.size() < 2) {
                        throw new InvalidCommandException("Invalid number of arguments!");
                    }
                    CatalogCommand toSave = new SaveCommand(mediaCatalog, arguments.get(1));
                    toSave.executeCommand();
                    break;
            }
        }
    }

}
