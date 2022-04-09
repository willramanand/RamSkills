package com.gmail.willramanand.RamSkills.stats;

import com.gmail.willramanand.RamSkills.RamSkills;
import com.gmail.willramanand.RamSkills.utils.Txt;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class StatManager {

    private final RamSkills plugin;
    private final Map<Stat, Double> statBase;
    private final Map<Stat, Double> statPerLvl;

    public StatManager(RamSkills plugin) {
        this.plugin = plugin;
        this.statBase = new HashMap<>();
        this.statPerLvl = new HashMap<>();
    }

    public void loadStats() {
        FileConfiguration config = plugin.getConfig();
        ConfigurationSection section = config.getConfigurationSection("stat");

        for (Stat stat : Stat.values()) {
            String path = stat.getClazzName().toLowerCase() + ".";

            double baseAmount = section.getDouble(path + "base");
            double perAmount = section.getDouble(path + "perLvl");
            String[] skills = section.getString(path + "skills").split(",");

            plugin.getStatRegistry().setBase(stat, baseAmount);
            plugin.getStatRegistry().setPointsPerLvl(stat, perAmount);
            plugin.getStatRegistry().setAssociatedSkills(stat, skills);

            statBase.put(stat, baseAmount);
            statPerLvl.put(stat, perAmount);
        }
        Bukkit.getServer().getConsoleSender().sendMessage(Txt.parse("[" + plugin.getName() + "] " + "&2Loaded stat configuration."));

    }

    public void allocateStats(Player player) {
        plugin.getStatRegistry().allocate(player);
    }

    public double getStatBase(Stat stat) { return statBase.get(stat); }

    public double getStatPerLvl(Stat stat) { return statPerLvl.get(stat); }
}
