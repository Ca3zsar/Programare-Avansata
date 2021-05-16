package com.lab12;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public class Main {


    public static void main(String[] args) {
        Tester tested = new Tester("C:\\Users\\cezar\\Desktop\\Sem2\\Programare-Avansata\\Laborator12\\Compulsory\\out\\production\\Compulsory\\com\\lab12\\testing");
        tested.loadClass("TestClass1");
        tested.showInfo();
        tested.runMethods();
    }
}
