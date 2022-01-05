package com.gmail.willramanand.RamSkills.skills;

import org.bukkit.boss.BarColor;

import java.util.Locale;

public interface Skill {

    String getDescription();

    String getDisplayName();

    String name();

    String toString();

    BarColor getBarColor();

}
