package com.cezartodirisca;

public class Main {

    public static void main(String[] args) {
        // Initialize the locations from the example
        Location[] locations = new Location[6];
        locations[0] = new Hotel("v1",5);
        locations[0].setDistance("v2",10);
        locations[0].setDistance("v3",50);

        locations[1] = new Museum("v2",10,16,5);
        locations[1].setDistance("v3",20);
        locations[1].setDistance("v4",20);
        locations[1].setDistance("v5",10);

        locations[2] = new Museum("v3",9,16,10);
        locations[2].setDistance("v4",20);

        locations[3] = new Church("v4",7,13);
        locations[3].setDistance("v5",30);
        locations[3].setDistance("v6",10);

        locations[4] = new Church("v5",7,12);
        locations[4].setDistance("v6",20);

        locations[5] = new Restaurant("v6",4,8,20);

        // Initialize a city
        City initialCity = new City("TestCity",locations);


        System.out.println(initialCity);
    }
}
