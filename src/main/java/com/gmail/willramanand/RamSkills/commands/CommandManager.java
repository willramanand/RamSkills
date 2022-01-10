package com.gmail.willramanand.RamSkills.commands;

import com.gmail.willramanand.RamSkills.RamSkills;
import com.gmail.willramanand.RamSkills.utils.ColorUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class CommandManager implements TabExecutor {
    private ArrayList<SubCommand> commands = new ArrayList<>();
    private final RamSkills plugin;

    public CommandManager(RamSkills plugin) {
        this.plugin = plugin;
    }

    public void setup() {
        plugin.getCommand(Commands.MAIN.getName()).setExecutor(this);

        this.commands.add(new HelpCommand(plugin));
        this.commands.add(new VersionCommand(plugin));
        this.commands.add(new XpCommand(plugin));
        this.commands.add(new ManaTestCommand(plugin));
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

        if (!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;

        if (command.getName().equalsIgnoreCase(Commands.MAIN.getName())) {
            if (args.length == 0) {
                player.performCommand("/rskills h");
                return true;
            }

            SubCommand target = this.get(args[0]);

            if (target == null) {
                player.sendMessage(ColorUtils.colorMessage("&eInvalid subcommand!"));
                player.performCommand("/rskills h");
                return true;
            }

            ArrayList<String> arrayList = new ArrayList<>(Arrays.asList(args));
            arrayList.remove(0);

            try {
                target.onCommand(player, args);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return true;
    }

    public List<SubCommand> getCommandList() {
        return this.commands;
    }

    private SubCommand get(String name) {

        for (SubCommand subCommand : this.commands) {
            if (subCommand.name().equalsIgnoreCase(name)) {
                return subCommand;
            }

            List<String> aliases = subCommand.aliases();

            if (aliases != null) {
                for (String alias : aliases) {
                    if (name.equalsIgnoreCase(alias)) {
                        return subCommand;
                    }
                }
            }
        }
        return null;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        Iterator<SubCommand> sc = this.commands.iterator();

        if (args.length == 1) {
            List<String> primary = new ArrayList<>();

            while (sc.hasNext()) {
                SubCommand subCommand = sc.next();
                primary.add(subCommand.name());

            }
            return primary;
        }
        if (args.length == 2) {
            while (sc.hasNext()) {
                SubCommand subCommand = sc.next();

                if (subCommand.name().equalsIgnoreCase(args[0]) || subCommand.aliases().contains(args[0])) {
                    return subCommand.getPrimaryArguments();
                }
            }
        } else if (args.length == 3) {
            while (sc.hasNext()) {
                SubCommand subCommand = sc.next();

                if (subCommand.name().equalsIgnoreCase(args[0]) || subCommand.aliases().contains(args[0])) {
                    return subCommand.getSecondaryArguments(args);
                }
            }
        }
        return null;
    }
}