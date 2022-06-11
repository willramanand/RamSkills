package com.gmail.willramanand.RamSkills.skills.combat;

import com.gmail.willramanand.RamSkills.skills.Skill;
import com.gmail.willramanand.RamSkills.skills.Skills;
import com.gmail.willramanand.RamSkills.source.Source;

public enum CombatSource implements Source {
    ALLAY,
    FROG,
    PLAYER,
    BAT,
    CAT,
    CHICKEN,
    COD,
    COW,
    DONKEY,
    FOX,
    GIANT,
    HORSE,
    MUSHROOM_COW("mooshroom"),
    MULE,
    OCELOT,
    PARROT,
    PIG,
    RABBIT,
    SALMON,
    SHEEP,
    SKELETON_HORSE,
    SNOWMAN("snow_golem"),
    SQUID,
    STRIDER,
    TROPICAL_FISH,
    TURTLE,
    VILLAGER,
    WANDERING_TRADER,
    BEE,
    CAVE_SPIDER,
    DOLPHIN,
    ENDERMAN,
    IRON_GOLEM,
    LLAMA,
    TRADER_LLAMA("llama"),
    PIGLIN,
    PANDA,
    POLAR_BEAR,
    PUFFERFISH,
    SPIDER,
    WOLF,
    ZOMBIFIED_PIGLIN,
    BLAZE,
    CREEPER,
    DROWNED,
    ELDER_GUARDIAN,
    ENDERMITE,
    EVOKER,
    GHAST,
    GUARDIAN,
    HOGLIN,
    HUSK,
    ILLUSIONER,
    MAGMA_CUBE,
    PHANTOM,
    PIGLIN_BRUTE,
    PILLAGER,
    RAVAGER,
    SHULKER,
    SILVERFISH,
    SKELETON,
    SLIME,
    STRAY,
    TADPOLE,
    VEX,
    VINDICATOR,
    WARDEN,
    WITCH,
    WITHER_SKELETON,
    ZOGLIN,
    ZOMBIE,
    ZOMBIE_VILLAGER,
    ENDER_DRAGON,
    WITHER,
    AXOLOTL,
    GLOW_SQUID,
    GOAT;
    ;

    private String configName;

    CombatSource() {}

    CombatSource(String configName) {
        this.configName = configName;
    }

    @Override
    public String toString() {
        return configName != null ? configName.toUpperCase() : name();
    }

    @Override
    public String getPath() {
        if (configName == null) {
            return getSkill().toString().toLowerCase() + "." + toString().toLowerCase();
        } else {
            return getSkill().toString().toLowerCase() + "." + configName.toLowerCase();
        }
    }

    @Override
    public Skill getSkill() {
        return Skills.COMBAT;
    }

}
