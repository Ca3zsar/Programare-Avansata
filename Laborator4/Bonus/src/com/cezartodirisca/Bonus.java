package com.cezartodirisca;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Bonus {

    public static void main(String[] args) {

        // Consider the case when the list of preferences of the student "S0" contains a tie, namely H1 and H2.
        // If there is a tie that means that you can either choose H1 first, or H2, no matter the order, and there will be a
        // stable matching.

        // Consider the case when "H1" will be chosen over "H2"
	    List<Student> students = Stream.of(
	            new Student("S0", new ArrayList<>(Arrays.asList("H0","H1","H2"))),
                new Student("S1", new ArrayList<>(Arrays.asList("H0","H1","H2"))),
                new Student("S2", new ArrayList<>(Arrays.asList("H0","H1"))),
                new Student("S3", new ArrayList<>(Arrays.asList("H0","H2")))
        ).collect(Collectors.toList());

	    List<School> highSchools = Stream.of(
	            new School("H0", new ArrayList<>(Arrays.asList("S3","S0","S1","S2")),1),
                new School("H1", new ArrayList<>(Arrays.asList("S0","S2","S1")),2),
                new School("H2", new ArrayList<>(Arrays.asList("S0","S1","S3")),2)
        ).collect(Collectors.toList());

        //Solve the problem
        Problem toSolve = new Problem(students,highSchools);
        Solution problemSolution = toSolve.solveProblem();

        System.out.println(problemSolution);
        System.out.println("-----");

        // Consider the case when "H2" is chosen over "H1" for the student "S0".
        List<Student> studentsNew = Stream.of(
                new Student("S0", new ArrayList<>(Arrays.asList("H0","H2","H1"))),
                new Student("S1", new ArrayList<>(Arrays.asList("H0","H1","H2"))),
                new Student("S2", new ArrayList<>(Arrays.asList("H0","H1"))),
                new Student("S3", new ArrayList<>(Arrays.asList("H0","H2")))
        ).collect(Collectors.toList());

        List<School> highSchoolsNew = Stream.of(
                new School("H0", new ArrayList<>(Arrays.asList("S3","S0","S1","S2")),1),
                new School("H1", new ArrayList<>(Arrays.asList("S0","S2","S1")),2),
                new School("H2", new ArrayList<>(Arrays.asList("S0","S1","S3")),2)
        ).collect(Collectors.toList());

        //Solve the problem
        Problem toSolveNew = new Problem(studentsNew,highSchoolsNew);
        Solution problemSolutionNew = toSolveNew.solveProblem();

        // The two solutions will be different
        System.out.println(problemSolutionNew);
    }
}
