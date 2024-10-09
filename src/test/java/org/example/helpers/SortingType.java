package org.example.helpers;

public enum SortingType {
    PRICE_LOW_TO_HIGH("hilo"),
    NAME_A_TO_Z("az"),
    NAME_Z_TO_A("za"),
    PRICE_HIGH_TO_LOW("lohi");

    private final String value;

    SortingType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}