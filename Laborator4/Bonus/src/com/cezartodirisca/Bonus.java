package com.cezartodirisca;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Bonus {

    public static void main(String[] args) {

        // Declare the objects from the example
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

        System.out.println("-----");
        System.out.println(problemSolution);
    }
}
