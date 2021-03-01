package com.cezartodirisca;

import java.time.LocalTime;

public class Optional {

    public static void main(String[] args) {
        // Initialize the locations from the example
        Location[] locations = new Location[6];
        locations[0] = new Hotel("v1",5);
        locations[0].setDistance("v2",10);
        locations[0].setDistance("v3",50);

        locations[1] = new Museum("v2", LocalTime.of(10,0),LocalTime.of(16,0),5);
        locations[1].setDistance("v3",20);
        locations[1].setDistance("v4",20);
        locations[1].setDistance("v5",10);

        locations[2] = new Museum("v3",LocalTime.of(9,0),LocalTime.of(16,0),10);
        locations[2].setDistance("v4",20);

        locations[3] = new Church("v4",LocalTime.of(7,30),LocalTime.of(13,0));
        locations[3].setDistance("v5",30);
        locations[3].setDistance("v6",10);

        locations[4] = new Church("v5",LocalTime.of(6,30),LocalTime.of(12,0));
        locations[4].setDistance("v6",20);

        locations[5] = new Restaurant("v6",4,LocalTime.of(5,0),LocalTime.of(20,0));

        // Initialize a city
        City initialCity = new City("TestCity",locations);

        System.out.println(initialCity);
        initialCity.displayVisitable();

        ((Visitable)locations[5]).setDefaultHours();
        System.out.println(Visitable.getVisitingHours((Visitable) locations[5]));
    }
}
