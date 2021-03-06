package com.cezartodirisca;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Problem {
    private final List<Student>students;
    private final List<School> schools;

    public Problem(List<Student> students, List<School> schools) {
        this.students = new ArrayList<>(students);
        this.schools = new ArrayList<>(schools);
    }

    /**
     * Considering that the preference of the schools is given by the students' scores  students will be ordered by their
     * scores and then associate every student with their top available preference.
     * @return the solution of the problem
     */
    public Solution solveProblem()
    {
        Solution problemSolution = new Solution();
        this.students.sort(new SortByStudentScore());

        //A list to hold the number of available positions of the schools.
        Map<String,Integer> available = this.schools.stream().collect(Collectors.toMap(School::getName,School::getCapacity));

        for(Student student:students)
        {
            for(String schoolName:student.getPreferences())
            {
                if(available.get(schoolName) > 0)
                {
                    problemSolution.addToSolution(student.getName(),schoolName);
                    available.put(schoolName,available.get(schoolName)-1);
                    break;
                }
            }
        }

        return problemSolution;
    }
}
