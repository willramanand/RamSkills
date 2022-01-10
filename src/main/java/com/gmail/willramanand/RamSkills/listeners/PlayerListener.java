package com.gmail.willramanand.RamSkills.listeners;

import com.gmail.willramanand.RamSkills.RamSkills;
import com.gmail.willramanand.RamSkills.utils.BlockUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener {

    private final RamSkills plugin;

    public PlayerListener(RamSkills plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void join(PlayerJoinEvent event) {
        plugin.getPlayerConfig().load(event.getPlayer());
    }

    @EventHandler
    public void leave(PlayerQuitEvent event) {
        plugin.getPlayerConfig().save(event.getPlayer(), false);
        plugin.getActionBar().resetActionBar(event.getPlayer());
    }

    @EventHandler
    public void playerPlace(BlockPlaceEvent event) {
        BlockUtils.setPlayerPlace(event.getBlock());
    }
}
