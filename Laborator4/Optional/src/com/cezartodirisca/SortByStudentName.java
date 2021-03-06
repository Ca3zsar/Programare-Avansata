package com.cezartodirisca;

import java.util.Comparator;

public class SortByStudentName implements Comparator<Student> {
    @Override
    public int compare(Student firstStudent, Student secondStudent) {
        return firstStudent.getName().compareTo(secondStudent.getName());
    }
}
