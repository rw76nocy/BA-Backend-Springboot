package de.phoenix.wgtest.model.management;

public enum EGender {
    MALE("m"),
    FEMALE("w"),
    DIVERSE("d");

    private String name;

    EGender(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
