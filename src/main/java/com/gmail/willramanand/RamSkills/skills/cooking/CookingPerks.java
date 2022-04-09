package com.gmail.willramanand.RamSkills.skills.cooking;

import com.gmail.willramanand.RamSkills.RamSkills;
import com.gmail.willramanand.RamSkills.player.SkillPlayer;
import com.gmail.willramanand.RamSkills.skills.Skills;
import com.gmail.willramanand.RamSkills.utils.Txt;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPotionEffectEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class CookingPerks implements Listener {

    private final RamSkills plugin;

    public CookingPerks(RamSkills plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void tastierFood(FoodLevelChangeEvent event) {
        if (event.getItem() == null) return;
        Player player = (Player) event.getEntity();
        SkillPlayer skillPlayer = plugin.getPlayerManager().getPlayerData(player);

        double foodIncrease = 1 + ((skillPlayer.getSkillLevel(Skills.COOKING) * 2.0)/ 100.0);
        event.setFoodLevel(Math.min((int) (event.getFoodLevel() * foodIncrease), 20));
    }

    @EventHandler
    public void ironStomach(EntityPotionEffectEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;
        if (event.getCause() != EntityPotionEffectEvent.Cause.FOOD) return;
        if (event.getNewEffect() == null) return;
        if (event.getNewEffect().getType() != PotionEffectType.HUNGER) return;

        SkillPlayer skillPlayer = plugin.getPlayerManager().getPlayerData(player);

        if (skillPlayer.getSkillLevel(Skills.COOKING) < 25) return;
        player.sendMessage(Txt.parse("&dIron Stomach &eprotects you!"));

        new BukkitRunnable() {
            @Override
            public void run() {
                player.removePotionEffect(PotionEffectType.HUNGER);
            }
        }.runTaskLater(plugin, 1);
    }
}
