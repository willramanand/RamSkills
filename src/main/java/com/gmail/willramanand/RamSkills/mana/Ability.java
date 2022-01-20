package com.gmail.willramanand.RamSkills.mana;

public enum Ability {
    VEIN_MINER("Vein Miner", 10.0),
    DEMETERS_TOUCH("Demeter's Touch", 5),
    SOUL_STEAL("Soul Steal", 0.3),
    TREECAPTITOR("Treecapitator", 3.0),
    QUICKSHOT("Quickshot", 15.0),
    EXCAVATOR("Excavator", 2.5);
    private final String displayName;
    private final double manaCost;

    Ability(String displayName, double manaCost) {
        this.displayName = displayName;
        this.manaCost = manaCost;
    }

    public String getDisplayName() {
        return displayName;
    }

    public double getManaCost() {
        return manaCost;
    }
}
