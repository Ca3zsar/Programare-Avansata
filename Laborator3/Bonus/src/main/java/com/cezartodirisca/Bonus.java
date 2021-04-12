package com.cezartodirisca;

import java.time.LocalTime;
import java.util.ArrayList;

public class Bonus {

    public static void main(String[] args) {
        // Initialize the locations from the example
        ArrayList<Location> locations = new ArrayList<>();
        locations.add(new Hotel("v1", 5));
        locations.get(0).setDistance("v2", 10);
        locations.get(0).setDistance("v3", 50);

        locations.add(new Museum("v2", LocalTime.of(10,0), LocalTime.of(16,0), 5));
        locations.get(1).setDistance("v3", 20);
        locations.get(1).setDistance("v4", 20);
        locations.get(1).setDistance("v5", 10);
        locations.get(1).setDistance("v1",10);

        locations.add(new Museum("v3", LocalTime.of(9,0), LocalTime.of(16,0), 10));
        locations.get(2).setDistance("v2", 20);
        locations.get(2).setDistance("v4", 20);
        locations.get(2).setDistance("v1",50);

        locations.add(new Church("v4", LocalTime.of(7,0), LocalTime.of(13,0)));
        locations.get(3).setDistance("v5", 30);
        locations.get(3).setDistance("v6", 10);
        locations.get(3).setDistance("v3",20);

        locations.add(new Church("v5", LocalTime.of(7,0), LocalTime.of(12,0)));
        locations.get(4).setDistance("v4", 30);
        locations.get(4).setDistance("v6", 20);

        locations.add(new Restaurant("v6", 4, LocalTime.of(8,0), LocalTime.of(20,0)));
        locations.get(5).setDistance("v5",20);
        locations.get(5).setDistance("v4",10);

        // Initialize a city
        City initialCity = new City("TestCity",locations);

        TravelPlan testTravel = new TravelPlan(initialCity);
        System.out.println(testTravel.solveBonus(locations,locations.get(0),3,100) + " locations visited");
    }
}
