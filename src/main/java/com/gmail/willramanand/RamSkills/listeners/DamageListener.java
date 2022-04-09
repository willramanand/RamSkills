package com.gmail.willramanand.RamSkills.listeners;

import com.gmail.willramanand.RamSkills.RamSkills;
import com.gmail.willramanand.RamSkills.events.CriticalStrikeEvent;
import com.gmail.willramanand.RamSkills.mana.Ability;
import com.gmail.willramanand.RamSkills.player.SkillPlayer;
import com.gmail.willramanand.RamSkills.skills.Skills;
import com.gmail.willramanand.RamSkills.stats.Stat;
import com.gmail.willramanand.RamSkills.utils.ItemUtils;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
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
        if (!(event.getEntity() instanceof Player player)) return;
        SkillPlayer skillPlayer = plugin.getPlayerManager().getPlayerData(player);

        double damageReduction = 1 - (skillPlayer.getStatPoint(Stat.TOUGHNESS) / (skillPlayer.getStatPoint(Stat.TOUGHNESS) + 100));
        event.setDamage(Math.max(event.getFinalDamage() * damageReduction, 1.5));
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

        if (ItemUtils.isSword(player.getInventory().getItemInMainHand())
                && player.getMetadata("readied").get(0).asBoolean())
            lifeStealAbility(player, newDamage);

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
        if (critDamage > 1.0) {
            CriticalStrikeEvent event = new CriticalStrikeEvent(player);
            Bukkit.getPluginManager().callEvent(event);
        }
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

    public void lifeStealAbility(Player player, double damage) {
        SkillPlayer skillPlayer = plugin.getPlayerManager().getPlayerData(player);
        double maxHealth = player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
        double baseSteal = 0.1;
        double manaCost = Ability.SOUL_STEAL.getManaCost();
        double startHealth = player.getHealth();

        if (skillPlayer.getSkillLevel(Skills.COMBAT) < Ability.SOUL_STEAL.getUnlock()) {
            return;
        } else if (skillPlayer.getSkillLevel(Skills.COMBAT) == Ability.SOUL_STEAL.getUpgrade()) {
            baseSteal = 0.25;
            manaCost = 0.3;
        }

        double heal = player.getHealth() + (baseSteal * damage);

        manaCost *= heal;
        if (!(plugin.getManaAbility().checkMana(skillPlayer, manaCost))) {
            plugin.getActionBar().sendAbilityActionBar(player, "&4Not enough mana!");
            return;
        }

        if (heal >= maxHealth) {
            heal = maxHealth;
        }
        player.setHealth(heal);
        EntityRegainHealthEvent event = new EntityRegainHealthEvent(player, player.getHealth() - startHealth, EntityRegainHealthEvent.RegainReason.CUSTOM);
        Bukkit.getPluginManager().callEvent(event);
        plugin.getActionBar().sendAbilityActionBar(player, "Soul Steal Activated!");
    }
}
