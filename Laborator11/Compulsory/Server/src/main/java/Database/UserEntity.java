package Database;

import javax.persistence.*;

@NamedQuery(name="findByUsername",
        query="SELECT user FROM UserEntity user WHERE user.username = :username")
@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    @Column(name = "username")
    private String username;

    public UserEntity(){}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
