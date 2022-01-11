package com.gmail.willramanand.RamSkills.stats;

import com.gmail.willramanand.RamSkills.RamSkills;
import com.gmail.willramanand.RamSkills.utils.ColorUtils;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class StatManager {

    private final RamSkills plugin;

    public StatManager(RamSkills plugin) {
        this.plugin = plugin;
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
        }
        Bukkit.getServer().getConsoleSender().sendMessage(ColorUtils.colorMessage("[" + plugin.getName() + "] " + "&2Loaded stat configuration."));

    }

    public void allocateStats(Player player) {
        plugin.getStatRegistry().allocate(player);
    }
}
