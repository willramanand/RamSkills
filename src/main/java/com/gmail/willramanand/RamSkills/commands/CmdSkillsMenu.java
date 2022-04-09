package com.gmail.willramanand.RamSkills.commands;

import com.gmail.willramanand.RamSkills.RamSkills;
import com.gmail.willramanand.RamSkills.ui.SkillsScreen;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CmdSkillsMenu extends SkillCommand {

    public CmdSkillsMenu(RamSkills plugin) {
        super(plugin, true, true, 0, 0);
        this.aliases.addAll(Arrays.asList("menu", "m"));
    }

    @Override
    public void perform(CommandContext context) {
        SkillsScreen skillsScreen = new SkillsScreen(plugin, context.player);
        context.player.openInventory(skillsScreen.getInventory());
    }

    @Override
    public List<String> tabCompletes(CommandSender sender, String[] args) {
        return new ArrayList<>();
    }
}
