package com.gmail.willramanand.RamSkills.source;

import com.gmail.willramanand.RamSkills.RamSkills;
import com.gmail.willramanand.RamSkills.utils.ColorUtils;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class SourceManager {

    private final RamSkills plugin;
    private final Map<Source, Double> sources;

    public SourceManager(RamSkills plugin) {
        this.plugin = plugin;
        this.sources = new HashMap<>();
    }

    public void loadSources() {
        long start = System.currentTimeMillis();
        // Create file
        File file = new File(plugin.getDataFolder(), "sources_config.yml");
        if (!file.exists()) {
            plugin.saveResource("sources_config.yml", false);
        }
        // Load FileConfigurations
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        // Load sources
        int sourcesLoaded = 0;
        for (Source source : plugin.getSourceRegistry().values()) {
            String path = source.getPath();
            // Add if exists
            if (config.contains("sources." + path)) {
                sources.put(source, config.getDouble("sources." + path));
                sourcesLoaded++;
            }
            // Otherwise add default value and write to config
            else {
                RamSkills.logger().warning("[" + plugin.getName() + "] sources_config.yml is missing source of path sources." + path + ", value has been set to 5");
                sources.put(source, 5.0);
                config.set("sources." + path, 5.0);
                try {
                    config.save(file);
                    plugin.logger().info(ColorUtils.colorMessage("&2Added the new source!"));
                } catch (IOException exception) {
                    plugin.logger().info(ColorUtils.colorMessage("&4Failed to add new source!"));
                }
            }
        }
        RamSkills.logger().info(ColorUtils.colorMessage("[" + plugin.getName() + "] &eLoaded &d" + sourcesLoaded + "&e sources &d"
                + (System.currentTimeMillis() - start) + "&ems"));
    }

    public double getXp(Source source) {
        return sources.get(source);
    }
}
