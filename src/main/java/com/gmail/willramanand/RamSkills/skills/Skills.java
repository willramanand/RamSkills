package com.gmail.willramanand.RamSkills.skills;

import com.gmail.willramanand.RamSkills.mana.Ability;
import com.gmail.willramanand.RamSkills.stats.Stat;
import com.google.common.collect.ImmutableList;
import org.bukkit.boss.BarColor;

public enum Skills implements Skill {
    AGILITY("Agility", "This skill deals with movement.", BarColor.GREEN, ImmutableList.of(Stat.SPEED),
            null, ImmutableList.of("&dMarathoner&8: &eLess hunger is consumed when moving")),
    ALCHEMY("Alchemy", "This skill deals with brewing of potions.", BarColor.PURPLE, ImmutableList.of(Stat.WISDOM),
            null, ImmutableList.of("&dAlchemist&8: &ePotions last longer")),
    COOKING("Cooking", "This skill deals with the cooking of food.", BarColor.GREEN, ImmutableList.of(Stat.HEALTH),
            null, ImmutableList.of("&dNutritious&8: &eGain more hunger per item consumed", "&dIron Stomach&8: &eResist the effects of eating rotten flesh")),
    COMBAT("Combat", "This skill deals with the killing of creatures.", BarColor.RED, ImmutableList.of(Stat.CRIT_CHANCE, Stat.CRIT_DAMAGE, Stat.ATTACK_SPEED), ImmutableList.of(Ability.SOUL_STEAL, Ability.QUICKSHOT), null),
    DEFENSE("Defense", "This skill deals with taking damage.", BarColor.RED, ImmutableList.of(Stat.TOUGHNESS), null, null),
    ENCHANTING("Enchanting", "This skill deals with the enchanting of items.", BarColor.PURPLE, ImmutableList.of(Stat.WISDOM),
            null, ImmutableList.of("&dWizard&8: &eGain increased XP from all sources")),
    EXCAVATION("Excavation", "This skill deals with digging.", BarColor.BLUE, ImmutableList.of(Stat.TOUGHNESS), ImmutableList.of(Ability.EXCAVATOR), null),
    FARMING("Farming", "This skill deals with the harvesting of crops.", BarColor.GREEN, ImmutableList.of(Stat.HEALTH), ImmutableList.of(Ability.DEMETERS_TOUCH), null),
    FISHING("Fishing", "This skill deals with fishing.", BarColor.GREEN, ImmutableList.of(Stat.HEALTH),
            null, ImmutableList.of("&dAngler&8: &eIncreased chance to catch valuable items")),
    MINING("Mining", "This skill deals with the breaking of stones and ores.", BarColor.BLUE, ImmutableList.of(Stat.TOUGHNESS, Stat.FORTUNE), ImmutableList.of(Ability.VEIN_MINER), null),
    SORCERY("Sorcery", "This skill deals with mana usage and abilities.", BarColor.PURPLE, ImmutableList.of(Stat.WISDOM), null, null),
    WOODCUTTING("Woodcutting", "This skill deals with the chopping of trees.", BarColor.BLUE, ImmutableList.of(Stat.STRENGTH, Stat.FORTUNE), ImmutableList.of(Ability.TREECAPTITOR), null),

    ;

    private final String displayName;
    private final String desc;
    private final BarColor barColor;
    private final ImmutableList<Stat> stats;
    private final ImmutableList<Ability> ability;
    private final ImmutableList<String> perks;

    Skills(String displayName, String desc, BarColor barColor, ImmutableList<Stat> stats, ImmutableList<Ability> ability, ImmutableList<String> perks) {
        this.displayName = displayName;
        this.desc = desc;
        this.barColor = barColor;
        this.stats = stats;
        this.ability = ability;
        this.perks = perks;
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

    @Override
    public ImmutableList<String> getPerks() { return perks; }
}
