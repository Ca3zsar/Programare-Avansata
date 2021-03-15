package catalog;

import catalogEntries.CatalogEntry;

import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Looks for the specified entry, and if it finds it, play it.
 */
public class PlayCommand implements CatalogCommand {
    private final Catalog catalogToModify;
    private final String entryNameToPlay;

    public PlayCommand(Catalog newCatalog, String entryNameToPlay) {
        this.catalogToModify = newCatalog;
        this.entryNameToPlay = entryNameToPlay;
    }

    public void executeCommand() {
        int exists = 0;
        CatalogEntry correctEntry = null;

        for (CatalogEntry entry : this.catalogToModify.entries) {
            if (entry.getEntryName().equals(entryNameToPlay)) {
                correctEntry = entry;
                exists = 1;
                break;
            }
        }

        if (exists == 1) {
            try {
                File entryFile = new File(correctEntry.getFilePath());

                Desktop desktop = Desktop.getDesktop();

                desktop.open(entryFile);
            } catch (IllegalArgumentException exception) {
                System.out.println("File not found!" + exception.getMessage());
            } catch (IOException exception) {
                System.out.println("Cannot open file!" + exception.getMessage());
            }
        } else {
            System.out.println("The entry " + entryNameToPlay + "does not exist in the catalog!");
        }
    }
}
