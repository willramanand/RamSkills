package com.gmail.willramanand.RamSkills.commands;

import com.gmail.willramanand.RamSkills.RamSkills;
import com.gmail.willramanand.RamSkills.ui.StatsScreen;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CmdSkillsStats extends SkillCommand {

    public CmdSkillsStats(RamSkills plugin) {
        super(plugin, true, true, 0, 0);
        this.aliases.addAll(Collections.singletonList("stats"));
    }

    @Override
    public void perform(CommandContext context) {
        StatsScreen statsScreen = new StatsScreen(plugin, context.player);
        context.player.openInventory(statsScreen.getInventory());
    }

    @Override
    public List<String> tabCompletes(CommandSender sender, String[] args) {
        return new ArrayList<>();
    }
}
