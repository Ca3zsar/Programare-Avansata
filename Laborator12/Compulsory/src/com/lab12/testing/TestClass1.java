package com.lab12.testing;

public class TestClass1 {
    public int count;
    public String message;
    private Boolean tested;

    public TestClass1(String newMessage)
    {
        this.message = newMessage;
    }

    public static void addition()
    {
        int a=3;
        int b=2;
        System.out.println(a+b);
    }

    @Test
    public static void printingStuff()
    {
        System.out.println("this is a test");
    }

}
