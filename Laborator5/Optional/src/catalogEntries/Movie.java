package catalogEntries;

import java.io.Serializable;
import java.util.Calendar;

public class Movie extends CatalogEntry implements Serializable {
    private final int releaseYear;
    private final String genre;

    public Movie(String movieName, String moviePath, String genre, int newReleaseYear) throws InvalidYearException
    {
        super(movieName,moviePath);
        this.genre = genre;

        if(newReleaseYear < 1900 || newReleaseYear > Calendar.getInstance().get(Calendar.YEAR))
        {
            throw new InvalidYearException("Invalid year!");
        }

        this.releaseYear = newReleaseYear;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public String getGenre() {
        return genre;
    }

    @Override
    public String toString() {
        return "Movie{" + "name=" + entryName +
                "filePath=" + filePath +
                "releaseYear=" + releaseYear +
                ", genre='" + genre + '\'' +
                '}';
    }
}
