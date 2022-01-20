package com.gmail.willramanand.RamSkills.skills;

import com.gmail.willramanand.RamSkills.mana.Ability;
import com.gmail.willramanand.RamSkills.stats.Stat;
import com.google.common.collect.ImmutableList;
import org.bukkit.boss.BarColor;

public enum Skills implements Skill {
    AGILITY("Agility", "This skill deals with movement.", BarColor.GREEN, ImmutableList.of(Stat.SPEED), null),
    ALCHEMY("Alchemy", "This skill deals with brewing of potions.", BarColor.PURPLE, ImmutableList.of(Stat.WISDOM), null),
    COOKING("Cooking", "This skill deals with the cooking of food.", BarColor.GREEN, ImmutableList.of(Stat.HEALTH), null),
    COMBAT("Combat", "This skill deals with the killing of creatures.", BarColor.RED, ImmutableList.of(Stat.CRIT_CHANCE, Stat.CRIT_DAMAGE, Stat.ATTACK_SPEED), ImmutableList.of(Ability.SOUL_STEAL, Ability.QUICKSHOT)),
    DEFENSE("Defense", "This skill deals with taking damage.", BarColor.RED, ImmutableList.of(Stat.TOUGHNESS), null),
    ENCHANTING("Enchanting", "This skill deals with the enchanting of items.", BarColor.PURPLE, ImmutableList.of(Stat.WISDOM), null),
    EXCAVATION("Excavation", "This skill deals with digging.", BarColor.BLUE, ImmutableList.of(Stat.TOUGHNESS), ImmutableList.of(Ability.EXCAVATOR)),
    FARMING("Farming", "This skill deals with the harvesting of crops.", BarColor.GREEN, ImmutableList.of(Stat.HEALTH), ImmutableList.of(Ability.DEMETERS_TOUCH)),
    FISHING("Fishing", "This skill deals with fishing.", BarColor.GREEN, ImmutableList.of(Stat.HEALTH), null),
    MINING("Mining", "This skill deals with the breaking of stones and ores.", BarColor.BLUE, ImmutableList.of(Stat.TOUGHNESS, Stat.FORTUNE), ImmutableList.of(Ability.VEIN_MINER)),
    SORCERY("Sorcery", "This skill deals with mana usage and abilities.", BarColor.PURPLE, ImmutableList.of(Stat.WISDOM), null),
    WOODCUTTING("Woodcutting", "This skill deals with the chopping of trees.", BarColor.BLUE, ImmutableList.of(Stat.STRENGTH, Stat.FORTUNE), ImmutableList.of(Ability.TREECAPTITOR)),

    ;

    private String displayName;
    private String desc;
    private BarColor barColor;
    private ImmutableList<Stat> stats;
    private ImmutableList<Ability> ability;

    Skills(String displayName, String desc, BarColor barColor, ImmutableList<Stat> stats, ImmutableList<Ability> ability) {
        this.displayName = displayName;
        this.desc = desc;
        this.barColor = barColor;
        this.stats = stats;
        this.ability = ability;
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

    @Override
    public ImmutableList<Ability> getAbility() {return ability;}
}
