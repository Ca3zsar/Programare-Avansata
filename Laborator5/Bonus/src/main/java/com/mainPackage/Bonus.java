package com.mainPackage;

import catalog.AddCommand;
import catalog.Catalog;
import catalog.CatalogCommand;
import catalog.Playlist;
import catalogEntries.*;
import com.github.javafaker.Faker;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Bonus {

    public static void main(String[] args) {
        Catalog mediaCatalog = new Catalog();

        try {
            // Used for the large instances part of the problem.
            CatalogCommand bulkAdd;
            List<CatalogEntry> entries = new ArrayList<>();

            Random random = new Random();
            Faker faker = new Faker();

            for (int i = 0; i < 1000; i++) {
                int randomChoice = random.nextInt(3);
                // Faker doesn't have any movie names, so i will give random ones
                // Neither movies genres, so used music genres.
                switch (randomChoice) {
                    case 0 -> entries.add(new Song(faker.name().name(), "C:\\", faker.music().genre(), faker.artist().name(), random.nextInt(100) + 1920));
                    case 1 -> entries.add(new Book(faker.book().title(), "C:\\", faker.book().author(), "0985930802"));
                    case 2 -> entries.add(new Movie(faker.funnyName().name(), "C:\\", faker.music().genre(), random.nextInt(100) + 1920));
                }
            }
            bulkAdd = new AddCommand(mediaCatalog,entries);
            bulkAdd.execute();

            //Used for the info part
//            CatalogCommand firstAdd = new AddCommand(mediaCatalog, Stream.of(
//                    new Song("Run", "C:\\Users\\cezar\\Desktop\\pa_resources\\Run.mp3", "Nectar", "Joji", 2020),
//                    new Song("MODUS", "C:\\Users\\cezar\\Desktop\\pa_resources\\Modus.mp3", "Nectar", "Joji", 2020),
//                    new Song("Buried Alive", "C:\\Users\\cezar\\Desktop\\pa_resources\\BuriedAlive.mp3", "\n" +
//                            "Nightmare", "Avenged Sevenfold", 2013),
//                    new Book("The Stand", "C:\\Users\\cezar\\Desktop\\pa_resources\\TheStand.pdf", "Stephen King", "9780307743688"),
//                    new Movie("Penguin", "C:\\Users\\cezar\\Desktop\\pa_resources\\Penguin.mp4", "Comedy", 2018)
//            ).collect(Collectors.toList()));
//            firstAdd.execute();
        } catch (InvalidYearException yearException) {
            System.err.println(yearException.getMessage());
        } catch (InvalidISBNException ISBNException) {
            System.err.println("Verify the ISBN!" + ISBNException.getMessage());
        }

//        CatalogCommand firstInfo = new InfoCommand(mediaCatalog);
//        firstInfo.execute();

        Playlist playlist = new Playlist(mediaCatalog);
        System.out.println(playlist.createPlaylist());

    }
}
