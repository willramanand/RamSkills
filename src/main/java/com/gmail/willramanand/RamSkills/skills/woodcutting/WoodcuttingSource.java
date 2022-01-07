package com.gmail.willramanand.RamSkills.skills.woodcutting;

import com.gmail.willramanand.RamSkills.skills.Skill;
import com.gmail.willramanand.RamSkills.skills.Skills;
import com.gmail.willramanand.RamSkills.source.Source;

public enum WoodcuttingSource implements Source {

    ;
    @Override
    public Skill getSkill() {
        return Skills.WOODCUTTING;
    }

}
