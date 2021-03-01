package com.cezartodirisca;

import java.util.*;
import java.util.stream.Collectors;

public class City{
    private final String name;
    private final List<Location> locations;

    /**
     *
     * @param newName The name of the city
     * @param newLocations A List of locations in the city
     * A shallow copy of the list of the locations will be used because
     * if there is any modification of the list outside the class, it should
     * happen in the City class too.
     */
    public City(String newName, Location[] newLocations)
    {
        this.name=newName;
        this.locations = Arrays.asList(newLocations.clone());
    }

    public String getName()
    {
        return this.name;
    }

    public void addNewLocation(Location newLocation)
    {
        locations.add(newLocation);
    }

    public List<Location> getLocationList()
    {
        return locations;
    }

    static private int sortByOpeningHour(Location firstLocation, Location secondLocation)
    {
        Visitable firstVisitable = (Visitable) firstLocation;
        Visitable secondVisitable = (Visitable) secondLocation;
        return firstVisitable.getStartTime().compareTo(secondVisitable.getStartTime());
    }

    /**
     * Display the Visitable locations that are not Payable.
     */
    public void displayVisitable()
    {
        List<Location>notPayableButVisitable = locations.stream().
            filter(location -> (!(location instanceof Payable))). // Filter the locations that are Payable
                filter(location -> location instanceof Visitable). // Select the ones that are Visitable
                sorted(City::sortByOpeningHour). // Sort them using the sortByOpeningHour callback function
                collect(Collectors.toList()); // Transform it in a List.
        System.out.println(notPayableButVisitable.toString()); // Print them
    }

    /**
     * Show the table from the example, containing the names and the costs
     */
    @Override
    public String toString() {
        StringBuilder toShowTable = new StringBuilder();
        toShowTable.append("From-To\t\tCost\n");

        // Iterate through all the locations in the city
        for(Location temporaryLocation:this.locations)
        {
            Map<String,Integer> temporaryMap;
            temporaryMap = temporaryLocation.getAllDistances();

            // Get the costs of every path starting from that city
            for(Map.Entry<String,Integer> entry: temporaryMap.entrySet())
            {
                toShowTable.append(temporaryLocation.getName()).
                        append(" -> ").
                        append(entry.getKey()).
                        append("\t").
                        append(entry.getValue()).
                        append("\n");
            }
        }

        return toShowTable.toString();
    }
}
