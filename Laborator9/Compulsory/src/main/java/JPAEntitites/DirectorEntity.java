package JPAEntitites;

import javax.persistence.*;
import java.util.Date;

@NamedQuery(name="findByDirectorName",
        query="SELECT director FROM DirectorEntity director WHERE director.name = :name")
@Entity
@Table(name = "directors")
public class DirectorEntity {
    @Id
    @SequenceGenerator(name = "directorsSequence", sequenceName = "directorsSequence", allocationSize = 1)
    @GeneratedValue(generator = "directorsSequence", strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "birthday")
    private Date birthday;

    public DirectorEntity(){}

    public DirectorEntity(String name, Date birthday) {
        this.name = name;
        this.birthday = birthday;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
}
