package catalog;

import catalogEntries.CatalogEntry;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Catalog implements Serializable {
    final List<CatalogEntry> entries; // accessible to the class and package

    public Catalog() {
        this.entries = new ArrayList<>();
    }

}
