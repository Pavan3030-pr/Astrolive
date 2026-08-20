package com.astroloop.enums;

public enum ConsultationType {
    CHAT("Chat", 15),
    VOICE("Voice Call", 30),
    VIDEO("Video Call", 45);

    private final String displayName;
    private final int durationMinutes;

    ConsultationType(String displayName, int durationMinutes) {
        this.displayName = displayName;
        this.durationMinutes = durationMinutes;
    }

    public String getDisplayName() { return displayName; }
    public int getDurationMinutes() { return durationMinutes; }
}
