package com.cezartodirisca;

import java.util.Comparator;

public class SortByStudentScore implements Comparator<Student> {
    @Override
    public int compare(Student firstStudent, Student secondStudent) {
        return Double.compare(secondStudent.getScore(),firstStudent.getScore());
    }
}
