package com.gmail.willramanand.RamSkills.commands;

import com.gmail.willramanand.RamSkills.RamSkills;
import com.gmail.willramanand.RamSkills.utils.Txt;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CmdSkillsVersion extends SkillCommand {

    public CmdSkillsVersion(RamSkills plugin) {
        super(plugin, true, false, 0, 0);
        this.aliases.addAll(Arrays.asList("version", "v"));
    }

    @Override
    public void perform(CommandContext context) {
        context.msg(Txt.header(plugin.getName().toUpperCase()));
        context.msg("&dAuthor: &eWillRam");
        context.msg("&dVersion: &e" + plugin.getDescription().getVersion());
        context.msg("&e" + plugin.getDescription().getDescription());
    }

    @Override
    public List<String> tabCompletes(CommandSender sender, String[] args) {
        return new ArrayList<>();
    }
}
