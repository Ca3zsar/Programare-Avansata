package com.mainPackage;

import catalog.*;
import catalogEntries.*;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Optional {

    public static void main(String[] args) {
        Catalog mediaCatalog = new Catalog();

        try {
            CatalogCommand firstAdd = new AddCommand(mediaCatalog, Stream.of(
                    new Song("Run", "C:\\Users\\cezar\\Desktop\\pa_resources\\Run.mp3", "Nectar", "Joji", 2020),
                    new Song("MODUS", "C:\\Users\\cezar\\Desktop\\pa_resources\\Modus.mp3", "Nectar", "Joji", 2020),
                    new Song("Buried Alive", "C:\\Users\\cezar\\Desktop\\pa_resources\\BuriedAlive.mp3", "\n" +
                            "Nightmare", "Avenged Sevenfold", 2013),
                    new Book("The Stand", "C:\\Users\\cezar\\Desktop\\pa_resources\\TheStand.pdf", "Stephen King", "9780307743688"),
                    new Movie("Penguin", "C:\\Users\\cezar\\Desktop\\pa_resources\\Penguin.mp4", "Comedy", 2018)
            ).collect(Collectors.toList()));
            firstAdd.executeCommand();
        } catch (InvalidYearException yearException) {
            System.err.println(yearException.getMessage());
        } catch (InvalidISBNException ISBNException) {
            System.err.println("Verify the ISBN!" + ISBNException.getMessage());
        }

        try {
            mediaCatalog.report();
        } catch (IOException | TemplateException exception) {
            exception.printStackTrace();
        }

        try {
            Shell.runShell(mediaCatalog);
        } catch (InvalidCommandException commandException) {
            System.out.println(commandException.getMessage());
        }
    }
}
