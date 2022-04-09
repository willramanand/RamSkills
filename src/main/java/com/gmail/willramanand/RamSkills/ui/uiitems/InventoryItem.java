package com.gmail.willramanand.RamSkills.ui.uiitems;

import com.gmail.willramanand.RamSkills.RamSkills;
import com.gmail.willramanand.RamSkills.mana.Ability;
import com.gmail.willramanand.RamSkills.player.SkillPlayer;
import com.gmail.willramanand.RamSkills.skills.Skill;
import com.gmail.willramanand.RamSkills.stats.Stat;
import com.gmail.willramanand.RamSkills.utils.Txt;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.OfflinePlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public class InventoryItem {

    public static ItemStack getStatItem(Stat stat, Material material, Player player) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(Component.text(Txt.parse(stat.getPrefix() + stat.getDisplayName() + " " + stat.getSymbol())));
        List<Component> lore = new ArrayList<>();
        lore.add(Component.empty());
        lore.add(Component.text(Txt.parse("&r"+ stat.getPrefix() + RamSkills.getInstance().getStatRegistry().print(stat, player))));
        meta.lore(lore);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack getSkillItem(Skill skill, Material material, Player player) {
        SkillPlayer skillPlayer = RamSkills.getInstance().getPlayerManager().getPlayerData(player);
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();

        // Set display name
        meta.displayName(Component.text(Txt.parse("&r&b" + skill.getDisplayName())));

        // Begin adding lore
        List<Component> lore = new ArrayList<>();
        lore.add(Component.empty());

        // Progress Section
        int lvl = skillPlayer.getSkillLevel(skill);
        lore.add(Component.text(Txt.parse("&r&6LVL: &d" + lvl)));
        if (lvl < RamSkills.getInstance().getLeveler().getXpRequirements().getMaxLevel(skill)) {
            lore.add(Component.text(Txt.parse("&r&6["
                    + getProgressBar(skillPlayer.getSkillXp(skill).intValue(), RamSkills.getInstance().getLeveler().getXpRequirements().getXpRequired(skill, lvl + 1), 20, '=')
                    + "&6]")));
        } else {
            lore.add(Component.text(Txt.parse("&r&6["
                    + getProgressBar(1, 1, 20, '=')
                    + "&6]")));

            // Enchant item to indicate max level
            meta.addEnchant(Enchantment.MENDING, 1, true);
        }
        lore.add(Component.empty());

        // Description Section
        lore.add(Component.text(Txt.parse("&r&e" + skill.getDescription())));
        lore.add(Component.empty());

        // Stats section
        lore.add(Component.text(Txt.parse("&r&6Stats:")));
        for (Stat stat : skill.getStats()) {
            lore.add(Component.text(Txt.parse(stat.getPrefix() + stat.getDisplayName() + " " + stat.getSymbol())));
        }
        lore.add(Component.empty());

        // Ability section
        if (skill.getAbility() != null) {
            lore.add(Component.text(Txt.parse("&r&6Abilities:")));
            for (Ability ability : skill.getAbility()) {
                if (lvl < ability.getUnlock()) {
                    lore.add(Component.text(Txt.parse("&r&3" + ability.getDisplayName() + "&8: &cUNLOCKS AT LVL " + ability.getUnlock())));
                } else if (lvl < ability.getUpgrade()) {
                    lore.add(Component.text(Txt.parse("&r&3" + ability.getDisplayName() + "&8: &aUNLOCKED &8| &dUPGRADES AT LVL " + ability.getUpgrade())));
                } else {
                    lore.add(Component.text(Txt.parse("&r&3" + ability.getDisplayName() + "&8: &dUPGRADED")));
                }
            }
        }

        // Perks Section
        if (skill.getPerks() != null) {
            lore.add(Component.text(Txt.parse("&r&6Perks:")));
            for (String string : skill.getPerks()) {
                lore.add(Component.text(Txt.parse("&r" + string)));
            }
        }
        meta.lore(lore);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_POTION_EFFECTS, ItemFlag.HIDE_ENCHANTS);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack getHead(Player player) {
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(player.getUniqueId());
        ItemStack item = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta skull = (SkullMeta) item.getItemMeta();
        skull.displayName(Component.text(player.getName()).decoration(TextDecoration.ITALIC, false));
        skull.setOwningPlayer(offlinePlayer);
        item.setItemMeta(skull);
        return item;
    }

    public static ItemStack getStatsPage() {
        ItemStack item = new ItemStack(Material.ENCHANTED_BOOK);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(Component.text(Txt.parse("&aView Stats")).decoration(TextDecoration.ITALIC, false));
        meta.getPersistentDataContainer().set(new NamespacedKey(RamSkills.getInstance(), "next_page"), PersistentDataType.INTEGER, 0);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack getBack() {
        ItemStack item = new ItemStack(Material.RED_STAINED_GLASS_PANE);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(Component.text(Txt.parse("&cBack")).decoration(TextDecoration.ITALIC, false));
        meta.getPersistentDataContainer().set(new NamespacedKey(RamSkills.getInstance(), "back_button"), PersistentDataType.INTEGER, 0);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack getClose() {
        ItemStack item = new ItemStack(Material.BARRIER);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(Component.text(Txt.parse("&4Close")).decoration(TextDecoration.ITALIC, false));
        meta.getPersistentDataContainer().set(new NamespacedKey(RamSkills.getInstance(), "close_button"), PersistentDataType.INTEGER, 0);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack getBlank() {
        ItemStack item = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(Component.empty());
        meta.getPersistentDataContainer().set(new NamespacedKey(RamSkills.getInstance(), "empty"), PersistentDataType.INTEGER, 0);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        item.setItemMeta(meta);
        return item;
    }

    public static String getProgressBar(int current, int max, int totalBars, char symbol) {
        float percent = (float) current / max;
        int progressBars = (int) (totalBars * percent);
        String completedBars = Txt.parse("&a");

        for (int i = 0; i < progressBars; i++) {
            completedBars = completedBars.concat(String.valueOf(symbol));
        }

        String incompletedBars = Txt.parse("&c");
        for (int i = 0; i < Math.max(totalBars - progressBars, 0); i++) {
            incompletedBars = incompletedBars.concat(String.valueOf(symbol));
        }

        return completedBars + incompletedBars;
    }
}
