package com.gmail.willramanand.RamSkills.skills.fishing;

import com.gmail.willramanand.RamSkills.skills.Skill;
import com.gmail.willramanand.RamSkills.skills.Skills;
import com.gmail.willramanand.RamSkills.source.Source;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

public enum FishingSource implements Source {

    COD,
    SALMON,
    TROPICAL_FISH,
    PUFFERFISH,
    TREASURE,
    JUNK,
    RARE,
    EPIC;

    @Override
    public Skill getSkill() {
        return Skills.FISHING;
    }

    @Override
    public String getPath() {
        return "fishing." + toString().toLowerCase();
    }

    @SuppressWarnings("deprecation")
    @Nullable
    public static FishingSource valueOf(ItemStack item) {
        Material mat = item.getType();

        if (mat.equals(Material.COD)) {
            return FishingSource.COD;
        } else if (mat.equals(Material.SALMON)) {
            return FishingSource.SALMON;
        } else if (mat.equals(Material.TROPICAL_FISH)) {
            return FishingSource.TROPICAL_FISH;
        } else if (mat.equals(Material.PUFFERFISH)) {
            return FishingSource.PUFFERFISH;
        }
        if (mat.equals(Material.BOW) || mat.equals(Material.ENCHANTED_BOOK) || mat.equals(Material.NAME_TAG) || mat.equals(Material.SADDLE) || mat.equals(Material.NAUTILUS_SHELL)) {
            return FishingSource.TREASURE;
        } else if (mat.equals(Material.BOWL) || mat.equals(Material.LEATHER) || mat.equals(Material.LEATHER_BOOTS) || mat.equals(Material.ROTTEN_FLESH)
                || mat.equals(Material.POTION) || mat.equals(Material.BONE) || mat.equals(Material.TRIPWIRE_HOOK) || mat.equals(Material.STICK)
                || mat.equals(Material.STRING) || mat.equals(Material.INK_SAC) || mat.equals(Material.LILY_PAD)
                || mat.equals(Material.BAMBOO)) {
            return FishingSource.JUNK;
        } else if (mat.equals(Material.FISHING_ROD)) {
            if (item.getEnchantments().size() != 0) {
                return FishingSource.TREASURE;
            } else {
                return FishingSource.JUNK;
            }
        }
        return null;
    }
}
