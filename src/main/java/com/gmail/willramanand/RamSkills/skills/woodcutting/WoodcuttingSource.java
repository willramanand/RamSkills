package com.gmail.willramanand.RamSkills.skills.woodcutting;

import com.gmail.willramanand.RamSkills.skills.Skill;
import com.gmail.willramanand.RamSkills.skills.Skills;
import com.gmail.willramanand.RamSkills.source.Source;

public enum WoodcuttingSource implements Source {
    OAK_LOG,
    SPRUCE_LOG,
    BIRCH_LOG,
    JUNGLE_LOG,
    ACACIA_LOG,
    DARK_OAK_LOG,
    OAK_LEAVES,
    SPRUCE_LEAVES,
    BIRCH_LEAVES,
    JUNGLE_LEAVES,
    ACACIA_LEAVES,
    DARK_OAK_LEAVES,
    CRIMSON_STEM,
    WARPED_STEM,
    NETHER_WART_BLOCK,
    WARPED_WART_BLOCK,
    MOSS_BLOCK,
    MOSS_CARPET,
    AZALEA,
    FLOWERING_AZALEA,
    AZALEA_LEAVES,
    FLOWERING_AZALEA_LEAVES,
    ;
    @Override
    public Skill getSkill() {
        return Skills.WOODCUTTING;
    }

}
