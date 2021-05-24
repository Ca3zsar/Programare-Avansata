package com.lab12.testing;

@TestClass
public class TestClass2 implements TestInterface{
    @Override
    @TestMethod
    public void printMessage(String message) {
        System.out.println(this.getClass().getName() + " : " + message);
    }
}
