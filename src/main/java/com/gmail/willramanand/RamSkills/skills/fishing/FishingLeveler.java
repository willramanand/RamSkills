package com.gmail.willramanand.RamSkills.skills.fishing;

import com.gmail.willramanand.RamSkills.RamSkills;
import com.gmail.willramanand.RamSkills.leveler.SkillLeveler;
import com.gmail.willramanand.RamSkills.skills.Skills;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;

public class FishingLeveler extends SkillLeveler implements Listener {

    public FishingLeveler(RamSkills plugin) {
        super(plugin, Skills.FISHING);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onFish(PlayerFishEvent event) {
        if (!(event.getState().equals(PlayerFishEvent.State.CAUGHT_FISH))) return;
        if (!(event.getCaught() instanceof Item)) return;

        Item item = (Item) event.getCaught();
        Player player = event.getPlayer();

        if (blockXpGainPlayer(player)) return;

        plugin.getLeveler().addXp(player, Skills.FISHING, getXp(player, FishingSource.valueOf(item.getItemStack())));
    }
}
