package de.phoenix.wgtest.model.management;

import java.util.Arrays;

public enum EGender {
    MALE("male", "m", "männlich"),
    FEMALE("female", "w", "weiblich"),
    DIVERSE("diverse", "d", "divers");

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

    public static EGender findByName(String name) {
        return Arrays.stream(values())
                .filter(g -> g.getName().equals(name))
                .findAny()
                .orElse(null);
    }
}
