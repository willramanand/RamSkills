package com.gmail.willramanand.RamSkills.skills.mining;

import com.gmail.willramanand.RamSkills.skills.Skill;
import com.gmail.willramanand.RamSkills.skills.Skills;
import com.gmail.willramanand.RamSkills.source.Source;

public enum MiningSource implements Source {
    STONE,
    COBBLESTONE,
    GRANITE,
    DIORITE,
    ANDESITE,
    COAL_ORE,
    IRON_ORE,
    NETHER_QUARTZ_ORE,
    REDSTONE_ORE,
    GOLD_ORE,
    LAPIS_ORE,
    DIAMOND_ORE,
    EMERALD_ORE,
    TERRACOTTA,
    WHITE_TERRACOTTA,
    ORANGE_TERRACOTTA,
    YELLOW_TERRACOTTA,
    LIGHT_GRAY_TERRACOTTA,
    BROWN_TERRACOTTA,
    RED_TERRACOTTA,
    NETHERRACK,
    BLACKSTONE,
    BASALT,
    MAGMA_BLOCK,
    NETHER_GOLD_ORE,
    ANCIENT_DEBRIS,
    END_STONE,
    OBSIDIAN,
    DEEPSLATE,
    COPPER_ORE,
    TUFF,
    CALCITE,
    SMOOTH_BASALT,
    AMETHYST_BLOCK,
    AMETHYST_CLUSTER,
    DEEPSLATE_COAL_ORE,
    DEEPSLATE_IRON_ORE,
    DEEPSLATE_COPPER_ORE,
    DEEPSLATE_GOLD_ORE,
    DEEPSLATE_REDSTONE_ORE,
    DEEPSLATE_EMERALD_ORE,
    DEEPSLATE_LAPIS_ORE,
    DEEPSLATE_DIAMOND_ORE,
    DRIPSTONE_BLOCK,
    ICE(true),
    PACKED_ICE(true),
    BLUE_ICE(true),
    ;

    private final boolean requiresSilkTouch;

    MiningSource() {
        this(false);
    }

    MiningSource(boolean requiresSilkTouch) {
        this.requiresSilkTouch = requiresSilkTouch;
    }
    @Override
    public Skill getSkill() {
        return Skills.MINING;
    }

    public boolean requiresSilkTouch() {
        return requiresSilkTouch;
    }
}
