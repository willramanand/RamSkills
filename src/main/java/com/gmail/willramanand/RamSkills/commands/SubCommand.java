package com.gmail.willramanand.RamSkills.commands;

import org.bukkit.entity.Player;

import java.util.List;

public abstract class SubCommand {

    public SubCommand() {}
    public abstract void onCommand(Player player, String[] args);

    public abstract String name();

    public abstract String info();

    public abstract List<String> aliases();

    public abstract List<String> getPrimaryArguments();

    public abstract List<String> getSecondaryArguments(String[] args);
}
