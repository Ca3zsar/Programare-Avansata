package com.cezartodirisca;

public class Compulsory {
    /**
     * Author : Todirisca Cezar
     */
    public static void main(String[] args) {
	    System.out.println("Hello World!");

	    String languages[] = {"C", "C++", "C#", "Python", "Go", "Rust", "JavaScript", "PHP",
	                            "Swift","Java"};
        int n = (int) (Math.random() * 1_000_000);
        n = (n*3 + 0b10101 + 0xFF) * 6;

        //First Option : n % 9, if n % 9 == 0, the sum is 9, otherwise the sum is n % 9
        /*
        int result = n % 9;
        if(result == 0 ){
            results = 9;
        }
        */
        //Second Option : Compute the sum of the digits using a While Statement
        int result;
        while(n > 9)
        {
            result = 0;
            while(n>0)
            {
                result = result + (n % 10);
                n = n / 10;
            }
            n = result;
        }
        System.out.println("Willy-nilly, this semester I will learn " + languages[n]);
    }
}
