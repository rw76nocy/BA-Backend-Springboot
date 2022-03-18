package de.phoenix.wgtest.model.management;

public enum ERole {
    GUARDIAN("guardian"),
    ASD("asd"),
    MOTHER("mother"),
    FATHER("father"),
    CHILDDOCTOR("childdoctor"),
    DAYCARE("daycare"),
    HEALTHINSURANCE("healtinsurance"),
    FOODSUPPLIER("foodsupplier"),
    DRIVER("driver");

    private final String type;

    ERole(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

}
