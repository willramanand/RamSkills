package com.gmail.willramanand.RamSkills.mana;

import com.gmail.willramanand.RamSkills.RamSkills;
import com.gmail.willramanand.RamSkills.events.ManaRegenerateEvent;
import com.gmail.willramanand.RamSkills.player.SkillPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class ManaManager {

    private final RamSkills plugin;

    public ManaManager(RamSkills plugin) {
        this.plugin = plugin;
    }

    public void regenMana() {
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    SkillPlayer skillPlayer = plugin.getPlayerManager().getPlayerData(player);
                    if (skillPlayer == null) return;
                    double originalMana = skillPlayer.getMana();
                    double maxMana = skillPlayer.getMaxMana();
                    if (originalMana < maxMana) {
                            double regen = skillPlayer.getManaRegen();
                            ManaRegenerateEvent event = new ManaRegenerateEvent(player, regen);
                            Bukkit.getPluginManager().callEvent(event);
                            if (!event.isCancelled()) {
                                skillPlayer.updateMana(regen);
                            }
                    }

                }
            }
        }.runTaskTimer(plugin, 0L, 20L);
    }
}
