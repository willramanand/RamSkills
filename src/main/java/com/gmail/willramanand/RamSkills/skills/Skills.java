package com.gmail.willramanand.RamSkills.skills;

import org.bukkit.boss.BarColor;

public enum Skills implements Skill {
    AGILITY("Agility", "This skill deals with movement.", BarColor.GREEN),
    ALCHEMY("Alchemy", "This skill deals with brewing of potions.", BarColor.PURPLE),
    COOKING("Cooking", "This skill deals with the cooking of food.", BarColor.GREEN),
    COMBAT("Combat", "This skill deals with the killing of creatures.", BarColor.RED),
    DEFENSE("Defense", "This skill deals with taking damage.", BarColor.RED),
    ENCHANTING("Enchanting", "This skill deals with the enchanting of items.", BarColor.PURPLE),
    EXCAVATION("Excavation", "This skill deals with digging.", BarColor.BLUE),
    FARMING("Farming", "This skill deals with the harvesting of crops.", BarColor.GREEN),
    FISHING("Fishing", "This skill deals with fishing.", BarColor.GREEN),
    MINING("Mining", "This skill deals with the breaking of stones and ores.", BarColor.BLUE),
    SORCERY("Sorcery", "This skill deals with mana usage and abilities.", BarColor.PURPLE),
    WOODCUTTING("Woodcutting", "This skill deals with the chopping of trees.", BarColor.BLUE),

    ;

    private String displayName;
    private String desc;
    private BarColor barColor;

    Skills(String displayName, String desc, BarColor barColor) {
        this.displayName = displayName;
        this.desc = desc;
        this.barColor = barColor;
    }


    @Override
    public String getDescription() {
        return desc;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }

    public BarColor getBarColor() {
        return barColor;
    }
}
