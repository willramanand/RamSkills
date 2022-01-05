package com.gmail.willramanand.RamSkills;

import com.gmail.willramanand.RamSkills.commands.CommandManager;
import com.gmail.willramanand.RamSkills.config.ConfigManager;
import com.gmail.willramanand.RamSkills.listeners.PlayerListener;
import com.gmail.willramanand.RamSkills.player.PlayerManager;
import com.gmail.willramanand.RamSkills.ui.SkillBossBar;
import com.gmail.willramanand.RamSkills.utils.ColorUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class RamSkills extends JavaPlugin {

    private static Logger log = Logger.getLogger("Minecraft");
    private static RamSkills i;
    private ConfigManager configManager;
    private PlayerManager playerManager;
    private CommandManager commandManager;
    private SkillBossBar bossBar;

    @Override
    public void onEnable() {
        i = this;

        long startTime = System.currentTimeMillis();
        log.info(ColorUtils.colorMessage("[" + this.getName() + "] &6===&b ENABLE START &6==="));

        configManager = new ConfigManager(this);
        playerManager = new PlayerManager(this);
        commandManager = new CommandManager(this);
        bossBar = new SkillBossBar(this);

        // Config
        this.getConfig().options().copyDefaults(true);
        this.saveConfig();

        // Listeners
        getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
        getServer().getPluginManager().registerEvents(bossBar, this);

        // Commands
        commandManager.setup();

        // Load Modules
        bossBar.load();

        startTime = System.currentTimeMillis() - startTime;
        log.info(ColorUtils.colorMessage("[" + this.getName() + "] &6=== &bENABLE &2COMPLETE &6(&eTook &d" + startTime +"ms&6) ==="));
    }

    @Override
    public void onDisable() {
        for (Player player : Bukkit.getOnlinePlayers())
            configManager.save(player, true);

        log.info("Disabled");
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public PlayerManager getPlayerManager() {
        return playerManager;
    }

    public CommandManager getCommandManager() { return commandManager; }

    public SkillBossBar getBossBar() { return bossBar; }
}
