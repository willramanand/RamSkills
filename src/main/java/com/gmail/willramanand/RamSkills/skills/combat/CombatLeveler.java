package com.gmail.willramanand.RamSkills.skills.combat;

import com.gmail.willramanand.RamSkills.RamSkills;
import com.gmail.willramanand.RamSkills.leveler.SkillLeveler;
import com.gmail.willramanand.RamSkills.skills.Skills;
import com.gmail.willramanand.RamSkills.utils.ItemUtils;
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
        if (!(e.getLastDamageCause() instanceof EntityDamageByEntityEvent)) return;

        EntityDamageByEntityEvent ee = (EntityDamageByEntityEvent) e.getLastDamageCause();
        if (ee.getDamager() instanceof Player) {
            EntityType type = e.getType();
            Player p = (Player) ee.getDamager();
            if (blockXpGainPlayer(p)) return;
            if (e.equals(p)) return;
            plugin.getLeveler().addXp(p, Skills.COMBAT, getXp(p, CombatSource.valueOf(type.toString())));
        } else if (ee.getDamager() instanceof Projectile) {
            EntityType type = e.getType();
            Projectile projectile = (Projectile) ee.getDamager();
            Player p = (Player) projectile.getShooter();
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
        } if (event.getDamager() instanceof Projectile
                && ((Projectile) event.getDamager()).getShooter() instanceof Player) {
            Projectile projectile = (Projectile) event.getDamager();
            player = (Player) projectile.getShooter();
        }

        if (player == null) return;

        if (event.getCause() != EntityDamageEvent.DamageCause.ENTITY_ATTACK && event.getCause() != EntityDamageEvent.DamageCause.ENTITY_SWEEP_ATTACK
                && event.getCause() != EntityDamageEvent.DamageCause.PROJECTILE)
            return;
        if (event.getEntity() instanceof LivingEntity) {
            LivingEntity entity = (LivingEntity) event.getEntity();
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

