package com.gmail.willramanand.RamSkills.stats.stat;

import com.gmail.willramanand.RamSkills.RamSkills;
import com.gmail.willramanand.RamSkills.player.SkillPlayer;
import com.gmail.willramanand.RamSkills.stats.Stat;
import com.gmail.willramanand.RamSkills.utils.Formatter;
import org.bukkit.entity.Player;

public class AttackSpeed extends AbstractStat {

    public AttackSpeed(RamSkills plugin) {
        super(plugin, Stat.ATTACK_SPEED);
    }

    @Override
    public String print(Player player) {
        SkillPlayer skillPlayer = RamSkills.getInstance().getPlayerManager().getPlayerData(player);
        double points = skillPlayer.getStatPoint(Stat.ATTACK_SPEED);
        return "+ " + Formatter.decimalFormat(points, 1);
    }
}
