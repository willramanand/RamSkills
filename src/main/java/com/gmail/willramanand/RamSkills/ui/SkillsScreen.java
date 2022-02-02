package com.gmail.willramanand.RamSkills.ui;

import com.gmail.willramanand.RamSkills.RamSkills;
import com.gmail.willramanand.RamSkills.player.SkillPlayer;
import com.gmail.willramanand.RamSkills.skills.Skills;
import com.gmail.willramanand.RamSkills.ui.uiitems.InventoryItem;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class SkillsScreen implements InventoryHolder {

    private final RamSkills plugin;
    private final Player player;
    private final SkillPlayer skillPlayer;
    private final Inventory inventory;

    public SkillsScreen(RamSkills plugin, Player player) {
        this.plugin = plugin;
        this.player = player;
        this.skillPlayer = plugin.getPlayerManager().getPlayerData(player);
        this.inventory = Bukkit.createInventory(this, 54, Component.text(player.getName() + " Skill's").color(TextColor.color(0, 170, 170)));
        init();
    }

    private void init() {
        inventory.setItem(0, InventoryItem.getHead(player));
        inventory.setItem(12, InventoryItem.getSkillItem(Skills.AGILITY, Material.RABBIT_FOOT, player));
        inventory.setItem(13, InventoryItem.getSkillItem(Skills.ALCHEMY, Material.POTION, player));
        inventory.setItem(14, InventoryItem.getSkillItem(Skills.COMBAT, Material.DIAMOND_SWORD, player));
        inventory.setItem(20, InventoryItem.getSkillItem(Skills.COOKING, Material.COOKED_BEEF, player));
        inventory.setItem(21, InventoryItem.getSkillItem(Skills.DEFENSE, Material.SHIELD, player));
        inventory.setItem(22, InventoryItem.getSkillItem(Skills.ENCHANTING, Material.ENCHANTING_TABLE, player));
        inventory.setItem(23, InventoryItem.getSkillItem(Skills.EXCAVATION, Material.IRON_SHOVEL, player));
        inventory.setItem(24, InventoryItem.getSkillItem(Skills.FARMING, Material.STONE_HOE, player));
        inventory.setItem(30, InventoryItem.getSkillItem(Skills.FISHING, Material.FISHING_ROD, player));
        inventory.setItem(31, InventoryItem.getSkillItem(Skills.MINING, Material.GOLDEN_PICKAXE, player));
        inventory.setItem(32, InventoryItem.getSkillItem(Skills.SORCERY, Material.BLAZE_ROD, player));
        inventory.setItem(40, InventoryItem.getSkillItem(Skills.WOODCUTTING, Material.NETHERITE_AXE, player));
        inventory.setItem(45, InventoryItem.getClose());
        inventory.setItem(53, InventoryItem.getStatsPage());

        for (int i = 0; i < inventory.getContents().length; i++) {
            if (inventory.getItem(i) == null) inventory.setItem(i, InventoryItem.getBlank());
        }
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }
}
