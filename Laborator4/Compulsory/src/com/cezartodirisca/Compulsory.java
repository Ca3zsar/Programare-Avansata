package com.cezartodirisca;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Compulsory {

    public static void main(String[] args) {
        // Declare the objects from the example
	    List<Student> students = Stream.of(
	            new Student("S0", new ArrayList<>(Arrays.asList("H0", "H1","H2"))),
                new Student("S1", new ArrayList<>(Arrays.asList("H0", "H1", "H2"))),
                new Student("S2", new ArrayList<>(Arrays.asList("H0","H1"))),
                new Student("S3", new ArrayList<>(Arrays.asList("H0","H2")))
        ).collect(Collectors.toList());

	    List<School> highSchools = Stream.of(
	            new School("H0", new ArrayList<>(Arrays.asList("S3","S0","S1","S2")),1),
                new School("H1", new ArrayList<>(Arrays.asList("S0","S2","S1")),2),
                new School("H2", new ArrayList<>(Arrays.asList("S0","S1","S3")),2)
        ).collect(Collectors.toList());

        System.out.println(students);
        System.out.println(highSchools);

        // Create a linkedList of students and sort them by name
        List<Student> linkedStudents = new LinkedList<>();
        linkedStudents.add(students.get(2));
        linkedStudents.add(students.get(1));
        linkedStudents.add(students.get(3));
        linkedStudents.add(students.get(0));

        System.out.println("--------------------");
        System.out.println(linkedStudents); // Before the sorting
        linkedStudents.sort(new SortByStudentName());
        System.out.println(linkedStudents); // After sorting
        System.out.println("--------------------");

        Set<School> schoolSet = new TreeSet<>();
        schoolSet.add(highSchools.get(2));
        schoolSet.add(highSchools.get(0));
        schoolSet.add(highSchools.get(1));

        System.out.println(schoolSet);

        //A HashMap describing the students
        Map<String,List<String>> describeStudents = new HashMap<>();
        for(Student temporaryStudent:linkedStudents)
        {
            describeStudents.put(temporaryStudent.getName(),temporaryStudent.getPreferences());
        }
        // Print the student preferences
        System.out.println();
        System.out.println("Students preferences");
        for(Map.Entry<String,List<String>>entry : describeStudents.entrySet())
        {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
        System.out.println();

        //A TreeMap describing the schools
        Map<String,List<String>> describeSchools = new TreeMap<>();
        for(School temporarySchool:schoolSet)
        {
            describeSchools.put(temporarySchool.getName(),temporarySchool.getPreferences());
        }

        // Print the schools preferences
        System.out.println("Schools preferences");
        for(Map.Entry<String,List<String>>entry : describeSchools.entrySet())
        {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}
