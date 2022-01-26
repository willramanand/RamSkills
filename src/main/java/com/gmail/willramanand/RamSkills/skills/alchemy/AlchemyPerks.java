package com.gmail.willramanand.RamSkills.skills.alchemy;

import com.gmail.willramanand.RamSkills.RamSkills;
import com.gmail.willramanand.RamSkills.player.SkillPlayer;
import com.gmail.willramanand.RamSkills.skills.Skills;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPotionEffectEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;

public class AlchemyPerks implements Listener {

    private final RamSkills plugin;

    public AlchemyPerks(RamSkills plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void increaseDuration(EntityPotionEffectEvent event) {
        if (event.getCause() != EntityPotionEffectEvent.Cause.POTION_DRINK && event.getCause() != EntityPotionEffectEvent.Cause.POTION_SPLASH) return;
        if (!(event.getEntity() instanceof Player)) return;
        if (event.getNewEffect() == null) return;
        Player player = (Player) event.getEntity();
        SkillPlayer skillPlayer = plugin.getPlayerManager().getPlayerData(player);

        double increase = 1 + ((skillPlayer.getSkillLevel(Skills.ALCHEMY) * 2.0 )/ 100.0);

        PotionEffect potionEffect = event.getNewEffect();
        int increasedDuration = (int) (potionEffect.getDuration() * increase);

        PotionEffect newPotionEffect = new PotionEffect(potionEffect.getType(), increasedDuration, potionEffect.getAmplifier());
        new BukkitRunnable() {
            @Override
            public void run() {
                player.addPotionEffect(newPotionEffect);
            }
        }.runTaskLater(plugin, 2);
    }
}
