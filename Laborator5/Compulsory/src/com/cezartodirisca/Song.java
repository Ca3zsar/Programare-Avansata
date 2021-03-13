package com.cezartodirisca;

import java.io.Serializable;
import java.util.Calendar;

public class Song extends CatalogEntry implements Serializable {
    private final String album;
    private final String artist;
    private final int releaseYear;

    public Song(String songName, String songPath, String album, String artist, int newReleaseYear) throws InvalidYearException
    {
        super(songName, songPath);
        this.album = album;
        this.artist = artist;

        if(newReleaseYear < 1900 || newReleaseYear> Calendar.getInstance().get(Calendar.YEAR))
        {
            throw new InvalidYearException("The year is not correct!");
        }

        this.releaseYear = newReleaseYear;
    }

    public String getAlbum() {
        return album;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public String getArtist() {
        return artist;
    }

    @Override
    public String toString() {
        return "Song{" +
                "entryName='" + entryName + '\'' +
                ", filePath=" + filePath +
                ", album='" + album + '\'' +
                ", artist='" + artist + '\'' +
                ", releaseYear=" + releaseYear +
                '}';
    }
}
