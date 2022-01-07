package com.gmail.willramanand.RamSkills.skills.fighting;

import com.gmail.willramanand.RamSkills.RamSkills;
import com.gmail.willramanand.RamSkills.leveler.SkillLeveler;
import com.gmail.willramanand.RamSkills.skills.Skills;
import org.bukkit.entity.Boss;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;

public class FightingLeveler extends SkillLeveler implements Listener {

    public FightingLeveler(RamSkills plugin) {
        super(plugin, Skills.FIGHTING);
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
            plugin.getLeveler().addXp(p, Skills.FIGHTING, getXp(p, FightingSource.valueOf(type.toString())));
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        // Damage based listener
        if (event.isCancelled()) return;
        if (!(event.getDamager() instanceof Player)) return;
        Player player = (Player) event.getDamager();
        if (event.getCause() != EntityDamageEvent.DamageCause.ENTITY_ATTACK && event.getCause() != EntityDamageEvent.DamageCause.ENTITY_SWEEP_ATTACK)
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
            plugin.getLeveler().addXp(player, Skills.FIGHTING, damage * getXp(player, FightingSource.valueOf(type.toString())));

        }
    }
}

