package com.gmail.willramanand.RamSkills.commands;

import com.gmail.willramanand.RamSkills.RamSkills;
import com.gmail.willramanand.RamSkills.skills.Skill;
import com.gmail.willramanand.RamSkills.skills.Skills;
import com.gmail.willramanand.RamSkills.utils.ColorUtils;
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
        if (player.hasPermission("skills.xp")) {
            if (args[1].equalsIgnoreCase("set")) {
                plugin.getLeveler().setXp(player, Skills.valueOf(args[2].toUpperCase()), Double.parseDouble(args[3]));
            } else if (args[1].equalsIgnoreCase("give")) {
                plugin.getLeveler().addXp(player, Skills.valueOf(args[2].toUpperCase()), Double.parseDouble(args[3]));
            }
        } else {
            player.sendMessage(ColorUtils.colorMessage("&4You do not have permission for that command!"));
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
        List<String> arg = new ArrayList<>();
        for (Skill skill : Skills.values()) {
            arg.add(skill.name().toLowerCase());
        }
        return arg;
    }
}
