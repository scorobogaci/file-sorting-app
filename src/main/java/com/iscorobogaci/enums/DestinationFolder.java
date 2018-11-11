package com.iscorobogaci.enums;

public enum DestinationFolder {

    PICTURES("pictures"),
    VIDEO("video"),
    NOT_SORTED("not-sorted");

    private String value;

    DestinationFolder(String value) {
        this.value = value;
    }

    public String value() {
        return this.value;
    }
}
