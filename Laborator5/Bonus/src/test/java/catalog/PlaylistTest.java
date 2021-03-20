package catalog;

import catalogEntries.*;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PlaylistTest {
    public Playlist playlist;
    public Catalog mediaCatalog;

    @BeforeEach
    public void setUp(){
        mediaCatalog = new Catalog();
        CatalogCommand bulkAdd;
        List<CatalogEntry> entries = new ArrayList<>();

        Random random = new Random();
        Faker faker = new Faker();

        try {
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
        } catch (InvalidYearException yearException) {
            System.err.println(yearException.getMessage());
        } catch (InvalidISBNException ISBNException) {
            System.err.println("Verify the ISBN!" + ISBNException.getMessage());
        }

        bulkAdd = new AddCommand(mediaCatalog, entries);
        bulkAdd.execute();

        playlist = new Playlist(mediaCatalog);
    }

    @RepeatedTest(100)
    public void correctTest() {
        Random random = new Random();
        int randomChoice = random.nextInt(mediaCatalog.entries.size());

        String firstNumber = this.playlist.createPlayList(randomChoice).split("[ \n]")[0];


        int secondRandomChoice = random.nextInt(mediaCatalog.entries.size());
        while(randomChoice == secondRandomChoice)
        {
            secondRandomChoice = random.nextInt(mediaCatalog.entries.size());
        }

        String secondNumber = this.playlist.createPlayList(randomChoice).split("[ \n]")[0];

        System.out.println(firstNumber + " " + secondNumber + "\n");
        Assertions.assertEquals(firstNumber,secondNumber);
    }

}