package com.gmail.willramanand.RamSkills.stats.stat;

import com.gmail.willramanand.RamSkills.RamSkills;
import com.gmail.willramanand.RamSkills.player.SkillPlayer;
import com.gmail.willramanand.RamSkills.stats.Stat;
import com.gmail.willramanand.RamSkills.utils.Formatter;
import org.bukkit.entity.Player;

public class Toughness extends AbstractStat {

    public Toughness(RamSkills plugin) {
        super(plugin, Stat.TOUGHNESS);
    }

    @Override
    public String print(Player player) {
        SkillPlayer skillPlayer = RamSkills.getInstance().getPlayerManager().getPlayerData(player);
        double points = (skillPlayer.getStatPoint(Stat.TOUGHNESS) / (skillPlayer.getStatPoint(Stat.TOUGHNESS) + 100));
        points = points * 100;
        return "+ " + Formatter.decimalFormat(points, 1) + "% Damage Reduction";
    }
}
