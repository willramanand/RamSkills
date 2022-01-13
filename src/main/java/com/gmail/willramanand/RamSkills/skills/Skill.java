package com.gmail.willramanand.RamSkills.skills;

import com.gmail.willramanand.RamSkills.stats.Stat;
import com.google.common.collect.ImmutableList;
import org.bukkit.boss.BarColor;

public interface Skill {

    String getDescription();

    String getDisplayName();

    String name();

    String toString();

    BarColor getBarColor();

    ImmutableList<Stat> getStats();

}
