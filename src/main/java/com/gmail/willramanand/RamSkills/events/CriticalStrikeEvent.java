package com.gmail.willramanand.RamSkills.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class CriticalStrikeEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    private final Player player;
    private boolean isCancelled;

    public CriticalStrikeEvent(Player player) {
        this.player = player;
        this.isCancelled = false;
    }


    public Player getPlayer() {
        return player;
    }

    public boolean isCancelled() {
        return isCancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.isCancelled = cancelled;
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