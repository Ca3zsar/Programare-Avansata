package com;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

public class SetLocale {
    public static void setLocale (String newLocale)
    {
        Locale.setDefault(Locale.forLanguageTag(newLocale));
        String baseName = "res.Messages";
        ResourceBundle messages =
                ResourceBundle.getBundle(baseName, Locale.getDefault());

        String pattern = messages.getString("locale.set");
        Object[] arguments = {Locale.getDefault().getDisplayName()};

        String message = new MessageFormat(pattern).format(arguments);
        System.out.println(message);
    }
}
