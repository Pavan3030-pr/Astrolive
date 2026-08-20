package com.astroloop.enums;

public enum CosmicElement {
    FIRE("Fire", "Passion and energy"),
    EARTH("Earth", "Stability and growth"),
    AIR("Air", "Intellect and communication"),
    WATER("Water", "Intuition and emotion");

    private final String displayName;
    private final String description;

    CosmicElement(String displayName, String description) {
        this.displayName = displayName;
        this.description = description;
    }

    public String getDisplayName() { return displayName; }
    public String getDescription() { return description; }
}
