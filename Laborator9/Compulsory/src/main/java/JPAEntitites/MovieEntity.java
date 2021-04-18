package JPAEntitites;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table(name = "movies")
public class MovieEntity {
    @Id
    @SequenceGenerator(name = "movieSequence", sequenceName = "movieSequence",allocationSize = 1)
    @GeneratedValue(generator = "movieSequence",strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private int id;

    @Column(name = "title")
    private String title;

    @Temporal(TemporalType.DATE)
    @Column(name = "release_date")
    private Date releaseDate;

    @Column(name = "duration")
    private int duration;

    @Column(name = "score")
    private double score;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "MovieEntity{" +
                "title='" + title + '\'' +
                ", releaseDate=" + new SimpleDateFormat("yyyy-MM-dd").format(releaseDate) +
                ", duration=" + duration +
                ", score=" + score +
                '}';
    }
}
