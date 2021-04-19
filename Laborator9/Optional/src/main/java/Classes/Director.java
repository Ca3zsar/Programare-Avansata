package Classes;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Director {
    private final String name;
    private Date birthday;

    public Director(String name, String birthday) {
        this.name = name;
        try {
            this.birthday = new SimpleDateFormat("dd-MM-yyyy").parse(birthday);
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
