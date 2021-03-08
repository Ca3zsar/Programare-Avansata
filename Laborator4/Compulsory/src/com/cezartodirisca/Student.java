package com.cezartodirisca;

import java.util.List;

public class Student {
    private final String name;
    private List<String> preferences;

    public Student(String newName, List<String> newPreferences) {
        this.name = newName;
        this.preferences = newPreferences;
    }

    public String getName() {
        return this.name;
    }

    public List<String> getPreferences() {
        return this.preferences;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", preferences=" + preferences +
                '}';
    }
}
