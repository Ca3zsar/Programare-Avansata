package app;

import com.DisplayError;
import com.DisplayLocales;
import com.Info;
import com.SetLocale;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Locale;

public class LocaleExplorer {
    public static void main(String[] args) {
        boolean isRunning = true;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String command;

        //Read the data from the terminal
        while (isRunning) {
            try {
                command = reader.readLine();
                String[] parameters = command.split(" ");
                switch (parameters[0]) {
                    case "display":
                        if (parameters.length != 1) {
                            DisplayError.displayError();
                            break;
                        }
                        DisplayLocales.showAll();
                        break;
                    case "info":
                        if (parameters.length == 2) {
                            Info.showInfo(parameters[1]);
                        } else {
                            Info.showInfo();
                        }
                        break;
                    case "set":
                        if (parameters.length != 2) {
                            DisplayError.displayError();
                            break;
                        }
                        SetLocale.setLocale(parameters[1]);
                        break;
                    case "quit":
                        isRunning = false;
                        break;
                    default:
                        DisplayError.displayError();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
    }
}
