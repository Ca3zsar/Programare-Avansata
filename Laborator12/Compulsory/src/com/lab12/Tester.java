package com.lab12;

import com.lab12.testing.TestClass;
import com.lab12.testing.TestMethod;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.MalformedURLException;
import java.util.Enumeration;
import java.util.Objects;
import java.util.Random;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class Tester {
    private final String classURL;
    private String packageString;
    private TestClassLoader urlClassLoader;
    private Class<?> loadedClass;

    private int totalPassed;
    private int totalFailed;

    public Tester(String classURL) {
        this.classURL = classURL;
        urlClassLoader = new TestClassLoader();

        packageString = classURL.substring(classURL.indexOf("com")).replace("\\", ".");
        packageString = packageString.replace("/", ".");
    }

    public void loadClass(String className, String subPackage) {
        try {
            urlClassLoader = new TestClassLoader();

            File path = new File(classURL);
            if (path.exists()) {
                try {
                    urlClassLoader.addURL(path.toURI().toURL());
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
            if (!subPackage.equals("")) {
                subPackage = subPackage + ".";
            }
            String packageTemporary;
            if (packageString.equals("")) {
                packageTemporary = "";
            } else {
                packageTemporary = packageString + ".";
            }
            if (!classURL.endsWith(".jar")) {
                loadedClass = Class.forName(packageTemporary + subPackage + className);
            } else {
                loadedClass = urlClassLoader.loadClass(packageString + subPackage + className);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void showInfo() {
        System.out.println("Class name : " + loadedClass.getName());
        System.out.println("Is Interface : " + loadedClass.isInterface());
        System.out.println("Package : " + loadedClass.getPackage());
        System.out.println("METHODS : ");
        for (Method method : loadedClass.getDeclaredMethods()) {
            System.out.println(method.getName());
        }
        System.out.println("MEMBERS : ");
        for (Field field : loadedClass.getDeclaredFields()) {
            System.out.println(field.getModifiers() + " " + field.getType() + " " + field.getName());
        }
        System.out.println("-----------");
    }

    public void showAllClasses() {
        showAllClasses(classURL, "");
    }

    public void showAllClasses(String startURL, String lastDirectory) {
        File root = new File(startURL);

        if (startURL.endsWith(".jar")) {
            try {
                this.packageString = "";
                JarFile jarFile = new JarFile(startURL);
                Enumeration<?> enumeration = jarFile.entries();
                while (enumeration.hasMoreElements()) {
                    JarEntry entry = (JarEntry) enumeration.nextElement();
                    if (entry.getName().endsWith(".class")) {
                        String name = entry.getName().replace("/", ".");
                        loadClass(name.replaceFirst("[.][^.]+$", ""), "");
                        showInfo();
                    }
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }

        } else {
            for (File file : Objects.requireNonNull(root.listFiles())) {
                if (file.isDirectory() || file.getName().endsWith(".jar")) {
                    showAllClasses(file.getAbsolutePath(), file.getName());
                } else {
                    if (file.getName().endsWith(".class")) {
                        loadClass(file.getName().replaceFirst("[.][^.]+$", ""), lastDirectory);
                        showInfo();
                    }
                }
            }
        }
    }

    public void executeMethod() {
        if (loadedClass.isAnnotationPresent(TestClass.class) && Modifier.isPublic(loadedClass.getModifiers())) {
            int passed = 0;
            int failed = 0;

            System.out.println("EXECUTING " + loadedClass.getName());

            for (Method method : loadedClass.getDeclaredMethods()) {
                if (method.isAnnotationPresent(TestMethod.class)) {
                    try {
                        System.out.println("CALLED METHOD: " + method.getName());
                        Class<?>[] parametersType = method.getParameterTypes();
                        Object[] parameters = new Object[parametersType.length];
                        for (int i = 0; i < parametersType.length; i++) {
                            if (parametersType[i].equals(int.class)) {
                                Random random = new Random();
                                parameters[i] = random.nextInt() % 200;
                            } else if (parametersType[i].equals(String.class)) {
                                parameters[i] = "i am trying";
                            }
                        }

                        Method toInvoke = loadedClass.getDeclaredMethod(method.getName(), parametersType);

                        if (Modifier.isStatic(method.getModifiers())) {
                            toInvoke.invoke(null, parameters);
                        } else {
                            toInvoke.invoke(loadedClass.newInstance(), parameters);
                        }

                        passed++;
                        totalPassed++;
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                        failed++;
                        totalFailed++;
                    } catch (NoSuchMethodException | InstantiationException e) {
                        e.printStackTrace();
                    }
                }
            }
            System.out.println("Passed : " + passed + " ; Failed : " + failed);
        }
    }

    public void runMethods() {
        runMethods(classURL, "");
        System.out.println("Statistics : Passed - " + this.totalPassed + "; Failed - " + this.totalFailed);
    }

    public void runMethods(String startURL, String lastDirectory) {
        if (startURL.endsWith(".jar")) {
            try {
                this.packageString = "";
                JarFile jarFile = new JarFile(startURL);
                Enumeration<?> enumeration = jarFile.entries();
                while (enumeration.hasMoreElements()) {
                    JarEntry entry = (JarEntry) enumeration.nextElement();
                    if (entry.getName().endsWith(".class")) {
                        String name = entry.getName().replace("/", ".");
                        loadClass(name.replaceFirst("[.][^.]+$", ""), "");

                        executeMethod();
                    }
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }

        } else {
            File root = new File(startURL);
            for (File file : Objects.requireNonNull(root.listFiles())) {
                if (file.isDirectory()) {
                    runMethods(file.getAbsolutePath(), file.getName());
                } else {
                    if (file.getName().endsWith(".class")) {
                        loadClass(file.getName().replaceFirst("[.][^.]+$", ""), lastDirectory);
                        executeMethod();
                    }
                }
            }
        }
    }
}
