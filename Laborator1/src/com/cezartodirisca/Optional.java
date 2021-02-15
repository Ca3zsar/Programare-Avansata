package com.cezartodirisca;

public class Optional {
    public static void main(String[] args) {
        int n;
        try{
            n = Integer.parseInt(args[0]);
        } catch(NumberFormatException nfe){
            System.out.println("Invalid input!");
            return;
        }
        if(n % 2 == 0)
        {
            System.out.printf("Input not odd!");
            return;
        }
    }
}
