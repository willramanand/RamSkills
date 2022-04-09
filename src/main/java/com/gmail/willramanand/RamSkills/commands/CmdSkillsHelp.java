package com.gmail.willramanand.RamSkills.commands;

import com.gmail.willramanand.RamSkills.RamSkills;
import com.gmail.willramanand.RamSkills.utils.Txt;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CmdSkillsHelp extends SkillCommand {

    private final CmdSkillsRoot root;

    public CmdSkillsHelp(RamSkills plugin, CmdSkillsRoot root) {
        super(plugin, true, false, 0, 0);
        this.root = root;
        this.aliases.addAll(Arrays.asList("help", "h"));
    }

    @Override
    public void perform(CommandContext context) {
        context.msg(Txt.header("RAMSKILLS HELP"));
        for (SkillCommand subCommand : root.subCommands) {
            context.msg("{h}/skills " + subCommand.aliases.get(0) + " {s}" + subCommand.getHelpText());
        }
    }

    @Override
    public List<String> tabCompletes(CommandSender sender, String[] args) {
        return new ArrayList<>();
    }
}
