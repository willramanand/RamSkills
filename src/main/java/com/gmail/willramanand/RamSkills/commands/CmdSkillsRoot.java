package com.gmail.willramanand.RamSkills.commands;

import com.gmail.willramanand.RamSkills.RamSkills;
import com.gmail.willramanand.RamSkills.ui.SkillsScreen;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CmdSkillsRoot extends SkillCommand {

    private final CmdSkillsHelp cmdSkillsHelp;
    private final CmdSkillsVersion cmdSkillsVersion;
    private final CmdSkillsMenu cmdSkillsMenu;
    private final CmdSkillsXpEvent cmdSkillsXpEvent;
    private final CmdSkillsStats cmdSkillsStats;
    private final CmdSkillsSetLevel cmdSkillsSetLevel;
    private final CmdSkillsXpGive cmdSkillsXpGive;
    private final CmdSkillsXpSet cmdSkillsXpSet;

    public CmdSkillsRoot(RamSkills plugin) {
        super(plugin, true, false, 0, -1);

        cmdSkillsHelp = new CmdSkillsHelp(plugin, this);
        cmdSkillsVersion = new CmdSkillsVersion(plugin);
        cmdSkillsMenu = new CmdSkillsMenu(plugin);
        cmdSkillsXpEvent = new CmdSkillsXpEvent(plugin);
        cmdSkillsStats = new CmdSkillsStats(plugin);
        cmdSkillsSetLevel = new CmdSkillsSetLevel(plugin);
        cmdSkillsXpGive = new CmdSkillsXpGive(plugin);
        cmdSkillsXpSet = new CmdSkillsXpSet(plugin);

        this.subCommands.add(cmdSkillsHelp);
        this.subCommands.add(cmdSkillsVersion);
        this.subCommands.add(cmdSkillsMenu);
        this.subCommands.add(cmdSkillsXpEvent);
        this.subCommands.add(cmdSkillsStats);
        this.subCommands.add(cmdSkillsSetLevel);
        this.subCommands.add(cmdSkillsXpGive);
        this.subCommands.add(cmdSkillsXpSet);
    }

    @Override
    public void perform(CommandContext context) {
        context.commandChain.add(this);
        this.cmdSkillsHelp.execute(context);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        this.execute(new CommandContext(sender, command, new ArrayList<>(Arrays.asList(args)), label));
        return true;
    }
    @Override
    public List<String> tabCompletes(CommandSender sender, String[] args) {

        if (args.length == 1) {
            List<String> allowAliases = new ArrayList<>();
            for (SkillCommand subCommand : subCommands) {
                if (subCommand.getPermission() == null) {
                    allowAliases.addAll(subCommand.aliases);
                } else if (sender.hasPermission(subCommand.getPermission())) {
                    allowAliases.addAll(subCommand.aliases);
                }
            }
            return allowAliases;
        }

        if (args.length >= 2) {
            for (SkillCommand subCommand : subCommands) {
                if (subCommand.aliases.contains(args[0])) {
                    String[] modifiedArgs = Arrays.copyOfRange(args, 1, args.length);
                    return subCommand.tabCompletes(sender, modifiedArgs);
                }
            }
        }
        return new ArrayList<>();
    }
}
