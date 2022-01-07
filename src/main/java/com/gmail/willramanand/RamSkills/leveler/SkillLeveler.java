package com.gmail.willramanand.RamSkills.leveler;

import com.gmail.willramanand.RamSkills.RamSkills;
import com.gmail.willramanand.RamSkills.skills.Skill;
import com.gmail.willramanand.RamSkills.source.Source;
import com.gmail.willramanand.RamSkills.source.SourceManager;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import java.util.Locale;

public class SkillLeveler {

    protected final RamSkills plugin;
    private final SourceManager sourceManager;
    private final String skillName;

    public SkillLeveler(RamSkills plugin, Skill skill) {
        this.plugin = plugin;
        this.skillName = skill.toString().toLowerCase();
        this.sourceManager = plugin.getSourceManager();
    }

    public double getXp(Player player, Source source) {
        return sourceManager.getXp(source);
    }

    public boolean blockXpGainPlayer(Player player) {
        //Check creative mode disable
        if (player.getGameMode().equals(GameMode.CREATIVE)) {
            return true;
        }
        return false;
    }
}
