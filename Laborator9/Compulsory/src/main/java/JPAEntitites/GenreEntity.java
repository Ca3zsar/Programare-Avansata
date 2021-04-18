package JPAEntitites;

import javax.persistence.*;

@NamedQuery(name="findByGenreName",
        query="SELECT genre FROM GenreEntity genre WHERE genre.name = :name")
@Entity
@Table(name = "genres")
public class GenreEntity {
    @Id
    @SequenceGenerator(name = "genresSequence", sequenceName = "genresSequence", allocationSize = 1)
    @GeneratedValue(generator = "genresSequence", strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private int id;

    @Column(name="genre_name")
    private String name;

    public GenreEntity(){}

    public GenreEntity(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
