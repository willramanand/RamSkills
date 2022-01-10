package com.gmail.willramanand.RamSkills.skills.enchanting;

import com.gmail.willramanand.RamSkills.RamSkills;
import com.gmail.willramanand.RamSkills.leveler.SkillLeveler;
import com.gmail.willramanand.RamSkills.skills.Skills;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.inventory.ItemStack;

public class EnchantingLeveler extends SkillLeveler implements Listener {

    public EnchantingLeveler(RamSkills plugin) {
        super(plugin, Skills.ENCHANTING);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onEnchant(EnchantItemEvent event) {
        if (event.isCancelled()) return;
        if (event.getEnchanter() == null) return;
        if (event.getItem() == null) return;

        ItemStack enchantedItem = event.getItem();
        Player player = event.getEnchanter();

        if (blockXpGainPlayer(player)) return;

        EnchantingSource source;

        if (EnchantmentTarget.ARMOR.includes(enchantedItem)) {
            source = EnchantingSource.ARMOR_PER_LEVEL;
        } else if (EnchantmentTarget.TOOL.includes(enchantedItem)) {
            source = EnchantingSource.TOOL_PER_LEVEL;
        } else if (EnchantmentTarget.WEAPON.includes(enchantedItem)) {
            source = EnchantingSource.WEAPON_PER_LEVEL;
        } else {
            source = EnchantingSource.BOOK_PER_LEVEL;
        }

        plugin.getLeveler().addXp(player, Skills.ENCHANTING, getXp(player, source));
    }
}
