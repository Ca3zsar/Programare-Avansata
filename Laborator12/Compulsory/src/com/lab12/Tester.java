package com.lab12;

import com.lab12.testing.Test;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class Tester {
    private final String classURL;
    private final String packageString;
    private TestClassLoader urlClassLoader;
    private Class loadedClass;

    public Tester(String classURL)
    {
        this.classURL = classURL;
        urlClassLoader = new TestClassLoader();

        packageString = classURL.substring(classURL.indexOf("com")).replace("\\",".");
        packageString.replace("/",".");
        File path = new File(classURL);
        if(path.exists())
        {
            try {
                urlClassLoader.addURL(path.toURI().toURL());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
    }

    public void loadClass(String className)
    {
        try {
            urlClassLoader = new TestClassLoader();
            loadedClass = Class.forName(packageString + '.' + className);
//            loadedClass = urlClassLoader.loadClass(packageString + '.' + className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void showInfo()
    {
        System.out.println("Package : " + loadedClass.getPackage());
        System.out.println("METHODS : ");
        for(Method method:loadedClass.getDeclaredMethods())
        {
            System.out.println(method.getName());
        }
        System.out.println("MEMBERS : ");
        for(Field field:loadedClass.getDeclaredFields())
        {
            System.out.println(field.getModifiers() +" " + field.getType() + " "  + field.getName());
        }
    }

    public void runMethods()
    {
        System.out.println("EXECUTING");
        int passed = 0;
        int failed = 0;
        for(Method method:loadedClass.getDeclaredMethods())
        {
            if(method.isAnnotationPresent(Test.class)){
                try{
                    method.invoke(null);
                    passed ++;
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                    failed++;
                }
            }
        }
        System.out.println("Passed : " + passed + " ; Failed : " + failed);
    }
}
