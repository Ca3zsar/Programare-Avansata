package com.lab12.testing.interior;

import com.lab12.testing.TestClass;
import com.lab12.testing.TestInterface;
import com.lab12.testing.TestMethod;

@TestClass
public class InteriorTest implements TestInterface {
    @Override
    public void printMessage(String message) {
        System.out.println(this.getClass().getName() + " : " + message);
    }

    @TestMethod
    public static void multiply(int first, int second)
    {
        System.out.println(first + "*" + second + " = " +first*second);
    }
}
