package com.gmail.willramanand.RamSkills.ui;

import com.gmail.willramanand.RamSkills.RamSkills;
import com.gmail.willramanand.RamSkills.player.SkillPlayer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

public class StatsInventory {

    private final RamSkills plugin;
    private final Player player;
    private final SkillPlayer skillPlayer;
    private final Inventory inventory;

    public StatsInventory(RamSkills plugin, Player player) {
        this.plugin = plugin;
        this.player = player;
        this.skillPlayer = plugin.getPlayerManager().getPlayerData(player);
        this.inventory = Bukkit.createInventory(player, InventoryType.CHEST, Component.text(player.getName() + " Stat's").color(TextColor.color(0, 170, 170)));
    }
}
