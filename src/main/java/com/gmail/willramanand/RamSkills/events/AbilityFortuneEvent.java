package com.gmail.willramanand.RamSkills.events;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class AbilityFortuneEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    private Player player;
    private Material type;
    private Location blockLoc;
    private int blocksBroken;
    private boolean isCancelled;

    public AbilityFortuneEvent(Player player, Material type, Location blockLoc, int blocksBroken) {
        this.player = player;
        this.type = type;
        this.blockLoc = blockLoc;
        this.blocksBroken = blocksBroken;
        this.isCancelled = false;
    }


    public Player getPlayer() {
        return player;
    }

    public Material getType() {
        return type;
    }

    public Location getBlockLocation() {
        return blockLoc;
    }

    public int getAmount() {
        return blocksBroken;
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