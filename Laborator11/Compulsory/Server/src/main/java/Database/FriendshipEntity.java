package Database;

import javax.persistence.*;

@NamedQuery(name="findAllFriendships",
        query="SELECT friendship FROM FriendshipEntity friendship WHERE friendship.firstUser = :firstUser")
@Entity
@Table(name = "friendships")
public class FriendshipEntity {
    @Id
    @SequenceGenerator(name = "friendshipSequence", sequenceName = "friendshipSequence",allocationSize = 1)
    @GeneratedValue(generator = "friendshipSequence",strategy = GenerationType.SEQUENCE)
    @Column(name = "friendship_id")
    private int id;

    @Column(name = "first_user")
    private String firstUser;

    @Column(name = "second_user")
    private String secondUser;

    public FriendshipEntity(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstUser() {
        return firstUser;
    }

    public void setFirstUser(String firstUser) {
        this.firstUser = firstUser;
    }

    public String getSecondUser() {
        return secondUser;
    }

    public void setSecondUser(String secondUser) {
        this.secondUser = secondUser;
    }
}
