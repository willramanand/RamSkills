package com.gmail.willramanand.RamSkills.skills.defense;

import com.gmail.willramanand.RamSkills.skills.Skill;
import com.gmail.willramanand.RamSkills.skills.Skills;
import com.gmail.willramanand.RamSkills.source.Source;

public enum DefenseSource implements Source {
    MOB_DAMAGE,
    PLAYER_DAMAGE;
    ;

    @Override
    public Skill getSkill() {
        return Skills.DEFENSE;
    }
}
