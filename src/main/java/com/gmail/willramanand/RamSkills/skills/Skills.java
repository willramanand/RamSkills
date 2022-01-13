package com.gmail.willramanand.RamSkills.skills;

import com.gmail.willramanand.RamSkills.stats.Stat;
import com.google.common.collect.ImmutableList;
import org.bukkit.boss.BarColor;

import java.util.function.Supplier;

public enum Skills implements Skill {
    AGILITY("Agility", "This skill deals with movement.", BarColor.GREEN, ImmutableList.of(Stat.SPEED)),
    ALCHEMY("Alchemy", "This skill deals with brewing of potions.", BarColor.PURPLE, ImmutableList.of(Stat.WISDOM)),
    COOKING("Cooking", "This skill deals with the cooking of food.", BarColor.GREEN, ImmutableList.of(Stat.HEALTH)),
    COMBAT("Combat", "This skill deals with the killing of creatures.", BarColor.RED, ImmutableList.of(Stat.CRIT_CHANCE, Stat.CRIT_DAMAGE, Stat.ATTACK_SPEED)),
    DEFENSE("Defense", "This skill deals with taking damage.", BarColor.RED, ImmutableList.of(Stat.TOUGHNESS)),
    ENCHANTING("Enchanting", "This skill deals with the enchanting of items.", BarColor.PURPLE, ImmutableList.of(Stat.WISDOM)),
    EXCAVATION("Excavation", "This skill deals with digging.", BarColor.BLUE, ImmutableList.of(Stat.TOUGHNESS)),
    FARMING("Farming", "This skill deals with the harvesting of crops.", BarColor.GREEN, ImmutableList.of(Stat.HEALTH)),
    FISHING("Fishing", "This skill deals with fishing.", BarColor.GREEN, ImmutableList.of(Stat.HEALTH)),
    MINING("Mining", "This skill deals with the breaking of stones and ores.", BarColor.BLUE, ImmutableList.of(Stat.TOUGHNESS, Stat.FORTUNE)),
    SORCERY("Sorcery", "This skill deals with mana usage and abilities.", BarColor.PURPLE, ImmutableList.of(Stat.WISDOM)),
    WOODCUTTING("Woodcutting", "This skill deals with the chopping of trees.", BarColor.BLUE, ImmutableList.of(Stat.STRENGTH, Stat.FORTUNE)),

    ;

    private String displayName;
    private String desc;
    private BarColor barColor;
    private ImmutableList<Stat> stats;

    Skills(String displayName, String desc, BarColor barColor, ImmutableList<Stat> stats) {
        this.displayName = displayName;
        this.desc = desc;
        this.barColor = barColor;
        this.stats = stats;
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

    @Override
    public ImmutableList<Stat> getStats() { return stats; }
}
