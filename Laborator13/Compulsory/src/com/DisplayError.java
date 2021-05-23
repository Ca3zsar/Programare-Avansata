package com;

import java.util.Locale;
import java.util.ResourceBundle;

public class DisplayError {
    public static void displayError()
    {
        String baseName = "res.Messages";
        ResourceBundle messages =
                ResourceBundle.getBundle(baseName, Locale.getDefault());
        System.out.println(messages.getString("invalid"));
    }
}
