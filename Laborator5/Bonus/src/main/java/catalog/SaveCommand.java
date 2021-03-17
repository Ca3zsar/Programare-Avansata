package catalog;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class SaveCommand implements CatalogCommand{
    private final Catalog catalogToModify;
    private final String filePath;

    public SaveCommand(Catalog newCatalog, String filePath)
    {
        this.catalogToModify = newCatalog;
        this.filePath = filePath;
    }

    @Override
    public void execute(){
        try (FileOutputStream saveFileStream = new FileOutputStream(this.filePath);
             ObjectOutputStream outStream = new ObjectOutputStream(saveFileStream)) {

            outStream.writeObject(catalogToModify);
            outStream.flush();
        }catch(IOException exception)
        {
            exception.printStackTrace();
        }
    }
}
