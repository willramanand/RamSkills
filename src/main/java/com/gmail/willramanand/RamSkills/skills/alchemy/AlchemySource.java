package com.gmail.willramanand.RamSkills.skills.alchemy;

import com.gmail.willramanand.RamSkills.skills.Skill;
import com.gmail.willramanand.RamSkills.skills.Skills;
import com.gmail.willramanand.RamSkills.source.Source;

public enum AlchemySource implements Source {
    AWKWARD,
    REGULAR,
    EXTENDED,
    UPGRADED,
    SPLASH,
    LINGERING,
    ;

    @Override
    public Skill getSkill() {
        return Skills.ALCHEMY;
    }
}
