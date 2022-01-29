package de.phoenix.wgtest.model.management;

public enum EGender {
    MALE("Mann", "m", "männlich"),
    FEMALE("Frau", "w", "weiblich"),
    DIVERSE("Divers", "d", "divers");

    private final String name;
    private final String shortName;
    private final String description;

    EGender(String name, String shortName, String description) {
        this.name = name;
        this.shortName = shortName;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getShortName() {
        return shortName;
    }

    public String getDescription() {
        return description;
    }
}
