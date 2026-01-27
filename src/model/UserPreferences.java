package model;

import java.util.List;

public class UserPreferences {
    private List<String> preferredTypes;
    private double attackWeight;
    private double defenseWeight;
    private double speedWeight;
    private String preferredSize; // SMALL, MEDIUM, LARGE, ANY
    private boolean wantsLegendary;

    public UserPreferences(List<String> preferredTypes, double attackWeight,
                           double defenseWeight, double speedWeight,
                           String preferredSize, boolean wantsLegendary) {
        this.preferredTypes = preferredTypes;
        this.attackWeight = attackWeight;
        this.defenseWeight = defenseWeight;
        this.speedWeight = speedWeight;
        this.preferredSize = preferredSize;
        this.wantsLegendary = wantsLegendary;
    }

    // Gettery
    public List<String> getPreferredTypes() { return preferredTypes; }
    public double getAttackWeight() { return attackWeight; }
    public double getDefenseWeight() { return defenseWeight; }
    public double getSpeedWeight() { return speedWeight; }
    public String getPreferredSize() { return preferredSize; }
    public boolean wantsLegendary() { return wantsLegendary; }
}
