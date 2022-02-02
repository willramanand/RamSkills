package com.gmail.willramanand.RamSkills.listeners;

import com.gmail.willramanand.RamSkills.RamSkills;
import com.gmail.willramanand.RamSkills.ui.SkillsScreen;
import com.gmail.willramanand.RamSkills.ui.StatsScreen;
import com.gmail.willramanand.RamSkills.ui.uiitems.InventoryItem;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.checkerframework.checker.units.qual.N;

public class GUIListener implements Listener {

    private final RamSkills plugin;

    public GUIListener(RamSkills plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onSkillClick(InventoryClickEvent e) {
        if (e.getClickedInventory() == null) return;
        if (e.getClickedInventory().getHolder() instanceof SkillsScreen) {
            Player player = (Player) e.getWhoClicked();
            if (e.getCurrentItem() == null) return;
            if (e.getCurrentItem().getItemMeta().getPersistentDataContainer().has(new NamespacedKey(plugin, "next_page"))) {
                player.closeInventory();
                StatsScreen statsScreen = new StatsScreen(plugin, player);
                player.openInventory(statsScreen.getInventory());
            } else if (e.getCurrentItem().getItemMeta().getPersistentDataContainer().has(new NamespacedKey(plugin, "close_button"))) {
                player.closeInventory();
            }
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onStatClick(InventoryClickEvent e) {
        if (e.getClickedInventory() == null) return;
        if (e.getClickedInventory().getHolder() instanceof StatsScreen) {
            Player player = (Player) e.getWhoClicked();
            if (e.getCurrentItem() == null) return;
            if (e.getCurrentItem().getItemMeta().getPersistentDataContainer().has(new NamespacedKey(plugin, "back_button"))) {
                player.closeInventory();
                SkillsScreen skillsScreen = new SkillsScreen(plugin, player);
                player.openInventory(skillsScreen.getInventory());
            } else if (e.getCurrentItem().getItemMeta().getPersistentDataContainer().has(new NamespacedKey(plugin, "close_button"))) {
                player.closeInventory();
            }
            e.setCancelled(true);
        }
    }
}
