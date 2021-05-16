package com.lab12.testing;

@TestClass
public class TestClass1 implements TestInterface{
    public int count;
    public String message;
    private Boolean tested;

    public TestClass1(String newMessage)
    {
        this.message = newMessage;
    }

    @Override
    public void printMessage(String message) {
        System.out.println(this.getClass().getName() + " : " + message);
    }

    public static void addition()
    {
        int a=3;
        int b=2;
        System.out.println(a+b);
    }

    @TestMethod
    public static void printingStuff()
    {
        System.out.println("this is a test");
    }

}
