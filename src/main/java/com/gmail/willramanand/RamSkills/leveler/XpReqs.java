package com.gmail.willramanand.RamSkills.leveler;

import com.gmail.willramanand.RamSkills.RamSkills;
import com.gmail.willramanand.RamSkills.skills.Skill;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import com.udojava.evalex.Expression;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XpReqs {

    private final RamSkills plugin;
    private final List<Integer> defaultXpRequirements;
    private final Map<Skill, List<Integer>> skillXpRequirements;
    private final int maxLevel = 50;

    public XpReqs(RamSkills plugin) {
        this.plugin = plugin;
        this.defaultXpRequirements = new ArrayList<>();
        this.skillXpRequirements = new HashMap<>();
    }

    public int getXpRequired(Skill skill, int level) {
        // Use skill specific xp requirements if exists
        List<Integer> skillList = skillXpRequirements.get(skill);
        if (skillList != null) {
            if (skillList.size() > level - 2) {
                return skillList.get(level - 2);
            } else {
                return 0;
            }
        }
        // Else use default
        if (defaultXpRequirements.size() > level - 2) {
            return defaultXpRequirements.get(level - 2);
        } else {
            return 0;
        }
    }

    public int getListSize(Skill skill) {
        List<Integer> skillList = skillXpRequirements.get(skill);
        if (skillList != null) {
            return skillList.size();
        }
        return defaultXpRequirements.size();
    }

    public void loadXpRequirements() {
        FileConfiguration config = plugin.getConfig();
        loadDefaultSection(config);
    }

    private void loadDefaultSection(FileConfiguration config) {
        ConfigurationSection section = config.getConfigurationSection("xp_requirements");
//        if (section != null) {
//            Expression expression = getXpExpression(section);
//            // Add xp requirement for each level
//            defaultXpRequirements.clear();
//            for (int i = 0; i < maxLevel; i++) {
//                expression.setVariable("level", BigDecimal.valueOf(i + 2));
//                defaultXpRequirements.add((int) Math.round(expression.eval().doubleValue()));
//            }
//        } else {
//            addDefaultXpRequirements();
//        }
        addDefaultXpRequirements();
    }

//    private Expression getXpExpression(ConfigurationSection section) {
//        String expressionString = section.getString("expression");
//        Expression expression = new Expression(expressionString);
//        // Set variables
//        for (String variable : section.getKeys(false)) {
//            if (variable.equals("expression")) continue;
//            double variableValue = section.getDouble(variable);
//            expression.setVariable(variable, BigDecimal.valueOf(variableValue));
//        }
//        return expression;
//    }

    private void addDefaultXpRequirements() {
        defaultXpRequirements.clear();
        for (int i = 0; i < maxLevel - 1; i++) {
            defaultXpRequirements.add((int) ((50.0 * i) + 100));
        }
    }

    public int getMaxLevel() {
        return maxLevel;
    }

}
