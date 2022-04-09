package com.gmail.willramanand.RamSkills.stats;

public enum Stat {

    HEALTH("Health", "Health", "skills_health_points", "❤", "&c"),
    STRENGTH("Strength", "Strength", "skills_str_points", "❁", "&4"),
    FORTUNE("Fortune", "Fortune", "skills_fortune_points", "♠", "&6"),
    CRIT_CHANCE("CritChance", "Critical Chance", "skills_critchance_points", "☣", "&1"),
    CRIT_DAMAGE("CritDamage", "Critical Damage", "skills_critdamage_points", "❉", "&9"),
    ATTACK_SPEED("AttackSpeed", "Attack Speed", "skills_attackspeed_points", "♦", "&3"),
    TOUGHNESS("Toughness", "Toughness", "skills_tough_points", "❈", "&a"),
    SPEED("Speed", "Speed", "skills_speed_points", "✦", "&f"),
    WISDOM("Wisdom", "Wisdom", "skills_wisdom_points", "❃", "&b"),

    ;

    private final String clazzName;
    private final String displayName;
    private final String modifierName;
    private final String symbol;
    private final String prefix;

    Stat(String clazzName, String displayName , String modifierName, String symbol, String prefix) {
        this.clazzName = clazzName;
        this.displayName = displayName;
        this.modifierName = modifierName;
        this.symbol = symbol;
        this.prefix = prefix;
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

    public String getPrefix() { return prefix; }
}
