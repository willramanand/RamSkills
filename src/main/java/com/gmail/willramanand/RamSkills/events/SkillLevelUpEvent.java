package com.gmail.willramanand.RamSkills.events;

import com.gmail.willramanand.RamSkills.skills.Skill;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class SkillLevelUpEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    private final Player player;
    private final Skill skill;
    private final int skillLvl;


    public SkillLevelUpEvent(Player player, Skill skill, int skillLvl) {
        this.player = player;
        this.skill = skill;
        this.skillLvl = skillLvl;
    }

    public Player getPlayer() {
        return player;
    }

    public Skill getSkill() {
        return skill;
    }

    public int getSkillLvl() {
        return skillLvl;
    }


    @Override
    public @NotNull HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
