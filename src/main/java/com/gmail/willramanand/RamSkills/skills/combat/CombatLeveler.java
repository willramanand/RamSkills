package com.gmail.willramanand.RamSkills.skills.combat;

import com.gmail.willramanand.RamSkills.RamSkills;
import com.gmail.willramanand.RamSkills.leveler.SkillLeveler;
import com.gmail.willramanand.RamSkills.skills.Skills;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;

public class CombatLeveler extends SkillLeveler implements Listener {

    public CombatLeveler(RamSkills plugin) {
        super(plugin, Skills.COMBAT);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntityDeath(EntityDeathEvent event) {
        LivingEntity e = event.getEntity();

        if (e.getKiller() == null) return;
        if (!(e.getLastDamageCause() instanceof EntityDamageByEntityEvent ee)) return;

        if (ee.getDamager() instanceof Player p) {
            EntityType type = e.getType();
            if (blockXpGainPlayer(p)) return;
            if (e.equals(p)) return;
            plugin.getLeveler().addXp(p, Skills.COMBAT, getXp(p, CombatSource.valueOf(type.toString())));
        } else if (ee.getDamager() instanceof Projectile projectile) {
            EntityType type = e.getType();
            if (!(projectile.getShooter() instanceof Player p)) return;
            if (blockXpGainPlayer(p)) return;
            if (e.equals(p)) return;
            plugin.getLeveler().addXp(p, Skills.COMBAT, getXp(p, CombatSource.valueOf(type.toString())));
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        // Damage based listener
        if (event.isCancelled()) return;
        Player player = null;
        if (event.getDamager() instanceof Player) {
            player = (Player) event.getDamager();
        } if (event.getDamager() instanceof Projectile projectile
                && ((Projectile) event.getDamager()).getShooter() instanceof Player) {
            player = (Player) projectile.getShooter();
        }

        if (player == null) return;

        if (event.getCause() != EntityDamageEvent.DamageCause.ENTITY_ATTACK && event.getCause() != EntityDamageEvent.DamageCause.ENTITY_SWEEP_ATTACK
                && event.getCause() != EntityDamageEvent.DamageCause.PROJECTILE)
            return;
        if (event.getEntity() instanceof LivingEntity entity) {
            EntityType type = entity.getType();
            if (blockXpGainPlayer(player)) return;
            if (entity.equals(player)) return;
            double health = entity.getHealth();
            double damage = Math.min(1, (event.getFinalDamage() / health));
            if (entity instanceof Boss) {
                damage = Math.min(0.01, (event.getFinalDamage() / health) / 100);
            }
            if (event.getFinalDamage() > health) return;
            plugin.getLeveler().addXp(player, Skills.COMBAT, damage * getXp(player, CombatSource.valueOf(type.toString())));

        }
    }
}

