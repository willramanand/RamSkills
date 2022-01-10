package com.gmail.willramanand.RamSkills.commands;

import com.gmail.willramanand.RamSkills.RamSkills;
import com.gmail.willramanand.RamSkills.utils.ColorUtils;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class HelpCommand extends SubCommand {

    private final RamSkills plugin;

    public HelpCommand(RamSkills plugin) {
        this.plugin = plugin;
    }

    @Override
    public void onCommand(Player player, String[] args) {

        List<SubCommand> commands = plugin.getCommandManager().getCommandList();
        String prefix = Commands.MAIN.getName();

        player.sendMessage(ColorUtils.colorMessage("&6---- &b" + plugin.getName() + " Help &6----"));

        commands.forEach(command ->{
                String commandAlias = command.name();
                if (command.aliases() != null) {
                    for (String alias : command.aliases()) {
                        commandAlias += "&6,&b" + alias;
                    }
                }
                player.sendMessage(ColorUtils.colorMessage("&b/" + prefix + " " + commandAlias + " &e" + command.info()));
        });
    }

    @Override
    public String name() {
        return Commands.HELP.getName();
    }

    @Override
    public String info() {
        return "Information on the commands of RamSkills.";
    }

    @Override
    public List<String> aliases() {
        List<String> alias = new ArrayList<>();
        alias.add("h");
        return alias;
    }

    @Override
    public List<String> getPrimaryArguments() {
        return new ArrayList<>();
    }

    @Override
    public List<String> getSecondaryArguments(String[] args) {
        return new ArrayList<>();
    }

}
