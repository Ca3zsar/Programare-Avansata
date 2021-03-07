package com.cezartodirisca;

import java.util.*;
import java.util.stream.Collectors;

public class Problem {
    private final List<Student>students;
    private final List<School> schools;

    public Problem(List<Student> students, List<School> schools) {
        this.students = new ArrayList<>(students);
        this.schools = new ArrayList<>(schools);
    }

    /**
     * To solve the problem when the schools have particular preferences the Gale-Shapley algorithm will be used:
     * An unassigned student wants to get admitted to the best current school on their
     * preference list. If the school is not full, it will accept them. Otherwise check to
     * see if the current student is better than the bottom on the school accepted list.
     * If so, replace them and mark the respective student as unassigned. Repeat until all
     * the students are assigned to a school.
     * @return the solution of the problem
     */
    public Solution solveProblem()
    {
        Solution problemSolution = new Solution();

        Map<Student ,Status> unassignedStudents = new HashMap<>();
        for(Student student : students)
        {
            unassignedStudents.put(student,Status.FREE);
        }

        Map<School,ArrayList<Student>>schoolAdmission = new HashMap<>();
        for(School school : schools)
        {
            schoolAdmission.put(school,new ArrayList<>());
        }

        while(unassignedStudents.containsValue(Status.FREE))
        {
            Student toAssignStudent = null;
            for(Student student:students)
            {
                if(unassignedStudents.get(student).equals(Status.FREE))
                {
                    toAssignStudent = student;
                    break;
                }
            }
            int numberOfPreferences = toAssignStudent.getPreferences().size();
            for(int i=0;i<numberOfPreferences;i++)
            {
                School toAssignSchool = null;
                for(School school:schools)
                {
                    if(school.getName().equals(toAssignStudent.getPreferences().get(i)))
                    {
                        toAssignSchool = school;
                        break;
                    }
                }

                List<Student>toSort = schoolAdmission.get(toAssignSchool);
                toSort.add(toAssignStudent);

                unassignedStudents.put(toAssignStudent,Status.ASSIGNED);

                List<String>actualPreferences = toAssignSchool.getPreferences();
                toSort = toSort.stream().
                        sorted(Comparator.comparingInt(student -> actualPreferences.indexOf(student.getName()))).
                        collect(Collectors.toList());

                schoolAdmission.put(toAssignSchool,new ArrayList<>(toSort));

                if(schoolAdmission.get(toAssignSchool).size() > toAssignSchool.getCapacity())
                {
                    int listSize = schoolAdmission.get(toAssignSchool).size();
                    unassignedStudents.put(schoolAdmission.get(toAssignSchool).get(listSize-1),Status.FREE);
                    schoolAdmission.get(toAssignSchool).remove(listSize-1);
                }

                if(unassignedStudents.get(toAssignStudent).equals(Status.ASSIGNED))
                {
                    break;
                }
            }
        }



        return problemSolution;
    }
}
