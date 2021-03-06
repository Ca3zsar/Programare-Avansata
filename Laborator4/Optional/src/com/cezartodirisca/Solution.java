package com.cezartodirisca;

import java.util.HashMap;
import java.util.Map;

public class Solution {
    private Map<String,String> matching;

    public Solution() {
        this.matching = new HashMap<>();
    }

    public void addToSolution(String student, String school)
    {
        this.matching.put(student,school);
    }

    @Override
    public String toString() {
        StringBuilder toReturn = new StringBuilder();
        for(Map.Entry<String,String>entry : matching.entrySet())
        {
            toReturn.append("( ").
                    append(entry.getKey()).
                    append(" : ").
                    append(entry.getValue()).
                    append(" )\n");
        }
        return toReturn.toString();
    }
}
