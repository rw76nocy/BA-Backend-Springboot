package de.phoenix.wgtest.model.management;

public enum ERole {
    SUPERVISOR1("supervisor1"),
    SUPERVISOR2("supervisor2"),
    GUARDIAN("guardian"),
    ASD("asd"),
    MOTHER("mother"),
    FATHER("father"),
    CHILDDOCTOR("childdoctor"),
    DAYCARE("daycare"),
    HEALTHINSURANCE("healtinsurance"),
    FOODSUPPLIER("foodsupplier"),
    DRIVER("driver"),
    REFERENCE_PERSON("reference_person"),
    DOCTOR("doctor"),
    THERAPIST("therapist"),
    PARTNER("partner");

    private final String type;

    ERole(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

}
