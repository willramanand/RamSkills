package com.gmail.willramanand.RamSkills.skills.farming;

import com.gmail.willramanand.RamSkills.skills.Skill;
import com.gmail.willramanand.RamSkills.skills.Skills;
import com.gmail.willramanand.RamSkills.source.Source;
import com.gmail.willramanand.RamSkills.utils.BlockUtils;
import org.bukkit.block.Block;

public enum FarmingSource implements Source {

    WHEAT(new String[] {"CROPS"}, false, true),
    POTATO(new String[] {"POTATOES"}, false, true),
    CARROT(new String[] {"CARROTS"}, false, true),
    BEETROOT(new String[] {"BEETROOTS", "BEETROOT_BLOCK"}, false, true),
    NETHER_WART(new String[] {"NETHER_WARTS", "NETHER_STALK"}, false, true),
    PUMPKIN(true),
    MELON(new String[] {"MELON_BLOCK"}, true),
    SUGAR_CANE(new String[] {"SUGAR_CANE_BLOCK"}, true),
    BAMBOO,
    COCOA(null, false, true),
    CACTUS(true),
    BROWN_MUSHROOM(true),
    RED_MUSHROOM(true),
    KELP(new String[] {"KELP_PLANT"}, true),
    SEA_PICKLE(true),
    SWEET_BERRY_BUSH(null, false, false, 2, true),
    GLOW_BERRIES(new String[] {"CAVE_VINES", "CAVE_VINES_PLANT"}, false, false, 0, true);

    private String[] otherMaterials;
    private boolean checkBlockReplace;
    private boolean requiresFullyGrown;
    private int minGrowthStage;
    private boolean rightClickHarvestable;

    FarmingSource() {

    }

    FarmingSource(String... otherMaterials) {
        this.otherMaterials = otherMaterials;
    }

    FarmingSource(boolean checkBlockReplace) {
        this.checkBlockReplace = checkBlockReplace;
    }

    FarmingSource(String[] otherMaterials, boolean checkBlockReplace) {
        this(otherMaterials);
        this.checkBlockReplace = checkBlockReplace;
    }

    FarmingSource(String[] otherMaterials, boolean checkBlockReplace, boolean requiresFullyGrown) {
        this(otherMaterials, checkBlockReplace);
        this.requiresFullyGrown = requiresFullyGrown;
    }

    FarmingSource(String[] otherMaterials, boolean checkBlockReplace, boolean requiresFullyGrown, int minGrowthStage) {
        this(otherMaterials, checkBlockReplace, requiresFullyGrown);
        this.minGrowthStage = minGrowthStage;
    }

    FarmingSource(String[] otherMaterials, boolean checkBlockReplace, boolean requiresFullyGrown, int minGrowthStage, boolean rightClickHarvestable) {
        this(otherMaterials, checkBlockReplace, requiresFullyGrown, minGrowthStage);
        this.rightClickHarvestable = rightClickHarvestable;
    }

    public boolean shouldCheckBlockReplace() {
        return checkBlockReplace;
    }

    public boolean isRightClickHarvestable() {
        return rightClickHarvestable;
    }

    public boolean isMatch(Block block) {
        String materialName = block.getType().toString();
        boolean match = false;
        if (materialName.equalsIgnoreCase(toString())) { // Try to match by enum name
            match = true;
        } else { // Try to match by other material names instead
            if (otherMaterials != null) {
                for (String sourceMaterial : otherMaterials) {
                    if (materialName.equalsIgnoreCase(sourceMaterial)) {
                        match = true;
                        break;
                    }
                }
            }
        }
        if (requiresFullyGrown && match) {
            match = BlockUtils.isFullyGrown(block);
        }
        if (minGrowthStage > 0 && match) {
            match = BlockUtils.getGrowthStage(block) >= minGrowthStage;
        }
        return match;
    }

    @Override
    public Skill getSkill() {
        return Skills.FARMING;
    }

}
