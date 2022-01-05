package com.gmail.willramanand.RamSkills.commands;

import com.gmail.willramanand.RamSkills.RamSkills;
import com.gmail.willramanand.RamSkills.skills.Skills;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class BossBarCommand extends SubCommand {

    private final RamSkills plugin;

    public BossBarCommand(RamSkills plugin) {
        this.plugin = plugin;
    }

    @Override
    public void onCommand(Player player, String[] args) {
        if (args[1].equalsIgnoreCase("none")) {
            plugin.getBossBar().sendBossBar(player, Skills.AGILITY, 0.0, 100.0, 25, false);
        } else if (args[1].equalsIgnoreCase("half")) {
            plugin.getBossBar().sendBossBar(player, Skills.AGILITY, 50.0, 100.0, 25, false);
        } else if (args[1].equalsIgnoreCase("full")) {
            plugin.getBossBar().sendBossBar(player, Skills.AGILITY, 100.0, 100.0, 25, true);
        }
    }

    @Override
    public String name() {
        return Commands.BOSSBAR.getName();
    }

    @Override
    public String info() {
        return "Test boss bar sending.";
    }

    @Override
    public List<String> aliases() {
        return new ArrayList<>();
    }

    @Override
    public List<String> getPrimaryArguments() {
        List<String> args = new ArrayList<>();
        args.add("none");
        args.add("half");
        args.add("full");
        return args;
    }

    @Override
    public List<String> getSecondaryArguments(String[] args) {
        return null;
    }
}
