package com.gmail.willramanand.RamSkills.player;

import com.gmail.willramanand.RamSkills.RamSkills;
import com.gmail.willramanand.RamSkills.skills.Skill;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SkillPlayer {

    private final RamSkills plugin;

    private final Player player;
    private final UUID uuid;

    private final Map<Skill, Integer> skillsLvl;
    private final Map<Skill, Double> skillsXp;
    private final Map<Skill, Double> skillsXpModifers;

    private boolean saving;
    private boolean shouldSave;

    public SkillPlayer(RamSkills plugin, Player player) {
        this.plugin = plugin;
        this.player = player;
        this.uuid = this.player.getUniqueId();
        this.saving = false;
        this.shouldSave = true;
        this.skillsLvl = new HashMap<>();
        this.skillsXp = new HashMap<>();
        this.skillsXpModifers = new HashMap<>();
    }

    public Player getPlayer() {
        return player;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setSkillLevel(Skill skill, int lvl) {
        updateXPModifier(skill, lvl);
        this.skillsLvl.put(skill, lvl);
    }

    public Integer getSkillLevel(Skill skill) {
        return skillsLvl.getOrDefault(skill, 1);
    }

    public void setSkillXp(Skill skill, double xp) {
        this.skillsXp.put(skill, xp);
    }

    public Double getSkillXp(Skill skill) {
        return skillsXp.getOrDefault(skill, 0.0);
    }

    public void addSkillXp(Skill skill, double amount) {
        skillsXp.merge(skill, amount, Double::sum);
    }

    public boolean isSaving() {
        return saving;
    }

    public void setSaving(boolean saving) {
        this.saving = saving;
    }

    public boolean shouldNotSave() {
        return !shouldSave;
    }

    public void setShouldSave(boolean shouldSave) {
        this.shouldSave = shouldSave;
    }

    public void updateXPModifier(Skill skill, int level) {
        double modifier = 1 + (level * 0.02);
        this.skillsXpModifers.put(skill, modifier);
    }

    public double getXpModifier(Skill skill) {
        return this.skillsXpModifers.get(skill);
    }

}
