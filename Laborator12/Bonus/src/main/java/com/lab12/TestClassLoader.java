package com.lab12;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.net.URL;
import java.net.URLClassLoader;

public class TestClassLoader extends URLClassLoader {
    public TestClassLoader()
    {
        super(new URL[0],ClassLoader.getSystemClassLoader());
    }

    @Override
    public void addURL(URL url)
    {
        super.addURL(url);
    }
}
