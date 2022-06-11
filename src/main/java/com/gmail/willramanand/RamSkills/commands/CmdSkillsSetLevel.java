package com.gmail.willramanand.RamSkills.commands;

import com.gmail.willramanand.RamSkills.RamSkills;
import com.gmail.willramanand.RamSkills.events.SkillLevelUpEvent;
import com.gmail.willramanand.RamSkills.player.SkillPlayer;
import com.gmail.willramanand.RamSkills.skills.Skill;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CmdSkillsSetLevel extends SkillCommand {

    public CmdSkillsSetLevel(RamSkills plugin) {
        super(plugin, true, false, "skills.lvl", 3, 3);
        this.aliases.add("setlevel");
        this.helpText = "Allows admins to set the level of skills for players.";
    }

    @Override
    public void perform(CommandContext context) {
        SkillPlayer skillPlayer = context.argAsSkillPlayer(0);
        Skill skill = context.argAsSkill(1);
        int lvl = context.argAsInt(2);

        if (skillPlayer == null) {
            context.msg("{w}That is not a valid player!");
            return;
        }

        if (skill == null) {
            context.msg("{w}That is not a valid skill!");
            return;
        }

        if (lvl < 1 || plugin.getLeveler().getXpRequirements().getMaxLevel(skill) < lvl) {
            context.msg("{w}That is not a valid level!");
            return;
        }

        skillPlayer.setSkillLevel(skill, lvl);
        skillPlayer.setSkillXp(skill, 0);
        context.msg("{h}" + skillPlayer.getPlayer().getName() + " {s}has had skill {h}" + skill.getDisplayName() + " {s}set to lvl {h}" + lvl);
    }

    @Override
    public List<String> tabCompletes(CommandSender sender, String[] args) {
        if (args.length == 1) return tabCompletePlayers();
        if (args.length == 2) return tabCompleteSkills();
        if (args.length == 3) return Arrays.asList("1", "10", "20");
        return new ArrayList<>();
    }
}
