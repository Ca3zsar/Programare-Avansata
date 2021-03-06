package com.cezartodirisca;

import java.util.ArrayList;
import java.util.List;

public class School implements Comparable<School> {
    private final String name;
    private List<String> preferences;
    private final int capacity;

    public School(String newName, ArrayList<String> newPreferences,int newCapacity) {
        this.name = newName;
        this.preferences = new ArrayList<>();
        this.preferences = newPreferences;
        this.capacity = newCapacity;
    }

    public String getName() {
        return this.name;
    }

    public List<String> getPreferences() {
        return this.preferences;
    }

    @Override
    public int compareTo(School secondSchool) {
        return this.name.compareTo(secondSchool.getName());
    }

    @Override
    public String toString() {
        return "School{" +
                "name='" + name + '\'' +
                ", preferences=" + preferences +
                ", capacity=" + capacity +
                '}';
    }
}
