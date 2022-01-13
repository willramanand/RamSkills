package com.gmail.willramanand.RamSkills.stats.stat;

import com.gmail.willramanand.RamSkills.RamSkills;
import com.gmail.willramanand.RamSkills.player.SkillPlayer;
import com.gmail.willramanand.RamSkills.skills.Skill;
import com.gmail.willramanand.RamSkills.skills.Skills;
import com.gmail.willramanand.RamSkills.stats.Stat;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractStat {

    private final RamSkills plugin;
    private final Stat stat;

    private double base;
    private double pointsPerLevel;
    private final List<Skill> associatedSkills;


    public AbstractStat(RamSkills plugin, Stat stat) {
        this.plugin = plugin;
        this.stat = stat;
        this.associatedSkills = new ArrayList<>();
    }

    public void allocate(Player player) {
        SkillPlayer skillPlayer = plugin.getPlayerManager().getPlayerData(player);
        int skillLvls = 0;

        for (Skill skill : associatedSkills) {
            skillLvls += skillPlayer.getSkillLevel(skill) - 1;
        }

        double points = base + (skillLvls * pointsPerLevel);
        skillPlayer.setStatPoints(stat, points);
    }

    public void setAssociatedSkills(String[] skills) {
        for (int i = 0; i < skills.length; i++) {
            for (Skill skill : Skills.values()) {
                if (skills[i].equalsIgnoreCase(skill.getDisplayName()))
                    associatedSkills.add(skill);
            }
        }
    }

    public void setBase(double amount) {
        base = amount;
    }

    public void setPointsPerLevel(double amount) {
        pointsPerLevel = amount;
    }

    public abstract String print(Player player);
}
