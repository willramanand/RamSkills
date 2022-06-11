package com.gmail.willramanand.RamSkills.commands;

import com.gmail.willramanand.RamSkills.RamSkills;
import com.gmail.willramanand.RamSkills.utils.XpModifierUtil;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CmdSkillsXpEvent extends SkillCommand {

    public CmdSkillsXpEvent(RamSkills plugin) {
        super(plugin, true, false, "skills.xpevent", 1, 1);
        this.aliases.addAll(Collections.singletonList("xpevent"));
        this.helpText = "Allows admins to create xp events.";
    }

    @Override
    public void perform(CommandContext context) {
        int modifier = context.argAsInt(0);
        if (modifier == 1) {
            context.msg("{w}Disabled {s}exp event!");
        } else if (modifier > 1) {
            context.msg("{green}Enabled {s}xp event with modifier of {h}" + modifier);
        }
        XpModifierUtil.setModifier(modifier);
    }

    @Override
    public List<String> tabCompletes(CommandSender sender, String[] args) {
        if (args.length == 1) return Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10");
        return new ArrayList<>();
    }
}
