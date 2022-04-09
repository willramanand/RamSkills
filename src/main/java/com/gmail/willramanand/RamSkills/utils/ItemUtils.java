package com.gmail.willramanand.RamSkills.utils;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public class ItemUtils {

    public static boolean isSword(ItemStack item) {
        return item.getType().name().contains("SWORD");
    }

    public static boolean isShovel(ItemStack item) {
        return item.getType().name().contains("SHOVEL");
    }

    public static boolean isAxe(ItemStack item) {
        return item.getType().name().contains("_AXE");
    }
    public static boolean isPick(ItemStack item) {
        return item.getType().name().contains("PICKAXE");
    }

    public static boolean isHoe(ItemStack item) {
        return item.getType().name().contains("HOE");
    }

    public static boolean isBow(ItemStack item) {
        return item.getType().name().contains("BOW") && !(item.getType().name().contains("BOWL"));
    }

    public static boolean hasSilkTouch(ItemStack item) {
        return item.getItemMeta() != null && item.getItemMeta().hasEnchant(Enchantment.SILK_TOUCH);
    }
}
