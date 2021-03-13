package com.cezartodirisca;

import java.io.Serializable;

public abstract class CatalogEntry implements Serializable {
    protected final String entryName;
    protected String filePath; // Not using Path because is not serializable

    public CatalogEntry(String entryName, String stringFilePath)
    {
        this.entryName = entryName;
        this.filePath = stringFilePath;
    }

    public void setFilePath(String stringFilePath)
    {
        this.filePath = stringFilePath;
    }

    public String getEntryName() {
        return entryName;
    }

    public String getFilePath() {
        return filePath;
    }

    @Override
    public abstract String toString();
}
