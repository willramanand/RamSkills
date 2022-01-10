package com.gmail.willramanand.RamSkills.skills.cooking;

import com.gmail.willramanand.RamSkills.skills.Skill;
import com.gmail.willramanand.RamSkills.skills.Skills;
import com.gmail.willramanand.RamSkills.source.Source;

public enum CookingSource implements Source {
    ENCHANTED_GOLDEN_APPLE,
    GOLDEN_APPLE,
    APPLE,
    GOLDEN_CARROT,
    CARROT,
    COOKED_MUTTON,
    MUTTON,
    COOKED_PORKCHOP,
    PORKCHOP,
    COOKED_COD,
    COD,
    COOKED_SALMON,
    SALMON,
    PUFFERFISH,
    TROPICAL_FISH,
    COOKED_BEEF,
    BEEF,
    COOKED_CHICKEN,
    CHICKEN,
    COOKED_RABBIT,
    RABBIT,
    RABBIT_STEW,
    MUSHROOM_STEW,
    SUSPICIOUS_STEW,
    SWEET_BERRIES,
    COOKIE,
    CAKE,
    BREAD,
    POTATO,
    BAKED_POTATO,
    BEETROOT,
    BEETROOT_STEW,
    MELON,
    DRIED_KELP,
    GLOW_BERRIES,
    PUMPKIN_PIE,

    ;


    @Override
    public Skill getSkill() {
        return Skills.COOKING;
    }
}
