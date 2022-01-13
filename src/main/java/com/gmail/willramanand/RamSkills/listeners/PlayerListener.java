package com.gmail.willramanand.RamSkills.listeners;

import com.gmail.willramanand.RamSkills.RamSkills;
import com.gmail.willramanand.RamSkills.events.SkillLevelUpEvent;
import com.gmail.willramanand.RamSkills.player.SkillPlayer;
import com.gmail.willramanand.RamSkills.stats.Stat;
import com.gmail.willramanand.RamSkills.utils.BlockUtils;
import org.bukkit.GameMode;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

public class PlayerListener implements Listener {

    private final RamSkills plugin;
    private AttributeModifier healthModifier;
    private AttributeModifier speedModifier;
    private AttributeModifier swingModifier;

    public PlayerListener(RamSkills plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void join(PlayerJoinEvent event) {
        plugin.getPlayerConfig().load(event.getPlayer());
        plugin.getStatManager().allocateStats(event.getPlayer());
        applyModifiers(event.getPlayer(), event);
    }

    @EventHandler
    public void onLevel(SkillLevelUpEvent event) {
        plugin.getStatManager().allocateStats(event.getPlayer());
        removeModifiers(event.getPlayer());
        applyModifiers(event.getPlayer(), event);
    }

    @EventHandler
    public void worldChange(PlayerChangedWorldEvent event) {
        if (event.getPlayer().getGameMode() != GameMode.SURVIVAL) return;
        removeModifiers(event.getPlayer());
        applyModifiers(event.getPlayer(), event);
    }

    @EventHandler
    public void regenEvent(EntityRegainHealthEvent event) {
        if (!(event.getEntity() instanceof Player)) return;

        Player player = (Player) event.getEntity();
        SkillPlayer skillPlayer = plugin.getPlayerManager().getPlayerData(player);
        event.setAmount(event.getAmount() + (skillPlayer.getStatPoint(Stat.HEALTH) / 100));
    }

    @EventHandler
    public void leave(PlayerQuitEvent event) {
        plugin.getPlayerConfig().save(event.getPlayer(), false);
        plugin.getActionBar().resetActionBar(event.getPlayer());
        removeModifiers(event.getPlayer());
    }

    @EventHandler
    public void playerPlace(BlockPlaceEvent event) {
        BlockUtils.setPlayerPlace(event.getBlock());
    }

    public void applyModifiers(Player player, Event event) {
        SkillPlayer skillPlayer = plugin.getPlayerManager().getPlayerData(player);
        healthModifier = new AttributeModifier(Stat.HEALTH.getModifierName(), skillPlayer.getStatPoint(Stat.HEALTH), AttributeModifier.Operation.ADD_NUMBER);
        swingModifier = new AttributeModifier(Stat.ATTACK_SPEED.getModifierName(), skillPlayer.getStatPoint(Stat.ATTACK_SPEED), AttributeModifier.Operation.ADD_NUMBER);
        speedModifier = new AttributeModifier(Stat.SPEED.getModifierName(), skillPlayer.getStatPoint(Stat.SPEED), AttributeModifier.Operation.MULTIPLY_SCALAR_1);

        player.getAttribute(Attribute.GENERIC_MAX_HEALTH).addModifier(healthModifier);
        if (!(event instanceof SkillLevelUpEvent))
            player.setHealth(player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
        player.setHealthScale(40.0);

        player.getAttribute(Attribute.GENERIC_ATTACK_SPEED).addModifier(swingModifier);

        player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).addModifier(speedModifier);
    }

    public void removeModifiers(Player player) {
        player.getAttribute(Attribute.GENERIC_MAX_HEALTH).removeModifier(healthModifier);
        player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).removeModifier(speedModifier);
        player.getAttribute(Attribute.GENERIC_ATTACK_SPEED).removeModifier(swingModifier);
        player.setHealthScale(20.0);
    }
}
