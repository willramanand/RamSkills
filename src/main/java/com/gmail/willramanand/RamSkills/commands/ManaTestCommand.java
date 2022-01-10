package com.gmail.willramanand.RamSkills.commands;

import com.gmail.willramanand.RamSkills.RamSkills;
import com.gmail.willramanand.RamSkills.player.SkillPlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ManaTestCommand extends SubCommand {

    private final RamSkills plugin;

    public ManaTestCommand(RamSkills plugin) {
        this.plugin = plugin;
    }


    @Override
    public void onCommand(Player player, String[] args) {
        SkillPlayer skillPlayer = plugin.getPlayerManager().getPlayerData(player);
        skillPlayer.updateMana(-40);
        plugin.getSorceryLeveler().level(player, 40);
    }

    @Override
    public String name() {
        return Commands.MANA.getName();
    }

    @Override
    public String info() {
        return null;
    }

    @Override
    public List<String> aliases() {
        return new ArrayList<>();
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
