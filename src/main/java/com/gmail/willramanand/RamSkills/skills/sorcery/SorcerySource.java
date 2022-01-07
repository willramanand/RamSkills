package com.gmail.willramanand.RamSkills.skills.sorcery;

import com.gmail.willramanand.RamSkills.skills.Skill;
import com.gmail.willramanand.RamSkills.skills.Skills;
import com.gmail.willramanand.RamSkills.source.Source;

public enum SorcerySource implements Source {

    MANA_ABILITY_USE;

    @Override
    public Skill getSkill() {
        return Skills.SORCERY;
    }
}