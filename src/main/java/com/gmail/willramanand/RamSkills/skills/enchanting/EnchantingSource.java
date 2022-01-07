package com.gmail.willramanand.RamSkills.skills.enchanting;

import com.gmail.willramanand.RamSkills.skills.Skill;
import com.gmail.willramanand.RamSkills.skills.Skills;
import com.gmail.willramanand.RamSkills.source.Source;

public enum EnchantingSource implements Source {

    WEAPON_PER_LEVEL,
    ARMOR_PER_LEVEL,
    TOOL_PER_LEVEL,
    BOOK_PER_LEVEL;

    @Override
    public Skill getSkill() {
        return Skills.ENCHANTING;
    }
}