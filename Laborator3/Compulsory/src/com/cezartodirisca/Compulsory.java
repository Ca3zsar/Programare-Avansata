package com.cezartodirisca;

import java.util.ArrayList;

public class Compulsory {

    public static void main(String[] args) {
        // Initialize the locations from the example
        ArrayList<Location>locations = new ArrayList<Location>();
        locations.add(new Hotel("v1", 5));
        locations.get(0).setDistance("v2", 10);
        locations.get(0).setDistance("v3", 50);

        locations.add(new Museum("v2", 10, 16, 5));
        locations.get(1).setDistance("v3", 20);
        locations.get(1).setDistance("v4", 20);
        locations.get(1).setDistance("v5", 10);

        locations.add(new Museum("v3", 9, 16, 10));
        locations.get(2).setDistance("v2", 20);
        locations.get(2).setDistance("v4", 20);

        locations.add(new Church("v4", 7, 13));
        locations.get(3).setDistance("v5", 30);
        locations.get(3).setDistance("v6", 10);

        locations.add(new Church("v5", 7, 12));
        locations.get(4).setDistance("v4", 30);
        locations.get(4).setDistance("v6", 20);

        locations.add(new Restaurant("v6", 4, 8, 20));

        // Initialize a city
        City initialCity = new City("TestCity", locations);


        System.out.println(initialCity);
    }
}
