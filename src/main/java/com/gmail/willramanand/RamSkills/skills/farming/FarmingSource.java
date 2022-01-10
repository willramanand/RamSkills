package com.gmail.willramanand.RamSkills.skills.farming;

import com.gmail.willramanand.RamSkills.skills.Skill;
import com.gmail.willramanand.RamSkills.skills.Skills;
import com.gmail.willramanand.RamSkills.source.Source;

public enum FarmingSource implements Source {

    WHEAT(true, false, false),
    POTATO(true, false, false),
    CARROT(true, false, false),
    BEETROOT(true, false, false),
    NETHER_WART(true, false, false),
    PUMPKIN(false, false, false),
    MELON(false, false, false),
    SUGAR_CANE(false, false, true),
    BAMBOO(false, false, true),
    COCOA(true, false, false),
    CACTUS(false, false, true),
    BROWN_MUSHROOM(false, false, false),
    RED_MUSHROOM(false, false, false),
    KELP(false, false, true),
    SEA_PICKLE(true, false, false),
    SWEET_BERRY_BUSH(true, true, false),
    GLOW_BERRIES(true, true, false),
    ;

    private boolean requiresFullyGrown;
    private boolean rightClickHarvestable;
    private boolean multiblock;

    FarmingSource(boolean requiresFullyGrown, boolean rightClickHarvestable, boolean multiblock) {
        this.requiresFullyGrown = requiresFullyGrown;
        this.rightClickHarvestable = rightClickHarvestable;
        this.multiblock = multiblock;
    }

    public boolean requiresFullyGrown() {
        return requiresFullyGrown;
    }

    public boolean isRightClickHarvestable() {
        return rightClickHarvestable;
    }

    public boolean isMultiblock() {return multiblock;}

    @Override
    public Skill getSkill() {
        return Skills.FARMING;
    }

}
