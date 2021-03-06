package com.cezartodirisca;

import java.util.ArrayList;
import java.util.List;

public class Student {
    private final String name;
    private List<String> preferences;
    private double score;

    public Student(String newName, ArrayList<String> newPreferences,double newScore) {
        this.name = newName;
        this.preferences = new ArrayList<>();
        this.preferences = newPreferences;
        this.score = newScore;
    }

    public String getName() {
        return this.name;
    }

    public double getScore() {
        return score;
    }

    public List<String> getPreferences() {
        return this.preferences;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", preferences=" + preferences +
                ", score=" + score +
                "}\n";
    }
}
