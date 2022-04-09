package com.gmail.willramanand.RamSkills.skills.agility;

import com.gmail.willramanand.RamSkills.RamSkills;
import com.gmail.willramanand.RamSkills.leveler.SkillLeveler;
import com.gmail.willramanand.RamSkills.skills.Skills;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.metadata.FixedMetadataValue;

public class AgilityLeveler extends SkillLeveler implements Listener {

    public AgilityLeveler(RamSkills plugin) {
        super(plugin, Skills.AGILITY);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onFall(EntityDamageEvent event) {
        if (event.getCause() != EntityDamageEvent.DamageCause.FALL) return;
        if (!(event.getEntity() instanceof Player player)) return;

        if (blockXpGainPlayer(player)) return;
        if (event.getFinalDamage() > player.getHealth()) return;
        plugin.getLeveler().addXp(player, Skills.AGILITY, event.getOriginalDamage(EntityDamageEvent.DamageModifier.BASE) * getXp(player, AgilitySource.FALL_DAMAGE));
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onMove(PlayerMoveEvent event) {
        Location from = event.getFrom();
        Location to = event.getTo();
        if (to.getBlockX() == from.getBlockX() && to.getBlockY() == from.getBlockY() && to.getBlockZ() == from.getBlockZ()) return;
        Player player = event.getPlayer();
        if (!(player.hasMetadata("skills_moving"))) {
            player.setMetadata("skills_moving", new FixedMetadataValue(plugin, 1));
        }

        player.setMetadata("skills_moving", new FixedMetadataValue(plugin, player.getMetadata("skills_moving").get(0).asInt() + 1));

        if (player.getMetadata("skills_moving").get(0).asInt() >= 100) {
            if (blockXpGainPlayer(player)) return;
            plugin.getLeveler().addXp(player, Skills.AGILITY, 25 * getXp(player, AgilitySource.MOVE_PER_BLOCK));
            player.removeMetadata("skills_moving", plugin);
        }
    }
}
