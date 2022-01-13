package com.gmail.willramanand.RamSkills.utils;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public class ItemUtils {

    public static boolean isSword(ItemStack item) {
        if (item.getType().name().contains("SWORD")) return true;
        return false;
    }

    public static boolean isShovel(ItemStack item) {
        if (item.getType().name().contains("SHOVEL")) return true;
        return false;
    }

    public static boolean isAxe(ItemStack item) {
        if (item.getType().name().contains("_AXE")) return true;
        return false;
    }
    public static boolean isPick(ItemStack item) {
        if (item.getType().name().contains("PICKAXE")) return true;
        return false;
    }

    public static boolean isHoe(ItemStack item) {
        if (item.getType().name().contains("HOE")) return true;
        return false;
    }

    public static boolean isBow(ItemStack item) {
        if (item.getType().name().contains("BOW") && !(item.getType().name().contains("BOWL"))) return true;
        return false;
    }

    public static boolean hasSilkTouch(ItemStack item) {
        if (item.getItemMeta().hasEnchant(Enchantment.SILK_TOUCH)) return true;
        return false;
    }
}
