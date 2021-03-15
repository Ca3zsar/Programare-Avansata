package catalog;

import catalogEntries.CatalogEntry;

import java.util.List;

public class AddCommand implements CatalogCommand {
    private final Catalog catalogToModify;
    private final CatalogEntry entryToAdd;
    private final List<CatalogEntry> entriesToAdd;

    public AddCommand(Catalog newCatalog, CatalogEntry newEntry) {
        this.catalogToModify = newCatalog;
        this.entryToAdd = newEntry;
        this.entriesToAdd = null;
    }

    public AddCommand(Catalog newCatalog, List<CatalogEntry> newEntries) {
        this.catalogToModify = newCatalog;
        this.entriesToAdd = newEntries;
        this.entryToAdd = null;
    }

    public void executeCommand() {
        if (entriesToAdd == null) {
            catalogToModify.entries.add(this.entryToAdd);
        } else {
            catalogToModify.entries.addAll(this.entriesToAdd);
        }
    }
}
