package com.cezartodirisca;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Catalog implements Serializable {
    private final List<CatalogEntry> entries;

    public Catalog()
    {
        this.entries = new ArrayList<>();
    }

    public void add(CatalogEntry newEntry)
    {
        this.entries.add(newEntry);
    }

    public void add(List<CatalogEntry> entryList)
    {
        this.entries.addAll(entryList);
    }

    /**
     * Print every entry of the catalogue on the screen
     */
    public void list()
    {
        System.out.println("Catalogue contents:");
        for(CatalogEntry entry : entries)
        {
            System.out.println(entry);
        }
    }

    /**
     * Looks for the specified entry, and if it finds it, play it.
     * @param entryNameToPlay the entry to play
     */
    public void play(String entryNameToPlay)
    {
        int exists = 0;
        CatalogEntry correctEntry = null;

        for(CatalogEntry entry:entries)
        {
            if(entry.getEntryName().equals(entryNameToPlay))
            {
                correctEntry = entry;
                exists = 1;
                break;
            }
        }

        if(exists == 1)
        {
            try{
                File entryFile = new File(correctEntry.getFilePath());

                Desktop desktop = Desktop.getDesktop();

                desktop.open(entryFile);
            }
            catch(IllegalArgumentException exception)
            {
                System.out.println("File not found!" + exception.getMessage());
            }
            catch (IOException exception)
            {
                System.out.println("Cannot open file!" + exception.getMessage());
            }
        }else{
            System.out.println("The entry " + entryNameToPlay + "does not exist in the catalog!");
        }
    }

    /**
     * @param fileName the file where the catalog should be saved
     * @throws IOException in case the file exists or it cannot be created
     */
    public void save(String fileName) throws IOException {

        try(FileOutputStream saveFileStream = new FileOutputStream(fileName);
            ObjectOutputStream outStream = new ObjectOutputStream(saveFileStream)){

            outStream.writeObject(this);
            outStream.flush();
        }
    }

    /**
     *
     * @param fileName the file to load the catalog from
     * @return the catalog loaded from the file
     * @throws IOException in case the file cannot be opened
     * @throws ClassNotFoundException in case the object read from the stream is not a catalog
     */
    static public Catalog load(String fileName) throws IOException, ClassNotFoundException {
        FileInputStream loadFileStream;
        ObjectInputStream inStream =null;

        try {
            loadFileStream = new FileInputStream(fileName);
            inStream =  new ObjectInputStream(loadFileStream);

            Catalog toReturnCatalog;
            toReturnCatalog = (Catalog) inStream.readObject();

            return toReturnCatalog;
        }
        finally {
            if (inStream != null)
            {
                inStream.close();
            }
        }
    }
}
