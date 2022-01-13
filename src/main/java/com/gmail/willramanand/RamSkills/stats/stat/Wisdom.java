package com.gmail.willramanand.RamSkills.stats.stat;

import com.gmail.willramanand.RamSkills.RamSkills;
import com.gmail.willramanand.RamSkills.player.SkillPlayer;
import com.gmail.willramanand.RamSkills.stats.Stat;
import com.gmail.willramanand.RamSkills.utils.Formatter;
import org.bukkit.entity.Player;

public class Wisdom extends AbstractStat {

    public Wisdom(RamSkills plugin) {
        super(plugin, Stat.WISDOM);
    }

    @Override
    public String print(Player player) {
        SkillPlayer skillPlayer = RamSkills.getInstance().getPlayerManager().getPlayerData(player);
        double points = skillPlayer.getStatPoint(Stat.WISDOM) - RamSkills.getInstance().getStatManager().getStatBase(Stat.WISDOM);
        return "+ " + Formatter.decimalFormat(points, 1) + " Mana";
    }
}
