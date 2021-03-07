package com.cezartodirisca;

import com.github.javafaker.Faker;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Optional {

    public static void main(String[] args) {
        Faker nameGenerator = new Faker();
        List<String>studentName = Stream.of(nameGenerator.name().firstName(),
                                            nameGenerator.name().firstName(),
                                            nameGenerator.name().firstName(),
                                            nameGenerator.name().firstName()
        ).collect(Collectors.toList());

        List<String>schoolName = Stream.of(
                nameGenerator.university().name(),
                nameGenerator.university().name(),
                nameGenerator.university().name()
        ).collect(Collectors.toList());

        // Declare the objects from the example
	    List<Student> students = Stream.of(
	            new Student(studentName.get(0), new ArrayList<>(Arrays.asList(schoolName.get(0),schoolName.get(1),schoolName.get(2))),95.2),
                new Student(studentName.get(1), new ArrayList<>(Arrays.asList(schoolName.get(0),schoolName.get(1),schoolName.get(2))),82.05),
                new Student(studentName.get(2), new ArrayList<>(Arrays.asList(schoolName.get(0),schoolName.get(1))),75.7),
                new Student(studentName.get(3), new ArrayList<>(Arrays.asList(schoolName.get(0),schoolName.get(2))),93.6)
        ).collect(Collectors.toList());

	    List<School> highSchools = Stream.of(
	            new School(schoolName.get(0), new ArrayList<>(Arrays.asList(studentName.get(3),studentName.get(0),studentName.get(1),studentName.get(2))),1),
                new School(schoolName.get(1), new ArrayList<>(Arrays.asList(studentName.get(0),studentName.get(2),studentName.get(1))),2),
                new School(schoolName.get(2), new ArrayList<>(Arrays.asList(studentName.get(0),studentName.get(1),studentName.get(3))),2)
        ).collect(Collectors.toList());

        System.out.println(students);
        System.out.println(highSchools);

        System.out.println("-----");

        //Query to display the students who find acceptable a given list of schools
        List<String>toVerifySchools = new ArrayList<>();
        toVerifySchools.addAll(schoolName);

        students.stream().
                filter(student -> student.getPreferences().containsAll(toVerifySchools)).
                forEach(System.out::println);

        //Query to display the schools that have a given student as their top preference, in this case S0.
        highSchools.stream().
                    filter(school -> school.getPreferences().get(0).equals(students.get(0).getName())).
                    forEach(System.out::println);

        //Solve the problem
        Problem toSolve = new Problem(students,highSchools);
        Solution problemSolution = toSolve.solveProblem();

        System.out.println("-----");
        System.out.println(problemSolution);
    }
}
