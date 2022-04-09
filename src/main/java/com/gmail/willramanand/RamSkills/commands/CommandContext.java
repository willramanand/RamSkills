package com.gmail.willramanand.RamSkills.commands;

import com.gmail.willramanand.RamSkills.RamSkills;
import com.gmail.willramanand.RamSkills.player.SkillPlayer;
import com.gmail.willramanand.RamSkills.skills.Skill;
import com.gmail.willramanand.RamSkills.skills.Skills;
import com.gmail.willramanand.RamSkills.utils.Txt;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CommandContext {

    public Command command;
    public CommandSender sender;

    public Player player;
    public SkillPlayer skillPlayer;

    public List<String> args;
    public String alias;

    public List<SkillCommand> commandChain = new ArrayList<>();

    public CommandContext(CommandSender sender, Command command, List<String> args, String alias) {
        this.sender = sender;
        this.command = command;
        this.args = args;
        this.alias = alias;

        if (sender instanceof Player) {
            this.player = (Player) sender;
            this.skillPlayer = RamSkills.getInstance().getPlayerManager().getPlayerData(player);
        }
    }

    public void msg(String message) {
        sender.sendMessage(Txt.parse(message));
    }

    public void msg(String... messages) {
        sender.sendMessage(Txt.parse(messages));
    }

    public void msg(TextComponent component) {
        sender.sendMessage(component);
    }

    public void msgOther(Player player, String message) {
        player.sendMessage(Txt.parse(message));
    }

    public void msgOther(Player player, String... messages) {
        player.sendMessage(Txt.parse(messages));
    }

    public void msgOther(Player player, TextComponent component) {
        player.sendMessage(component);
    }


    // Check is set
    public boolean argIsSet(int index) {
        return args.size() >= index + 1;
    }

    // String args
    public String argAsString(int index, String def) {
        if (args.size() < index + 1) {
            return def;
        }
        return args.get(index);
    }

    public String argAsString(int index) {
        return argAsString(index, null);
    }

    public String argAsConcatString(int index, String def) {
        if (args.size() < index + 1) {
            return def;
        }

        StringBuilder concat = new StringBuilder();

        for (int i = index; i < args.size(); i++) {
            concat.append(args.get(i)).append(" ");
        }
        return concat.toString();
    }

    // Int args
    public Integer strAsInt(String str, Integer def) {
        if (str == null) {
            return def;
        }
        try {
            return Integer.parseInt(str);
        } catch (Exception e) {
            return def;
        }
    }

    public Integer argAsInt(int index, Integer def) {
        return strAsInt(argAsString(index), def);
    }

    public Integer argAsInt(int index) {
        return argAsInt(index, null);
    }

    // Double args
    public Double strAsDouble(String str, Double def) {
        if (str == null) {
            return def;
        }
        try {
            return Double.parseDouble(str);
        } catch (Exception e) {
            return def;
        }
    }

    public Double argAsDouble(int index, Double def) {
        return strAsDouble(argAsString(index), def);
    }

    public Double argAsDouble(int index) {
        return argAsDouble(index, null);
    }

    // Float args
    public Float strAsFloat(String str, Float def) {
        if (str == null) {
            return def;
        }
        try {
            return Float.parseFloat(str);
        } catch (Exception e) {
            return def;
        }
    }

    public Float argAsFloat(int index, Float def) {
        return strAsFloat(argAsString(index), def);
    }

    public Float argAsFloat(int index) {
        return argAsFloat(index, null);
    }

    // Player args
    public Player strAsPlayer(String str, Player def) {
        if (str == null) {
            return def;
        }

        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.getName().equals(str)) return player;
        }
        return def;
    }

    public Player argAsPlayer(int index, Player def) {
        return strAsPlayer(argAsString(index), def);
    }

    public Player argAsPlayer(int index) {
        return argAsPlayer(index, null);
    }

    // OfflinePlayer args
    public OfflinePlayer strAsOffPlayer(String str, OfflinePlayer def) {
        if (str == null) {
            return def;
        }

        for (OfflinePlayer player : Bukkit.getOfflinePlayers()) {
            if (player.getName().equals(str)) return player;
        }
        return def;
    }

    public OfflinePlayer argAsOffPlayer(int index, OfflinePlayer def) {
        return strAsOffPlayer(argAsString(index), def);
    }

    public OfflinePlayer argAsOffPlayer(int index) {
        return argAsOffPlayer(index, null);
    }

    // EPlayer args
    public SkillPlayer playerToSkillPlayer(Player player, SkillPlayer def) {
        if (player == null) {
            return def;
        }

        return RamSkills.getInstance().getPlayerManager().getPlayerData(player);
    }

    public SkillPlayer argAsSkillPlayer(int index, SkillPlayer def) {
        return playerToSkillPlayer(argAsPlayer(index), def);
    }

    public SkillPlayer argAsSkillPlayer(int index) {
        return argAsSkillPlayer(index, null);
    }

    public Skill strAsSkill(String str, Skill def) {
        if (str == null) {
            return def;
        }

        for (Skill skill : Skills.values()) {
            if (skill.name().equalsIgnoreCase(str)) return skill;
        }
        return def;
    }

    public Skill argAsSkill(int index, Skill def) {
        return strAsSkill(argAsString(index), def);
    }

    public Skill argAsSkill(int index) {
        return argAsSkill(index, null);
    }

    // World args
    public World strAsWorld(String str, World def) {
        if (str == null) {
            return def;
        }
        for (World world : Bukkit.getWorlds()) {
            if (world.getName().equalsIgnoreCase(str)) return world;
        }
        return def;
    }

    public World argAsWorld(int index, World def) {
        return strAsWorld(argAsString(index), def);
    }

    public World argAsWorld(int index) {
        return argAsWorld(index, null);
    }
}
