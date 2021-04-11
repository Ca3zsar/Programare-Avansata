package Classes;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Movie {
    private String title;
    private Date releaseDate;
    private int duration;
    private double score;

    public Movie(String title, String releaseDate, int duration, double score) {
        this.title = title;
        try {
            this.releaseDate = new SimpleDateFormat("yyyy-MM-dd").parse(releaseDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.duration = duration;
        this.score = score;
    }

    public String getTitle() {
        return title;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public int getDuration() {
        return duration;
    }

    public double getScore() {
        return score;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "title='" + title + '\'' +
                ", releaseDate=" + new SimpleDateFormat("yyyy-MM-dd").format(releaseDate) +
                ", duration=" + duration +
                ", score=" + score +
                '}';
    }
}
