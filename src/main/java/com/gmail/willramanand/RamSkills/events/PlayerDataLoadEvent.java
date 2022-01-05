package com.gmail.willramanand.RamSkills.events;

import com.gmail.willramanand.RamSkills.player.SkillPlayer;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class PlayerDataLoadEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    private final SkillPlayer skillPlayer;

    public PlayerDataLoadEvent(SkillPlayer skillPlayer) {
        this.skillPlayer = skillPlayer;
    }

    public SkillPlayer getSkillPlayer() {
        return skillPlayer;
    }

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
