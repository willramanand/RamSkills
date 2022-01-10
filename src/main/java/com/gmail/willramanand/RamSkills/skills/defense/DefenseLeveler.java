package com.gmail.willramanand.RamSkills.skills.defense;

import com.gmail.willramanand.RamSkills.RamSkills;
import com.gmail.willramanand.RamSkills.leveler.SkillLeveler;
import com.gmail.willramanand.RamSkills.skills.Skills;
import com.gmail.willramanand.RamSkills.source.Source;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class DefenseLeveler extends SkillLeveler implements Listener {

    public DefenseLeveler(RamSkills plugin) {
        super(plugin, Skills.DEFENSE);
    }

    @EventHandler
    public void playerDamaged(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player)) return;
        if (event.getEntity() == event.getDamager()) return;

        Source source;
        if (event.getDamager() instanceof Player) {
            source = DefenseSource.PLAYER_DAMAGE;
        } else {
            source = DefenseSource.MOB_DAMAGE;
        }
        plugin.getLeveler().addXp((Player) event.getEntity(), Skills.DEFENSE, event.getDamage() * getXp((Player) event.getEntity(), source));
    }
}
