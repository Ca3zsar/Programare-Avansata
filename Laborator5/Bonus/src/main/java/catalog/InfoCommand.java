package catalog;

import catalogEntries.CatalogEntry;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class InfoCommand implements CatalogCommand {
    Catalog catalogToInspect;

    public InfoCommand(Catalog newCatalog) {
        this.catalogToInspect = newCatalog;
    }

    public void execute() {
        for(CatalogEntry entry : catalogToInspect.entries) {
            File file = new File(entry.getFilePath());

            //Parser method parameters
            AutoDetectParser parser = new AutoDetectParser();
            BodyContentHandler handler = new BodyContentHandler(-1);
            Metadata metadata = new Metadata();
            FileInputStream inputStream = null;

            try {
                inputStream = new FileInputStream(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            ParseContext context = new ParseContext();

            try {
                parser.parse(inputStream, handler, metadata, context);
            } catch (IOException | SAXException | TikaException exception) {
                exception.printStackTrace();
            }

            //getting the list of all meta data elements
            String[] metadataNames = metadata.names();

            System.out.println(entry.getEntryName());
            for (String name : metadataNames) {
                System.out.println(name + ": " + metadata.get(name));

            }
            System.out.println("------------------");
        }
    }

}
