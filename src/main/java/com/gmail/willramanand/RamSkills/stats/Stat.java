package com.gmail.willramanand.RamSkills.stats;

public enum Stat {

    HEALTH("Health", "Health", "skills_health_points", "❤"),
    STRENGTH("Strength", "Strength", "skills_str_points", "❁"),
    CRIT_CHANCE("CritChance", "Critical Chance", "skills_critchance_points", "☣"),
    CRIT_DAMAGE("CritDamage", "Critical Damage", "skills_critdamage_points", "❉"),
    ATTACK_SPEED("AttackSpeed", "Attack Speed", "skills_attackspeed_points", "♦"),
    TOUGHNESS("Toughness", "Toughness", "skills_tough_points", "❈"),
    SPEED("Speed", "Speed", "skills_speed_points", "✦"),
    WISDOM("Wisdom", "Wisdom", "skills_wisdom_points", "❃"),

    ;

    private String clazzName;
    private String displayName;
    private String modifierName;
    private String symbol;

    Stat(String clazzName, String displayName , String modifierName, String symbol) {
        this.clazzName = clazzName;
        this.displayName = displayName;
        this.modifierName = modifierName;
        this.symbol = symbol;
    }

    public String getClazzName() {
        return clazzName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getModifierName() {
        return modifierName;
    }

    public String getSymbol() {
        return symbol;
    }
}
