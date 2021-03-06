package com.cezartodirisca;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.*;

public class ProblemTest {
    private Solution correctSolution,falseSolution;
    private Problem toSolve;

    @Before
    public void setUp()
    {
        List<String> studentName = Stream.of(
                "Ioan","Matei","Otilia","Radu"
        ).collect(Collectors.toList());

        List<String>schoolName = Stream.of(
                "UAIC","UBB","UMF Iasi"
        ).collect(Collectors.toList());

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

        toSolve = new Problem(students,highSchools);
    }

    @Test
    public void toPassTest()
    {
        correctSolution = new Solution();
        correctSolution.addToSolution("Ioan","UAIC");
        correctSolution.addToSolution("Radu","UMF Iasi");
        correctSolution.addToSolution("Matei","UBB");
        correctSolution.addToSolution("Otilia","UBB");

        assertEquals(correctSolution.getSolution(),toSolve.solveProblem().getSolution());
    }

    @Test
    public void toFailTest()
    {
        falseSolution = new Solution();
        falseSolution.addToSolution("Ioan","UAIC");
        falseSolution.addToSolution("Radu","UMF Iasi");
        falseSolution.addToSolution("Matei","UMF Iasi"); // There is the wrong one
        falseSolution.addToSolution("Otilia","UBB");

        assertEquals(falseSolution.getSolution(),toSolve.solveProblem().getSolution());

    }
}