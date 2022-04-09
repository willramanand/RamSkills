package com.gmail.willramanand.RamSkills.commands;

import com.gmail.willramanand.RamSkills.RamSkills;
import com.gmail.willramanand.RamSkills.skills.Skill;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CmdSkillsXpSet extends SkillCommand {

    public CmdSkillsXpSet(RamSkills plugin) {
        super(plugin, true, false, "skills.xp", 3, 3);
        this.aliases.addAll(Arrays.asList("xpset", "setxp"));
    }

    @Override
    public void perform(CommandContext context) {
        Player player = context.argAsPlayer(0);
        Skill skill = context.argAsSkill(1);
        double amount = context.argAsDouble(2);

        if (player == null) {
            context.msg("{w}That is not a valid player!");
            return;
        }

        if (skill == null) {
            context.msg("{w}That is not a valid skill!");
            return;
        }

        if (amount < 0.0) {
            context.msg("{w}That is not a valid amount of xp!");
            return;
        }

        plugin.getLeveler().setXp(player, skill, amount);
        context.msg("{h}" + amount + " {s}XP was set to {h}" + player.getName() + " {s}in skill {h}" + skill.getDisplayName());
    }

    @Override
    public List<String> tabCompletes(CommandSender sender, String[] args) {
        if (args.length == 1) return tabCompletePlayers();
        if (args.length == 2) return tabCompleteSkills();
        if (args.length == 3) return Arrays.asList("100.0", "1000.0", "10000.0");
        return new ArrayList<>();
    }
}
