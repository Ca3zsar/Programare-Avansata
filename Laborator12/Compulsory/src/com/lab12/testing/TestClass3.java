package com.lab12.testing;

public class TestClass3 implements TestInterface{
    @Override
    @TestMethod
    public void printMessage(String message) {
        System.out.println(this.getClass().getName() + " : " + message);
    }
}
