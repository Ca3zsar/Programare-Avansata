package Classes;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Actor {
    private final String name;
    private Date birthday;

    public Actor(String name, String birthday) {
        this.name = name;
        try {
            this.birthday = new SimpleDateFormat("yyyy-MM-dd").parse(birthday);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public String getName() {
        return name;
    }

    public Date getBirthday() {
        return birthday;
    }
}