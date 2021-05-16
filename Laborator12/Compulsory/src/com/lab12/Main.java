package com.lab12;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {


    public static void main(String[] args) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        try {
            String path = reader.readLine();
            Tester tested = new Tester(path);

            tested.showAllClasses();

            tested.runMethods();
        } catch (IOException exception) {
            exception.printStackTrace();
        }


    }
}
