package com.gmail.willramanand.RamSkills.leveler;

import com.gmail.willramanand.RamSkills.RamSkills;
import com.gmail.willramanand.RamSkills.skills.Skill;
import com.gmail.willramanand.RamSkills.skills.Skills;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XpReqs {

    private final RamSkills plugin;
    private final Map<Skill, List<Integer>> skillXpRequirements;
    private final Map<Skill, Integer> skillXpBase;
    private final Map<Skill, Integer> skillXpMultiplier;
    private final Map<Skill, Integer> skillMaxLvls;

    public XpReqs(RamSkills plugin) {
        this.plugin = plugin;
        this.skillMaxLvls = new HashMap<>();
        this.skillXpRequirements = new HashMap<>();
        this.skillXpBase = new HashMap<>();
        this.skillXpMultiplier = new HashMap<>();
    }

    public void loadXpRequirements() {
        FileConfiguration config = plugin.getConfig();
        ConfigurationSection section = config.getConfigurationSection("xp_requirements");
        for (Skill skill : Skills.values()) {
            int lvl = section.getInt(skill.name().toLowerCase() + ".max_lvl");
            int mult = section.getInt(skill.name().toLowerCase() + ".multiplier");
            int base = section.getInt(skill.name().toLowerCase() + ".base");
            skillMaxLvls.put(skill, lvl);
            skillXpBase.put(skill, base);
            skillXpMultiplier.put(skill, mult);
        }
        addXpRequirements();
    }

    private void addXpRequirements() {
        for (Skill skill : Skills.values()) {
            List<Integer> skillList = new ArrayList<>();
            int base = skillXpBase.getOrDefault(skill, 100);
            int mult = skillXpMultiplier.getOrDefault(skill, 100);
            for (int i = 0; i < skillMaxLvls.get(skill) - 1; i++) {
                skillList.add(calculateXpforLevel(i, base, mult));
            }
            skillXpRequirements.put(skill, skillList);
        }
    }

    public int getXpRequired(Skill skill, int level) {
        List<Integer> skillList = skillXpRequirements.get(skill);
        if (skillList != null) {
            if (skillList.size() > level - 2) {
                return skillList.get(level - 2);
            } else {
                return 0;
            }
        }
        return 0;
    }

    public int getListSize(Skill skill) {
        List<Integer> skillList = skillXpRequirements.get(skill);
        if (skillList != null) {
            return skillList.size();
        }
        return 0;
    }

    public int calculateXpforLevel(int lvl, int base, int mult) {
        return (base * lvl) + mult;
    }

    public int getMaxLevel(Skill skill) {
        return skillMaxLvls.get(skill);
    }

}
