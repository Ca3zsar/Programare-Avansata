package com.mainPackage;

import catalog.*;
import catalogEntries.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Optional {

    public static void main(String[] args) {
	    Catalog mediaCatalog = new Catalog();

	    try{
	        CatalogCommand firstAdd = new AddCommand(mediaCatalog,Stream.of(
                    new Song("Run","C:\\Users\\cezar\\Desktop\\pa_resources\\Run.mp3","Nectar","Joji",2020),
                    new Song("MODUS","C:\\Users\\cezar\\Desktop\\pa_resources\\Modus.mp3","Nectar","Joji",2020),
                    new Song("Buried Alive","C:\\Users\\cezar\\Desktop\\pa_resources\\BuriedAlive.mp3","\n" +
                            "Nightmare","Avenged Sevenfold",2013),
                    new Book("The Stand","C:\\Users\\cezar\\Desktop\\pa_resources\\TheStand.pdf","Stephen King","9780307743688"),
                    new Movie("Penguin","C:\\Users\\cezar\\Desktop\\pa_resources\\Penguin.mp4","Comedy",2018)
            ).collect(Collectors.toList()));
	        firstAdd.executeCommand();
        }
	    catch(InvalidYearException yearException)
        {
            System.err.println(yearException.getMessage());
        }
	    catch(InvalidISBNException ISBNException)
        {
            System.err.println("Verify the ISBN!" + ISBNException.getMessage());
        }

	    Shell shell = new Shell();

	    shell.showHelp();

	    boolean shellRunning = true;
        // Read from the command line some commands
	    while(shellRunning)
        {
            List<String> arguments = new ArrayList<>(Arrays.asList(shell.getCommand()));
            arguments = arguments.stream().map(argument->argument.replace("?"," ")).collect(Collectors.toList());

            switch (arguments.get(0))
            {
                case "pass":
                    continue;
                case "exit":
                    shellRunning = false;
                    break;
                case "help":
                    shell.showHelp();
                    break;
                case "add":
                    CatalogEntry newEntry;
                    CatalogCommand toAdd;
                    switch (arguments.get(1)){

                        case "Song":
                            try {
                                newEntry = new Song(arguments.get(2), arguments.get(3), arguments.get(4),arguments.get(5),Integer.parseInt(arguments.get(6)));

                                toAdd = new AddCommand(mediaCatalog,newEntry);
                                toAdd.executeCommand();
                            }catch(InvalidYearException exception)
                            {
                                System.err.println("Invalid year!");
                            }
                            break;
                        case "Movie":
                            try{
                                newEntry = new Movie(arguments.get(2),arguments.get(3),arguments.get(4),Integer.parseInt(arguments.get(5)));

                                toAdd = new AddCommand(mediaCatalog,newEntry);
                                toAdd.executeCommand();
                            }catch(InvalidYearException exception)
                            {
                                System.err.println("Invalid year!");
                            }
                            break;
                        case "Book":
                            try{
                                newEntry = new Book(arguments.get(2),arguments.get(3),arguments.get(4),arguments.get(5));

                                toAdd = new AddCommand(mediaCatalog,newEntry);
                                toAdd.executeCommand();
                            }catch(InvalidISBNException exception)
                            {
                                System.err.println("Invalid ISBN!");
                            }
                    }
                    break;
                case "list":
                    CatalogCommand toList = new ListCommand(mediaCatalog);
                    toList.executeCommand();
                    break;
                case "play":
                    CatalogCommand toPlay = new PlayCommand(mediaCatalog,arguments.get(1));
                    toPlay.executeCommand();
                    break;
                case "load":
                    CatalogCommand toLoad = new LoadCommand(arguments.get(1));
                    toLoad.executeCommand();
                    try {
                        mediaCatalog = ((LoadCommand) toLoad).getCatalog();
                    }catch(NoCatalogLoadedException noCatalog)
                    {
                        System.err.println("No catalog was loaded!");
                    }
                    break;
                case "save":
                    CatalogCommand toSave = new SaveCommand(mediaCatalog,arguments.get(1));
                    toSave.executeCommand();
                    break;
            }
        }

    }
}
