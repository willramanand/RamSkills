package com.gmail.willramanand.RamSkills.skills;

import org.bukkit.boss.BarColor;

public enum Skills implements Skill {
    AGILITY("Agility", "This skill deals with movement.", BarColor.GREEN),
    ALCHEMY("Alchemy", "This skill deals with brewing of potions.", BarColor.PURPLE),
    ARCHERY("Archery", "This skill deals with the use of bows.", BarColor.RED),
    DEFENSE("Defense", "This skill deals with taking damage.", BarColor.RED),
    COOKING("Cooking", "This skill deals with the cooking of food.", BarColor.GREEN),
    ENCHANTING("Enchanting", "This skill deals with the enchanting of items.", BarColor.PURPLE),
    EXCAVATION("Excavation", "This skill deals with digging.", BarColor.GREEN),
    FARMING("Farming", "This skill deals with the harvesting of crops.", BarColor.GREEN),
    FIGHTING("Fighting", "This skill deals with the killing of creatures.", BarColor.RED),
    FISHING("Fishing", "This skill deals with fishing.", BarColor.GREEN),
    MINING("Mining", "This skill deals with the breaking of stones and ores.", BarColor.GREEN),
    WOODCUTTING("Woodcutting", "This skill deals with the chopping of trees.", BarColor.GREEN),

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
