package com;

import java.text.DateFormatSymbols;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.*;

public class Info {
    public static void showInfo(String specificLocale)
    {
        String baseName = "res.Messages";
        ResourceBundle messages =
                ResourceBundle.getBundle(baseName, Locale.getDefault());

        String pattern = messages.getString("info");
        Object[] arguments = {specificLocale};

        String message = new MessageFormat(pattern).format(arguments);
        System.out.println(message);

        Locale specific = Locale.forLanguageTag(specificLocale);
        System.out.println(specific.getDisplayName());
        System.out.println("Country: " + specific.getDisplayCountry(Locale.forLanguageTag(specificLocale)));
        System.out.println("Language: " + specific.getDisplayLanguage(Locale.forLanguageTag(specificLocale)));
        System.out.println("Currency: " + Currency.getInstance(specific).getDisplayName());
        System.out.println("Weekdays : " + Arrays.toString(DateFormatSymbols.getInstance(specific).getWeekdays()));
        System.out.println("Months: " + Arrays.toString(DateFormatSymbols.getInstance(specific).getMonths()));

        DateTimeFormatter formatter = DateTimeFormatter
                .ofLocalizedDate(FormatStyle.FULL)
                .withLocale(specific);
        LocalDateTime today = LocalDateTime.now();

        System.out.println("Today:" + today.format(formatter));
    }

    public static void showInfo()
    {
        String baseName = "res.Messages";
        ResourceBundle messages =
                ResourceBundle.getBundle(baseName, Locale.getDefault());

        String pattern = messages.getString("info");
        Object[] arguments = {Locale.getDefault().getDisplayName()};

        String message = new MessageFormat(pattern).format(arguments);
        System.out.println(message);

        Locale defaultLocale = Locale.getDefault();
        System.out.println(defaultLocale.getDisplayName());
        System.out.println("Country: " + defaultLocale.getDisplayCountry());
        System.out.println("Language: " + defaultLocale.getDisplayLanguage());
        System.out.println("Currency: " + Currency.getInstance(defaultLocale).getDisplayName());
        System.out.println("Weekdays : " + Arrays.toString(DateFormatSymbols.getInstance(defaultLocale).getWeekdays()));
        System.out.println("Months: " + Arrays.toString(DateFormatSymbols.getInstance(defaultLocale).getMonths()));

        DateTimeFormatter formatter = DateTimeFormatter
                .ofLocalizedDate(FormatStyle.FULL)
                .withLocale(defaultLocale);
        LocalDateTime today = LocalDateTime.now();

        System.out.println("Today:" + today.format(formatter));
    }
}
