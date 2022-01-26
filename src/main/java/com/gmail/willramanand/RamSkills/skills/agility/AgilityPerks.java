package com.gmail.willramanand.RamSkills.skills.agility;

import com.gmail.willramanand.RamSkills.RamSkills;
import com.gmail.willramanand.RamSkills.player.SkillPlayer;
import com.gmail.willramanand.RamSkills.skills.Skills;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExhaustionEvent;

public class AgilityPerks implements Listener {

    private final RamSkills plugin;

    public AgilityPerks(RamSkills plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void marathon(EntityExhaustionEvent event) {
        if (!(event.getEntity() instanceof Player)) return;
        if (event.getExhaustionReason() != EntityExhaustionEvent.ExhaustionReason.JUMP
                && event.getExhaustionReason() != EntityExhaustionEvent.ExhaustionReason.JUMP_SPRINT
                && event.getExhaustionReason() != EntityExhaustionEvent.ExhaustionReason.CROUCH
                && event.getExhaustionReason() != EntityExhaustionEvent.ExhaustionReason.WALK_UNDERWATER
                && event.getExhaustionReason() != EntityExhaustionEvent.ExhaustionReason.WALK_ON_WATER
                && event.getExhaustionReason() != EntityExhaustionEvent.ExhaustionReason.WALK) return;

        Player player = (Player) event.getEntity();
        SkillPlayer skillPlayer = plugin.getPlayerManager().getPlayerData(player);

        float exhaustionReduction = 1.0f - ((float) skillPlayer.getSkillLevel(Skills.AGILITY) / 100.0f);
        event.setExhaustion(event.getExhaustion() * exhaustionReduction);
    }
}
