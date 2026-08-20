package com.astroloop.enums;

public enum PremiumTier {
    BASIC("Basic", 0),
    PLUS("Plus", 299),
    PRO("Pro", 699),
    ELITE("Elite", 1299);

    private final String displayName;
    private final int monthlyPriceINR;

    PremiumTier(String displayName, int monthlyPriceINR) {
        this.displayName = displayName;
        this.monthlyPriceINR = monthlyPriceINR;
    }

    public String getDisplayName() { return displayName; }
    public int getMonthlyPriceINR() { return monthlyPriceINR; }
}
