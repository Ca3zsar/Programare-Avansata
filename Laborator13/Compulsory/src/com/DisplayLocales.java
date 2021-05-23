package com;

import java.util.Locale;
import java.util.ResourceBundle;

public class DisplayLocales {
    public static void showAll()
    {
        String baseName = "res.Messages";
        ResourceBundle messages =
                ResourceBundle.getBundle(baseName, Locale.getDefault());
        System.out.println(messages.getString("locales"));

        for(Locale locale : Locale.getAvailableLocales())
        {
            System.out.println(locale.getDisplayName() + " " + locale.getDisplayCountry());
        }
    }
}
