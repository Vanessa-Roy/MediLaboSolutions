package com.medilabosolutions.assessmentService.model;

/**
 * Represent a trigger.
 */
public enum Trigger {
    /**
     * The constant HEMOGLOBINA1C.
     */
    HEMOGLOBINA1C("HEMOGLOBIN A1C"),
    /**
     * Microalbumin trigger.
     */
    MICROALBUMIN,
    /**
     * Height trigger.
     */
    HEIGHT,
    /**
     * Weight trigger.
     */
    WEIGHT,
    /**
     * Smoker trigger.
     */
    SMOKER("SMOK"),
    /**
     * Abnormal trigger.
     */
    ABNORMAL,
    /**
     * Cholesterol trigger.
     */
    CHOLESTEROL,
    /**
     * Dizziness trigger.
     */
    DIZZINESS,
    /**
     * Relapse trigger.
     */
    RELAPSE,
    /**
     * Reaction trigger.
     */
    REACTION,
    /**
     * Antibodies trigger.
     */
    ANTIBODIES;

    private final String description;

    Trigger() {
        this.description = this.name();
    }

    Trigger(String description) {
        this.description = description;
    }

    /**
     * Gets description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }
}
