package com.gmail.willramanand.RamSkills.commands;

import com.gmail.willramanand.RamSkills.RamSkills;
import com.gmail.willramanand.RamSkills.skills.Skills;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class XpCommand extends SubCommand {

    private final RamSkills plugin;

    public XpCommand(RamSkills plugin) {
        this.plugin = plugin;
    }

    @Override
    public void onCommand(Player player, String[] args) {
        if (args[1].equalsIgnoreCase("set")) {
            player.sendMessage("set working");
            plugin.getLeveler().setXp(player, Skills.AGILITY, 100.0);
        } else if (args[1].equalsIgnoreCase("give")) {
            player.sendMessage("give working");
            plugin.getLeveler().addXp(player, Skills.AGILITY, 10000.0);
        }
    }

    @Override
    public String name() {
        return Commands.XP.getName();
    }

    @Override
    public String info() {
        return "Set and give player skill xp.";
    }

    @Override
    public List<String> aliases() {
        return null;
    }

    @Override
    public List<String> getPrimaryArguments() {
        List<String> args = new ArrayList<>();
        args.add("set");
        args.add("give");
        return args;
    }

    @Override
    public List<String> getSecondaryArguments(String[] args) {
        return null;
    }
}
