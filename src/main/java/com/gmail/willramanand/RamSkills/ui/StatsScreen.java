package com.gmail.willramanand.RamSkills.ui;

import com.gmail.willramanand.RamSkills.RamSkills;
import com.gmail.willramanand.RamSkills.player.SkillPlayer;
import com.gmail.willramanand.RamSkills.stats.Stat;
import com.gmail.willramanand.RamSkills.ui.uiitems.InventoryItem;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class StatsScreen implements InventoryHolder {

    private final RamSkills plugin;
    private final Player player;
    private final SkillPlayer skillPlayer;
    private final Inventory inventory;

    public StatsScreen(RamSkills plugin, Player player) {
        this.plugin = plugin;
        this.player = player;
        this.skillPlayer = plugin.getPlayerManager().getPlayerData(player);
        this.inventory = Bukkit.createInventory(this, InventoryType.CHEST, Component.text(player.getName() + " Stat's").color(TextColor.color(0, 170, 170)));
        init();
    }

    private void init() {
        inventory.setItem(0, InventoryItem.getHead(player));
        inventory.setItem(3, InventoryItem.getStatItem(Stat.HEALTH, Material.GOLDEN_APPLE, player));
        inventory.setItem(4, InventoryItem.getStatItem(Stat.SPEED, Material.LEATHER_BOOTS, player));
        inventory.setItem(5, InventoryItem.getStatItem(Stat.STRENGTH, Material.DIAMOND_AXE, player));
        inventory.setItem(11, InventoryItem.getStatItem(Stat.ATTACK_SPEED, Material.SUGAR, player));
        inventory.setItem(12, InventoryItem.getStatItem(Stat.CRIT_DAMAGE, Material.GOLDEN_SWORD, player));
        inventory.setItem(13, InventoryItem.getStatItem(Stat.CRIT_CHANCE, Material.BOW, player));
        inventory.setItem(14, InventoryItem.getStatItem(Stat.FORTUNE, Material.IRON_PICKAXE, player));
        inventory.setItem(15, InventoryItem.getStatItem(Stat.WISDOM, Material.ENCHANTING_TABLE, player));
        inventory.setItem(22, InventoryItem.getStatItem(Stat.TOUGHNESS, Material.NETHERITE_CHESTPLATE, player));
        inventory.setItem(18, InventoryItem.getClose());
        inventory.setItem(26, InventoryItem.getBack());

        for (int i = 0; i < inventory.getContents().length; i++) {
            if (inventory.getItem(i) == null) inventory.setItem(i, InventoryItem.getBlank());
        }
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }
}
