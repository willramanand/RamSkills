package com.gmail.willramanand.RamSkills.skills.agility;

import com.gmail.willramanand.RamSkills.skills.Skill;
import com.gmail.willramanand.RamSkills.skills.Skills;
import com.gmail.willramanand.RamSkills.source.Source;

public enum AgilitySource implements Source {

    JUMP_PER_100,
    FALL_DAMAGE,
    WALK_PER_METER,
    SPRINT_PER_METER,
    SWIM_PER_METER,
    ;

    @Override
    public Skill getSkill() {
        return Skills.AGILITY;
    }
}
