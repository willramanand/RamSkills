package com.gmail.willramanand.RamSkills.skills.excavation;

import com.gmail.willramanand.RamSkills.skills.Skill;
import com.gmail.willramanand.RamSkills.skills.Skills;
import com.gmail.willramanand.RamSkills.source.Source;

public enum ExcavationSource implements Source {

    DIRT,
    GRASS_BLOCK,
    SAND,
    GRAVEL,
    MYCELIUM,
    CLAY,
    SOUL_SAND,
    COARSE_DIRT,
    PODZOL,
    SOUL_SOIL,
    RED_SAND,
    ROOTED_DIRT,
    MUD
    ;

    @Override
    public Skill getSkill() {
        return Skills.EXCAVATION;
    }
}
