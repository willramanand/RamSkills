package com.gmail.willramanand.RamSkills.listeners;

import com.gmail.willramanand.RamSkills.RamSkills;
import com.gmail.willramanand.RamSkills.events.SkillLevelUpEvent;
import com.gmail.willramanand.RamSkills.player.SkillPlayer;
import com.gmail.willramanand.RamSkills.stats.Stat;
import com.gmail.willramanand.RamSkills.utils.BlockUtils;
import org.bukkit.GameMode;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.metadata.FixedMetadataValue;

public class PlayerListener implements Listener {

    private final RamSkills plugin;

    public PlayerListener(RamSkills plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void join(PlayerJoinEvent event) {
        plugin.getPlayerConfig().load(event.getPlayer());
        plugin.getStatManager().allocateStats(event.getPlayer());
        applyModifiers(event.getPlayer(), event);
        event.getPlayer().setMetadata("readied", new FixedMetadataValue(plugin, false));
    }

    @EventHandler
    public void onLevel(SkillLevelUpEvent event) {
        plugin.getStatManager().allocateStats(event.getPlayer());
        applyModifiers(event.getPlayer(), event);
    }

    @EventHandler
    public void worldChange(PlayerChangedWorldEvent event) {
        if (event.getPlayer().getGameMode() != GameMode.SURVIVAL) return;
        applyModifiers(event.getPlayer(), event);
        event.getPlayer().setMetadata("readied", new FixedMetadataValue(plugin, false));
    }

    @EventHandler
    public void regenEvent(EntityRegainHealthEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;

        SkillPlayer skillPlayer = plugin.getPlayerManager().getPlayerData(player);
        event.setAmount(event.getAmount() + (skillPlayer.getStatPoint(Stat.HEALTH) / 100));
    }

    @EventHandler
    public void leave(PlayerQuitEvent event) {
        plugin.getPlayerConfig().save(event.getPlayer(), false);
        plugin.getActionBar().resetActionBar(event.getPlayer());
        event.getPlayer().setMetadata("readied", new FixedMetadataValue(plugin, false));
    }

    @EventHandler
    public void playerPlace(BlockPlaceEvent event) {
        BlockUtils.setPlayerPlace(event.getBlock());
    }

    public void applyModifiers(Player player, Event event) {
        SkillPlayer skillPlayer = plugin.getPlayerManager().getPlayerData(player);

        double healthAdd = skillPlayer.getStatPoint(Stat.HEALTH);
        double swingAdd = skillPlayer.getStatPoint(Stat.ATTACK_SPEED);
        double speedAdd = 1 + skillPlayer.getStatPoint(Stat.SPEED);

        float baseMove = 0.2f;
        player.setWalkSpeed((float) (baseMove * speedAdd));
        int baseHealth = 20;
        player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(baseHealth + healthAdd);
        int baseSwing = 4;
        player.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(baseSwing + swingAdd);
        if (event instanceof PlayerJoinEvent)
            player.setHealth(player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue());
        player.setHealthScale(20.0);
    }
}
