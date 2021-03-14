package catalog;

import catalogEntries.CatalogEntry;

public class ListCommand implements CatalogCommand{
    private final Catalog catalogToModify;

    public ListCommand(Catalog newCatalog)
    {
        this.catalogToModify = newCatalog;
    }

    public void executeCommand() {
        System.out.println("Catalogue contents:");
        for(CatalogEntry entry : this.catalogToModify.entries)
        {
            System.out.println(entry);
        }
    }
}
