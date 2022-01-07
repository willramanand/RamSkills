package com.gmail.willramanand.RamSkills.source;

import com.gmail.willramanand.RamSkills.skills.Skill;

public interface Source {

    Skill getSkill();

    default String getPath() {
        return getSkill().toString().toLowerCase() + "." + toString().toLowerCase();
    }
}
