package catalog;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class LoadCommand implements CatalogCommand {
    private final String filePath;
    private Catalog toLoad;
    private int loaded;

    public LoadCommand(String filePath) {
        this.filePath = filePath;
        this.loaded = 0;
    }

    @Override
    public void execute() {
        try (FileInputStream loadFileStream = new FileInputStream(filePath);
             ObjectInputStream inStream = new ObjectInputStream(loadFileStream)) {

            Catalog toReturnCatalog;
            toReturnCatalog = (Catalog) inStream.readObject();

            toLoad = toReturnCatalog;

            loaded = 1;
        } catch (ClassNotFoundException classException) {
            System.err.println("Can't load the catalog class!");
            classException.printStackTrace();
        } catch (IOException ioException) {
            System.err.println("Can't read from the file!");
            ioException.printStackTrace();
        }
    }

    public Catalog getCatalog() throws NoCatalogLoadedException {
        if (loaded == 0) throw new NoCatalogLoadedException("This class didn't loaded any catalog!");
        return toLoad;
    }
}
