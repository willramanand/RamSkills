package com.gmail.willramanand.RamSkills.mana;

public enum Ability {

    VEIN_MINER("Vein Miner", 10.0, 25, 50),
    DEMETERS_TOUCH("Demeter's Touch", 5, 25, 50),
    SOUL_STEAL("Soul Steal", 0.3, 25, 50),
    TREECAPTITOR("Treecapitator", 3.0, 25, 50),
    QUICKSHOT("Quickshot", 15.0, 25, 50),
    EXCAVATOR("Excavator", 2.5, 25, 50)

    ;

    private final String displayName;
    private final double manaCost;
    private final int unlock;
    private final int upgrade;

    Ability(String displayName, double manaCost, int unlock, int upgrade) {
        this.displayName = displayName;
        this.manaCost = manaCost;
        this.unlock = unlock;
        this.upgrade = upgrade;
    }

    public String getDisplayName() {
        return displayName;
    }

    public double getManaCost() {
        return manaCost;
    }

    public int getUnlock() { return unlock; }

    public int getUpgrade() { return upgrade; }
}
