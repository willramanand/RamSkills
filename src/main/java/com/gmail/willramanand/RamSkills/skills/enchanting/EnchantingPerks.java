package com.gmail.willramanand.RamSkills.skills.enchanting;

import com.destroystokyo.paper.event.player.PlayerPickupExperienceEvent;
import com.gmail.willramanand.RamSkills.RamSkills;
import com.gmail.willramanand.RamSkills.player.SkillPlayer;
import com.gmail.willramanand.RamSkills.skills.Skills;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class EnchantingPerks implements Listener {

    private final RamSkills plugin;

    public EnchantingPerks(RamSkills plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void experienceOrbPickup(PlayerPickupExperienceEvent event) {
        Player player = event.getPlayer();
        SkillPlayer skillPlayer = plugin.getPlayerManager().getPlayerData(player);

        double xpIncrease = 1 + ((skillPlayer.getSkillLevel(Skills.ENCHANTING) * 2.0) / 100.0);
        ExperienceOrb orb = event.getExperienceOrb();
        player.sendMessage("" + orb.getExperience());

        int newExperience = (int) (orb.getExperience() * xpIncrease);
        orb.setExperience(newExperience);
        player.sendMessage("" + orb.getExperience());
    }
}
