package com.gmail.willramanand.RamSkills.listeners;

import com.gmail.willramanand.RamSkills.RamSkills;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener {

    private final RamSkills plugin;

    public PlayerListener(RamSkills plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void join(PlayerJoinEvent event) {
        plugin.getPlayerConfig().setup(event.getPlayer());
        plugin.getPlayerConfig().load(event.getPlayer());
    }

    @EventHandler
    public void leave(PlayerQuitEvent event) {
        plugin.getPlayerConfig().save(event.getPlayer(), false);
    }
}
