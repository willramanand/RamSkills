package com.gmail.willramanand.RamSkills.stats.stat;

import com.gmail.willramanand.RamSkills.RamSkills;
import com.gmail.willramanand.RamSkills.player.SkillPlayer;
import com.gmail.willramanand.RamSkills.stats.Stat;
import com.gmail.willramanand.RamSkills.utils.Formatter;
import org.bukkit.entity.Player;

public class CritDamage extends AbstractStat {

    public CritDamage(RamSkills plugin) {
        super(plugin, Stat.CRIT_DAMAGE);
    }

    @Override
    public String print(Player player) {
        SkillPlayer skillPlayer = RamSkills.getInstance().getPlayerManager().getPlayerData(player);
        double points = skillPlayer.getStatPoint(Stat.CRIT_DAMAGE);
        return "+ " + Formatter.decimalFormat(points, 1) + "%";
    }
}
