package com.medilabosolutions.assessmentService.model;

public enum Trigger {
    // or create a class Trigger with a static attribute as a List<String>
    HEMOGLOBINA1C("HEMOGLOBIN A1C"),
    MICROALBUMIN("MICROALBUMIN"),
    HEIGHT("HEIGTH"),
    WEIGHT("WEIGHT"),
    SMOKER("SMOK"),
    ABNORMAL("ABNORMAL"),
    CHOLESTEROL("CHOLESTEROL"),
    DIZZINESS("DIZZINESS"),
    RELAPSE("RELAPSE"),
    REACTION("REACTION"),
    ANTIBODIES("ANTIBODIES");

    private final String description;

    Trigger(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
