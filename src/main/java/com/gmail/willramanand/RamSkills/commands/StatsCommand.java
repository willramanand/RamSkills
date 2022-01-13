package com.gmail.willramanand.RamSkills.commands;

import com.gmail.willramanand.RamSkills.RamSkills;
import com.gmail.willramanand.RamSkills.stats.Stat;
import com.gmail.willramanand.RamSkills.utils.ColorUtils;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class StatsCommand extends SubCommand {

    private final RamSkills plugin;

    public StatsCommand(RamSkills plugin) {
        this.plugin = plugin;
    }


    @Override
    public void onCommand(Player player, String[] args) {
        player.sendMessage(ColorUtils.colorMessage("&6_____________ [ &bStats &6] _____________"));
        for (Stat stat : Stat.values()) {
            String point = plugin.getStatRegistry().print(stat, player);
            player.sendMessage(ColorUtils.colorMessage(stat.getPrefix() + stat.getDisplayName() + " " + stat.getSymbol() + ": " + point));
        }
    }

    @Override
    public String name() {
        return Commands.STATS.getName();
    }

    @Override
    public String info() {
        return "The level of the stats of the player earned through leveling skills.";
    }

    @Override
    public List<String> aliases() {
        return new ArrayList<>();
    }

    @Override
    public List<String> getPrimaryArguments() {
        return new ArrayList<>();
    }

    @Override
    public List<String> getSecondaryArguments(String[] args) {
        return new ArrayList<>();
    }
}
