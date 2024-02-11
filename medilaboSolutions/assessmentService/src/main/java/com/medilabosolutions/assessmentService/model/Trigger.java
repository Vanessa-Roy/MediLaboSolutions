package com.medilabosolutions.assessmentService.model;

public enum Trigger {
    // or create a class Trigger with a static attribute as a List<String>
    HEMOGLOBINA1C("HEMOGLOBIN A1C"),
    MICROALBUMIN,
    HEIGHT,
    WEIGHT,
    SMOKER("SMOK"),
    ABNORMAL,
    CHOLESTEROL,
    DIZZINESS,
    RELAPSE,
    REACTION,
    ANTIBODIES;

    private final String description;

    Trigger() {
        this.description = this.name();
    }

    Trigger(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
