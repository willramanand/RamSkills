package com.gmail.willramanand.RamSkills.skills.agility;

import com.gmail.willramanand.RamSkills.skills.Skill;
import com.gmail.willramanand.RamSkills.skills.Skills;
import com.gmail.willramanand.RamSkills.source.Source;

public enum AgilitySource implements Source {

    FALL_DAMAGE,
    MOVE_PER_BLOCK,
    ;

    @Override
    public Skill getSkill() {
        return Skills.AGILITY;
    }
}
