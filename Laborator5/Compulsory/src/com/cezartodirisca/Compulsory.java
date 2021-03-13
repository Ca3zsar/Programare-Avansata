package com.cezartodirisca;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Compulsory {

    public static void main(String[] args) {
	    Catalog mediaCatalog = new Catalog();

	    try{
            mediaCatalog.add(Stream.of(
                    new Song("Run","C:\\Users\\cezar\\Desktop\\pa_resources\\Run.mp3","Nectar","Joji",2020),
                    new Song("MODUS","C:\\Users\\cezar\\Desktop\\pa_resources\\Modus.mp3","Nectar","Joji",2020),
                    new Song("Buried Alive","C:\\Users\\cezar\\Desktop\\pa_resources\\BuriedAlive.mp3","\n" +
                            "Nightmare","Avenged Sevenfold",2013),
                    new Book("The Stand","C:\\Users\\cezar\\Desktop\\pa_resources\\TheStand.pdf","Stephen King","9780307743688"),
                    new Movie("Penguin","C:\\Users\\cezar\\Desktop\\pa_resources\\Penguin.mp4","Comedy",2018)
            ).collect(Collectors.toList()));
        }
	    catch(InvalidYearException yearException)
        {
            System.err.println(yearException.getMessage());
        }
	    catch(InvalidISBNException ISBNException)
        {
            System.err.println("Verify the ISBN!" + ISBNException.getMessage());
        }

	    mediaCatalog.list();
	    try {
            mediaCatalog.save("C:\\Users\\cezar\\Desktop\\pa_resources\\catalog.ser");
        }
	    catch(IOException saveException)
        {
            System.err.println("Can't save the catalog!");
            saveException.printStackTrace();
        }

        System.out.println();

	    Catalog loadCatalog;
	    try{
	        loadCatalog = Catalog.load("C:\\Users\\cezar\\Desktop\\pa_resources\\catalog.ser");
	        loadCatalog.list();
        }catch(FileNotFoundException fileException)
        {
            System.err.println("Can't find the specified file!");
        }catch(ClassNotFoundException classException) {
            System.err.println("Can't load specified class!");
        }catch(IOException exception)
        {
            System.err.println("IO Error");
            exception.printStackTrace();
        }

	    mediaCatalog.play("Run");
	    mediaCatalog.play("The Stand");

    }
}
