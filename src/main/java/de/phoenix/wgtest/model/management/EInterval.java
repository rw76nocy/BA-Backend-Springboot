package de.phoenix.wgtest.model.management;

import java.util.Arrays;

public enum EInterval {
    DAILY("täglich"),
    WEEK("wöchentlich"),
    TWO_WEEK("2-Wochen-Takt"),
    MONTH("monatlich");

    private final String name;

    EInterval(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static EInterval findByName(String name) {
        return Arrays.stream(values())
                .filter(i -> i.getName().equals(name))
                .findAny()
                .orElse(null);
    }
}
