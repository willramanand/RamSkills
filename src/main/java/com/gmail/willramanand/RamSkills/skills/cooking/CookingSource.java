package com.gmail.willramanand.RamSkills.skills.cooking;

import com.gmail.willramanand.RamSkills.skills.Skill;
import com.gmail.willramanand.RamSkills.skills.Skills;
import com.gmail.willramanand.RamSkills.source.Source;

public enum CookingSource implements Source {
    COOKED_MUTTON,
    ;


    @Override
    public Skill getSkill() {
        return Skills.COOKING;
    }
}
