package com.gmail.willramanand.RamSkills.listeners;

import com.gmail.willramanand.RamSkills.RamSkills;
import com.gmail.willramanand.RamSkills.player.SkillPlayer;
import com.gmail.willramanand.RamSkills.stats.Stat;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class DamageListener implements Listener {

    private final RamSkills plugin;

    public DamageListener(RamSkills plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void damage(EntityDamageByEntityEvent event) {
        checkToughness(event);
        modifyPlayerDamage(event);
    }

    public void checkToughness(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player)) return;
        Player player = (Player) event.getEntity();
        SkillPlayer skillPlayer = plugin.getPlayerManager().getPlayerData(player);

        double damageReduction = 1 - (skillPlayer.getStatPoint(Stat.TOUGHNESS) / (skillPlayer.getStatPoint(Stat.TOUGHNESS) + 100));
        event.setDamage(damageReduction * event.getFinalDamage());
    }


    public void modifyPlayerDamage(EntityDamageByEntityEvent event) {
        double damage = event.getFinalDamage();
        boolean isValid = false;

        Player player = null;
        if (event.getDamager() instanceof Player) {
            player = (Player) event.getDamager();

            ItemStack playerItem = player.getInventory().getItemInMainHand();
            if (!(EnchantmentTarget.TOOL.includes(playerItem)) && !(EnchantmentTarget.WEAPON.includes(playerItem))) return;
            isValid = true;

        } else if (event.getDamager() instanceof Projectile projectile) {
            if (projectile.getShooter() instanceof Player) {
                player = (Player) projectile.getShooter();

                ItemStack playerItem = player.getInventory().getItemInMainHand();
                if (!(EnchantmentTarget.BOW.includes(playerItem))) return;
                isValid = true;
            }
        }

        if (!isValid) return;
        double newDamage = calculateDamage(player, damage);
        event.setDamage(newDamage);
    }

    public double calculateDamage(Player player, double originalDamage) {
        SkillPlayer skillPlayer = plugin.getPlayerManager().getPlayerData(player);

        double strengthCalc = 1 + (skillPlayer.getStatPoint(Stat.STRENGTH) / 100);
        double critDamage = 1.0;

        if (isCrit(skillPlayer)) {
            critDamage = 1 + (skillPlayer.getStatPoint(Stat.CRIT_DAMAGE) / 100);
        }

        double newDamage = originalDamage * strengthCalc * critDamage;
        return newDamage;
    }

    public boolean isCrit(SkillPlayer skillPlayer) {
        double randDouble = new Random().nextDouble(1.01);

        double critChance = skillPlayer.getStatPoint(Stat.CRIT_CHANCE) / 100;
        if (critChance >= randDouble) {
            plugin.getActionBar().sendAbilityActionBar(skillPlayer.getPlayer(), "Critical Strike!");
            return true;
        }
        return false;
    }
}
