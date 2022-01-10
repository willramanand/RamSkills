package com.gmail.willramanand.RamSkills.skills.sorcery;

import com.gmail.willramanand.RamSkills.RamSkills;
import com.gmail.willramanand.RamSkills.leveler.SkillLeveler;
import com.gmail.willramanand.RamSkills.skills.Skills;
import org.bukkit.entity.Player;

public class SorceryLeveler extends SkillLeveler {

    public SorceryLeveler(RamSkills plugin) {
        super(plugin, Skills.SORCERY);
    }

    public void level(Player player, double manaUsed) {
        plugin.getLeveler().addXp(player, Skills.SORCERY, manaUsed * getXp(player, SorcerySource.MANA_ABILITY_USE));
    }
}
