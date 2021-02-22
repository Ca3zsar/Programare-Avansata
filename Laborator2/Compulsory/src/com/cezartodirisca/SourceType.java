package com.cezartodirisca;

public enum SourceType{
    WAREHOUSE("Warehouse"),
    FACTORY("Factory");

    public final String label;

    private SourceType(String label)
    {
        this.label = label;
    }
}