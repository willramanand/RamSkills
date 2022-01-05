package com.gmail.willramanand.RamSkills.config;

import com.gmail.willramanand.RamSkills.RamSkills;
import com.gmail.willramanand.RamSkills.events.PlayerDataLoadEvent;
import com.gmail.willramanand.RamSkills.player.SkillPlayer;
import com.gmail.willramanand.RamSkills.skills.Skill;
import com.gmail.willramanand.RamSkills.skills.Skills;
import com.gmail.willramanand.RamSkills.utils.ColorUtils;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class ConfigManager {

    private final RamSkills plugin;
    public ConfigManager(RamSkills plugin) {
        this.plugin = plugin;
    }

    public void setup(Player player) {
        File dir = new File(plugin.getDataFolder() + "/playerdata/");
        if (!dir.exists()) {
            dir.mkdir();
        }

        File file = new File(plugin.getDataFolder() + "/playerdata/" + player.getUniqueId() + ".yml");

        if (!file.exists()) {
            try {
                file.createNewFile();
                Bukkit.getServer().getConsoleSender().sendMessage(ColorUtils.colorMessage("&2Created player config for UUID: " + player.getUniqueId()));

                FileConfiguration config = YamlConfiguration.loadConfiguration(file);
                SkillPlayer skillPlayer = new SkillPlayer(plugin, player);

                if (file.exists()) {
                    for (Skill skill : Skills.values()) {
                        String path = "skills." + skill.name().toLowerCase() + ".";

                        config.set(path + "lvl", 1);
                        config.set(path + "xp", 0.0);
                        skillPlayer.setSkillsLevel(skill, 1);
                        skillPlayer.setSkillsXp(skill, 0.0);
                    }
                    plugin.getPlayerManager().addPlayerData(skillPlayer);
                    try {
                        config.save(file);
                    } catch (IOException e) {
                        Bukkit.getServer().getConsoleSender().sendMessage(ColorUtils.colorMessage("&4Could not save player config for UUID: " + player.getUniqueId()));
                    }
                }
            } catch (IOException e) {
                Bukkit.getServer().getConsoleSender().sendMessage(ColorUtils.colorMessage("&4Could not create player config for UUID: " + player.getUniqueId()));
            }
        }
    }

    public void load(Player player) {
        File file = new File(plugin.getDataFolder() + "/playerdata/" + player.getUniqueId() + ".yml");

        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        SkillPlayer skillPlayer = new SkillPlayer(plugin, player);

        if (file.exists()) {
            for (Skill skill : Skills.values()) {
                String path = "skills." + skill.name().toLowerCase() + ".";
                int skillLvl = config.getInt(path + "lvl");
                double skillXp = config.getInt(path + "xp");
                skillPlayer.setSkillsLevel(skill, skillLvl);
                skillPlayer.setSkillsXp(skill, skillXp);
            }
            plugin.getPlayerManager().addPlayerData(skillPlayer);
            PlayerDataLoadEvent playerDataLoadEvent = new PlayerDataLoadEvent(skillPlayer);
        } else {
            Bukkit.getServer().getConsoleSender().sendMessage(ColorUtils.colorMessage("&bCould not load player config for UUID: " + player.getUniqueId()));
            setup(player);
        }
    }

    public void save(Player player, boolean isShutdown) {
        File file = new File(plugin.getDataFolder() + "/playerdata/" + player.getUniqueId() + ".yml");
        SkillPlayer skillPlayer = plugin.getPlayerManager().getPlayerData(player);

        if (skillPlayer == null) return;
        if (skillPlayer.shouldNotSave()) return;
        if (skillPlayer.isSaving()) return;
        skillPlayer.setSaving(true);

        if (file.exists()) {
            FileConfiguration config = YamlConfiguration.loadConfiguration(file);
            try {
                for (Skill skill : Skills.values()) {
                    String path = "skills." + skill.name().toLowerCase() + ".";
                    config.set(path + "lvl", 1);
                    config.set(path + "xp", 0.0);
                }
                config.save(file);
                if (isShutdown) {
                    plugin.getPlayerManager().removePlayerData(player.getUniqueId());
                }
            } catch (IOException e) {
                Bukkit.getServer().getConsoleSender().sendMessage(ColorUtils.colorMessage("&bCould not save player config for UUID: " + player.getUniqueId()));
            }
        } else {
            Bukkit.getServer().getConsoleSender().sendMessage(ColorUtils.colorMessage("&bCould not save player config for UUID: " + player.getUniqueId() + " because it does not exist!"));
        }
        skillPlayer.setSaving(false);
    }

}
