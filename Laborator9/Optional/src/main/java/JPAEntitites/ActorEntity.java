package JPAEntitites;

import javax.persistence.*;
import java.util.Date;

@NamedQuery(name="findByActorName",
        query="SELECT actor FROM ActorEntity actor WHERE actor.name = :name")
@Entity
@Table(name = "actors")
public class ActorEntity {
    @Id
    @SequenceGenerator(name = "actorsSequence", sequenceName = "actorsSequence",allocationSize = 1)
    @GeneratedValue(generator = "actorsSequence",strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "birthday")
    private Date birthday;

    public ActorEntity(){}

    public ActorEntity(String name, Date birthday) {
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
